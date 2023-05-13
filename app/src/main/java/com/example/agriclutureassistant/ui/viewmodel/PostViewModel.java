package com.example.agriclutureassistant.ui.viewmodel;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.agriclutureassistant.data.RemoteRequest;
import com.example.agriclutureassistant.ui.pojo.CommentModel;
import com.example.agriclutureassistant.ui.pojo.PostModel;
import com.example.agriclutureassistant.ui.pojo.PostRoot;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewModel extends ViewModel {

    private static final String TAG = "PostViewModel";

    private MutableLiveData<List<PostModel>> getAllPostsALiveData = new MutableLiveData<>();
    private MutableLiveData<List<CommentModel>> commentsLiveData = new MutableLiveData<>();

    private RemoteRequest remoteRequest = new RemoteRequest();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MutableLiveData<List<PostModel>> getGetAllPostsALiveData() {
        return getAllPostsALiveData;
    }

    public MutableLiveData<List<CommentModel>> getCommentsLiveData() {
        return commentsLiveData;
    }

    @SuppressLint("CheckResult")
    public void getAllPosts() {

        Observable<PostRoot> observable = remoteRequest.getRequest().getAllPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        try {
            compositeDisposable.add(observable.subscribe(o -> getAllPostsALiveData.setValue(o.posts)));
        }catch (Exception e){
            Log.d(TAG, "SHR: "+e.getMessage());
        }
    }

    public void setPost(PostModel postModel) {

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

    @SuppressLint("CheckResult")
    public void getAllComments(CommentModel commentModel) {
        Observable<PostRoot> observable = remoteRequest.getRequest().getComments(commentModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(o -> commentsLiveData.setValue(o.Commint));
    }
}
