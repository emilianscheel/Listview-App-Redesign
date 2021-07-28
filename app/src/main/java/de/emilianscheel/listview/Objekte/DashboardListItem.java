package de.emilianscheel.listview.Objekte;

import android.text.format.DateFormat;

import java.util.*;

public class DashboardListItem {

    private String name;
    private String identifier;
    private ArrayList<ListItem> items;
    private ListType type;
    private ArrayList<ListTag> tags;
    private long timestamp;

    public DashboardListItem() {}

    public DashboardListItem(String name, ListType type, ArrayList<ListTag> tags) {
        this.name = name;
        this.items = new ArrayList<>();
        this.type = type;
        this.tags = tags;
        this.timestamp = System.currentTimeMillis() / 1000;
        this.identifier = UUID.randomUUID().toString();
    }

    public DashboardListItem(String name, ArrayList<ListItem> items, ListType type, ArrayList<ListTag> tags, Integer timestamp) {
        this.name = name;
        this.items = items;
        this.type = type;
        this.tags = tags;
        this.timestamp = timestamp;
        this.identifier = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ListItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<ListItem> items) {
        this.items = items;
    }

    public ListType getType() {
        return type;
    }

    public void setType(ListType type) {
        this.type = type;
    }

    public ArrayList<ListTag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<ListTag> tags) {
        this.tags = tags;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void addItem(ListItem item) {
        this.items.add(item);
    }

    public void addTag(ListTag tag) {
        this.tags.add(tag);
    }

    public void removeItem(int position) {
        this.items.remove(position);
    }

    public void removeItem(ListItem item) {
        this.items.remove(item);
    }

    public void removeTag(ListTag tag) {
        for (int i = 0; i < this.tags.size(); i++) {
            if (this.tags.get(i).identifier.equals(tag.identifier)) {
                this.tags.remove(i);
            }
        }
    }

    public String getDate(String format) {
        Calendar calendar = Calendar.getInstance(Locale.GERMANY);
        calendar.setTimeInMillis(this.timestamp * 1000);
        return DateFormat.format(format, calendar).toString();
    }
}
