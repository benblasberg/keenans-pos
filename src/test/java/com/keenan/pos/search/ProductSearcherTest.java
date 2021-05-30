package com.keenan.pos.search;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductSearcherTest {

    ProductSearcher productSearcher;

    @BeforeEach
    public void beforeEach() {
        productSearcher = new ProductSearcher();
    }

    @Test
    public void testFindOnEmptyTrie() {
        assertEquals( Collections.emptySet(), productSearcher.findBarcodesByPrefix( "1234" ) );
    }

    @Test
    public void testFindBarcodeByPrefix() {
        productSearcher.add( "123456789012" );
        productSearcher.add( "123456789137" );
        productSearcher.add( "123456783743" );

        Set<String> actualBarCodes = productSearcher.findBarcodesByPrefix( "123456789" );
        assertFalse( actualBarCodes.isEmpty() );

        Set<String> expectedBarCodes = new HashSet<>( Arrays.asList("123456789012", "123456789137"));
        assertEquals( expectedBarCodes, actualBarCodes);
    }

    @Test
    public void testFindBarcodeByPrefix_NoMatch() {
        productSearcher.add( "123456789012" );
        productSearcher.add( "123456789137" );
        productSearcher.add( "123456783743" );

        Set<String> actualBarCodes = productSearcher.findBarcodesByPrefix( "1234567894" );
        assertTrue( actualBarCodes.isEmpty() );
    }
}