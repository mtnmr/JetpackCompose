package com.example.composecounter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composecounter.ui.theme.ComposeCounterTheme


@Composable
fun CounterScreen(viewModel: CountViewModel = viewModel()){
    val count by viewModel.count.observeAsState()

    Counter(
        text = count.toString(),
        onClicked = { viewModel.countUp() }
    )
}

@Composable
fun Counter(text:String, onClicked:() -> Unit){
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
fun CounterScreenPreview(){
    ComposeCounterTheme() {
        Surface(color = Color.White) {
            CounterScreen()
        }
    }
}