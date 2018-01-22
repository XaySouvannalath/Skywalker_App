package com.example.ge.skywalker;

/**
 * Created by GE on 12/31/2017.
 */

public class TestDetailVal {
    String testDetailId, products; Integer quantity;

    public TestDetailVal(String testDetailId, String products, Integer quantity) {
        this.testDetailId = testDetailId;
        this.products = products;
        this.quantity = quantity;
    }

    public TestDetailVal() {
    }

    public String getTestDetailId() {
        return testDetailId;
    }

    public String getProducts() {
        return products;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
