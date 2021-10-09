package com.example.demo.config;

import org.springframework.batch.item.ItemProcessor;

import com.example.demo.model.Records;

public class RecordsProcessor implements ItemProcessor<Records, Records>{

	@Override
	public Records process(Records item) throws Exception {
		// TODO Auto-generated method stub
		return item;
	}

}