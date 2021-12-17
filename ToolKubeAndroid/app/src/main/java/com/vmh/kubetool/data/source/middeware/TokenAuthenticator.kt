package com.vmh.kubetool.data.source.middeware

import com.vmh.kubetool.data.models.AccessToken
import com.vmh.kubetool.data.source.local.SharedPrefsApi
import com.vmh.kubetool.data.source.local.SharedPrefsKey
import com.vmh.kubetool.network.TokenService
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenService: TokenService,
    private val sharedPrefsApi: SharedPrefsApi
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request {
        val newAccessToken =
            tokenService.getApiService()!!
                .refreshToken(sharedPrefsApi[SharedPrefsKey.ACCESS_TOKEN, AccessToken::class.java]!!.refreshToken)
                .blockingGet()
        sharedPrefsApi.put(SharedPrefsKey.ACCESS_TOKEN, newAccessToken)
        return response.request.newBuilder()
            .header("Authorization", "Bearer ${newAccessToken.token}")
            .build()
    }
}
