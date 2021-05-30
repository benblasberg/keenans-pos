package com.keenan.pos.receipt;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.keenan.pos.model.Product;
import com.keenan.pos.tax.CityTaxCalculator;
import com.keenan.pos.tax.CountyTaxCalculator;
import com.keenan.pos.tax.StateTaxCalculator;
import com.keenan.pos.tax.TaxCalculator;
import com.keenan.pos.util.DecimalUtil;
import lombok.Getter;

/**
 * Calculates fields to display as part of the receipt.
 */
public class ReceiptCalculator {
    private final List<TaxCalculator> taxCalculators = Arrays.asList(new StateTaxCalculator(), new CountyTaxCalculator(), new CityTaxCalculator() );

    @Getter
    private Map<String, BigDecimal> taxAmountsByName;

    @Getter
    private BigDecimal total;

    @Getter
    private BigDecimal subTotal;

    @Getter
    private BigDecimal amountPaid;

    @Getter
    private BigDecimal changeDue;

    public ReceiptCalculator(final double amountPaid, final List<Product> products) {
        this.amountPaid = DecimalUtil.toDecimal( amountPaid );
        calculate( products );
    }

    private void calculate(final List<Product> products) {
        subTotal = sum( products.stream()
            .map( p -> DecimalUtil.toDecimal( p.getPrice() ) ) );

        taxAmountsByName = taxCalculators.stream()
            .collect( Collectors.toMap( TaxCalculator::getName, tc -> tc.calculateTax( products ) ) );

        total = subTotal.add( sum(taxAmountsByName.values().stream() ) );

        changeDue = amountPaid.subtract( total );
    }

    private BigDecimal sum(Stream<BigDecimal> bigDecimalStream) {
        return bigDecimalStream.reduce( BigDecimal.ZERO, BigDecimal::add );
    }
}
