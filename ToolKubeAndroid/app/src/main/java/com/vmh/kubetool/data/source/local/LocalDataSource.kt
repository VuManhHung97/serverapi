package com.vmh.kubetool.data.source.local

import com.vmh.kubetool.data.models.User
import com.vmh.kubetool.data.models.UserSave
import com.vmh.kubetool.data.source.DataSource
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val sharedPrefsApi: SharedPrefsApi,
    private val photoDAO: DAO
): DataSource.LocalDataSource {
    override fun saveUser(username: String, password: String) {
        sharedPrefsApi.put(SharedPrefsKey.USER_NAME, username)
        sharedPrefsApi.put(SharedPrefsKey.PASSWORD, password)
    }

    override fun loadUser(): UserSave {
        return UserSave(
            username = sharedPrefsApi[SharedPrefsKey.USER_NAME, String::class.java]!!,
            password = sharedPrefsApi[SharedPrefsKey.PASSWORD, String::class.java]!!
        )
    }
}
