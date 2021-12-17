package com.vmh.kubetool.screen.main.information

import androidx.lifecycle.ViewModel
import com.vmh.kubetool.data.source.Repository
import com.vmh.kubetool.utils.SessionManager
import javax.inject.Inject

class InformationViewModel @Inject constructor(
    private val photoRepository: Repository,
    private val sessionManager: SessionManager
): ViewModel() {

    companion object {
        private const val TAG = "InformationViewModel"
    }
}
