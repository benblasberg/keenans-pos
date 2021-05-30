package com.keenan.pos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import com.keenan.pos.model.Product;
import com.keenan.pos.search.ProductSearcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ReplTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    Repl repl;

    @BeforeEach
    public void beforeEach() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Product p = Product.builder()
            .id( "123456789012" )
            .category( Product.Category.CLOTHING )
            .name( "TEST Product" )
            .price( 10.00d )
            .build();

        Product p2 = Product.builder()
            .id( "123456789013" )
            .category( Product.Category.OTHER )
            .name( "TEST Product2" )
            .price( 15.00d )
            .build();

        ProductSearcher productSearcher = new ProductSearcher();
        productSearcher.add( p.getId() );
        productSearcher.add( p2.getId() );

        final Map<String, Product> products = new HashMap<>();
        products.put( p.getId(), p );
        products.put( p2.getId(), p2 );
        repl = new Repl( productSearcher, products );
    }

    private void sendInput(final String input) {
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
    }

    @Test
    public void testSearch() {
        sendInput( "12\nq\n" );

        repl.runRepl();

        String output = testOut.toString();

        StringBuilder expectedOutput = new StringBuilder()
            .append( "Found matching barcodes:\n" )
            .append( "123456789012\tTEST Product\n" )
            .append( "123456789013\tTEST Product2\n" );

        assertTrue(output.contains( expectedOutput.toString() ));
    }

    @Test
    public void testInvalidBarcode() {
        sendInput( "12hahj3902j##\nq\n" );

        repl.runRepl();

        String output = testOut.toString();

        StringBuilder expectedOutput = new StringBuilder()
            .append( "Invalid barcode: <12hahj3902j##>" );
        assertTrue(output.contains( expectedOutput.toString() ));
    }



}