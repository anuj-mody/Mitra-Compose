package id.kitabeli.mitra.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    var phoneNumber: MutableState<String> = mutableStateOf("")
        private set

    var isLoading: MutableState<Boolean> = mutableStateOf(false)
        private set

    var showOTPView: MutableState<Boolean> = mutableStateOf(true)
        private set
}