package com.example.crazyquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crazyquiz.ui.theme.CrazyQuizTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrazyQuizTheme {
                MyList()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyList() {
    Box(
        modifier = Modifier
            .pullRefresh(
                rememberPullRefreshState(
                    refreshing = false,
                    onRefresh = { /*TODO*/ })
            )
            .background(Color(0xFF903D00))
    ) {
        LazyColumn {
            items(jokesList) { joke ->
                Column(
                    modifier = Modifier
                        .background(Color(0xFFD00000))
                        .border(3.dp, color = Color.Black, shape = RoundedCornerShape(15.dp))
                ) {
                    Text(
                        joke.question,
                        color = Color(0xFF6C7D00),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    var selectedOption by remember { mutableStateOf<String?>(null) }

                    val scrollState = rememberLazyListState()
                    val reverseLayout = remember { Random.nextBoolean() }
                    LazyColumn(
                        modifier = Modifier.height(60.dp).simpleVerticalScrollbar(scrollState),
                        state = scrollState,
                        reverseLayout = reverseLayout
                    ) {
                        items(joke.options) { option ->
                            val isSelected = selectedOption == option
                            Text(
                                modifier = Modifier.clickable {
                                    selectedOption = option
                                },
                                text = option,
                                fontSize = if (isSelected) 28.sp else 20.sp,
                                color = Color(0xFF2250F4).copy(alpha = if (isSelected) 0.4f else 1f),
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Modifier.simpleVerticalScrollbar(
    state: LazyListState,
    width: Dp = 8.dp
): Modifier {
    val targetAlpha = if (state.isScrollInProgress) 1f else 0f
    val duration = if (state.isScrollInProgress) 150 else 500

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration)
    )

    return drawWithContent {
        drawContent()

        val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index
        val needDrawScrollbar = state.isScrollInProgress || alpha > 0.0f

        // Draw scrollbar if scrolling or if the animation is still running and lazy column has content
        if (needDrawScrollbar && firstVisibleElementIndex != null) {
            val elementHeight = this.size.height / state.layoutInfo.totalItemsCount
            val scrollbarOffsetY = firstVisibleElementIndex * elementHeight
            val scrollbarHeight = state.layoutInfo.visibleItemsInfo.size * elementHeight

            drawRect(
                color = Color.Red,
                topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
                size = Size(width.toPx(), scrollbarHeight),
                alpha = alpha
            )
        }
    }
}

data class Joke(val question: String, val options: List<String>, val correctAnswer: Char)

val jokesList = listOf(
    Joke(
        "What is a computer's favorite snack?",
        listOf("a) Chips", "b) Cookies", "c) Java beans", "d) Microchips"),
        'c'
    ),
    Joke(
        "Why did the scarecrow become a successful motivational speaker?",
        listOf(
            "a) Outstanding in his field",
            "b) Great public speaking skills",
            "c) Master of corny jokes",
            "d) Excellent dressing sense"
        ),
        'a'
    ),
    Joke(
        "What do you call a fish wearing a crown?",
        listOf("a) Kingfish", "b) Goldfish", "c) Tuna royalty", "d) Gills Aloud"),
        'a'
    ),
    Joke(
        "How do you organize a space party?",
        listOf(
            "a) You planet",
            "b) Invite the whole galaxy",
            "c) Hire an alien DJ",
            "d) All of the above"
        ),
        'd'
    ),
    Joke(
        "Why did the bicycle fall over?",
        listOf(
            "a) It was two-tired",
            "b) It lost its balance",
            "c) Someone pushed it",
            "d) It wanted to rest"
        ),
        'a'
    ),
    Joke(
        "What's a vampire's favorite fruit?",
        listOf("a) Apple", "b) Blood orange", "c) Grape", "d) Bat-berry"),
        'b'
    ),
    Joke(
        "How does a penguin build its house?",
        listOf(
            "a) Igloos for everyone!",
            "b) Ice cubes and glue",
            "c) Buys it from Antarctica real estate",
            "d) Assembles it from IKEA"
        ),
        'b'
    ),
    Joke(
        "What did one wall say to the other wall?",
        listOf(
            "a) \"I got you covered!\"",
            "b) \"I’ll never get bored of you!\"",
            "c) \"You’re on my nerves!\"",
            "d) \"Let’s meet in the corner.\""
        ),
        'a'
    ),
    Joke(
        "What's a cat's favorite color?",
        listOf("a) Purr-ple", "b) Black", "c) Red", "d) Meow-genta"),
        'a'
    ),
    Joke(
        "How do you catch a squirrel?",
        listOf(
            "a) Climb a tree and wait",
            "b) Set up an acorn trap",
            "c) Act like a nut",
            "d) Squirrel-nado"
        ),
        'c'
    ),
    Joke(
        "Why did the tomato turn red?",
        listOf(
            "a) It saw the salad dressing",
            "b) Embarrassed for being a fruit",
            "c) Blushing from the compliments",
            "d) Tomato ketchup squeeze"
        ),
        'c'
    ),
    Joke(
        "What’s a skeleton's least favorite room in the house?",
        listOf(
            "a) Kitchen",
            "b) Living room",
            "c) Bathroom",
            "d) The living room (it has too many people)"
        ),
        'd'
    ),
    Joke(
        "Why did the math book look sad?",
        listOf(
            "a) Too many problems",
            "b) Failed its test",
            "c) Missing its cover",
            "d) Couldn't count on anyone"
        ),
        'a'
    ),
    Joke(
        "What's a tree's favorite networking tool?",
        listOf("a) Roots", "b) Leaves", "c) Bark", "d) Branch Manager"),
        'a'
    ),
    Joke("What's Bigfoot's shoe size?", listOf("a) 10", "b) 15", "c) Huge!", "d) Sasquatch"), 'c'),
    Joke(
        "How do you organize a space party? (Again)",
        listOf(
            "a) You planet",
            "b) Invite the whole galaxy",
            "c) Hire an alien DJ",
            "d) All of the above"
        ),
        'd'
    ),
    Joke(
        "What’s a vampire's favorite fruit? (Again)",
        listOf("a) Apple", "b) Blood orange", "c) Grape", "d) Bat-berry"),
        'b'
    ),
    Joke(
        "Why don't scientists trust atoms?",
        listOf(
            "a) They are too small",
            "b) They make up everything",
            "c) Unpredictable behavior",
            "d) Allergic to lab coats"
        ),
        'b'
    ),
    Joke(
        "How does a penguin build its house? (Again)",
        listOf(
            "a) Igloos for everyone!",
            "b) Ice cubes and glue",
            "c) Buys it from Antarctica real estate",
            "d) Assembles it from IKEA"
        ),
        'b'
    ),
    Joke(
        "What's a cat's favorite subject in school?",
        listOf("a) Paw-sic education", "b) Meow-thematics", "c) Literature", "d) History of mice"),
        'b'
    )
)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CrazyQuizTheme {
        MyList()
    }
}