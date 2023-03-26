package com.example.agriclutureassistant.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.agriclutureassistant.data.RemoteRequest;
import com.example.agriclutureassistant.pojo.PostModel;
import com.example.agriclutureassistant.pojo.PostRoot;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewModel extends ViewModel {

    private static final String TAG = "PostViewModel";

    private MutableLiveData<List<PostModel>> getAllPostsALiveData = new MutableLiveData<>();

    private RemoteRequest remoteRequest = new RemoteRequest();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MutableLiveData<List<PostModel>> getGetAllPostsALiveData() {
        return getAllPostsALiveData;
    }

    public void getAllPosts(){

        Single<PostRoot> observable =remoteRequest.getRequest().getAllPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        compositeDisposable.add(observable.subscribe(o-> getAllPostsALiveData.setValue(o.posts)));
    }

    public void setPost(PostModel postModel){

        remoteRequest.getRequest().addPost(postModel).enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                Log.d(TAG, "SHR: Post is sent successfully.");
            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {

            }
        });
    }

}
