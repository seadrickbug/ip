#!/usr/bin/env python3
import argparse
import re
import shutil
import subprocess
import sys
from dataclasses import dataclass
from pathlib import Path


@dataclass
class TestCase:
    name: str
    aim: str
    inputs: str
    expected: str


def normalize_output(text):
    lines = text.replace("\r\n", "\n").replace("\r", "\n").splitlines()
    return "\n".join(line.rstrip() for line in lines).strip()


def extract_block(section, label):
    pattern = rf"{label}:\s*```(?:text)?\n(.*?)\n```"
    match = re.search(pattern, section, re.DOTALL)
    if not match:
        raise ValueError(f"Missing fenced block for {label}")
    return match.group(1)


def parse_plan(plan_path):
    text = plan_path.read_text()
    matches = list(re.finditer(r"^## Test Case:\s*(.+)$", text, re.MULTILINE))
    test_cases = []
    for index, match in enumerate(matches):
        start = match.end()
        end = matches[index + 1].start() if index + 1 < len(matches) else len(text)
        section = text[start:end]
        name = match.group(1).strip()
        aim_match = re.search(r"^Aim:\s*(.+)$", section, re.MULTILINE)
        if not aim_match:
            raise ValueError(f"Missing aim for test case: {name}")
        test_cases.append(
            TestCase(
                name=name,
                aim=aim_match.group(1).strip(),
                inputs=extract_block(section, "Inputs"),
                expected=extract_block(section, "Expected output"),
            )
        )
    if not test_cases:
        raise ValueError("No test cases found")
    return test_cases


def compile_sources(project_root, build_dir):
    source_files = sorted((project_root / "src/main/java").glob("*.java"))
    if not source_files:
        raise ValueError("No Java source files found in src/main/java")
    if build_dir.exists():
        shutil.rmtree(build_dir)
    build_dir.mkdir(parents=True)
    subprocess.run(
        ["javac", "-d", str(build_dir)] + [str(path) for path in source_files],
        cwd=project_root,
        check=True,
    )


def run_case(project_root, build_dir, main_class, test_case):
    input_text = test_case.inputs
    if input_text and not input_text.endswith("\n"):
        input_text += "\n"
    return subprocess.run(
        ["java", "-cp", str(build_dir), main_class],
        cwd=project_root,
        input=input_text,
        text=True,
        capture_output=True,
        check=False,
    )


def print_transcript(test_case, actual):
    print(f"=== Test Case: {test_case.name} ===")
    print(f"Aim: {test_case.aim}")
    print("--- Console input ---")
    print(test_case.inputs)
    print("--- Console output ---")
    print(actual.rstrip())
    print()


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--plan", default="test/ui-test-plan.md")
    parser.add_argument("--main-class", default="Duke")
    parser.add_argument("--build-dir", default="/tmp/cs2103-ip-ui-tests")
    args = parser.parse_args()

    project_root = Path.cwd()
    plan_path = project_root / args.plan
    build_dir = Path(args.build_dir)

    test_cases = parse_plan(plan_path)
    compile_sources(project_root, build_dir)

    for test_case in test_cases:
        result = run_case(project_root, build_dir, args.main_class, test_case)
        actual = result.stdout
        if result.stderr:
            actual += "\n[stderr]\n" + result.stderr
        if result.returncode != 0 or normalize_output(actual) != normalize_output(test_case.expected):
            print(f"FAILED: {test_case.name}")
            print(f"Aim: {test_case.aim}")
            print("--- Console input ---")
            print(test_case.inputs)
            print("--- Expected output ---")
            print(test_case.expected.rstrip())
            print("--- Actual output ---")
            print(actual.rstrip())
            sys.exit(1)
        print_transcript(test_case, actual)

    print(f"PASS: {len(test_cases)} UI test case(s) passed.")


if __name__ == "__main__":
    main()
