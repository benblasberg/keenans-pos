package com.keenan.pos.tax;

import java.math.BigDecimal;
import java.util.List;

import com.keenan.pos.model.Product;

/**
 * Calculates the amount of tax for the given products
 */
public interface TaxCalculator {

    /**
     * @param products the list of products to calculate tax for
     * @return the amount of tax.
     */
    BigDecimal calculateTax(List<Product> products);

    /**
     * @return The name of this tax.
     */
    String getName();
}
