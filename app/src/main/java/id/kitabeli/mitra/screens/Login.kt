package id.kitabeli.mitra.screens

import android.app.Activity
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animate
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import id.kitabeli.mitra.R
import id.kitabeli.mitra.components.AppButton
import id.kitabeli.mitra.components.AppTextField
import id.kitabeli.mitra.ui.theme.BLACK
import id.kitabeli.mitra.ui.theme.TEXT_GREY
import id.kitabeli.mitra.ui.theme.appFontFamily
import id.kitabeli.mitra.viewmodels.AuthViewModel
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Preview(showBackground = true)
@Composable
fun Login() {

    val scrollState = rememberScrollState()
    val activity = LocalContext.current as Activity

    val authViewModel: AuthViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(scrollState),
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8F)
                .padding(10.dp)
                .animateContentSize()

        ) {

            Image(
                painter = painterResource(id = R.drawable.login),
                contentDescription = "Login",
                contentScale = ContentScale.Inside,
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.BottomCenter
            )


        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Text(
                text = "Selamat datang di Kita Beli",
                style = TextStyle(color = BLACK),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = appFontFamily,

                )
            Text(
                text = "Masukkan nomor telepon kamu untuk memulai belanja di Kita Beli",
                style = TextStyle(color = TEXT_GREY),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontFamily = appFontFamily,
                modifier = Modifier.padding(horizontal = 10.dp)

            )

            if (authViewModel.showOTPView.value) {
                OTPView(authViewModel)
            } else {
                NumberView(authViewModel) {
                    getOTP(activity, authViewModel)
                }
            }


        }
        Box(
            modifier = Modifier
                .weight(0.17f)
                .fillMaxWidth()
                .padding(5.dp)
        ) {

            AppButton(
                modifier = Modifier.fillMaxSize(),
                btnText = "Masuk",
                showProgress = authViewModel.isLoading.value
            ) {
                if (authViewModel.showOTPView.value) {

                } else {
                    getOTP(activity = activity, authViewModel)
                }
            }

        }


    }
}


@Composable
fun NumberView(authViewModel: AuthViewModel, onDonePress: () -> Unit) {

    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose {

        }
    }

    Row(
        Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(10.dp)
    ) {
        Box(
            Modifier
                .fillMaxHeight()
                .background(Color(0xFFEEEEEE)),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "+62",
                fontSize = 22.sp,
                fontFamily = appFontFamily,
                modifier = Modifier.padding(10.dp),
                color = TEXT_GREY
            )
        }
        AppTextField(
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester),
            placeHolder = "Number",
            value = authViewModel.phoneNumber.value,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
            onPressDone = {
                focusManager.clearFocus()
                onDonePress()
            }

        ) {
            if (it.length <= 12) {
                authViewModel.phoneNumber.value = it
            }

        }

    }

}

@Composable
fun OTPView(authViewModel: AuthViewModel) {

    var otpBox1 by remember {
        mutableStateOf("")
    }
    var otpBox2 by remember {
        mutableStateOf("")
    }
    var otpBox3 by remember {
        mutableStateOf("")
    }
    var otpBox4 by remember {
        mutableStateOf("")
    }
    var otpBox5 by remember {
        mutableStateOf("")
    }
    var otpBox6 by remember {
        mutableStateOf("")
    }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
            authViewModel.showOTPView.value = false
        }) {
            Text(
                text = "+62 ${authViewModel.phoneNumber.value}",
                color = MaterialTheme.colors.primary,
                fontSize = 16.sp,
                fontFamily = appFontFamily
            )
            Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            Icon(
                Icons.Default.Edit,
                contentDescription = "edit",
                modifier = Modifier.size(20.dp, 20.dp),
                tint = MaterialTheme.colors.primary
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(10.dp)
        ) {

            OTPBox(true, value = otpBox1) {
                otpBox1 = it
            }
            OTPBox(false, value = otpBox2) {
                otpBox2 = it
            }
            OTPBox(false, value = otpBox3) {
                otpBox3 = it
            }
            OTPBox(false, value = otpBox4) {
                otpBox4 = it
            }
            OTPBox(false, value = otpBox5) {
                otpBox5 = it
            }
            OTPBox(false, isLastItem = true, value = otpBox6) {
                otpBox6 = it
            }

        }
        
        OTPTimer()
        
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OTPBox(
    isFirstItem: Boolean = false,
    isLastItem: Boolean = false,
    value: String = "",
    onValueChanged: (String) -> Unit
) {

    val focusManager = LocalFocusManager.current
    val focusRequester = FocusRequester()

    val colors = TextFieldDefaults.textFieldColors(
        focusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        backgroundColor = Color.Transparent

    )

    if (isFirstItem) {
        DisposableEffect(Unit) {
            focusRequester.requestFocus()
            onDispose {

            }
        }
    }
    Row {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center,
        ) {
            TextField(
                modifier = Modifier
                    .size(50.dp, 50.dp)
                    .focusRequester(focusRequester)
                    .onKeyEvent { keyEvent ->
                        if (keyEvent.key == Key.Backspace && value.isNotEmpty()) {
                            focusManager.moveFocus(FocusDirection.Left)
                            onValueChanged("")
                        }
                        false
                    },
                value = value,
                onValueChange = {
                    if (it.length == 1 && it.isDigitsOnly()) {
                        onValueChanged(it)
                        focusManager.moveFocus(FocusDirection.Right)
                    }

                },
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontFamily = appFontFamily,
                    fontWeight = FontWeight.SemiBold
                ),
                colors = colors,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = if (isLastItem) ImeAction.Done else ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Right)
                }),

                )

        }
        if (!isLastItem) {
            Spacer(modifier = Modifier.padding(horizontal = 2.dp))
        }

    }

}

@Composable
fun OTPTimer(){
    
    Column(Modifier.fillMaxWidth()) {
        Text(text = "Tidak dapat menerima kode verifikasi?")
    }
    
}

private fun getOTP(activity: Activity, authViewModel: AuthViewModel) {
    authViewModel.isLoading.value = true
    var verificationId: String? = null
    var resentToken: PhoneAuthProvider.ForceResendingToken? = null
    var fbToken = ""

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {

        }

        override fun onVerificationFailed(p0: FirebaseException) {
            authViewModel.isLoading.value = false

        }

        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(p0, p1)
            Timber.e("success")
            verificationId = p0
            resentToken = p1
            authViewModel.isLoading.value = false
            authViewModel.showOTPView.value = true
        }

    }
    val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
        .setPhoneNumber("+62${authViewModel.phoneNumber.value}")       // Phone number to verify
        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
        .setActivity(activity)                 // Activity (for callback binding)
        .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
    if (resentToken != null) {
        options.setForceResendingToken(resentToken!!)
    }
    PhoneAuthProvider.verifyPhoneNumber(options.build())
}





