public class Main {
    public static void main(String[] args) {
        Entry ent = new Entry("1234", 10.0, 5.0, 5.0, 5.0, 10);
        System.out.println(ent.upc);
        System.out.println(Entry.getEntry("1234"));
        
    }
}