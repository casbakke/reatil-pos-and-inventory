package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class RowData {
    private final SimpleStringProperty rowNumber;
    private final SimpleStringProperty system;
    private final SimpleStringProperty user;
    private final SimpleBooleanProperty check;

    // Constructor
    public RowData(Integer rowNumber, String system, String user, Boolean check) {
        this.rowNumber = new SimpleStringProperty(String.format("%02d", rowNumber));
        this.system = new SimpleStringProperty(system);
        this.user = new SimpleStringProperty(user);
        this.check = new SimpleBooleanProperty(check);
    }

    // Getter for rowNumber
    public SimpleStringProperty rowNumberProperty() {
        return rowNumber;
    }

    public String getRowNumber() {
        return rowNumber.get();
    }

    // Getter for system
    public SimpleStringProperty systemProperty() {
        return system;
    }

    public String getSystem() {
        return system.get();
    }

    // Getter for user
    public SimpleStringProperty userProperty() {
        return user;
    }

    public String getUser() {
        return user.get();
    }

    // Getter for check
    public SimpleBooleanProperty checkProperty() {
        return check;
    }

    public Boolean getCheck() {
        return check.get();
    }
}


