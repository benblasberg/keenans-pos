package com.keenan.pos.search;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.Data;

/**
 * Builds a trie of product barcodes, and uses dfs to search for products with matching suffixes
 */
public class ProductSearcher {
    private Node root;

    /**
     * Constructs a new {@link ProductSearcher}
     */
    public ProductSearcher() {
        root = new Node();
    }

    /**
     * Adds the given product barcode to the searchable graph of barcodes
     * @param productId The barcode to add. Assumed to be a valid string of digits.
     */
    public void add(String productId) {
        Node curr = root;
        for (char currentDigit : productId.toCharArray()) {
            curr = curr.children.computeIfAbsent( currentDigit, Node::new );
        }
        curr.setLeaf( true );
    }

    /**
     * @param prefix the barcode prefix to search with.
     * @return A non-null {@link Set} of barcodes that match the {@code prefix}
     */
    public Set<String> findBarcodesByPrefix(final String prefix) {

        Node curr = root;
        for (char currentDigit : prefix.toCharArray()) {
            curr = curr.children.get( currentDigit );

            if (curr == null) { //no match for this prefix
                return Collections.emptySet();
            }
        }

        //perform dfs to find the matching suffixes
        final Set<String> barcodes = new HashSet<>();
        dfs(curr, prefix.substring( 0, prefix.length()-1 ), barcodes);

        return barcodes;
    }

    private void dfs(Node node, String current, Set<String> barcodes) {
        if (node.isLeaf) {
            barcodes.add( current + node.digit );
            return;
        }

        node.children.values().forEach( n -> dfs(n, current + node.getDigit(), barcodes) );
    }

    @Data
    private class Node {
        char digit;
        boolean isLeaf;
        private Map<Character, Node> children;

        public Node(char digit) {
            this.digit = digit;
            this.children = new HashMap<>();
        }

        public Node() {
            this.children = new HashMap<>();
        }
    }


}
