package com.keenan.pos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.keenan.pos.model.Product;
import com.keenan.pos.receipt.Receipt;
import com.keenan.pos.search.ProductSearcher;

public class Repl {

    private static final Pattern VALID_BARCODE_PATTERN = Pattern.compile( "^\\d{1,12}$" );

    private final ProductSearcher productSearcher;
    private final Map<String, Product> productsById;

    public Repl(ProductSearcher productSearcher, Map<String, Product> productsById) {
        this.productSearcher = productSearcher;
        this.productsById = productsById;
    }

    public void runRepl() {
        System.out.println("Ready to process. Enter q to quit");

        final List<Product> products = new ArrayList<>();
        try (Scanner scanner = new Scanner( System.in )) {

            while (true) {
                String input = getValidatedInput( scanner );

                if ( input.equals( "q" ) ) {
                    break;
                }

                if ( input.equals( "checkout" ) ) {
                    double amountPaid = getAmountPaid( scanner );
                    final Receipt receipt = new Receipt(amountPaid, products);

                    System.out.println(receipt.generateReceipt());
                    products.clear();
                    continue;
                }

                if ( input.length() < 12 ) { //perform search
                    Set<String> barcodes = productSearcher.findBarcodesByPrefix( input );

                    if ( barcodes.isEmpty() ) {
                        System.out.println( "No barcodes found for prefix: " + input );
                    }
                    else {
                        System.out.println( "Found matching barcodes:" );
                        barcodes.forEach( barcode -> System.out.printf("%s\t%s\n", barcode, productsById.get( barcode ).getName() ) );
                    }
                }
                else { //add to productLists
                    final Product product = productsById.get( input );
                    products.add( product );
                    System.out.printf( "%s\t%.2f%n", product.getName(), product.getPrice() );
                }
            }
        }
    }

    private double getAmountPaid(final Scanner scanner) {
        double amountPaid = 0d;
        while (amountPaid == 0d) {
            System.out.print("Enter amount paid: ");

            try {
                amountPaid = Double.parseDouble( scanner.next() );
            } catch ( NumberFormatException ex) {
                System.out.println("Invalid amount entered");
                scanner.reset();
            }
        }

        return amountPaid;
    }

    private String getValidatedInput(final Scanner scanner) {
        String input = "";
        while (input.isEmpty()) {
            System.out.print("Enter a product barcode: ");
            input = scanner.next();

            if (input.equals( "q" ) || input.equals( "checkout" )) {
                return input;
            }

            Matcher m = VALID_BARCODE_PATTERN.matcher( input );

            if (!m.matches()) {
                System.out.println("Invalid barcode: <" + input + ">");
                input = "";
            }
        }

        return input;
    }
}
