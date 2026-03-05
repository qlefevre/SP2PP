package com.github.qlefevre.sp2pp.settings;

public class Settings {

    private Bookmarks bookmarks;
    private AttributeTypes attributeTypes;
    private ConfigurationSets configurationSets;

    public Settings() {
    }

    public Bookmarks getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(Bookmarks bookmarks) {
        this.bookmarks = bookmarks;
    }

    public AttributeTypes getAttributeTypes() {
        return attributeTypes;
    }

    public void setAttributeTypes(AttributeTypes attributeTypes) {
        this.attributeTypes = attributeTypes;
    }

    public ConfigurationSets getConfigurationSets() {
        return configurationSets;
    }

    public void setConfigurationSets(ConfigurationSets configurationSets) {
        this.configurationSets = configurationSets;
    }
}