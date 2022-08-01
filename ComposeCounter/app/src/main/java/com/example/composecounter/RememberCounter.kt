package com.example.composecounter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composecounter.ui.theme.ComposeCounterTheme


@Composable
fun RememberCount(){
    var count by rememberSaveable { mutableStateOf(0) }

    RememberCounter(
        text = count.toString(),
        onClicked = { count ++}
    )
}

@Composable
fun RememberCounter(text:String, onClicked:() -> Unit){
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text)
        Button(onClick =  onClicked ) {
            Text(text = "count up")
        }
    }
}

@Preview
@Composable
fun RememberCountPreview() {
    ComposeCounterTheme() {
        Surface(color = Color.White) {
            RememberCount()
        }
    }
}
