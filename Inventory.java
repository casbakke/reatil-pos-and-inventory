import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.*;

class Inventory {
    static HashMap<String, Entry> inventory = new HashMap<String, Entry>();

    public static void fetchData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.ser"))) {
            inventory = (HashMap<String, Entry>) ois.readObject();
            System.out.println("Loaded HashMap: " + inventory);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.ser"))) {
            oos.writeObject(inventory);
            System.out.println("HashMap saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public static class Entry extends Inventory {
        
        public String toString() {
            return String.format("UPC: %s, Name: %s, Weight: %f, Dimensions: %fx%fx%f, Quantity: %d, Last Sale at %s # %s", this.upc, this.name, this.weight, this. length, this.width, this.height, this.quantity, this.timeStampOfLastSale, this.checkNumOfLastSale);
        }

        public String upc;
        public String name;
        public double weight;
        public double length, width, height;
        public int quantity;
        public String timeStampOfLastSale;
        public String checkNumOfLastSale;

        Entry(String upc, String name, double weight, double length, double width, double height) {
            this.upc = upc;
            this.name = name;
            this.weight = weight;
            this.length = length;
            this.width = width;
            this.height = height;
            this.quantity = 0;
            this.timeStampOfLastSale = null;
            this.checkNumOfLastSale = null;
            inventory.put(upc, this);
        }
        Entry(String upc, String name, double weight, double length, double width, double height, int quantity) {
            if (!inventory.containsKey(upc)) {
                this.upc = upc;
                this.name = name;
                this.weight = weight;
                this.length = length;
                this.width = width;
                this.height = height;
                this.quantity = quantity;
                this.timeStampOfLastSale = null;
                this.checkNumOfLastSale = null;
                inventory.put(upc, this);
            } else {
                // Error: duplicate entry
            }
        }

        public void sale(int quantitySold, String checkNum) {
            this.quantity -= quantitySold;
            this.timeStampOfLastSale = getTimeStamp();
            this.checkNumOfLastSale = checkNum;
        }
    }
}