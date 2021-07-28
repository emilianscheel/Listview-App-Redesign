package de.emilianscheel.listview.Objekte;

public class ListItemAttribute {

    private String key;
    private String sValue = null;
    private Boolean bValue = null;
    private Integer iValue = null;

    public ListItemAttribute(String key, String value) {
        this.key = key;
        this.sValue = value;
    }

    public ListItemAttribute(String key, Boolean value) {
        this.key = key;
        this.bValue = value;
    }

    public ListItemAttribute(String key, Integer value) {
        this.key = key;
        this.iValue = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStringValue() {
        return sValue;
    }

    public Boolean getBooleanValue() {
        return bValue;
    }

    public Integer getIntegerValue() {
        return iValue;
    }

    public void setValue(String value) {
        this.sValue = value;
    }

    public void setValue(Boolean value) {
        this.bValue = value;
    }

    public void setValue(Integer value) {
        this.iValue = value;
    }
}
