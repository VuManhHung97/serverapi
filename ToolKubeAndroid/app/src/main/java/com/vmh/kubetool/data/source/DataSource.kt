package com.vmh.kubetool.data.source

import com.vmh.kubetool.data.models.Post
import com.vmh.kubetool.data.models.User
import com.vmh.kubetool.data.models.UserSave
import com.vmh.kubetool.data.source.remote.response.LinkResponse
import com.vmh.kubetool.data.source.remote.response.SignInResponse
import com.vmh.kubetool.data.source.remote.response.SignUpResponse
import com.vmh.kubetool.data.source.remote.response.StatusToolResponse
import io.reactivex.Flowable
import io.reactivex.Observable

interface DataSource {
    interface LocalDataSource {
        fun saveUser(username: String, password: String)
        fun loadUser(): UserSave
    }

    interface RemoteDataSource {
        fun login(phoneNumber: String, password: String): Flowable<SignInResponse>
        fun signUp(fullName: String, phoneNumber: String, password: String): Observable<SignUpResponse>
        fun posts(userId: Int): Flowable<List<Post>>
        fun getStatusTool(): Observable<StatusToolResponse>
        fun getLink(name: String): Observable<LinkResponse>
    }
}
