package com.cetc28.seu.query.struct;

import java.util.HashMap;
import java.util.List;

public class QueryCondition implements QueryElement{
	private String family;
	private HashMap<String,String> conditions;//查询使用的条�? 列名 和�??
	private List<String> answer;//�?要查询的列名
	
	
	public QueryCondition(String family, HashMap<String, String> conditions, List<String> answer) {
		super();
		this.family = family;
		this.conditions = conditions;
		this.answer = answer;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public HashMap<String, String> getConditions() {
		return conditions;
	}

	public void setConditions(HashMap<String, String> conditions) {
		this.conditions = conditions;
	}

	public List<String> getAnswer() {
		return answer;
	}

	public void setAnswer(List<String> answer) {
		this.answer = answer;
	}
	
}
