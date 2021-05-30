package com.keenan.pos.tax;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;

import com.keenan.pos.model.Product;
import com.keenan.pos.util.DecimalUtil;

public abstract class AbstractTaxCalculator implements TaxCalculator {

    @Override
    public BigDecimal calculateTax(List<Product> products) {
        return priceSum( products ).multiply( getTaxRate() ).setScale( 2, RoundingMode.HALF_EVEN );
    }

    protected BigDecimal priceSum(List<Product> products) {
        return products.stream()
            .filter( p -> !getExemptCategories().contains( p.getCategory() ) )
            .map( p -> DecimalUtil.toDecimal( p.getPrice() ) )
            .reduce( BigDecimal.ZERO, BigDecimal::add );
    }

    /**
     * @return Set of categories that are tax exempt
     */
    protected abstract Set<Product.Category> getExemptCategories();

    /**
     * @return the rate at which to tax non-exempt products
     */
    protected abstract BigDecimal getTaxRate();
}
