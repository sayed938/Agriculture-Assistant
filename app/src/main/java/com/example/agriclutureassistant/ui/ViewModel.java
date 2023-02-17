package com.example.agriclutureassistant.ui;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.example.agriclutureassistant.ProjectData.api_key;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.agriclutureassistant.data.WeatherBuilder;
import com.example.agriclutureassistant.pojo.WeatherModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewModel extends androidx.lifecycle.ViewModel {
    public MutableLiveData<WeatherModel.Root> livedataWeather1 = new MutableLiveData<>();
    public MutableLiveData<List<WeatherModel.Hour>> livedataWeather2 = new MutableLiveData<>();

    @SuppressLint("CheckResult")
    public void getCurrentTemper() {

        Observable<WeatherModel.Root> observable = new WeatherBuilder().temperOBJ().getTemper(api_key, "Benha")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(o -> livedataWeather1.setValue(o), e -> Log.d(TAG, "ERROR : " + e));
        observable.subscribe(o -> livedataWeather2.setValue(o.forecast.forecastday.get(0).getHour()), e -> Log.d(TAG, "ERROR : " + e));
    }
}
