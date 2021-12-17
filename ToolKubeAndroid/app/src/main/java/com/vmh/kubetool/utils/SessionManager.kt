package com.vmh.kubetool.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.vmh.kubetool.data.source.remote.response.SignInResponse
import com.vmh.kubetool.screen.auth.AuthResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {
    private var cachedUser = MediatorLiveData<AuthResource<out SignInResponse?>>()

    fun authenticate(source: LiveData<AuthResource<out SignInResponse?>>) {
        cachedUser.value = AuthResource.loading(null)
        cachedUser.addSource(source) {
            cachedUser.value = it
            cachedUser.removeSource(source)
        }
    }

    fun logout() {
        Log.d(TAG, "logOut: logging out...")
        cachedUser.value = AuthResource.logout()
    }

    fun getAuthUser(): LiveData<AuthResource<out SignInResponse?>> {
        return cachedUser
    }

    fun clearAuth(){
        cachedUser = MediatorLiveData<AuthResource<out SignInResponse?>>()
    }

    companion object {
        private const val TAG = "SessionManager"
    }
}
