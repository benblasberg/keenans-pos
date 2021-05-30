package com.keenan.pos;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Map;

import com.keenan.pos.model.Product;
import com.keenan.pos.parse.CsvParser;
import com.keenan.pos.search.ProductSearcher;

/**
 * Main entry point for the point of sale terminal
 */
public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {

        if (args.length != 1) {
            System.out.println( Arrays.toString(args));
            System.out.println("Usage: java -jar keenan-pos.jar <data csv file>");
            System.exit( 0 );
        }

        File f = new File( args[0] );

        if (!f.exists()) {
            System.out.printf("File not found at %s\n", args[0]);
            System.exit( 0 );
        }

        final CsvParser csvParser = new CsvParser( f );

        final Map<String, Product> productsById = csvParser.load();
        ProductSearcher productSearcher = new ProductSearcher();

        productsById.values()
            .stream()
            .map( Product::getId )
            .forEach( productSearcher::add );

        Repl repl = new Repl( productSearcher, productsById );
        repl.runRepl();
    }
}
