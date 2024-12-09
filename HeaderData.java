package application;

public class HeaderData {
    private String system;
    private String user;
    private String checkNum;

    public HeaderData(String checkNum) {
        this.system = "System 1";
        this.user = "cbakke";
        this.checkNum = checkNum;
    }

    public String getSystem() {
        return system;
    }

    public String getUser() {
        return user;
    }

    public String getCheck() {
        return checkNum;
    }
    
    public void setCheck(String newCheck) {
    	this.checkNum = newCheck;
    }
}