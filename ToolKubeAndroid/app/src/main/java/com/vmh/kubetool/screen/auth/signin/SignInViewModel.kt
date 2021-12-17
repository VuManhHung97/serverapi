package com.vmh.kubetool.screen.auth.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vmh.kubetool.data.models.UserSave
import com.vmh.kubetool.data.source.Repository
import com.vmh.kubetool.data.source.remote.response.LinkResponse
import com.vmh.kubetool.data.source.remote.response.SignInResponse
import com.vmh.kubetool.screen.auth.AuthResource
import com.vmh.kubetool.utils.SessionManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val photoRepository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val linkLiveData: MutableLiveData<LinkResponse> = MutableLiveData()
    val loginLiveData: MutableLiveData<SignInResponse> = MutableLiveData()
    val linkAdminGmailLiveData: MutableLiveData<LinkResponse> = MutableLiveData()
    val errorLiveData: MutableLiveData<Throwable> = MutableLiveData()

    fun getLink(name: String) {
        compositeDisposable.add(
            photoRepository.getLink(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    linkLiveData.value = data
                } ,{ error ->
                    errorLiveData.value = error
                })
        )
    }

    fun getLinkAdminGmail() {
        compositeDisposable.add(
            photoRepository.getLink("AdminGmail")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    linkAdminGmailLiveData.value = data
                } ,{ error ->
                    errorLiveData.value = error
                })
        )
    }

    fun login(phoneNumber: String, password: String) {
        compositeDisposable.add(
            photoRepository.login(phoneNumber, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    loginLiveData.value = data
                } ,{ error ->
                    errorLiveData.value = error
                })
        )
    }

    fun authenticate(phoneNumber: String, password: String) {
        Log.d(TAG, "authenticateWithId: attempting to login.")
        sessionManager.authenticate(queryUser(phoneNumber, password))
    }

    private fun queryUser(phoneNumber: String, password: String): LiveData<AuthResource<out SignInResponse?>> {
        return LiveDataReactiveStreams.fromPublisher(photoRepository.login(phoneNumber, password)
            .onErrorReturn {
                val errorUser = SignInResponse("Auth failed", null)
                errorUser
            }.map {
                if (it.message == "Auth") {
                    AuthResource.error("Could not authenticate", null)
                } else {
                    AuthResource.authenticated(it)
                }
            }.subscribeOn(Schedulers.io())
        )
    }

    fun saveUser(username: String, password: String) {
        photoRepository.saveUser(username, password)
    }

    fun loadUser(): UserSave {
        return photoRepository.loadUser()
    }

    fun observerAuthState(): LiveData<AuthResource<out SignInResponse?>> {
        return sessionManager.getAuthUser()
    }

    fun clearAuthState() {
        sessionManager.clearAuth()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {
        private const val TAG = "SignInViewModel"
    }
}