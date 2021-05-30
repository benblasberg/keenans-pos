package com.keenan.pos.tax;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

import com.keenan.pos.model.Product;

public class StateTaxCalculator extends AbstractTaxCalculator {

    private static final BigDecimal STATE_TAX_RATE = BigDecimal.valueOf( 0.063 );

    @Override
    public String getName() {
        return "State";
    }

    @Override
    protected Set<Product.Category> getExemptCategories() {
        return Collections.singleton( Product.Category.GROCERIES );
    }

    @Override
    protected BigDecimal getTaxRate() {
        return STATE_TAX_RATE;
    }
}