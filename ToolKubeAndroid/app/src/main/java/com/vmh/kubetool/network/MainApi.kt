package com.vmh.kubetool.network

import com.vmh.kubetool.data.models.AccessToken
import com.vmh.kubetool.data.models.Post
import com.vmh.kubetool.data.models.User
import com.vmh.kubetool.data.source.remote.response.LinkResponse
import com.vmh.kubetool.data.source.remote.response.SignInResponse
import com.vmh.kubetool.data.source.remote.response.SignUpResponse
import com.vmh.kubetool.data.source.remote.response.StatusToolResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*


interface MainApi {

    @GET("https://jsonplaceholder.typicode.com/users/{id}")
    fun getUser(@Path("id") id: Int): Flowable<User>

    @POST("refresh")
    @FormUrlEncoded
    fun refreshToken(@Field("refreshToken") refreshToken: String): Single<AccessToken>

    @GET("https://jsonplaceholder.typicode.com/posts")
    fun getPostsFromUser(@Query("userId") id: Int): Flowable<List<Post>>

    @POST("users/login")
    @FormUrlEncoded
    fun login(@Field("phone_number") phoneNumber: String,
              @Field("password") password: String): Flowable<SignInResponse>

    @POST("users/signup")
    @FormUrlEncoded
    fun signUp(@Field("fullname") fullName: String,
               @Field("phone_number") phoneNumber: String,
               @Field("password") password: String): Observable<SignUpResponse>

    @GET("get_status_tool")
    fun getStatusTool(): Observable<StatusToolResponse>

    @POST("get_link")
    @FormUrlEncoded
    fun getLink(@Field("name") name: String): Observable<LinkResponse>

}
