package com.vmh.kubetool.data.source

import com.vmh.kubetool.data.models.Post
import com.vmh.kubetool.data.models.UserSave
import com.vmh.kubetool.data.source.local.LocalDataSource
import com.vmh.kubetool.data.source.remote.RemoteDataSource
import com.vmh.kubetool.data.source.remote.response.LinkResponse
import com.vmh.kubetool.data.source.remote.response.SignInResponse
import com.vmh.kubetool.data.source.remote.response.SignUpResponse
import com.vmh.kubetool.data.source.remote.response.StatusToolResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class Repository @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) {
    fun login(phoneNumber: String, password: String): Flowable<SignInResponse> {
        return remote.login(phoneNumber, password)
    }

    fun signUp(fullName: String, phoneNumber: String, password: String): Observable<SignUpResponse> {
        return remote.signUp(fullName, phoneNumber, password)
    }

    fun postFromUser(userId: Int): Flowable<List<Post>> {
        return remote.posts(userId)
    }

    fun getStatusTool(): Observable<StatusToolResponse> {
        return remote.getStatusTool()
    }

    fun getLink(name: String): Observable<LinkResponse> {
        return remote.getLink(name)
    }

    fun saveUser(username: String, password: String) {
        return local.saveUser(username, password)
    }

    fun loadUser(): UserSave {
        return local.loadUser()
    }
}
