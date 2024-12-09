package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InventoryData {
	private final StringProperty column1;
    private final StringProperty column2;
    private final StringProperty column3;

    public InventoryData(String column1, String column2, String column3) {
        this.column1 = new SimpleStringProperty(column1);
        this.column2 = new SimpleStringProperty(column2);
        this.column3 = new SimpleStringProperty(column3);
    }

    public String getColumn1() {
        return column1.get();
    }

    public StringProperty column1Property() {
        return column1;
    }

    public String getColumn2() {
        return column2.get();
    }

    public StringProperty column2Property() {
        return column2;
    }

    public String getColumn3() {
        return column3.get();
    }

    public StringProperty column3Property() {
        return column3;
    }
 }
