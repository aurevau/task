package com.example.composetask.presentation.sign_in.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.composetask.User
import com.example.composetask.util.InitialsAvatar

@Composable
fun ProfileScreen(
    userData: User?,
    onSignOut: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

//        if (userData?.profilePictureUrl != null) {
//            AsyncImage(model = userData.profilePictureUrl,
//                contentDescription = "Profile picture",
//                modifier = Modifier
//                    .size(150.dp)
//                    .clip(CircleShape),
//                contentScale = ContentScale.Crop)
//
//            Spacer(modifier = Modifier.height(16.dp))
//        }

        when {
            userData?.profilePictureUrl
                ?.takeIf { it.isNotBlank() && it != "null" } != null -> {

                AsyncImage(
                    model = userData.profilePictureUrl,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            !userData?.username.isNullOrBlank() -> {
                InitialsAvatar(
                    username = userData.username,
                    size = 150.dp
                )
            }

            else -> {
                InitialsAvatar(
                    username = "?",
                    size = 150.dp
                )
            }
        }


        Spacer(modifier = Modifier.height(16.dp))



        if (userData?.username != null) {
            Text(
                text = userData.username,
                textAlign = TextAlign.Center,
                fontSize = 35.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))

        }
        Button(onClick = onSignOut) {
            Text(text = "Sign out")
        }
    }

}