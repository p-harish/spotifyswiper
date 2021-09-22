package me.harishpartha.spotifyswiper;

public class ImagesModal {

    private String songName, albumName, artistName, imgUrl;

    // Getters
    public String getImgUrl() {
        return imgUrl;
    }
    public String getsongName() {
        return songName;
    }
    public String getalbumName() {
        return albumName;
    }
    public String getartistName() {
        return artistName;
    }

    // Constructor
    public ImagesModal(String songName, String albumName, String artistName, String imgUrl) {
        this.songName = songName;
        this.albumName = albumName;
        this.artistName = artistName;
        this.imgUrl = imgUrl;
    }
}
