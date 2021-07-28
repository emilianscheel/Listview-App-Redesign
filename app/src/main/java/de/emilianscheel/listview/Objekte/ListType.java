package de.emilianscheel.listview.Objekte;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class ListType {

    public String name;
    public String identifier;

    public ListType() {}

    public ListType(String name) {
        this.name = name;
        this.identifier = UUID.randomUUID().toString();
    }

    public ListType(String name, String identifier) {
        this.name = name;
        this.identifier = identifier;
    }

    public Boolean compare(ListType type2) {
        return ListType.compare(this, type2);
    }

    public static Boolean compare(ListType type1, ListType type2) {
        return type1.identifier.equals(type2.identifier);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Boolean existsIn(ArrayList<ListType> types) {
        for (ListType type : types) {
            if (Objects.equals(type.getIdentifier(), this.identifier)) {
                return true;
            }
        }
        return false;
    }
}
