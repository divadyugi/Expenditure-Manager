package com.david.expenditures;

public class Expenditure {

    private double amount;
    private String supplier;
    private String type;

    public Expenditure(double amount, String supplier, String type){
        this.amount = amount;
        this.supplier = supplier;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
