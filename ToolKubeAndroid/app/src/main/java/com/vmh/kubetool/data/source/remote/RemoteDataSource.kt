package com.vmh.kubetool.data.source.remote

import com.vmh.kubetool.data.models.Post
import com.vmh.kubetool.data.source.DataSource
import com.vmh.kubetool.data.source.remote.response.LinkResponse
import com.vmh.kubetool.data.source.remote.response.SignInResponse
import com.vmh.kubetool.data.source.remote.response.SignUpResponse
import com.vmh.kubetool.data.source.remote.response.StatusToolResponse
import com.vmh.kubetool.network.MainApi
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val mainApi: MainApi) :
    DataSource.RemoteDataSource {

    override fun posts(userId: Int): Flowable<List<Post>> {
        return mainApi.getPostsFromUser(userId)
    }

    override fun login(phoneNumber: String, password: String): Flowable<SignInResponse> {
        return mainApi.login(phoneNumber, password)
    }

    override fun getStatusTool(): Observable<StatusToolResponse> {
        return mainApi.getStatusTool()
    }

    override fun getLink(name: String): Observable<LinkResponse> {
        return mainApi.getLink(name)
    }

    override fun signUp(
        fullName: String,
        phoneNumber: String,
        password: String
    ): Observable<SignUpResponse> {
        return mainApi.signUp(fullName, phoneNumber, password)
    }
}
