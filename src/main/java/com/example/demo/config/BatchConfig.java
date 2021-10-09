package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.example.demo.model.Records;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private DataSource datasource;
	
	@Autowired
	private JobBuilderFactory jobbuilderfactory;
	
	@Autowired
	private StepBuilderFactory stepbuilderfactory;
	
	@Bean
	public FlatFileItemReader<Records> reader(){
		FlatFileItemReader<Records> reader=new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("annual-enterprise-survey-2020-financial-year-provisional-size-bands-csv.csv"));    //Insert CSV file inside ClassPathResource
		reader.setLineMapper(getLineMapper());
		reader.setLinesToSkip(1);
		return reader;
	}
		
	
	private LineMapper<Records> getLineMapper() {
		// TODO Auto-generated method stub
		DefaultLineMapper<Records> lineMapper= new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer=new DelimitedLineTokenizer();
		
		lineTokenizer.setNames(new String []{"Year","Industry_code","Industry_name","Size","Variable","Value","Unit"});
		lineTokenizer.setIncludedFields(new int[] {0,1,2,3,4,5,6});
		
		BeanWrapperFieldSetMapper<Records> fieldSetter =new BeanWrapperFieldSetMapper<>();
		fieldSetter.setTargetType(Records.class);
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetter);
		return lineMapper;
		
	}	
	
	@Bean
	public RecordsProcessor processor() {
		return new RecordsProcessor();
	}
	
	@Bean
	public JdbcBatchItemWriter<Records> writer(){
		JdbcBatchItemWriter<Records> writer= new JdbcBatchItemWriter<>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Records>());
        writer.setSql("insert into records(Year,Industry_code,Industry_name,Size,Variable,Value,Unit) values (:Year,:Industry_code,:Industry_name,:Size,:Variable,:Value,:Unit)");     
		writer.setDataSource(this.datasource);
		return writer;
	}
	
	
	@Bean
	public Job importrecordsjob() {
		return this.jobbuilderfactory.get("RECORDS-IMPORT-JOB")
				.incrementer(new RunIdIncrementer())
				.flow(step1())
				.end()
				.build();
	}

	@Bean
	public Step step1() {
		// TODO Auto-generated method stub
		return this.stepbuilderfactory.get("step1")
				.<Records,Records>chunk(100)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}
	
	
}




























