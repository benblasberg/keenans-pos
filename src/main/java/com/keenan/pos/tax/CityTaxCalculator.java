package com.keenan.pos.tax;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

import com.keenan.pos.model.Product;

public class CityTaxCalculator extends AbstractTaxCalculator {

    private static final BigDecimal CITY_TAX_RATE = BigDecimal.valueOf( 0.02 );

    @Override
    protected Set<Product.Category> getExemptCategories() {
        return Collections.emptySet();
    }

    @Override
    protected BigDecimal getTaxRate() {
        return CITY_TAX_RATE;
    }

    @Override
    public String getName() {
        return "City";
    }
}
