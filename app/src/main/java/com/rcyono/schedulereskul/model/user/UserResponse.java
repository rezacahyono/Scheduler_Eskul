package com.rcyono.schedulereskul.model.user;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UserResponse{

	@SerializedName("success")
	private int success;

	@SerializedName("message")
	private String message;

	@SerializedName("user")
	private List<User> user;

	public int getSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}

	public List<User> getUser(){
		return user;
	}
}