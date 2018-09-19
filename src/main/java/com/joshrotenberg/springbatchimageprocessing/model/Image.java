package com.joshrotenberg.springbatchimageprocessing.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Image implements Serializable {
    @Expose
    private String name;
    @Expose
    private String base64Encoding;

    public Image() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBase64Encoding() {
        return base64Encoding;
    }

    public void setBase64Encoding(String base64Encoding) {
        this.base64Encoding = base64Encoding;
    }

    @Override
    public String toString() {
        return "Image{" +
                "name='" + name + '\'' +
                ", base64Encoding='" + base64Encoding + '\'' +
                '}';
    }
}
