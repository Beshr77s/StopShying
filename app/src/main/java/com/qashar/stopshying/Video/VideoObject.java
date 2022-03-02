package com.qashar.stopshying.Video;

public class VideoObject {

    //here we need video url, name, and description

    public String videoURL, videoTitle, videoDescription;

    public VideoObject() {
    }

    public VideoObject(String videoURL, String videoTitle, String videoDescription) {
        this.videoURL = videoURL;
        this.videoTitle = videoTitle;
        this.videoDescription = videoDescription;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

}