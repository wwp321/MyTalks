package com.byron.mytalks.Talks;

public class TalksItem {

    private String author;
    private String title;
    private String duration;
    private int thumbnailId;

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public int getThumbnailId() {
        return  thumbnailId;
    }

    public TalksItem(String author, String title, String duration, int thumbnailId) {
        this.author = author;
        this.title = title;
        this.duration = duration;
        this.thumbnailId = thumbnailId;
    }
}
