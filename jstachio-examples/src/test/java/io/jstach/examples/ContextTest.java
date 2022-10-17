package io.jstach.examples;

import static org.junit.Assert.*;

import org.junit.Test;

import io.jstach.examples.ContextExample;

public class ContextTest {

    @Test
    public void testParent() throws Exception {
        ContextExample ce = ContextExample.forTest();
        String r = ContextExampleRenderer.of(ce).renderString();
        System.out.println(r);
        
        String expected = "\"foo, bar, baz\"";
        
        assertEquals(expected, r);
    }


}