package com.whatstools.statusSaver;

public class FileModel {
    private String imageFilePath;
    private String imgFileName;
    public Boolean isImageChecked;

    public void setImageFileName(String imageFileName) {
        this.imgFileName = imageFileName;
    }

    public Boolean getImageChecked() {
        return this.isImageChecked;
    }

    public void setImageChecked(Boolean imageChecked) {
        this.isImageChecked = imageChecked;
    }

    public String getImageFilePath() {
        return this.imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public String getImageFileName() {
        return this.imgFileName;
    }
}
