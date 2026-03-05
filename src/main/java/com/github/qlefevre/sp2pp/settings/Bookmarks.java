package com.github.qlefevre.sp2pp.settings;

import java.util.ArrayList;
import java.util.List;

public class Bookmarks {
    private List<Bookmark> bookmark = new ArrayList<>();

    public Bookmarks() {
    }

    public List<Bookmark> getBookmark() {
        return bookmark;
    }

    public void setBookmark(List<Bookmark> bookmark) {
        this.bookmark = bookmark;
    }
}