package com.david.expenditures;

public class Income {

    private double amount;
    private String supplier;


    public Income(double amount, String supplier){
        this.amount = amount;
        this.supplier = supplier;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}
