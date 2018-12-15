package tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import simulator.Command;
import simulator.CommandType;

class CommandTest {

    @Test
    final void testCommand() {

        assertThrows(NullPointerException.class, () -> new Command(null));
        assertThrows(IllegalArgumentException.class, () -> new Command("r"));
        assertThrows(IllegalArgumentException.class, () -> new Command("r 0 0"));
        assertThrows(IllegalArgumentException.class, () -> new Command("w"));
        assertThrows(IllegalArgumentException.class, () -> new Command("w 0"));
        assertThrows(IllegalArgumentException.class, () -> new Command("h 0"));
        assertThrows(IllegalArgumentException.class, () -> new Command("m 0"));
        assertThrows(IllegalArgumentException.class, () -> new Command("c 0"));
        assertThrows(IllegalArgumentException.class, () -> new Command("s 0"));
        assertThrows(IllegalArgumentException.class, () -> new Command("e 0"));
        assertThrows(IllegalArgumentException.class, () -> new Command("a"));
        assertThrows(IllegalArgumentException.class, () -> new Command("ag 6"));
        assertThrows(IllegalArgumentException.class, () -> new Command("th 6 7"));
        assertThrows(IllegalArgumentException.class, () -> new Command("a a a a a "));
        assertAll(() -> new Command("r 0"));
        assertAll(() -> new Command("w 0 10"));
        assertAll(() -> new Command("h"));
        assertAll(() -> new Command("m"));
        assertAll(() -> new Command("c"));
        assertAll(() -> new Command("e"));
        assertAll(() -> new Command("s"));
    }

    @Test
    final void testGetcType() {
        Command command = new Command("r 10");
        CommandType type = command.getcType();
        assertEquals(type, CommandType.READ);

        command = new Command("w 10 20");
        type = command.getcType();
        assertEquals(type, CommandType.WRITE);
    }

    @Test
    final void testGetCommand() {
        Command command = new Command("r 10");
        String string = command.getCommand();
        assertEquals(string, "r 10");
    }

    @Test
    final void testGetAdress() {
        Command command = new Command("r 10");
        assertThrows(UnsupportedOperationException.class, () -> new Command("h").getAdress());
        int adress = command.getAdress();
        assertEquals(adress, 10);
    }

    @Test
    final void testGetValue() {
        Command command = new Command("w 0 10");
        assertThrows(UnsupportedOperationException.class, () -> new Command("h").getValue());
        int value = command.getValue();
        assertEquals(value, 10);
    }

    @Test
    final void testToString() {
        Command command = new Command("r 10");
        String string = command.toString();
        assertEquals(string, "Read 10");

        command = new Command("w 0 10");
        string = command.toString();
        assertEquals(string, "Write 0 10");

        command = new Command("s");
        string = command.toString();
        assertEquals(string, "Show");

        command = new Command("c");
        string = command.toString();
        assertEquals(string, "Config");

        command = new Command("h");
        string = command.toString();
        assertEquals(string, "Hit");

        command = new Command("m");
        string = command.toString();
        assertEquals(string, "Miss");

        command = new Command("e");
        string = command.toString();
        assertEquals(string, "Exit");
    }
}
