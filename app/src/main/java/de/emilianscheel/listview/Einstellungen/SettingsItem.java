package de.emilianscheel.listview.Einstellungen;

enum SettingsItemType {
    SWITCH,
    SUBTITLE,
    BUTTON,
    TITLE
}

public class SettingsItem {

    private String title, value;
    private Boolean switchValue;
    private SettingsItemType type;
    private SettingsItemViewHolder viewHolder;

    public SettingsItem(String title, SettingsItemType type) {
        this.title = title;
        this.type = type;
        this.value = "Blau";
        this.viewHolder = new SettingsItemViewHolder() {
            @Override
            public void onBindViewHolder(SettingsAdapter.ExampleViewHolder holder, int position) {

            }
        };
    }

    public SettingsItem(String title, SettingsItemType type, SettingsItemViewHolder viewHolder) {
        this.title = title;
        this.type = type;
        this.viewHolder = viewHolder;
        this.value = "Blau";
    }

    public Boolean getSwitchValue() {
        return switchValue;
    }

    public void setSwitchValue(Boolean switchValue) {
        this.switchValue = switchValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SettingsItemType getType() {
        return type;
    }

    public void setType(SettingsItemType type) {
        this.type = type;
    }

    public void onBindViewHolder(SettingsAdapter.ExampleViewHolder holder, int position) {
        viewHolder.onBindViewHolder(holder, position);
    }

    public SettingsItemViewHolder getViewHolder() {
        return viewHolder;
    }

    public void setViewHolder(SettingsItemViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }
}
