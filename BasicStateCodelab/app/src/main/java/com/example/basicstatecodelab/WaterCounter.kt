package com.example.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterCounter(modifier: Modifier = Modifier){
    Column(modifier = modifier.padding(16.dp)) {
        var count by remember { mutableStateOf(0) }
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }

    //compositionから離れるとstateがリセットされる例
    //count=0のボタンを押してリコンポジションされた時、showStateは忘れられて、初期化される
//    Column(modifier = modifier.padding(16.dp)) {
//        var count by remember { mutableStateOf(0)}
//
//        if (count > 0){
//            var showTask by remember { mutableStateOf(true)}
//            if (showTask){
//                WellnessTaskItem(
//                    onClose = { showTask = false },
//                    taskName = "Have you taken your 15 minute walk today?",
//                    )
//            }
//
//            Text(text = "You've had $count glasses.")
//        }
//
//        Row(modifier = Modifier.padding(top = 8.dp)) {
//            Button(onClick = { count++ }, enabled = count < 10) {
//                Text(text = "Add One")
//            }
//            Button(onClick = { count = 0 }, modifier = Modifier.padding(start = 8.dp)) {
//                Text(text = "Clear water count")
//            }
//
//        }
//    }
}