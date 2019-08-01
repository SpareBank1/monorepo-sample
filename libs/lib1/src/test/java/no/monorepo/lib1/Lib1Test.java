package no.monorepo.lib1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

public class Lib1Test {

    @Test
    public void shouldAnswerWithTrue() throws InterruptedException {
        Thread.sleep(50);
        assertEquals("ABC", Lib1.scream("abc"));
    }
}
