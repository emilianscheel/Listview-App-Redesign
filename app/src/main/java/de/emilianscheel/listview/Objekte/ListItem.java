package de.emilianscheel.listview.Objekte;

import java.util.ArrayList;

public class ListItem {

    private ArrayList<ListItemAttribute> attributes;

    public ListItem() {
        this.attributes = new ArrayList<>();
    }

    public ListItem(ArrayList<ListItemAttribute> attributes) {
        this.attributes = attributes;
    }

    public ArrayList<ListItemAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<ListItemAttribute> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(ListItemAttribute attribute) {
        this.attributes.add(attribute);
    }

    public Boolean setAttribute(String key, String value) {
        for (ListItemAttribute attribute : attributes) {
            if (attribute.getKey().equals(key)) {
                attribute.setValue(value);
                return true;
            }
        }
        return false;
    }

    public Boolean setAttribute(String key, Boolean value) {
        for (ListItemAttribute attribute : attributes) {
            if (attribute.getKey().equals(key)) {
                attribute.setValue(value);
                return true;
            }
        }
        return false;
    }

    public Boolean setAttribute(String key, Integer value) {
        for (ListItemAttribute attribute : attributes) {
            if (attribute.getKey().equals(key)) {
                attribute.setValue(value);
                return true;
            }
        }
        return false;
    }

    public String stringValueFor(String key) {
        for (ListItemAttribute attribute : attributes) {
            if (attribute.getKey().equals(key)) {
                return attribute.getStringValue();
            }
        }
        return "";
    }

    public Integer integerValueFor(String key) {
        for (ListItemAttribute attribute : attributes) {
            if (attribute.getKey().equals(key)) {
                return attribute.getIntegerValue();
            }
        }
        return -1;
    }

    public Boolean booleanValueFor(String key) {
        for (ListItemAttribute attribute : attributes) {
            if (attribute.getKey().equals(key)) {
                return attribute.getBooleanValue();
            }
        }
        return false;
    }
}
