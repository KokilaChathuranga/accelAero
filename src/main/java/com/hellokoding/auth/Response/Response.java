package com.hellokoding.auth.Response;

public class Response<T> {

	private ResponseStatus statusCode = ResponseStatus.UNKNOWN_ERROR;
	private String statusDescription = null;
	private T returnValue;

	public Response() {
	}

	public Response(T returnValue, ResponseStatus status, String description) {
		setReturnValue(returnValue);
		setStatusCode(status);
		setStatusDescription(description);
	}

	public ResponseStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(ResponseStatus statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public T getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(T returnValue) {
		this.returnValue = returnValue;
	}
}
