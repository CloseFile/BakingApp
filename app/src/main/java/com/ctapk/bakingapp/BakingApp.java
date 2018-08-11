package com.ctapk.bakingapp;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ctapk.bakingapp.api.QueryApi;
import com.ctapk.bakingapp.db.AppDB;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Android Application class. Used for accessing singletons.
 */
public class BakingApp extends Application {
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    private AppExecutors mAppExecutors;
    private static QueryApi mQueryApi;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер JSON'а в объекты Gson
                .build();
        mQueryApi = retrofit.create(QueryApi.class); //Создаем объект, при помощи которого будем выполнять запросы
    }
    public static QueryApi getApi() { return mQueryApi; }
    public AppDB getDatabase() { return AppDB.getInstance(this, mAppExecutors);}
    public DataRepository getRepository() { return DataRepository.getInstance(getDatabase());}

    public boolean deviceIsOnline() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
