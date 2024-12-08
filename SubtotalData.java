package application;

import javafx.beans.property.SimpleStringProperty;

public class SubtotalData {

    private final SimpleStringProperty label;
    private final SimpleStringProperty value;

    // Constructor
    public SubtotalData(String label, String value) {
        this.label = new SimpleStringProperty(label);
        this.value = new SimpleStringProperty(value);
    }

    // Getter for label
    public String getLabel() {
        return label.get();
    }

    // Setter for label
    public void setLabel(String label) {
        this.label.set(label);
    }

    // Getter for value
    public String getValue() {
        return value.get();
    }

    // Setter for value
    public void setValue(String value) {
        this.value.set(value);
    }
}
