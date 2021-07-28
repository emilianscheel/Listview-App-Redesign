package de.emilianscheel.listview.Objekte;

import java.util.ArrayList;

public class ListTag {

    public String name;
    public String identifier;

    public ListTag(String name, String identifier) {
        this.name = name;
        this.identifier = identifier;
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

    public Boolean compare(ListTag type2) {
        return ListTag.compare(this, type2);
    }

    public Boolean compareName(ListTag type2) {
        return ListTag.compareName(this, type2);
    }

    public static Boolean compare(ListTag type1, ListTag type2) {
        return type1.identifier == type2.identifier;
    }

    public static Boolean compareName(ListTag type1, ListTag type2) {
        return type1.name.toLowerCase() == type2.name.toLowerCase();
    }

    public static ListTag verify(ArrayList<ListTag> tags, String tagName) {
        for (ListTag tag : tags) {
            if (tag.name.equals(tagName)) {
                return tag;
            }
        }
        return null;
    }
}
