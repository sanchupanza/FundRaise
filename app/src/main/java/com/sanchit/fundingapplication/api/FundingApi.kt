package com.sanchit.fundingapplication.api

import com.sanchit.fundingapplication.models.FundResponse
import retrofit2.Response
import retrofit2.http.GET

interface FundingApi {

    @GET("testdata.json")
    suspend fun getFunds(
    ): Response<FundResponse>
}