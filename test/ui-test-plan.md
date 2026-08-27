# UI Test Plan

## Test Case: Exit On Bye

Aim: Verify that the chatbot greets the user and exits when the user enters `bye`.

Inputs:
```text
bye
```

Expected output:
```text
____________________________________________________________
  ____                            _        _
 / ___|___  _ __ ___  _ __  _   _| |_ __ _| |__
| |   / _ \| '_ ` _ \| '_ \| | | | __/ _` | '_ \
| |__| (_) | | | | | | |_) | |_| | || (_| | | | |
 \____\___/|_| |_| |_| .__/ \__,_|\__\__,_|_| |_|
                     |_|

Hello! I'm Computah.
What can I do for you?
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test Case: Add And List ToDos

Aim: Verify that `todo` commands are stored as to-do tasks and displayed when `list` is entered.

Inputs:
```text
todo read book
todo return book
list
bye
```

Expected output:
```text
____________________________________________________________
  ____                            _        _
 / ___|___  _ __ ___  _ __  _   _| |_ __ _| |__
| |   / _ \| '_ ` _ \| '_ \| | | | __/ _` | '_ \
| |__| (_) | | | | | | |_) | |_| | || (_| | | | |
 \____\___/|_| |_| |_| .__/ \__,_|\__\__,_|_| |_|
                     |_|

Hello! I'm Computah.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] return book
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
2.[T][ ] return book
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test Case: Add Typed Tasks

Aim: Verify that `todo`, `deadline`, and `event` commands create tasks with the correct type icons and details.

Inputs:
```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
list
bye
```

Expected output:
```text
____________________________________________________________
  ____                            _        _
 / ___|___  _ __ ___  _ __  _   _| |_ __ _| |__
| |   / _ \| '_ ` _ \| '_ \| | | | __/ _` | '_ \
| |__| (_) | | | | | | |_) | |_| | || (_| | | | |
 \____\___/|_| |_| |_| .__/ \__,_|\__\__,_|_| |_|
                     |_|

Hello! I'm Computah.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] borrow book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: Sunday)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] borrow book
2.[D][ ] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test Case: Mark And Unmark Tasks

Aim: Verify that `mark` and `unmark` update task done status in subsequent `list` output.

Inputs:
```text
todo read book
todo return book
todo buy bread
mark 1
mark 2
list
unmark 2
list
bye
```

Expected output:
```text
____________________________________________________________
  ____                            _        _
 / ___|___  _ __ ___  _ __  _   _| |_ __ _| |__
| |   / _ \| '_ ` _ \| '_ \| | | | __/ _` | '_ \
| |__| (_) | | | | | | |_) | |_| | || (_| | | | |
 \____\___/|_| |_| |_| .__/ \__,_|\__\__,_|_| |_|
                     |_|

Hello! I'm Computah.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] return book
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] buy bread
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] read book
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] return book
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][X] read book
2.[T][X] return book
3.[T][ ] buy bread
____________________________________________________________
____________________________________________________________
OK, I've marked this task as not done yet:
  [T][ ] return book
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][X] read book
2.[T][ ] return book
3.[T][ ] buy bread
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test Case: Delete Task

Aim: Verify that `delete` removes the specified task and the remaining tasks are renumbered.

Inputs:
```text
todo read book
deadline return book /by June 6th
event project meeting /from Aug 6th 2pm /to 4pm
todo join sports club
todo borrow book
mark 1
mark 2
mark 4
list
delete 3
list
bye
```

Expected output:
```text
____________________________________________________________
  ____                            _        _
 / ___|___  _ __ ___  _ __  _   _| |_ __ _| |__
| |   / _ \| '_ ` _ \| '_ \| | | | __/ _` | '_ \
| |__| (_) | | | | | | |_) | |_| | || (_| | | | |
 \____\___/|_| |_| |_| .__/ \__,_|\__\__,_|_| |_|
                     |_|

Hello! I'm Computah.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: June 6th)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] join sports club
Now you have 4 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] borrow book
Now you have 5 tasks in the list.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] read book
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [D][X] return book (by: June 6th)
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] join sports club
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][X] read book
2.[D][X] return book (by: June 6th)
3.[E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
4.[T][X] join sports club
5.[T][ ] borrow book
____________________________________________________________
____________________________________________________________
Noted. I've removed this task:
  [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
Now you have 4 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][X] read book
2.[D][X] return book (by: June 6th)
3.[T][X] join sports club
4.[T][ ] borrow book
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test Case: Save Tasks To File

Aim: Verify that the chatbot writes the current task list to `data/duke.txt` after task list changes.

Inputs:
```text
todo read book
deadline return book /by June 6th
event project meeting /from Aug 6th 2pm /to 4pm
todo join sports club
mark 1
delete 2
bye
```

Expected output:
```text
____________________________________________________________
  ____                            _        _
 / ___|___  _ __ ___  _ __  _   _| |_ __ _| |__
| |   / _ \| '_ ` _ \| '_ \| | | | __/ _` | '_ \
| |__| (_) | | | | | | |_) | |_| | || (_| | | | |
 \____\___/|_| |_| |_| .__/ \__,_|\__\__,_|_| |_|
                     |_|

Hello! I'm Computah.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: June 6th)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] join sports club
Now you have 4 tasks in the list.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] read book
____________________________________________________________
____________________________________________________________
Noted. I've removed this task:
  [D][ ] return book (by: June 6th)
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

