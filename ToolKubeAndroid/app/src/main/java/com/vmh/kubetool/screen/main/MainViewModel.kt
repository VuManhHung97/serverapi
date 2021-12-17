package com.vmh.kubetool.screen.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vmh.kubetool.data.source.Repository
import com.vmh.kubetool.data.source.remote.response.LinkResponse
import com.vmh.kubetool.data.source.remote.response.StatusToolResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val photoRepository: Repository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val statusToolLiveData: MutableLiveData<StatusToolResponse> = MutableLiveData()
    val errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    val linkLiveData: MutableLiveData<LinkResponse> = MutableLiveData()

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

    fun getStatusTool() {
        compositeDisposable.add(
            photoRepository.getStatusTool()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    statusToolLiveData. value = data
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