package com.joshrotenberg.springbatchimageprocessing.reader;

import com.joshrotenberg.springbatchimageprocessing.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.FileInputStream;

public class ImageItemReader extends AbstractItemCountingItemStreamItemReader<Image>
        implements ResourceAwareItemReaderItemStream<Image> {
    private static final Logger log = LoggerFactory.getLogger(ImageItemReader.class);

    private Resource resource;
    private boolean read = false;

    public ImageItemReader() {
        setName(ClassUtils.getShortName(ImageItemReader.class));
    }

    @Override
    protected void doOpen() throws Exception {
        read = false;
    }

    @Override
    protected Image doRead() throws Exception {
        if (read) {
            return null;
        }

        log.info("Reading " + resource.getFilename());

        Image image = new Image(resource.getFilename());
        File file = resource.getFile();
        FileInputStream fileInputStreamReader = new FileInputStream(file);

        byte[] bytes = new byte[(int) file.length()];
        int l = fileInputStreamReader.read(bytes);
        image.setContent(bytes);
        image.setPath(file.getPath());

        read = true;
        return image;
    }

    @Override
    protected void doClose() {
        read = false;
    }

    @Override
    public void setResource(Resource resource) {
        log.info("Set resource to " + resource.getFilename());
        this.resource = resource;
    }
}
