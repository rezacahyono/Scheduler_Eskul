package com.rcyono.schedulereskul.model.schedule;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ScheduleResponse{

	@SerializedName("schedule")
	private List<Schedule> schedule;

	@SerializedName("success")
	private int success;

	@SerializedName("message")
	private String message;

	public List<Schedule> getSchedule(){
		return schedule;
	}

	public int getSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}