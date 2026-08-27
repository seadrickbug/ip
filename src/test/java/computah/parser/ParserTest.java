package computah.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import computah.command.Command;
import computah.command.FindCommand;
import computah.exception.ComputahException;

/**
 * Tests command parsing behavior in {@link Parser}.
 */
public class ParserTest {
    @Test
    public void parse_findWithKeyword_returnsFindCommand() throws ComputahException {
        Command command = Parser.parse("find book", 0);

        assertInstanceOf(FindCommand.class, command);
    }

    @Test
    public void parse_findWithoutKeyword_exceptionThrown() {
        ComputahException exception = assertThrows(ComputahException.class, () -> Parser.parse("find", 0));

        assertEquals("The keyword of a find command cannot be empty.", exception.getMessage());
    }

    @Test
    public void parse_findWithBlankKeyword_exceptionThrown() {
        ComputahException exception = assertThrows(ComputahException.class, () -> Parser.parse("find   ", 0));

        assertEquals("The keyword of a find command cannot be empty.", exception.getMessage());
    }

    @Test
    public void parse_wordStartingWithFindButNotFindCommand_exceptionThrown() {
        ComputahException exception = assertThrows(ComputahException.class, () -> Parser.parse("findbook", 0));

        assertEquals("I'm sorry, but I don't know what that means :-(", exception.getMessage());
    }
}
