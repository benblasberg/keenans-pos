package com.keenan.pos.receipt;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.keenan.pos.model.Product;
import com.keenan.pos.util.DecimalUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptCalculatorTest {

    List<Product> products;

    @BeforeEach
    public void beforeEach() {
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

        products = Arrays.asList(p, p2);
    }

    @Test
    public void testCalculations() {

        final ReceiptCalculator receiptCalculator = new ReceiptCalculator( 30.00d, products );

        assertEquals( DecimalUtil.toDecimal( 25d ), receiptCalculator.getSubTotal() );
        assertEquals( DecimalUtil.toDecimal( 30.00d ), receiptCalculator.getAmountPaid() );
        assertEquals( DecimalUtil.toDecimal( 0.18 ), receiptCalculator.getTaxAmountsByName().get( "County" ) );
        assertEquals( DecimalUtil.toDecimal( 1.58d ), receiptCalculator.getTaxAmountsByName().get( "State" ) );
        assertEquals( DecimalUtil.toDecimal( 0.5d ), receiptCalculator.getTaxAmountsByName().get( "City" ) );
        assertEquals( DecimalUtil.toDecimal( 27.26 ), receiptCalculator.getTotal() );
        assertEquals( DecimalUtil.toDecimal( 2.74d ), receiptCalculator.getChangeDue() );

    }

}