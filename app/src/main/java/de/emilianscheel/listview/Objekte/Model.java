package de.emilianscheel.listview.Objekte;

import android.content.Context;

import java.util.ArrayList;
import java.util.Objects;

public class Model {

    private Context context;

    public Model(Context context) {
        this.context = context;
    }

    public DashboardListItem loadList(String identifier) {

        ArrayList<DashboardListItem> lists = loadAllLists();

        for (DashboardListItem list : lists) {
            if (Objects.equals(list.getIdentifier(), identifier)) {
                return list;
            }
        }

        return new DashboardListItem();
    }

    public void saveList(DashboardListItem item) {

        ArrayList<DashboardListItem> lists = loadAllLists();

        for (int i = 0; i < lists.size(); i ++) {
            if (lists.get(i).getIdentifier().equals(item.getIdentifier())) {
                lists.set(i, item);
            }
        }
        saveAllLists(lists);
    }

    public void saveAllLists(ArrayList<DashboardListItem> allLists) {
        new Defaults(context).setArray("allLists", allLists);
    }

    public ArrayList<DashboardListItem> loadAllLists() {
        ArrayList<DashboardListItem> allLists = new Defaults(context).getArray("allLists", DashboardListItem[].class);

        if (allLists == null) {
            allLists = new ArrayList<>();
        }

        return allLists;
    }

    public void saveBookmarks(ArrayList<ListItem> allLists) {
        new Defaults(context).setArray("bookmarkList", allLists);
    }

    public ArrayList<ListItem> loadBookmarks() {
        ArrayList<ListItem> list = new Defaults(context).getArray("bookmarkList", ListItem[].class);

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    public ArrayList<ListTag> loadAllTags() {
        ArrayList<DashboardListItem> lists = loadAllLists();
        ArrayList<ListTag> tags = new ArrayList<>();

        for (DashboardListItem list : lists) {
            tags.addAll(list.getTags());
        }
        return tags;
    }

    public ArrayList<ListType> loadAllTypes() {
        ArrayList<DashboardListItem> lists = loadAllLists();
        ArrayList<ListType> allTypes = new ArrayList<>();
        ArrayList<ListType> uniqTypes = new ArrayList<>();
        for (DashboardListItem list : lists) {
            allTypes.add(list.getType());
        }
        for (ListType type : allTypes) {
            if (!type.existsIn(uniqTypes)) {
                uniqTypes.add(type);
            }
        }
        return uniqTypes;
    }

    public ArrayList<ListItem> loadExampleItems(DashboardListItem listItem) {

        ArrayList<DashboardListItem> lists = loadAllLists();
        ArrayList<DashboardListItem> filteredList = new ArrayList<>();
        ArrayList<ListItem> addItems = new ArrayList<>();
        for (DashboardListItem list : lists) {
            if (list.getType().identifier.equals(listItem.getType().identifier) && !list.getIdentifier().equals(listItem.getIdentifier())) {
                filteredList.add(list);
            }
        }
        for (DashboardListItem list : filteredList) {
            addItems.addAll(list.getItems());
        }

        return addItems;
    }
}
