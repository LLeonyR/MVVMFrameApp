package com.leonyr.mvvmframe;

import com.leonyr.mvvmframe.model.Momodiy;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * ==============================================================
 * Description:
 * <p>
 * Created by 01385127 on 2019.04.29
 * (C) Copyright sf_Express Corporation 2014 All Rights Reserved.
 * ==============================================================
 */
public interface GitHubApi {

    @GET("/users/momodiy")
    Observable<Momodiy> gitMomodiy();

}
