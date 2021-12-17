package com.vmh.kubetool.screen.main.posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vmh.kubetool.data.models.Post
import com.vmh.kubetool.data.source.Repository
import com.vmh.kubetool.screen.main.Resource
import com.vmh.kubetool.utils.SessionManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private val photoRepository: Repository,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val postsLiveData = MutableLiveData<Resource<List<Post>>>()

    //sessionManager.getAuthUser().value!!.data!!.id!!
    fun posts() {
        postsLiveData.value = Resource.loading()
        compositeDisposable.add(
            photoRepository.postFromUser(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    data.let { postsLiveData.value = Resource.success(it) }
                }, { error ->
                    error.let { postsLiveData.value = Resource.error(it.localizedMessage!!, null) }
                })
        )
    }

    fun observerPosts(): MutableLiveData<Resource<List<Post>>> {
        return postsLiveData
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
