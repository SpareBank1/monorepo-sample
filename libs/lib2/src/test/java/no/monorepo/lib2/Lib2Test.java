package no.monorepo.lib2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class Lib2Test
{

    @Test
    public void shouldWhisper() {
       assertEquals("abc", Lib2.whisper("ABC"));
    }

    @Test
    public void shouldCapitalize() {
        assertEquals("John doe", Lib2.capitalize("john doe"));
    }

}
