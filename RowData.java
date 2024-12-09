public class RowData {
    private final String rowNumber;
    private final String system;
    private final String user;
    private final Boolean check;

    // Constructor
    public RowData(Integer rowNumber, String system, String user, Boolean check) {
        this.rowNumber = String.format("%02d", rowNumber);  // String for row number
        this.system = system;
        this.user = user;
        this.check = check;
    }

    // Getter for rowNumber
    public String getRowNumber() {
        return rowNumber;
    }

    // Getter for system
    public String getSystem() {
        return system;
    }

    // Getter for user
    public String getUser() {
        return user;
    }

    // Getter for check
    public Boolean getCheck() {
        return check;
    }
}