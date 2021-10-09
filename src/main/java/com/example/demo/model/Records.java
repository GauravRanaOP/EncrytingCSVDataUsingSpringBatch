package com.example.demo.model;

import com.example.demo.config.AesEncryptDecrypt;

public class Records {

	private String Year;
	
	private String Industry_code;
	
	private String Industry_name;
	
	private String Size;
	
	private String Variable;
	private String Value;
	private String Unit;
	
	
	public Records() {
		
		
	}


	public Records(String year, String industry_code, String industry_name, String size, String variable, String value,
			String unit) {
		super();
		Year = year;
		Industry_code = industry_code;
		Industry_name = industry_name;
		Size = size;
		Variable = variable;
		Value = value;
		Unit = unit;
	}


	public String getYear() {
		return Year;
	}


	public void setYear(String year) {
		Year = year;
	}


	public String getIndustry_code() {
		return Industry_code;
	}


	public void setIndustry_code(String industry_code) {
		this.Industry_code = AesEncryptDecrypt.encrypt(industry_code);
	}


	public String getIndustry_name() {
		return Industry_name;
	}


	public void setIndustry_name(String industry_name) {
		Industry_name = industry_name;
	}


	public String getSize() {
		return Size;
	}


	public void setSize(String size) {
		Size = size;
	}


	public String getVariable() {
		return Variable;
	}


	public void setVariable(String variable) {
		this.Variable = AesEncryptDecrypt.encrypt(variable);
	}


	public String getValue() {
		return Value;
	}


	public void setValue(String value) {
		this.Value = AesEncryptDecrypt.encrypt(value);
	}


	public String getUnit() {
		return Unit;
	}


	public void setUnit(String unit) {
		this.Unit = AesEncryptDecrypt.encrypt(unit);
	}


	
	
}
