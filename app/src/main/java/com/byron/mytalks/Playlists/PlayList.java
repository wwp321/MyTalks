package com.byron.mytalks.Playlists;

public class PlayList {
    private String playlistName;
    private String talkNumber;
    private int totalDuration;
    private int playlistThumbnail;

    public PlayList(String playlistName, int talkNumber, int totalDuration, int playlistThumbnail) {
        this.playlistName = playlistName;
        this.talkNumber = talkNumber + " talks";
        this.totalDuration = totalDuration;
        this.playlistThumbnail = playlistThumbnail;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public String getTalkNumber() {
        return talkNumber;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public int getPlaylistThumbnail() {
        return playlistThumbnail;
    }
}
