package com.joshrotenberg.springbatchimageprocessing.processor;

import com.joshrotenberg.springbatchimageprocessing.model.Image;
import okio.BufferedSource;
import okio.Okio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.nio.file.Path;

public class ImageItemProcessor implements ItemProcessor<Path, Image> {
    private static final Logger log = LoggerFactory.getLogger(ImageItemProcessor.class);

    @Override
    public Image process(final Path path) throws Exception {
        BufferedSource b = Okio.buffer(Okio.source(path));
        Image image = new Image();
        image.setName(path.getFileName().toString());
        image.setBase64Encoding(b.readByteString().base64());

        b.close();
        return image;
    }
}
