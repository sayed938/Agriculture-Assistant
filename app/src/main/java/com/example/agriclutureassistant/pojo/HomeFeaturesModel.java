package com.example.agriclutureassistant.pojo;

public class HomeFeaturesModel {

    String featureName;
   String images;

    public HomeFeaturesModel(String images, String featureName) {
        this.featureName = featureName;
        this.images = images;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
