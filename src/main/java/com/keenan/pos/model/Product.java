package com.keenan.pos.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

    private String id;
    private String name;
    private Category category;
    private double price;

    public enum Category {
        GROCERIES,
        PREPARED_FOOD,
        PRESCRIPTION_DRUG,
        NON_PRESCRIPTION_DRUG,
        CLOTHING,
        OTHER;
    }
}
