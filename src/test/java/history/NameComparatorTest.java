package history;

import org.junit.Test;

import static org.junit.Assert.*;

public class NameComparatorTest {

    @Test
    public void testCompare() throws Exception {
        NameComparator cmp = new NameComparator();
        assertTrue(cmp.compare("AbCdE", "AbCDE") > 0);
        assertTrue(cmp.compare("AbCdE", "AbcDE") < 0);
        assertTrue(cmp.compare("AbCdE", "AbDDE") < 0);
        assertTrue(cmp.compare("abCdE", "ZbcDE") < 0);
        assertTrue(cmp.compare("aBCDE", "aBCDE") == 0);
    }
}