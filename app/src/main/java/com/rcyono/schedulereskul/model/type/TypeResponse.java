package com.rcyono.schedulereskul.model.type;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TypeResponse{

	@SerializedName("success")
	private int success;

	@SerializedName("type")
	private List<Type> type;

	@SerializedName("message")
	private String message;

	public int getSuccess(){
		return success;
	}

	public List<Type> getType(){
		return type;
	}

	public String getMessage(){
		return message;
	}
}