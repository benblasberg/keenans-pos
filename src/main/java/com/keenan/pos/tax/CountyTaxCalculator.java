package com.keenan.pos.tax;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

import com.keenan.pos.model.Product;

public class CountyTaxCalculator extends AbstractTaxCalculator {

    private static final BigDecimal COUNTY_TAX_RATE = BigDecimal.valueOf( 0.007 );

    @Override
    public String getName() {
        return "County";
    }

    @Override
    protected Set<Product.Category> getExemptCategories() {
        return Collections.singleton( Product.Category.GROCERIES );
    }

    @Override
    protected BigDecimal getTaxRate() {
        return COUNTY_TAX_RATE;
    }
}
