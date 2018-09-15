package com.joshrotenberg.springbatchimageprocessing.model;

import com.google.gson.annotations.Expose;

public class Image {
    @Expose
    private String path;
    @Expose
    private String base64Encoding;
    private byte[] content;
    @Expose
    private long length;

    public Image() {

    }

    public Image(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBase64Encoding() {
        return base64Encoding;
    }

    public void setBase64Encoding(String base64Encoding) {
        this.base64Encoding = base64Encoding;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Image{" +
                "path='" + path + '\'' +
                ", base64Encoding='" + base64Encoding + '\'' +
                '}';
    }
}
