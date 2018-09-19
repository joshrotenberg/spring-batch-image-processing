package com.joshrotenberg.springbatchimageprocessing;

import com.joshrotenberg.springbatchimageprocessing.listener.JobCompletionNotificationListener;
import com.joshrotenberg.springbatchimageprocessing.model.Image;
import com.joshrotenberg.springbatchimageprocessing.processor.ImageItemProcessor;
import com.joshrotenberg.springbatchimageprocessing.writer.ConsoleItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

    @Value("${batch.input:images/}")
    private String inputResources;

    @Value("${batch.chunksize:20}")
    private Integer chunkSize;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public IteratorItemReader<Path> itemReader() throws IOException {
        ClassPathResource resource = new ClassPathResource(inputResources);
        Stream<Path> sp = Files.walk(Paths.get(resource.getFile().getAbsolutePath()));
        Iterator<Path> iterator = sp.filter(path -> Files.isRegularFile(path) && Files.isReadable(path))
                .iterator();

        return new IteratorItemReader<>(iterator);
    }

    @Bean
    public ImageItemProcessor processor() {
        return new ImageItemProcessor();
    }

    @Bean
    public ConsoleItemWriter<Image> writer() {
        return new ConsoleItemWriter<>();
    }

    @Bean
    public Step step(IteratorItemReader<Path> itemReader, ImageItemProcessor processor, ConsoleItemWriter writer, TaskExecutor taskExecutor) {
        return stepBuilderFactory.get("imageStep")
                .<Path, Image>chunk(chunkSize)
                .reader(itemReader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job job(JobCompletionNotificationListener listener, Step step) {

        return jobBuilderFactory.get("imageJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step)
                .end()
                .build();
    }
}
