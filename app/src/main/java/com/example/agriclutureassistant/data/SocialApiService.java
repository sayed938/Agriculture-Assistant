package com.example.agriclutureassistant.data;

import android.net.Uri;

import com.example.agriclutureassistant.ui.pojo.AddComment;
import com.example.agriclutureassistant.ui.pojo.CommentModel;
import com.example.agriclutureassistant.ui.pojo.PlantsDiseases;

import com.example.agriclutureassistant.ui.pojo.PlantsTypes;
import com.example.agriclutureassistant.ui.pojo.PostModel;
import com.example.agriclutureassistant.ui.pojo.PostRoot;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface SocialApiService {

    @GET("show_posts.php")
    Observable<PostRoot> getAllPosts(@Query("Passwors") String password);

    @POST("add_post.php")
    Call<PostModel> setPost(@Body PostModel postModel);

    @POST("getCommintPost.php")
    Observable<PostRoot> getComments(@Body CommentModel commentModel);

    @POST("add_commnt.php")
    Call<AddComment> postComments(@Body HashMap<Object, Object> map);

    @Multipart
    @POST("/v2/identify/all")
    Call<PlantsTypes.Root> postPlant(@Part List<MultipartBody.Part> images,@Query("api-key") String api_key);

    @POST("plant_disease.php")
    Call<PlantsDiseases> postPlantDisease(@Body HashMap<Object, Object> plant_map);
}
