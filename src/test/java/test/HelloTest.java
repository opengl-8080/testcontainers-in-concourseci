package test;

import org.junit.Test;

import static org.junit.Assert.*;

public class HelloTest {

    @Test
    public void test() {
        String actual = new Hello().getMessage();
        assertEquals("Hello World!", actual);
    }
}