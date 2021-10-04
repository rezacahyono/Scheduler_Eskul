package com.rcyono.schedulereskul.model.type;

import com.google.gson.annotations.SerializedName;

public class Type {

	@SerializedName("id")
	private String id;

	@SerializedName("title_type")
	private String titleType;

	@SerializedName("icon_type")
	private String iconType;

	public String getId(){
		return id;
	}

	public String getTitleType(){
		return titleType;
	}

	public String getIconType(){
		return iconType;
	}
}