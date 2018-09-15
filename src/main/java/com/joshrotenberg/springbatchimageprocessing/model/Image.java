package com.joshrotenberg.springbatchimageprocessing.model;

public class Image {
    private String path;
    private String base64Encoding;
    private byte[] content;

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

    @Override
    public String toString() {
        return "Image{" +
                "path='" + path + '\'' +
                ", base64Encoding='" + base64Encoding + '\'' +
                '}';
    }
}
