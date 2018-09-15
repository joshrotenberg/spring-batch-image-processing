package com.joshrotenberg.springbatchimageprocessing.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Request {
    @Expose
    private List<Image> images;

    public Request() {
        images = new ArrayList<Image>();
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void addImage(Image image) {
        this.images.add(image);
    }
}
