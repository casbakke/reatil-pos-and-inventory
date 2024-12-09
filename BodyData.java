package application;

import javafx.collections.ObservableList;

public class BodyData {
    private int rowNum;
    private String item;
    private int quantity;
    private double price;
    private String upc;

    public BodyData(int rowNum, String item, int quantity, double price, String upc) {
        this.rowNum = rowNum;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.upc = upc;
    }

    public String getRowNum() {
        return String.format("%02d", this.rowNum);
    }
    
    public String getItem() {
        return this.item;
    }
    
    public int getQuantity() {
        return this.quantity;
    }
    
    public String getPrice() {
        return String.format("%.2f", price);
    }
    
    public void setRowNum(int newNum) {
    	this.rowNum = newNum;
    }
    
    public void setItem(String newName) {
    	this.item = newName;
    }
    
    public void setQuantity(int newQuant) {
    	this.quantity = newQuant;
    }
    
    public void setPrice(double newPrice) {
    	this.price = newPrice;
    }
    
    public String getUPC() {
    	return this.upc;
    }
}