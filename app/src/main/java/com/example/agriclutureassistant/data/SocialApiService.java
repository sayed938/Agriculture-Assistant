package com.example.agriclutureassistant.data;

import com.example.agriclutureassistant.pojo.CommentModel;
import com.example.agriclutureassistant.pojo.PostModel;
import com.example.agriclutureassistant.pojo.PostRoot;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SocialApiService {

    @GET("show_posts.php")
    Single<PostRoot> getAllPosts(@Query("Passwors") String password);

    @POST("add_post.php")
    Call<PostModel> setPost(@Body PostModel postModel);

    @POST("getCommintPost.php")
    Single<PostRoot> getComments(@Body CommentModel commentModel);
}
