package com.joshrotenberg.springbatchimageprocessing.writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joshrotenberg.springbatchimageprocessing.model.Image;
import com.joshrotenberg.springbatchimageprocessing.model.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class ConsoleItemWriter implements ItemWriter<Image>, StepExecutionListener {
    private static final Logger log = LoggerFactory.getLogger(ConsoleItemWriter.class);

    private Gson gson;

    public ConsoleItemWriter() {
        gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Step!");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Step done!");
        return ExitStatus.COMPLETED;
    }

    @Override
    public void write(List<? extends Image> images) throws Exception {
        Request r = new Request();
        for (Image image : images) {
            r.addImage(image);
        }
        log.info("Created request: " + gson.toJson(r));
    }
}
