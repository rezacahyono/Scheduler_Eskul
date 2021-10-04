package com.rcyono.schedulereskul.model.schedule;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Schedule implements Parcelable {

	@SerializedName("date")
	private String date;

	@SerializedName("time_start")
	private String timeStart;

	@SerializedName("id")
	private String id;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("place")
	private String place;

	@SerializedName("time_end")
	private String timeEnd;

	@SerializedName("title_type")
	private String titleType;

	@SerializedName("icon_type")
	private String iconType;

	@SerializedName("desc")
	private String desc;

	protected Schedule(Parcel in) {
		date = in.readString();
		timeStart = in.readString();
		id = in.readString();
		idUser = in.readString();
		place = in.readString();
		timeEnd = in.readString();
		titleType = in.readString();
		iconType = in.readString();
		desc = in.readString();
	}

	public Schedule() {}

	public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
		@Override
		public Schedule createFromParcel(Parcel in) {
			return new Schedule(in);
		}

		@Override
		public Schedule[] newArray(int size) {
			return new Schedule[size];
		}
	};

	public String getDate(){
		return date;
	}

	public String getTimeStart(){
		return timeStart;
	}

	public String getId(){
		return id;
	}

	public String getIdUser(){
		return idUser;
	}

	public String getPlace(){
		return place;
	}

	public String getTimeEnd(){
		return timeEnd;
	}

	public String getTitleType(){
		return titleType;
	}

	public String getIconType(){
		return iconType;
	}

	public String getDesc(){
		return desc;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public void setTitleType(String titleType) {
		this.titleType = titleType;
	}

	public void setIconType(String iconType) {
		this.iconType = iconType;
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
		parcel.writeString(date);
		parcel.writeString(timeStart);
		parcel.writeString(id);
		parcel.writeString(idUser);
		parcel.writeString(place);
		parcel.writeString(timeEnd);
		parcel.writeString(titleType);
		parcel.writeString(iconType);
		parcel.writeString(desc);
	}
}