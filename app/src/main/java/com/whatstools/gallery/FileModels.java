package com.whatstools.gallery;

public class FileModels {
    private String imageFilePath;
    private String imgFileName;
    private Boolean isImageChecked;

    void setImageFileName(String imageFileName) {
        this.imgFileName = imageFileName;
    }

    Boolean getImageChecked() {
        return this.isImageChecked;
    }

    void setImageChecked(Boolean imageChecked) {
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
