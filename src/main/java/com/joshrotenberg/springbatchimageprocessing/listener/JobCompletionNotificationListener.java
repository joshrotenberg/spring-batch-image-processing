package com.joshrotenberg.springbatchimageprocessing.listener;

import com.joshrotenberg.springbatchimageprocessing.processor.ImageItemProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    private static final Logger log = LoggerFactory.getLogger(ImageItemProcessor.class);

    @Autowired
    public JobCompletionNotificationListener() {
    }

    @Override
    public void beforeJob(JobExecution execution) {

    }

    @Override
    public void afterJob(JobExecution execution) {
        if(execution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Job completed!");
        }
    }
}
