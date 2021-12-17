package com.vmh.kubetool.screen.auth.signin

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vmh.kubetool.R
import com.vmh.kubetool.data.source.remote.response.SignInResponse
import com.vmh.kubetool.screen.auth.AuthResource
import com.vmh.kubetool.screen.main.MainActivity
import com.vmh.kubetool.utils.Constants
import com.vmh.kubetool.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject


class SignInFragment: DaggerFragment(R.layout.fragment_login), View.OnClickListener {
    private lateinit var navController: NavController
    private lateinit var viewModel: SignInViewModel
    private lateinit var dialog: AlertDialog
    private var linkAdmin = ""
    private var linkAdminGmail = ""

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        registerLiveData()
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.textViewRegister -> {
                navController.navigate(R.id.action_signInFragment_to_signUpFragment)
            }

            R.id.buttonLogin -> {
                attemptLogin()
            }

            R.id.imageViewMail -> {
                try {
                    startActivity(Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(linkAdminGmail)
                    ))
                }catch (e: Exception) {
                    Toast.makeText(requireContext(), "Lỗi đường dẫn sai!", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.imageViewZalo -> {
                try {
                    startActivity(Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(linkAdmin)
                    ))
                }catch (e: Exception) {
                    Toast.makeText(requireContext(), "Lỗi đường dẫn sai!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initView() {
        navController = findNavController()
        dialog = SpotsDialog.Builder().setContext(context).build()
        textViewRegister.setOnClickListener(this)
        buttonLogin.setOnClickListener(this)
        imageViewMail.setOnClickListener(this)
        imageViewZalo.setOnClickListener(this)
    }

    private fun initData() {
        viewModel = ViewModelProviders.of(this, providerFactory).get(SignInViewModel::class.java)
        setLogo()
        loadUser()
        viewModel.getLink("Admin")
        dialog.show()
    }

    private fun loadUser() {
        val user = viewModel.loadUser()
        if (user.username != "" && user.password != "") {
            textFieldPhone.editText?.setText(user.username)
            textFieldPassword.editText?.setText(user.password)
        }
    }

    private fun registerLiveData() {
        viewModel.clearAuthState()

        viewModel.observerAuthState().observe(viewLifecycleOwner,object: Observer<AuthResource<out SignInResponse?>>{
            override fun onChanged(data: AuthResource<out SignInResponse?>?) {
                if (data != null) {
                    when (data.status) {
                        AuthResource.AuthStatus.LOADING -> {
                        }

                        AuthResource.AuthStatus.AUTHENTICATED -> {
                            onLoginSuccess(data.data!!)
                            Log.d(TAG, "registerLiveData: LOGIN SUCCESS " + data.message)
                        }

                        AuthResource.AuthStatus.NOT_AUTHENTICATED -> {
                            Log.e(TAG, "registerLiveData: LOGOUT")
                        }

                        AuthResource.AuthStatus.ERROR -> {
                            showDialogAuthFailed("Auth Failed!", "Tài khoản hoặc mật khẩu không chính xác")
                        }
                    }
                }

            }
        })

        viewModel.linkLiveData.observe(viewLifecycleOwner, {
            if (it.message == "Successfuly") {
                linkAdmin = it.linkName
                Constants.linkAdmin = it.linkName
            }
            viewModel.getLinkAdminGmail()
        })

        viewModel.linkAdminGmailLiveData.observe(viewLifecycleOwner, {
            if (it.message == "Successfuly") {
                linkAdminGmail = it.linkName
            }
            dialog.dismiss()
        })
    }

    private fun setLogo() {
        Glide.with(requireContext())
            .setDefaultRequestOptions(RequestOptions
                .placeholderOf(R.drawable.ic_launcher_background)
                .error(R.drawable.white_background))
            .load(ContextCompat.getDrawable(requireContext(), R.drawable.logo))
            .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(30)))
            .into(imageViewLogo)
    }

    private fun attemptLogin() {
        if (TextUtils.isEmpty(textFieldPhone.editText?.text.toString()) ||
            TextUtils.isEmpty(textFieldPassword.editText?.text.toString())) {
                Toast.makeText(requireContext(), "Số điện thoại hoặc mật khẩu không được để trống", Toast.LENGTH_SHORT).show()
                return
        }
        viewModel.authenticate(textFieldPhone.editText?.text.toString(), textFieldPassword.editText?.text.toString())
    }

    private fun onLoginSuccess(signInResponse: SignInResponse) {
        when (signInResponse.message) {
            "Not active" -> {
                showDialog("Tài khoản chưa được kích hoạt!", "Vui lòng liên hệ admin để kích hoạt tài khoản")
            }
            "Auth failed" -> {
                showDialogAuthFailed("Auth Failed!", "Tài khoản hoặc mật khẩu không chính xác")
            }
            "User lock" -> {
                showDialog("Từ chối truy cập", "Tài khoản này đã bị khóa, vui lòng liên hệ admin.")
            }
            else -> {
                viewModel.saveUser(textFieldPhone.editText?.text.toString(),
                    textFieldPassword.editText?.text.toString())

                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    private fun showDialog(message: String, content: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(message)
            .setMessage(content)
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->
                dialog.cancel()
            }
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                // Respond to positive button press
                try {
                    startActivity(Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(linkAdmin)
                    ))
                }catch (e: Exception) {
                    Toast.makeText(requireContext(), "Lỗi đường dẫn sai!", Toast.LENGTH_SHORT).show()
                }
            }
            .show()
    }

    private fun showDialogAuthFailed(message: String, content: String){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(message)
            .setMessage(content)
            .setPositiveButton(resources.getString(R.string.cancel)) { dialog, which ->
                dialog.cancel()
            }
            .show()
    }

    companion object {
        private const val TAG = "SignInFragment"
    }
}
