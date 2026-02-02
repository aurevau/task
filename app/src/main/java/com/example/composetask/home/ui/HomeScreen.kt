package com.example.composetask.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.composetask.R
import com.example.composetask.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController){
    val viewModel = hiltViewModel<HomeViewModel>()
    val channels = viewModel.channels.collectAsState()
    val addChannel = remember {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        floatingActionButton = {
            Box(modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.onBackground)
                .clickable {
                    addChannel.value = true
                }) {
                Text(text = "Add Channel",
                    modifier = Modifier
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.background)
            }
        }, containerColor = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            LazyColumn() {
                item{
                    Text(text = stringResource(R.string.messages), color = MaterialTheme.colorScheme.onBackground, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Black), modifier = Modifier.padding(16.dp ))
                }

                item {
                    TextField(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clip(RoundedCornerShape(40.dp)),value = "", onValueChange = {}, placeholder = {Text(text = "Search..", color = MaterialTheme.colorScheme.onBackground)},
                      textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                        colors = TextFieldDefaults.colors().copy(focusedContainerColor = MaterialTheme.colorScheme.background, unfocusedContainerColor = MaterialTheme.colorScheme.background ),
                        trailingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.onBackground) }
                    )

                }
                items(channels.value) { channel ->
                    Column() {
                        ChannelItem(channel.name, {
                            navController.navigate("chat/${channel.id}")
                        })
//                        Text(modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(8.dp)
//                            .clip(RoundedCornerShape(16.dp))
//                            .background(MaterialTheme.colorScheme.onBackground)
//                            .clickable {
//
//                            }
//                            .padding(16.dp)
//                            ,
//                            color = MaterialTheme.colorScheme.background
//                            ,text = channel.name)
                    }
                }
            }
        }
    }

    if (addChannel.value) {
        ModalBottomSheet(onDismissRequest = {
            addChannel.value = false
        },
            sheetState = sheetState  ) {
            AddChannelDialog{
                viewModel.addChannel(it)
                addChannel.value = false
            }
        }
    }
}

@Composable
fun ChannelItem(channelName: String, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 2.dp)
        .clip(RoundedCornerShape(24.dp))
        .background(MaterialTheme.colorScheme.onBackground)
        .clickable{
            onClick()
        }
        ,
        verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.padding(12.dp)
            .size(60.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f))
            ) {

            Text(text = channelName[0].uppercase(),
                color = MaterialTheme.colorScheme.background,
                style = TextStyle(fontSize = 35.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.align (Alignment.Center))
        }


        Text(text = channelName.uppercase(), modifier = Modifier.padding(8.dp), color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun AddChannelDialog(onAddChannel: (String) -> Unit) {
    val channelName = remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Add Channel")
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(value = channelName.value, onValueChange = {
            channelName.value = it
        }, label = {Text(text = "Channel Name")},
            singleLine = true)
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick = {onAddChannel(channelName.value)}, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Add")
        }
    }

}