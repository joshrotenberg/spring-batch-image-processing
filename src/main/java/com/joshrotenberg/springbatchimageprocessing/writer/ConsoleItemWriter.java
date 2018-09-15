package com.joshrotenberg.springbatchimageprocessing.writer;

import com.google.gson.Gson;
import com.joshrotenberg.springbatchimageprocessing.model.Image;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class ConsoleItemWriter implements ItemWriter<Image> {
    private Gson gson;

    public ConsoleItemWriter() {
        gson = new Gson();
    }

    @Override
    public void write(List<? extends Image> images) throws Exception {
        for (Image image : images) {
            System.out.println(gson.toJson(image.getBase64Encoding()));
        }
    }
}