Expected file `data/duke.txt`:
```text
T | 1 | read book
E | 0 | project meeting | Aug 6th 2pm | 4pm
T | 0 | join sports club
```

## Test Case: Load Tasks From File

Aim: Verify that the chatbot loads tasks from `data/duke.txt` when it starts.

Inputs:
```text
list
bye
```

Initial file `data/duke.txt`:
```text
T | 1 | read book
D | 0 | return book | June 6th
E | 0 | project meeting | Aug 6th 2pm | 4pm
T | 1 | join sports club
```

Expected output:
```text
____________________________________________________________
  ____                            _        _
 / ___|___  _ __ ___  _ __  _   _| |_ __ _| |__
| |   / _ \| '_ ` _ \| '_ \| | | | __/ _` | '_ \
| |__| (_) | | | | | | |_) | |_| | || (_| | | | |
 \____\___/|_| |_| |_| .__/ \__,_|\__\__,_|_| |_|
                     |_|

Hello! I'm Computah.
What can I do for you?
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][X] read book
2.[D][ ] return book (by: June 6th)
3.[E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
4.[T][X] join sports club
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

Expected file `data/duke.txt`:
```text
T | 1 | read book
D | 0 | return book | June 6th
E | 0 | project meeting | Aug 6th 2pm | 4pm
T | 1 | join sports club
```

## Test Case: Handle Malformed Save File

Aim: Verify that malformed saved data is reported without crashing the chatbot.

Inputs:
```text
list
bye
```

Initial file `data/duke.txt`:
```text
D | 1 | return book
```

Expected output:
```text
____________________________________________________________
  ____                            _        _
 / ___|___  _ __ ___  _ __  _   _| |_ __ _| |__
| |   / _ \| '_ ` _ \| '_ \| | | | __/ _` | '_ \
| |__| (_) | | | | | | |_) | |_| | || (_| | | | |
 \____\___/|_| |_| |_| .__/ \__,_|\__\__,_|_| |_|
                     |_|

Hello! I'm Computah.
What can I do for you?
____________________________________________________________
OOPS!!! I could not load the task list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test Case: Handle Invalid Commands

Aim: Verify that invalid user inputs are handled with chatbot-specific error messages without ending the session.

Inputs:
```text
todo
blah
deadline return book
event meeting /from Mon 2pm
delete 1
mark 1
bye
```

Expected output:
```text
____________________________________________________________
  ____                            _        _
 / ___|___  _ __ ___  _ __  _   _| |_ __ _| |__
| |   / _ \| '_ ` _ \| '_ \| | | | __/ _` | '_ \
| |__| (_) | | | | | | |_) | |_| | || (_| | | | |
 \____\___/|_| |_| |_| .__/ \__,_|\__\__,_|_| |_|
                     |_|

Hello! I'm Computah.
What can I do for you?
____________________________________________________________
____________________________________________________________
OOPS!!! The description of a todo cannot be empty.
____________________________________________________________
____________________________________________________________
OOPS!!! I'm sorry, but I don't know what that means :-(
____________________________________________________________
____________________________________________________________
OOPS!!! The by date/time of a deadline cannot be empty.
____________________________________________________________
____________________________________________________________
OOPS!!! The end date/time of an event cannot be empty.
____________________________________________________________
____________________________________________________________
OOPS!!! The task number is not in the list.
____________________________________________________________
____________________________________________________________
OOPS!!! The task number is not in the list.
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Test Case: Handle Edge Case Inputs

Aim: Verify that blank commands, bad task numbers, and unsupported save-file delimiters are handled without crashing.

Inputs:
```text
   
todo visit | park
mark
mark abc
todo valid task
delete 99
unmark 0
   list   
   bye   
```

Expected output:
```text
____________________________________________________________
  ____                            _        _
 / ___|___  _ __ ___  _ __  _   _| |_ __ _| |__
| |   / _ \| '_ ` _ \| '_ \| | | | __/ _` | '_ \
| |__| (_) | | | | | | |_) | |_| | || (_| | | | |
 \____\___/|_| |_| |_| .__/ \__,_|\__\__,_|_| |_|
                     |_|

Hello! I'm Computah.
What can I do for you?
____________________________________________________________
____________________________________________________________
OOPS!!! Please enter a command.
____________________________________________________________
____________________________________________________________
OOPS!!! Task details cannot contain " | ".
____________________________________________________________
____________________________________________________________
OOPS!!! Please specify a task number after mark.
____________________________________________________________
____________________________________________________________
OOPS!!! The task number must be a valid number.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] valid task
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
OOPS!!! The task number is not in the list.
____________________________________________________________
____________________________________________________________
OOPS!!! The task number is not in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] valid task
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

Expected file `data/duke.txt`:
```text
T | 0 | valid task
```
