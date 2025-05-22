package com.bank.transactionmanagement.batch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.bank.transactionmanagement.entity.Transaction;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;

@Configuration
public class BatchConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void init() {
        System.out.println("JobRepository: " + jobRepository);
        System.out.println("TransactionManager: " + transactionManager);
    }

    @Bean
    public FlatFileItemReader<Map<String, String>> reader() {
        FlatFileItemReader<Map<String, String>> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("dataSource.txt"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setDelimiter("|");
                setNames("ACCOUNT_NUMBER", "TRX_AMOUNT", "DESCRIPTION", "TRX_DATE", "TRX_TIME", "CUSTOMER_ID");
            }});
            setFieldSetMapper(fieldSet -> {
                Map<String, String> map = new HashMap<>();
                map.put("ACCOUNT_NUMBER", fieldSet.readString("ACCOUNT_NUMBER"));
                map.put("TRX_AMOUNT", fieldSet.readString("TRX_AMOUNT"));
                map.put("DESCRIPTION", fieldSet.readString("DESCRIPTION"));
                map.put("TRX_DATE", fieldSet.readString("TRX_DATE"));
                map.put("TRX_TIME", fieldSet.readString("TRX_TIME"));
                map.put("CUSTOMER_ID", fieldSet.readString("CUSTOMER_ID"));
                return map;
            });
        }});
        return reader;
    }

    @Bean
    public TransactionItemProcessor processor() {
        return new TransactionItemProcessor();
    }

    @Bean
    public JpaItemWriter<Transaction> writer() {
        JpaItemWriter<Transaction> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<Map<String, String>, Transaction>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job importTransactionJob() {
        return new JobBuilder("importTransactionJob", jobRepository)
                .start(step1())
                .build();
    }
}