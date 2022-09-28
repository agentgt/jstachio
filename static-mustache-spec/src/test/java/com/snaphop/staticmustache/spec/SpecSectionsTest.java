package com.snaphop.staticmustache.spec;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.EnumSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.snaphop.staticmustache.spec.sections.SectionsSpecTemplate;

@RunWith(Parameterized.class)
public class SpecSectionsTest {

    @Parameters(name = "{1}")
    public static Collection<Object[]> data() {
        return EnumSet.allOf(SectionsSpecTemplate.class).stream().map(t -> new Object[] { t, t.title() }).toList();
    }
    
    private final SectionsSpecTemplate specItem;
    
    public SpecSectionsTest(SectionsSpecTemplate specItem, String name) {
    	this.specItem = specItem;
	}
    
    @Test
	public void testRender()
			throws Exception {
        String expected = specItem.expected();
        String json = specItem.json();
        String actual = render(specItem);
        assertEquals(json, expected, actual);
		
	}
    
    String render(SectionsSpecTemplate specTemplate) {
    	return specTemplate.render();
    }
}
