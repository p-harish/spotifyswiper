package me.harishpartha.spotifyswiper;

import android.content.Context;

import java.util.ArrayList;

public class Recommendation {

    String name;
    String cover;
    String artist;
    String url;
    String album;

    public Recommendation(String name, String album, String artist, String url, String cover) {
        this.name = name;
        this.cover = cover;
        this.artist = artist;
        this.url = url;
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
