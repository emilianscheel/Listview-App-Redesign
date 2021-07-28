package de.emilianscheel.listview.Einstellungen;

public enum SettingsField {
    IS_NIGHT_MODE("isNightMode"),
    SHOWS_CHECK_BOXES("showsCheckBoxes"),
    DASHBOARD_TYPES_SHOWN("dashboardTypesShown"),
    DASHBOARD_TAGS_SHOWN("dashboardTagsShown");

    public String rawValue;

    public String getRawValue() {
        return this.rawValue;
    }

    private SettingsField(String value) {
        this.rawValue = value;
    }
}