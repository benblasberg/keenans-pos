package com.keenan.pos.parse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.keenan.pos.model.Product;

public class CsvParser {

    private static final Map<String, Product.Category> categoryMap = new HashMap<>();

    static {
        categoryMap.put( "g", Product.Category.GROCERIES );
        categoryMap.put( "pf", Product.Category.PREPARED_FOOD );
        categoryMap.put( "pd", Product.Category.PRESCRIPTION_DRUG );
        categoryMap.put( "nd", Product.Category.NON_PRESCRIPTION_DRUG );
        categoryMap.put( "c", Product.Category.CLOTHING );
        categoryMap.put( "o", Product.Category.OTHER );
    }

    private final File file;

    /**
     * Constructs a new {@link CsvParser}
     * @param file the file to parse csv data from. This file is assumed to exist and be well formatted.
     */
    public CsvParser(File file) {
        this.file = file;
    }

    public Map<String, Product> load() throws IOException {
        final List<String> lines = Files.readAllLines(file.toPath());

        return lines.stream()
            .map( this::parseLine )
            .collect( Collectors.toMap( Product::getId, Function.identity() ) );
    }

    private Product parseLine(String line) {
        final String[] columns = line.split( "," );
        return Product.builder()
            .id( columns[ProductIndex.ID.index] )
            .name( columns[ProductIndex.NAME.index] )
            .price( Double.parseDouble( columns[ProductIndex.PRICE.index] ) )
            .category( categoryMap.get( columns[ProductIndex.CATEGORY.index] ) )
            .build();
    }

    private enum ProductIndex {
        ID(0),
        NAME(1),
        PRICE(2),
        CATEGORY(3);

        final int index;

        ProductIndex(int index) {
            this.index = index;
        }
    }

}
