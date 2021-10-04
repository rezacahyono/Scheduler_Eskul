package com.rcyono.schedulereskul.model.event;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class EventResponse{

	@SerializedName("image")
	private List<Event> event;

	@SerializedName("success")
	private int success;

	@SerializedName("message")
	private String message;

	public List<Event> getImage(){
		return event;
	}

	public int getSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}