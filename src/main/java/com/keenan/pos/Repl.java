package com.keenan.pos;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Repl {

    static final Pattern VALID_BARCODE_PATTERN = Pattern.compile( "^\\d{1,12}$" );

    public static void main(String[] args) {
        System.out.println("Starting up...");
        //load data from csv return map keyed by barcode id
        //use data to build barcode search trie

        System.out.println("Ready to process. Enter q to quit");

        try (Scanner scanner = new Scanner( System.in )) {
            String input = "";

            while (!input.equals( "q" ) ) {
                input = getValidatedInput( scanner );
            }
        }
    }

    private static String getValidatedInput(final Scanner scanner) {
        String input = "";
        while (input.isEmpty()) {
            System.out.print("Enter a product barcode: ");
            input = scanner.next();

            if (input.equals( "q" )) {
                return input;
            }

            Matcher m = VALID_BARCODE_PATTERN.matcher( input );

            if (!m.find()) {
                System.out.println("Invalid barcode: <" + input + ">");
                input = "";
            }
        }

        return input;
    }
}
