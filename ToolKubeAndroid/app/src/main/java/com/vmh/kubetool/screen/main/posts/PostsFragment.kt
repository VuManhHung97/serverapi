package com.vmh.kubetool.screen.main.posts

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.vmh.kubetool.R
import com.vmh.kubetool.data.models.Post
import com.vmh.kubetool.screen.main.Resource
import com.vmh.kubetool.utils.OnItemRecyclerViewClickListener
import com.vmh.kubetool.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_posts.*
import javax.inject.Inject

class PostsFragment: DaggerFragment(R.layout.fragment_posts), OnItemRecyclerViewClickListener<Post> {
    private lateinit var viewModel: PostsViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var postsAdapter: PostsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        subscribeObservers()
    }

    override fun onItemClick(data: Post) {

    }

    private fun initView() {
        viewModel =
            ViewModelProviders.of(this, providerFactory).get(PostsViewModel::class.java)
        recyclerViewPost.setHasFixedSize(true)
        recyclerViewPost.adapter = postsAdapter
        viewModel.posts()
        postsAdapter.setOnItemRecyclerViewClickListener(this)
    }

    private fun subscribeObservers() {
        viewModel.observerPosts().observe(viewLifecycleOwner, {
            if (it != null) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        postsAdapter.submitList(it.data!!)
                    }

                    Resource.Status.LOADING -> {

                    }

                    Resource.Status.ERROR -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        })
    }
}
