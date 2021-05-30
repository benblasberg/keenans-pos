package com.keenan.pos.parse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import com.keenan.pos.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CsvParserTest {

    File f;
    CsvParser csvParser;

    @BeforeEach
    public void beforeEach() throws IOException {
        f = File.createTempFile( "data", "csv" );
        csvParser = new CsvParser( f );
    }

    @AfterEach
    public void afterEach() {
        f.delete();
    }

    private void addLineToFile(String line) {
        try (PrintWriter printWriter = new PrintWriter( new FileOutputStream( f, /*append=*/ true ))) {
            printWriter.println(line);
        } catch ( Exception e ) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSingleRow() throws IOException {
        addLineToFile( "017082112774,JK LNK BEEF TERI,5.99,g" );

        Map<String, Product> productMap = csvParser.load();

        assertNotNull( productMap );
        assertEquals( 1, productMap.size() );

        Product p = productMap.values().iterator().next();
        assertTrue( productMap.containsKey( p.getId() ));
        assertEquals( "017082112774", p.getId() );
        assertEquals( "JK LNK BEEF TERI", p.getName() );
        assertEquals( 5.99d, p.getPrice() );
        assertEquals( Product.Category.GROCERIES, p.getCategory() );
    }

    @Test
    public void testMultipleRows() throws IOException {
        addLineToFile( "017082112774,JK LNK BEEF TERI,5.99,g" );
        addLineToFile( "018200530470,BUD LT 12 CAN,11.99,o" );

        Map<String, Product> productMap = csvParser.load();

        assertNotNull( productMap );
        assertEquals( 2, productMap.size() );

        Product p1 = productMap.get( "017082112774" );
        assertNotNull( p1 );
        assertEquals( "017082112774", p1.getId() );
        assertEquals( "JK LNK BEEF TERI", p1.getName() );
        assertEquals( 5.99d, p1.getPrice() );
        assertEquals( Product.Category.GROCERIES, p1.getCategory() );

        Product p2 = productMap.get( "018200530470" );
        assertNotNull( p2 );
        assertEquals( "018200530470", p2.getId() );
        assertEquals( "BUD LT 12 CAN", p2.getName() );
        assertEquals( 11.99d, p2.getPrice() );
        assertEquals( Product.Category.OTHER, p2.getCategory() );
    }

    @Test
    public void testAllCategoriesParsable() throws IOException {
        addLineToFile( "017082112774,JK LNK BEEF TERI,5.99,g" );
        addLineToFile( "018200530470,BUD LT 12 CAN,11.99,o" );
        addLineToFile( "028400157827,CHEETOS CHED JAL,3.99,pf" );
        addLineToFile( "028400589864,CHEETOS CRUNCHY,3.99,pd" );
        addLineToFile( "080660956756,CORONA LT 12 BTL,12.99,nd" );
        addLineToFile( "305730133203,ADVIL IBU 20CT,7.99,c" );

        Map<String, Product> productMap = csvParser.load();

        for ( Product.Category category : Product.Category.values()) {
            //check that all categories exist
            assertTrue( productMap.values().stream().anyMatch( p -> p.getCategory() == category ) );
        }
    }
}