package com.vmh.kubetool.screen.auth.signup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vmh.kubetool.R
import com.vmh.kubetool.utils.Constants
import com.vmh.kubetool.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.textFieldPassword
import kotlinx.android.synthetic.main.fragment_sign_up.textFieldPhone
import javax.inject.Inject

class SignUpFragment: DaggerFragment(R.layout.fragment_sign_up), View.OnClickListener {
    private lateinit var navController: NavController
    private lateinit var viewModel: SignUpViewModel

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
            R.id.textViewSignIn -> {
                navController.popBackStack()
            }

            R.id.buttonSignUp -> {
                attemptLogin()
            }
        }
    }

    private fun initView() {
        navController = findNavController()
        textViewSignIn.setOnClickListener(this)
        buttonSignUp.setOnClickListener(this)

        textInputEditTextPhone.doOnTextChanged { _, _, _, _ ->
            if (!textInputEditTextPhone.text.toString().matches(Regex("(^0\\d{9}\$)"))) {
                textFieldPhone.error = "Số điện thoại phải có 10 chữ số và bắt đầu bằng 0"
            }else {
                textFieldPhone.error = null
            }
        }

        textInputEditTextFullName.doOnTextChanged { _, _, _, _ ->
            if (!textInputEditTextFullName.text.toString().matches(Regex("^\\S[\\w\\s]*\\S\$"))) {
                textFieldFullName.error = "Họ tên không được chứa khoảng trắng đầu và cuối"
            }else {
                textFieldFullName.error = null
            }
        }
    }

    private fun registerLiveData() {
        viewModel.errorLiveData.observe(viewLifecycleOwner, {
            showDialogSignUpFailed("Xẩy ra lỗi.", "Đã xẩy ra lỗi vui lòng thử lại hoặc liên hệ admin.")
            Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
        })

        viewModel.signUpLiveData.observe(viewLifecycleOwner, {
            when (it.message) {
                "User created" -> {
                    showDialog("Đăng ký thành công!", "Vui lòng liên hệ admin để mở khóa tài khoản để được đăng nhập.")
                }
                "Account exists" -> {
                    showDialogSignUpFailed("Thất bại", "Số điện thoại đã tồn tại.")
                }
                else -> {
                    showDialogSignUpFailed("Thất bại", "Đăng ký tài khoản thất bại vui lòng thử lại.")
                    Toast.makeText(requireContext(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun showDialogSignUpFailed(message: String, content: String){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(message)
            .setMessage(content)
            .setPositiveButton(resources.getString(R.string.cancel)) { dialog, which ->
                dialog.cancel()
            }
            .show()
    }

    private fun showDialog(message: String, content: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(message)
            .setMessage(content)
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->
                dialog.cancel()
            }
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                try {
                    startActivity(
                        Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(Constants.linkAdmin)
                    ))
                }catch (e: Exception) {
                    Toast.makeText(requireContext(), "Lỗi đường dẫn sai!", Toast.LENGTH_SHORT).show()
                }
            }
            .show()
    }

    private fun attemptLogin() {
        if (TextUtils.isEmpty(textFieldFullName.editText?.text.toString()) ||
            TextUtils.isEmpty(textFieldPhone.editText?.text.toString()) ||
            TextUtils.isEmpty(textFieldPassword.editText?.text.toString()) &&
            textFieldFullName.error == null &&
            textFieldPhone.error == null) {
            Toast.makeText(requireContext(), "Thông tin không chính xác ", Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.signUp(
            textFieldFullName.editText?.text.toString(),
            textFieldPhone.editText?.text.toString(),
            textFieldPassword.editText?.text.toString()
        )
    }

    private fun initData() {
        viewModel = ViewModelProviders.of(this, providerFactory).get(SignUpViewModel::class.java)
    }
}
