package com.example.crazyquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.crazyquiz.ui.theme.CrazyQuizTheme
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrazyQuizTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var amountOfCorrectAnswers by remember { mutableStateOf<Int?>(null) }
    val jokes = remember { jokesList }
    JokesList(
        jokesList = jokes,
        onApplyClick = {
            amountOfCorrectAnswers = jokes.count {
                it.selectedAnswer == it.correctIndex
            }
        }
    )

    amountOfCorrectAnswers?.let { amount ->
        Dialog(onDismissRequest = { amountOfCorrectAnswers = null }) {
            var resultText by remember { mutableStateOf<String?>(null) }
            LaunchedEffect(true) {
                delay(2000)
                resultText = getFeedbackText(amount)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                resultText?.let { text ->
                    Text(text)
                } ?: run {
                    Text("Calculating the laughter index... \uD83E\uDD14")
                }
            }
        }
    }
}

fun getFeedbackText(correctAnswers: Int): String {
    return when (correctAnswers) {
        in 1..5 -> "Nice try! You're on the path to becoming a jokester extraordinaire. Keep honing your comedic skills!"
        in 6..7 -> "Well done! You're definitely making people chuckle with your witty responses. Keep those jokes coming!"
        in 8..10 -> "Wow, you're a comedy genius! Your mastery of jokes is truly impressive. Keep spreading laughter wherever you go!"
        else -> "Oops! Looks like no correct answers. Please try again."
    }
}


@Composable
fun JokesList(jokesList: SnapshotStateList<Joke>, onApplyClick: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF903D00))
    ) {
        itemsIndexed(jokesList) { jokeIndex, joke ->
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
                val scrollState = rememberLazyListState()
                val reverseLayout = remember { Random.nextBoolean() }
                LazyColumn(
                    modifier = Modifier
                        .height(60.dp)
                        .simpleVerticalScrollbar(scrollState),
                    state = scrollState,
                    reverseLayout = reverseLayout
                ) {
                    itemsIndexed(joke.options) { index, option ->
                        val isSelected = joke.selectedAnswer == index
                        Text(
                            modifier = Modifier.clickable {
                                jokesList[jokeIndex] = joke.copy(selectedAnswer = index)
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
        item {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onApplyClick
            ) {
                Text("Submit answers")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    CrazyQuizTheme {
        MainScreen()
    }
}