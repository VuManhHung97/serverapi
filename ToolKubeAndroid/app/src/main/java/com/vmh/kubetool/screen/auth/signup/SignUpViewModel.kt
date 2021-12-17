package com.vmh.kubetool.screen.auth.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vmh.kubetool.data.source.Repository
import com.vmh.kubetool.data.source.remote.response.SignUpResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val photoRepository: Repository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val signUpLiveData: MutableLiveData<SignUpResponse> = MutableLiveData()
    val errorLiveData: MutableLiveData<Throwable> = MutableLiveData()

    fun signUp(fullName: String, phoneNumber: String, password: String) {
        compositeDisposable.add(
            photoRepository.signUp(fullName, phoneNumber, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    signUpLiveData.value = data
                }, { error ->
                    errorLiveData.value = error
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}