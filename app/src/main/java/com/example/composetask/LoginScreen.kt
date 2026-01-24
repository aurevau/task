package com.example.composetask

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composetask.presentation.sign_in.SignInState
import com.example.composetask.ui.theme.AppTheme
import com.example.composetask.ui.theme.ComposeTaskTheme
import com.example.composetask.ui.theme.Roboto
import com.example.composetask.ui.theme.components.ThemeDropdown
import com.example.composetask.util.logoForTheme

@Composable
fun LoginScreen(
    state: SignInState,
    onSignInClick: () -> Unit,
    currentTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit) {
    Surface( modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val context = LocalContext.current
        LaunchedEffect(key1 = state.signInError) {
            state.signInError?.let { error ->
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            }
        }


        Column(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {


                ThemeDropdown(currentTheme = currentTheme,
                    onThemeSelected = onThemeSelected)
            }
            Row(modifier = Modifier.fillMaxWidth()
                .padding(top = 120.dp),
                verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Center) {
                Image(  modifier = Modifier.size(72.dp),
                    painter = painterResource(id = logoForTheme(currentTheme)),
                    contentDescription = "logo")
            }

            Spacer(modifier = Modifier.height(30.dp))


            Column(modifier = Modifier.fillMaxSize()
                .padding(horizontal = 30.dp)) {

                LoginTextField(label = "Email", trailing ="", modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(15.dp))
                LoginTextField(label = "Password", trailing ="Forgot?", modifier = Modifier.fillMaxWidth() )

                Spacer(modifier = Modifier.height(20.dp))

                Button(modifier = Modifier.fillMaxWidth()
                    .height(40.dp),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onBackground,
                        contentColor = MaterialTheme.colorScheme.background
                    ),
                    shape = RoundedCornerShape(size = 20.dp)) {
                    Text(text = "Login", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium))

                }
                Spacer(modifier = Modifier.height(20.dp))
                Column(modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "or continue with",
                        style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.outline))

                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically)
                    {
                        SocialMediaLogin(icon = R.drawable.google, text = "Google", modifier = Modifier.weight(1f).height(48.dp), onClick = onSignInClick)


                        Spacer(modifier = Modifier.width(20.dp))
                        SocialMediaLogin(icon = R.drawable.facebook, text = "Facebook", modifier = Modifier.weight(1f)) {

                        }


                    }

                    Box(modifier = Modifier
                        .fillMaxHeight(fraction = 0.8f)
                        .fillMaxWidth(),
                        contentAlignment = Alignment.BottomCenter
                    ){
                        Text(text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.outline,
                                    fontSize = 14.sp,
                                    fontFamily = Roboto,
                                    fontWeight = FontWeight.Normal
                                )
                            ) {
                                append("Don't have account?")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 14.sp,
                                    fontFamily = Roboto,
                                    fontWeight = FontWeight.Medium
                                )
                            ) {
                                append(" ")
                                append("Create now")
                            }
                        })

                    }
                }
            }





        }


    }


}

//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    ComposeTaskTheme(
//        theme = AppTheme.BLOSSOM,
//        dynamicColor = false
//    ) {
//        LoginScreen(
//            currentTheme = AppTheme.BLOSSOM,
//            onThemeSelected = {}
//        )
//    }
//}