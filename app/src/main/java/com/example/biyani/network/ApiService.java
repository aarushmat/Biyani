package com.example.biyani.network;




import com.example.biyani.Model.RegistrationModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by Juned on 7/26/2017.
 */

public interface ApiService
{

    @POST("Student/AddStudent")
    Call<ResponseBody> addStudent(@Body RegistrationModel registrationModel);

    @GET("State/GetState")
    Call<ResponseBody> getState();

    @GET("City/GetCity")
    Call<ResponseBody> getCity(@Query("StateId") String s);

    @GET("Student/GetSingle")
    Call<ResponseBody> getSingle(@Query("StudentId") String s);

    @GET("Category/GetCategory")
    Call<ResponseBody> getCategory(@Query("CourseId") String s);

    @POST("Student/ChangePassword")
    Call<ResponseBody> changePassword(@Query("StudentId") String s,@Query("OldPassword") String a,@Query("NewPassword") String b);

    @GET("Student/GetLogin")
    Call<ResponseBody> getLogin(@Query("Mobile") String a,@Query("Password") String s);

    @GET("Subject/GetSubject")
    Call<ResponseBody> getSubject(@Query("CourseId") String a,@Query("CategoryId") String s);

    @GET("Course/GetCourse")
    Call<ResponseBody> getCourse();


}
