package com.example.layoutscodelab

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.example.layoutscodelab.ui.theme.LayoutsCodelabTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutsCodelabTheme {
                LayoutCodelab()
            }
        }
    }
}

@Composable
fun LayoutCodelab() {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutsCodelab")
                },
                actions ={
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.FavoriteBorder,
                            contentDescription = null)
                    }
                }
            )
        }
    ){ innerPadding ->
        BodyContent(Modifier.padding(innerPadding))
//        LazyList()
//        TextWithPaddingToBaselinePreview()
    }
}

@Preview
@Composable
fun LayoutCodelabPreview() {
    LayoutsCodelabTheme {
        LayoutCodelab()
    }
}

//constraintLayout Apiの分離
private fun decoupledConstraints(margin: Dp): ConstraintSet{
    return ConstraintSet{
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button){
            top.linkTo(parent.top, margin = margin)
        }
        constrain(text){
            top.linkTo(button.bottom, margin = margin)
        }
    }
}

@Composable
fun DecoupledConstraintLayout(){
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight){
            decoupledConstraints(margin = 16.dp)
        }else{
            decoupledConstraints(margin = 32.dp)
        }

        ConstraintLayout(constraints){
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.layoutId("button")
            ) {
                Text(text = "Button")
            }

            Text(text = "Text", Modifier.layoutId("text"))
        }
    }
}


@Composable
fun LargeConstraintLayout(){
    ConstraintLayout{
        val text = createRef()

        val guideline = createGuidelineFromStart(fraction = 0.5f)
        Text(
            text = "This is a very very very very very very very long text",
            Modifier.constrainAs(text){
                linkTo(start = guideline, end = parent.end)
                width = Dimension.preferredWrapContent
            }
        )
    }
}

@Preview
@Composable
fun LargeConstraintLayoutPreview() {
    LayoutsCodelabTheme {
        LargeConstraintLayout()
    }
}




@Composable
fun ConstraintLayoutContent(){
    ConstraintLayout {

//        val (button, text) = createRefs()
//
//        Button(
//            onClick = { /*TODO*/ },
//            modifier = Modifier.constrainAs(button){
//                top.linkTo(parent.top, margin = 16.dp)
//            }
//        ) {
//            Text(text = "Button")
//        }
//
//        Text(text = "text",
//        modifier = Modifier.constrainAs(text){
//            top.linkTo(button.bottom, margin = 16.dp)
//            centerHorizontallyTo(parent)
//        })

        val (button1, button2, text) = createRefs()

        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button1){
                top.linkTo(parent.top, margin = 16.dp)
            }
        ){
            Text(text = "button1")
        }

        Text(text = "text",
        modifier = Modifier.constrainAs(text){
            top.linkTo(button1.bottom, margin = 16.dp)
            centerAround(button1.end)
        })

        val barrier = createEndBarrier(button1, text)

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(button2){
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
        ) {
            Text(text = "button2")
        }
    }
}

//@Preview
@Composable
fun ConstraintLayoutContentPreview(){
    LayoutsCodelabTheme {
        ConstraintLayoutContent()
    }
}



@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows:Int = 3,
    content: @Composable () -> Unit
){
    Layout(
        content = content,
        modifier = modifier
    ){ measurables, constraints ->
        val rowWidths = IntArray(rows){ 0 }
        val rowHeigths = IntArray(rows){ 0 }

        val placeables = measurables.mapIndexed{ index, measurable ->  
            val placeable = measurable.measure(constraints)
            val row = index % rows
            rowWidths[row] += placeable.width
            rowHeigths[row] = Math.max(rowHeigths[row], placeable.height)

            placeable
        }

        val width = rowWidths.maxOrNull()
            ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) ?: constraints.maxWidth

        val height = rowHeigths.sumOf { it }
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        val rowY = IntArray(rows){ 0 }
        for (i in 1 until rows){
            rowY[i] = rowY[i-1] + rowHeigths[i-1]
        }

        layout(width, height){
            val rowX = IntArray(rows){ 0 }

            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(rowX[row], rowY[row])
                rowX[row] += placeable.width
            }
        }
    }
}

@Composable
fun Chip(modifier: Modifier = Modifier, text : String){
    Card(modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text)
        }
    }
}

//@Preview
@Composable
fun ChipPreview(){
    LayoutsCodelabTheme() {
        Chip(text = "Hi there")
    }
}

val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)



@Composable
fun BodyContent(modifier: Modifier = Modifier){
    Row(modifier = modifier
        .background(color = Color.Gray)
        .padding(16.dp)
        .size(200.dp)
        .horizontalScroll(rememberScrollState()),
        content = {
            StaggeredGrid() {
                for (topic in topics){
                    Chip(modifier = Modifier.padding(8.dp), text = topic)
                }
            }
        }
    )
}
    
    
//    StaggeredGrid(modifier = modifier, rows = 5) {
//        for (topic in topics){
//            Chip(modifier = Modifier.padding(8.dp), text = topic)
//        }
//    }

//    MyOwnColumn(modifier = modifier.padding(8.dp)) {
//        Text("MyOwnColumn")
//        Text("places items")
//        Text("vertically.")
//        Text("We've done it by hand!")
//    }

//レイアウトコンポーザブルLayout
@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content : @Composable () -> Unit
){
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // measure and position children given constraints logic here
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        var yPosition = 0

        layout(constraints.maxWidth, constraints.maxHeight){
            placeables.forEach{ placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}

//レイアウト修飾子の例
fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = this.then(
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        // Check the composable has a first baseline
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]

        // Height of the composable with padding - first baseline
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY
        layout(placeable.width, height) {
            // Where the composable gets placed
            placeable.placeRelative(0, placeableY)
        }
    }
)

//@Preview
@Composable
fun TextWithPaddingToBaselinePreview() {
    LayoutsCodelabTheme {
        Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
    }
}

//@Preview
@Composable
fun TextWithNormalPaddingPreview() {
    LayoutsCodelabTheme {
        Text("Hi there!", Modifier.padding(top = 32.dp))
    }
}


//スクロールできるリストの例
@Composable
fun LazyList(){
    val listSize = 100
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    
    Column() {
        Row() {
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text(text = "Scroll to the top")
            }

            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
               Text(text = "Scroll to the end")
            }
        }
        
        
        LazyColumn(state = scrollState){
            items(100){
                ImageListItem(index = it)
            }
        }
    }
}

@Composable
fun ImageListItem(index:Int){
    Row(verticalAlignment = CenterVertically) {

        Image(
            painter = rememberImagePainter(
                data = "https://developer.android.com/images/brand/Android_Robot.png"
            ),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "Item #$index", style = MaterialTheme.typography.subtitle1)
    }    
}


@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {
    Row(modifier
        .padding(8.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(MaterialTheme.colors.surface)
        .clickable { }
        .padding(16.dp)
    ) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {

        }

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(CenterVertically)
        ) {
            Text(text = "Alfred Sisley", fontWeight = FontWeight.Bold)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("3 minutes ago", style = MaterialTheme.typography.body2)
            }

        }
    }
}

//@Preview
@Composable
fun PhotographerCardPreview() {
    LayoutsCodelabTheme {
        PhotographerCard()
    }
}