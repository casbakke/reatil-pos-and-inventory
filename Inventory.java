package application;

import java.text.SimpleDateFormat;
import java.util.HashMap;

class Inventory {
    static HashMap<String, Entry> inventory = new HashMap<String, Entry>();

    public static Entry getEntry(String upc) {
        return inventory.get(upc);
    }

    private static String getTimeStamp() {
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss").format(new java.util.Date());
        return timeStamp;
    }

    public static void removeEntry(String upc) {
        inventory.remove(upc);
    }
    
    public static Check nextCheck() {
    	return new Check(0); // placeholder
    }
    
    public static class Check extends Inventory {
    	public int checkNum;
    	public HashMap<String, Integer> data;
    	public double subtotal;
    	public double tax;
    	public double total;
    	
    	Check(int checkNum) {
    		this.checkNum = checkNum;
    	}
    	
    	public String toString() {
    		String output = "";
    		for (String upc: this.data.keySet()) {
    			output += ("UPC " + upc + "x" + this.data.get(upc) + "%n");
    		}
    		return output;
    	}
    	
    	public void addItem(String upc) {
    		Integer existingQuantity = data.get(upc);
    		if (existingQuantity != null) {
    			data.put(upc, existingQuantity + 1);
    		} else {
    			data.put(upc, 1);
    		}
    	}
    	
    	public void editQuantity(String upc, int newQuantity) {
    		// if quantity = 0, remove
    		if (newQuantity == 0) {
    			data.remove(upc);
    		} else if (newQuantity < 0 || newQuantity > 99) {
    			// error
    		} else {
    			data.put(upc, newQuantity);
    		}
    		// update price
    	}
    	
    	
    }

    public static class Entry extends Inventory {
        
        public String toString() {
            return String.format("UPC: %s, Name: %s, Price: %.2f, Weight: %.2f, Dimensions: %.1fx%.1fx%.1f, Quantity: %d, Last Sale at %s # %s", this.upc, this.name, this.price, this.weight, this. length, this.width, this.height, this.quantity, this.timeStampOfLastSale, this.checkNumOfLastSale);
        }

        public String upc;
        public String name;
        public double price;
        public double weight;
        public double length, width, height;
        public int quantity;
        public String timeStampOfLastSale;
        public String checkNumOfLastSale;

        Entry(String upc, String name, double price, double weight, double length, double width, double height) {
        	if (!inventory.containsKey(upc)) {
	        	this.upc = upc;
	            this.name = name;
	            this.price = price;
	            this.weight = weight;
	            this.length = length;
	            this.width = width;
	            this.height = height;
	            this.quantity = 0;
	            this.timeStampOfLastSale = "N/A";
	            this.checkNumOfLastSale = "N/A";
	            inventory.put(upc, this);
        	} else {
                // Error: duplicate entry
            }  
        }
        Entry(String upc, String name, double price, double weight, double length, double width, double height, int quantity) {
            if (!inventory.containsKey(upc)) {
                this.upc = upc;
                this.name = name;
                this.price = price;
                this.weight = weight;
                this.length = length;
                this.width = width;
                this.height = height;
                this.quantity = quantity;
                this.timeStampOfLastSale = "N/A";
                this.checkNumOfLastSale = "N/A";
                inventory.put(upc, this);
            } else {
                // Error: duplicate entry
            }
        }

        public void sale(Check check, String checkNum) {
            for (String upc : check.data.keySet()) {
            	Integer quantitySold = check.data.get(upc);
            	Inventory.Entry entry = Inventory.getEntry(upc);
            	entry.quantity -= quantitySold;
            	entry.timeStampOfLastSale = Inventory.getTimeStamp();
            	entry.checkNumOfLastSale = (checkNum);
            }
        }
    }
}