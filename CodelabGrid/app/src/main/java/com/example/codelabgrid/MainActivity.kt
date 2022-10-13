package com.example.codelabgrid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.codelabgrid.model.Topic
import com.example.codelabgrid.ui.theme.CodelabGridTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodelabGridTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ItemGrid(topicList = DataSource.topics)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemGrid(topicList:List<Topic>){
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp),
    ){
        items(topicList){ item: Topic ->
            ItemView(topic = item)
        }
    }
}


@Composable
fun ItemView(topic: Topic){
    Card(elevation = 4.dp) {
        Row() {
            Box() {
                Image(
                    painter = painterResource(id = topic.imageRes),
                    contentDescription = "",
                    modifier = Modifier
                        .size(width = 68.dp, height = 68.dp)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            }

            Column(){
                Text(
                    text = stringResource(id = topic.name),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    )
                )

                Text(
                    text = "${topic.availableCourses}",
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(
                        start = 16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ItemViewPreview(){
    CodelabGridTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            ItemView(topic = Topic(R.string.app_name, availableCourses = 100, imageRes = R.drawable.ic_baseline_insert_photo_24))
        }
    }
}
