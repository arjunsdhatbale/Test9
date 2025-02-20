package com.main.Test9;

import com.fasterxml.jackson.annotation.JsonView;

public class JsonViewResponse<T> {
	@JsonView
	private final Class<?> view;

	private final T data;

	public JsonViewResponse(Class<?> view, T data) {
		this.view = view;
		this.data = data;
	}

	public Class<?> getView() {
		return view;
	}

	public T getData() {
		return data;
	}
}
