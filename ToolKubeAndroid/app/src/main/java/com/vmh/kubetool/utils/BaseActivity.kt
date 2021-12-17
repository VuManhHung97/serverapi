package com.vmh.kubetool.utils

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.vmh.kubetool.screen.auth.AuthActivity
import com.vmh.kubetool.screen.auth.AuthResource
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity: DaggerAppCompatActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        sessionManager!!.getAuthUser().observe(this, { userAuthResource ->
            if (userAuthResource != null) {
                when (userAuthResource.status) {
                    AuthResource.AuthStatus.LOADING -> {
                        Log.d(TAG, "onChanged: BaseActivity: LOADING...")
                    }

                    AuthResource.AuthStatus.AUTHENTICATED -> {
                        Log.d(TAG, "onChanged: BaseActivity: AUTHENTICATED..." )
                    }

                    AuthResource.AuthStatus.ERROR -> {
                        Log.d(TAG, "onChanged: BaseActivity: ERROR...")
                    }

                    AuthResource.AuthStatus.NOT_AUTHENTICATED -> {
                        Log.d(TAG, "onChanged: BaseActivity: NOT AUTHENTICATED")
                        navLoginScreen()
                    }

                }
            }
        })
    }

    private fun navLoginScreen() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val TAG = "BaseActivity"
    }
}