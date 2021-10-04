package com.rcyono.schedulereskul.model.event;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Event implements Parcelable {

	@SerializedName("image_path")
	private String imagePath;

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	@SerializedName("desc")
	private String desc;

	protected Event(Parcel in) {
		imagePath = in.readString();
		id = in.readString();
		title = in.readString();
		desc = in.readString();
	}

	public static final Creator<Event> CREATOR = new Creator<Event>() {
		@Override
		public Event createFromParcel(Parcel in) {
			return new Event(in);
		}

		@Override
		public Event[] newArray(int size) {
			return new Event[size];
		}
	};

	public Event() {}

	public String getImagePath(){
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getId(){
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle(){
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc(){
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(imagePath);
		parcel.writeString(id);
		parcel.writeString(title);
		parcel.writeString(desc);
	}
}