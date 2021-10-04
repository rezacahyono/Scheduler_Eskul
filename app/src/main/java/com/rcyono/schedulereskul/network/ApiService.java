package com.rcyono.schedulereskul.network;

import com.rcyono.schedulereskul.model.event.EventResponse;
import com.rcyono.schedulereskul.model.schedule.ScheduleResponse;
import com.rcyono.schedulereskul.model.type.TypeResponse;
import com.rcyono.schedulereskul.model.user.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("login/login_user.php")
    Call<UserResponse> getUser(
            @Field("username") String username,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("login/login_user.php")
    Call<UserResponse> registUser(
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email);


    @GET("event/show_event.php")
    Call<EventResponse> getEvent();

    @FormUrlEncoded
    @POST("schedule/action_scheduler.php?action=getall")
    Call<ScheduleResponse> getAllSchedule(@Field("id") String id);

    @FormUrlEncoded
    @POST("schedule/action_scheduler.php?action=insert")
    Call<ScheduleResponse> addSchedule(
            @Field("id_user") String idUser,
            @Field("title_type") String titleType,
            @Field("desc") String desc,
            @Field("place") String place,
            @Field("date") String date,
            @Field("time_start") String timeStart,
            @Field("time_end") String timeEnd
    );

    @FormUrlEncoded
    @POST("schedule/action_scheduler.php?action=update")
    Call<ScheduleResponse> updateSchedule(
            @Field("id") String id,
            @Field(("id_user")) String idUser,
            @Field("title_type") String titleType,
            @Field("desc") String desc,
            @Field("place") String place,
            @Field("date") String date,
            @Field("time_start") String timeStart,
            @Field("time_end") String timeEnd
    );

    @FormUrlEncoded
    @POST("schedule/action_scheduler.php?action=delete")
    Call<ScheduleResponse> deleteSchedule(
            @Field("id") String id
    );

    @GET("type/type_eskul.php")
    Call<TypeResponse> getTypeEskul();

}
