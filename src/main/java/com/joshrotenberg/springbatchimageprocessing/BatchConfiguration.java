package com.joshrotenberg.springbatchimageprocessing;

import com.joshrotenberg.springbatchimageprocessing.listener.JobCompletionNotificationListener;
import com.joshrotenberg.springbatchimageprocessing.model.Image;
import com.joshrotenberg.springbatchimageprocessing.processor.ImageItemProcessor;
import com.joshrotenberg.springbatchimageprocessing.reader.ImageItemReader;
import com.joshrotenberg.springbatchimageprocessing.writer.ConsoleItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

//    @Value("images/*/*.jpg")
    @Value("images/n02086240-Shih-Tzu/*8.jpg")
    private Resource[] inputResources;

    @Bean
    public ImageItemReader reader() {
        return new ImageItemReader();
    }

    @Bean
    public ImageItemProcessor processor() {
        return new ImageItemProcessor();
    }

    @Bean
    public ConsoleItemWriter writer() {
        return new ConsoleItemWriter();
    }

    @Bean
    public MultiResourceItemReader<Image> multiResourceItemReader() {
        MultiResourceItemReader<Image> resourceItemReader = new MultiResourceItemReader<>();
        resourceItemReader.setResources(inputResources);
        resourceItemReader.setDelegate(reader());
        resourceItemReader.setName("readImages");
        return resourceItemReader;
    }

    @Bean
    public Step step(MultiResourceItemReader<Image> reader, ImageItemProcessor processor, ConsoleItemWriter writer) {
        return stepBuilderFactory.get("imageStep")
                .<Image, Image>chunk(20)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job job(JobCompletionNotificationListener listener, Step step1) {

        return jobBuilderFactory.get("imageJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }
}
