package com.mncomunity1.model;

public class ShoppingCartEntry {

    private ModelSpare mProduct;
    private int mQuantity;

    public ShoppingCartEntry(ModelSpare product, int quantity) {
        mProduct = product;
        mQuantity = quantity;
    }

    public ModelSpare getProduct() {
        return mProduct;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

}