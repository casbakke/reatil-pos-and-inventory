public class SubtotalData {
    private final String label;
    private final String value;

    // Constructor
    public SubtotalData(String label, String value) {
        this.label = label;
        this.value = value;
    }

    // Getter for label
    public String getLabel() {
        return label;
    }

    // Setter for label
    public void setLabel(String label) {
        this.label = label;
    }

    // Getter for value
    public String getValue() {
        return value;
    }

    // Setter for value
    public void setValue(String value) {
        this.value = value;
    }
}