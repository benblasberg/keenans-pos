package com.keenan.pos.receipt;

import java.util.List;

import com.keenan.pos.model.Product;
import com.keenan.pos.util.DecimalUtil;

/**
 * Class responsible for constructing the receipt display
 */
public class Receipt {
    private static final String BAR  = "=============================================";
    private static final String LINE = "---------------------------------------------";

    private final double amountPaid;
    private final List<Product> products;
    private final ReceiptCalculator receiptCalculator;

    public Receipt(final double amountPaid, final List<Product> products) {
        this.amountPaid = amountPaid;
        this.products = products;
        this.receiptCalculator = new ReceiptCalculator( amountPaid, products );
    }

    public String generateReceipt() {
        final StringBuilder receiptBuilder = new StringBuilder();
        receiptBuilder.append( BAR ).append( "\n" );
        buildProductsReceipt( receiptBuilder );
        receiptBuilder.append( LINE ).append( "\n" );
        buildSubTotal( receiptBuilder );
        appendTaxes( receiptBuilder );
        appendTotal( receiptBuilder );
        appendAmountPaid( receiptBuilder );
        appendChangeDue( receiptBuilder );
        receiptBuilder.append( BAR );
        return receiptBuilder.toString();
    }

    private void buildProductsReceipt(final StringBuilder receiptBuilder) {
        products
            .forEach( p -> receiptBuilder.append( String.format( "%s\t%s\t%.2f\t%s%n", p.getName(), p.getId(),
                DecimalUtil.toDecimal( p.getPrice() ), p.getCategory() ) ) );
    }

    private void buildSubTotal(final StringBuilder receiptBuilder) {
        receiptBuilder.append( String.format( "Subtotal: %.2f%n", receiptCalculator.getSubTotal() ) );
    }

    private void appendTaxes(final StringBuilder receiptBuilder) {
        receiptCalculator.getTaxAmountsByName().entrySet()
            .forEach( entry ->             receiptBuilder.append( String.format( "%s Tax: %.2f%n", entry.getKey(), entry.getValue() ) ) );
    }

    private void appendTotal(final StringBuilder receiptBuilder ) {
        receiptBuilder.append( String.format( "Total: %.2f%n", receiptCalculator.getTotal() ) );
    }

    private void appendAmountPaid(final StringBuilder receiptBuilder) {
        receiptBuilder.append( String.format( "Amount Paid: %.2f%n", receiptCalculator.getAmountPaid() ) );
    }

    private void appendChangeDue(final StringBuilder receiptBuilder) {
        receiptBuilder.append( String.format( "Change Due: %.2f%n", receiptCalculator.getChangeDue() ) );
    }
}
