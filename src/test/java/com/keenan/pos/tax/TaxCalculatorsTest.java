package com.keenan.pos.tax;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.keenan.pos.model.Product;
import com.keenan.pos.util.DecimalUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaxCalculatorsTest {

    private static final double DELTA = 0.0001d;

    TaxCalculator taxCalculator;

    @BeforeEach
    public void beforeEach() {
        taxCalculator = new CityTaxCalculator();
    }

    private void assertStateTax(List<Product> products, double expectedAmount) {
        BigDecimal stateTaxAmount = new StateTaxCalculator().calculateTax( products );
        assertBigDecimalEquals( expectedAmount, stateTaxAmount );
    }

    private void assertCountyTax(List<Product> products, double expectedAmount) {
        BigDecimal countyTaxAmount = new CountyTaxCalculator().calculateTax( products );
        assertBigDecimalEquals( expectedAmount, countyTaxAmount );
    }

    private void assertCityTax(List<Product> products, double expectedAmount) {
        BigDecimal cityTaxAmount = new CityTaxCalculator().calculateTax( products );
        assertBigDecimalEquals( expectedAmount, cityTaxAmount );
    }

    private void assertBigDecimalEquals(final double expected, final BigDecimal actual) {
        assertEquals( DecimalUtil.toDecimal( expected ), actual );
    }

    @Test
    public void testTaxRate() {
        Product p = Product.builder()
            .id( "123456789012" )
            .category( Product.Category.CLOTHING )
            .name( "TEST Product" )
            .price( 10.00d )
            .build();

        List<Product> products = Arrays.asList( p );

        assertCityTax( products, .2d  );
        assertCountyTax( products, .07d );
        assertStateTax( products, .63d );
    }

    @Test
    public void testTaxRate_multipleProdcuts() {
        Product p = Product.builder()
            .id( "123456789012" )
            .category( Product.Category.CLOTHING )
            .name( "TEST Product" )
            .price( 10.00d )
            .build();

        Product p2 = Product.builder()
            .id( "123456789013" )
            .category( Product.Category.OTHER )
            .name( "TEST Product" )
            .price( 15.00d )
            .build();

        List<Product> products = Arrays.asList( p, p2 );

        assertCityTax( products, .5d  );
        assertCountyTax( products, .175d );
        assertStateTax( products, 1.575d );
    }

    @Test
    public void testTaxRate_GroceriesUntaxed() {
        Product p = Product.builder()
            .id( "123456789012" )
            .category( Product.Category.GROCERIES )
            .name( "TEST Product" )
            .price( 10.00d )
            .build();

        Product p2 = Product.builder()
            .id( "123456789013" )
            .category( Product.Category.GROCERIES )
            .name( "TEST Product" )
            .price( 15.00d )
            .build();

        List<Product> products = Arrays.asList(p, p2);
        assertCityTax( products, .5d  );
        assertCountyTax( products, 0d );
        assertStateTax( products, 0d );
    }

    @Test
    public void testGetName() {
        assertEquals( "City", new CityTaxCalculator().getName() );
        assertEquals( "County", new CountyTaxCalculator().getName() );
        assertEquals( "State", new StateTaxCalculator().getName() );
    }

}