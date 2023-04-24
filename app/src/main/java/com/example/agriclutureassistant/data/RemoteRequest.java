package com.example.agriclutureassistant.data;

import com.example.agriclutureassistant.ProjectData;
import com.example.agriclutureassistant.pojo.CommentModel;
import com.example.agriclutureassistant.pojo.PostModel;
import com.example.agriclutureassistant.pojo.PostRoot;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteRequest {

    private RemoteRequest INSTANCE;
    private static final String key = "polahany";
    private static SocialApiService apiService;

    public RemoteRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ProjectData.BaseUrlSocial)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(SocialApiService.class);

    }

    public RemoteRequest getRequest() {
        if (INSTANCE == null)
            INSTANCE = new RemoteRequest();
        return INSTANCE;
    }

    public Observable<PostRoot> getAllPosts() {
        return apiService.getAllPosts(key);
    }

    public Observable<PostRoot> getComments(CommentModel commentModel) {
        return apiService.getComments(commentModel);
    }

    public Call<PostModel> addPost(PostModel postModel) {
        return apiService.setPost(postModel);
    }
}
