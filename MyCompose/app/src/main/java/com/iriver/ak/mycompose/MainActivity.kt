package com.iriver.ak.mycompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iriver.ak.mycompose.ui.theme.MyComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

//------------------------------------------------
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyComposeTheme {
//        Greetings()
//        BoxLayout()
//        StyledText()
//        DisplayImage()
//        GreetingColumn()
//        AnimatedBox()
//        CounterScreen()

        MyApp()
    }
}
//------------------------------------------------

/////////////////////////////////////// EXAMPLE ///////////////////////////////////////
@Composable
fun StyledText(name: String) {
    Text(
        text = "Hello2 $name!",
        Modifier
            .padding(80.dp)
            .background(color = Color.Green)
    )
}

//@Preview(showBackground = true)
@Composable
fun composableRows() {
    Row {
        Text(text = "Jetpack")
        Text(text = "Compose")
    }
}

//@Preview(showBackground = true)
@Composable
fun composableColumns() {
    Column {
        Text(text = "Jetpack")
        Text(text = "Compose")
    }
}

@Composable
fun composableBox() {
    Box {
        Text(text = "Jetpack")
        Text(text = "Compose")
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    Column {
        Text(text = msg.author)
        Text(text = msg.body)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Greetings(names: List<String> = List(100) { "$it" }) {
    val lazyIndex = 0
    when (lazyIndex) {
        0 -> {
            LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
                items(items = names) { name ->
                    Greeting(name = name)
                }
            }
        }
        1 -> {
            LazyRow(modifier = Modifier.padding(vertical = 4.dp)) {
                items(items = names) { name ->
                    Greeting(name = name)
                }
            }
        }
        2 -> {
            LazyVerticalGrid(
                GridCells.Adaptive(minSize = 128.dp)
            ) {
                items(items = names) { name ->
                    Greeting(name = name)
                }
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
private fun GreetingColumn(names: List<String> = List(10) { "$it" }) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

//@Preview(showBackground = true)
@Composable
private fun GreetingRow(names: List<String> = List(10) { "$it" }) {
    LazyRow(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

//@Preview(showBackground = true)
@Composable
fun SimpleText() {
    Text(text = "Hello, World!")
}

//@Preview()
@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }

    Column {
        Text(text = "Count: $count")
        Button(onClick = { count++ }) {
            Text(text = "Increment")
        }
    }
}

@Composable
fun Counter2(count: Int, onIncrement: () -> Unit) {
    Button(onClick = onIncrement, modifier = Modifier.wrapContentSize()) {
        Text("Count: $count")
    }
}

@Composable
fun CounterScreen() {
    var count by remember { mutableStateOf(0) }
    Counter2(count = count, onIncrement = { count++ })
}

//@Preview(showBackground = true)
@Composable
fun BoxLayout() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text("This is at the bottom", modifier = Modifier.align(Alignment.BottomEnd))
        Text("This is at the center", modifier = Modifier.align(Alignment.Center))
        Text("This is at the top", modifier = Modifier.align(Alignment.TopStart))
    }
}

@Composable
fun StyledText() {
    Text(
        text = "Styled Text",
        modifier = Modifier
            .padding(16.dp)
            .background(Color.LightGray)
            .fillMaxWidth()
            .fillMaxHeight()
    )
}

// Button
//@Preview(showBackground = true)
@Composable
fun SimpleButton() {
    val context = LocalContext.current
    Button(
        onClick = { Toast.makeText(context, "Clicked!!", Toast.LENGTH_SHORT).show() },
        modifier = Modifier.wrapContentSize()
    ) {
        Text(text = "Click Me")
    }
}

// Image
//@Preview(showBackground = true)
@Composable
fun DisplayImage() {
    Image(
        painter = painterResource(id = R.drawable.img_home_gallery_albumart),
        modifier = Modifier
            .wrapContentSize()
            .background(Color.Green)
            .clip(CircleShape)
            .size(200.dp),
        contentDescription = "Sample Image"
    )
}

// Animation
//@Preview
@Composable
fun AnimatedBox() {
    var isBig by remember { mutableStateOf(false) }
    val size by animateDpAsState(if (isBig) 100.dp else 50.dp)
    val color by animateColorAsState(if (isBig) Color.Blue else Color.Green)
    Box(
        modifier = Modifier
            .wrapContentSize()
            .size(size)
            .background(color)
            .clickable { isBig = !isBig }
    )
}
/////////////////////////////////////// EXAMPLE ///////////////////////////////////////