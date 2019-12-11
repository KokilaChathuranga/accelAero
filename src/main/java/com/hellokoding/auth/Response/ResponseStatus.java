package com.hellokoding.auth.Response;

public enum ResponseStatus {
	UNKNOWN_ERROR(-1),
	SUCCESS(0),
	ERROR(1),
	INVALID_CUSTOMER(2),
	BAD_REQUEST(3);

	private int status;

	ResponseStatus(int i) {
		this.status = i;
	}

	public int getStatus() {
		return status;
	}
}
