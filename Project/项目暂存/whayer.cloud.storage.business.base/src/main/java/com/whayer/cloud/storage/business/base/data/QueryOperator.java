package com.whayer.cloud.storage.business.base.data;

public class QueryOperator {

	public static enum Operator {
		LESS, LESS_OR_EQUAL, EQUAL, NOT_EQUAL, GREATER_OR_EQUAL, GREATER, NO_OP, LIKE, REGEX, IN
	}

	public QueryOperator(Operator operator) {
		// TODO Auto-generated constructor stub
		this.operator = operator;
	}

	private Operator operator;

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}
}
