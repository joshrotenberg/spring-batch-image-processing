package com.joshrotenberg.springbatchimageprocessing.processor;

import com.joshrotenberg.springbatchimageprocessing.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.Base64;

public class ImageItemProcessor  implements ItemProcessor<Image, Image> {
    private static final Logger log = LoggerFactory.getLogger(ImageItemProcessor.class);

    // This could probably just get done in the reader instead.
    @Override
    public Image process(final Image image) throws Exception {
        log.debug("Processing " + image.getPath());
        image.setBase64Encoding(Base64.getEncoder().encodeToString(image.getContent()));

        return image;
    }
}
