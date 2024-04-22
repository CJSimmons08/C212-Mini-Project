package edu.iu.c212.models;

public class Item {

    private String name;
    private double price;
    private int quantity;
    private int aisleNum;

    public Item(String name, double price, int quantity, int aisleNum){

    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }

    public int getQuantity(){
        return quantity;
    }

    public int getAisle(){
        return aisleNum;
    }
}
