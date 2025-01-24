package com.suno.android.sunointerview.network

import com.suno.android.sunointerview.data.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MediaService {

    @GET("songs")
    suspend fun getSongs(
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10
    ) : Response<ApiResponse>
    
}