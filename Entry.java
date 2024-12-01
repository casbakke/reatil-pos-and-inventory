import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Entry {
    static HashMap<String, Entry> inventory = new HashMap<String, Entry>();

    static Entry getEntry(String upc) {
        return inventory.get(upc);
    }

    public String upc;
    public String name;
    public double weight;
    public double length, width, height;
    public int quantity;
    public String timeStampOfLastSale;
    public String checkNumOfLastSale;

    Entry(String upc, double weight, double length, double width, double height) {
        this.upc = upc;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.quantity = 0;
        this.timeStampOfLastSale = null;
        this.checkNumOfLastSale = null;
        inventory.put(upc, this);
    }
    Entry(String upc, double weight, double length, double width, double height, int quantity) {
        
        // add check to see if upc already has an entry

        this.upc = upc;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.quantity = quantity;
        inventory.put(upc, this);
    }

    public void sale(String upc, int quantitySold, String timeStamp, String checkNum) {
        // decrease quantity
        // change last sale
    }
}