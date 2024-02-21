package com.example.crazyquiz

import androidx.compose.runtime.mutableStateListOf

data class Joke(
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    var selectedAnswer: Int? = null
)

val jokesList = mutableStateListOf(
    Joke(
        "What is a computer's favorite snack?",
        listOf("a) Chips", "b) Cookies", "c) Java beans", "d) Microchips"),
        2
    ),
    Joke(
        "Why did the scarecrow become a successful motivational speaker?",
        listOf(
            "a) Outstanding in his field",
            "b) Great public speaking skills",
            "c) Master of corny jokes",
            "d) Excellent dressing sense"
        ),
        0
    ),
    Joke(
        "What do you call a fish wearing a crown?",
        listOf("a) Kingfish", "b) Goldfish", "c) Tuna royalty", "d) Gills Aloud"),
        0
    ),
    Joke(
        "How do you organize a space party?",
        listOf(
            "a) You planet",
            "b) Invite the whole galaxy",
            "c) Hire an alien DJ",
            "d) All of the above"
        ),
        3
    ),
    Joke(
        "Why did the bicycle fall over?",
        listOf(
            "a) It was two-tired",
            "b) It lost its balance",
            "c) Someone pushed it",
            "d) It wanted to rest"
        ),
        0
    ),
    Joke(
        "What's a vampire's favorite fruit?",
        listOf("a) Apple", "b) Blood orange", "c) Grape", "d) Bat-berry"),
        1
    ),
    Joke(
        "How does a penguin build its house?",
        listOf(
            "a) Igloos for everyone!",
            "b) Ice cubes and glue",
            "c) Buys it from Antarctica real estate",
            "d) Assembles it from IKEA"
        ),
        1
    ),
    Joke(
        "What did one wall say to the other wall?",
        listOf(
            "a) \"I got you covered!\"",
            "b) \"I’ll never get bored of you!\"",
            "c) \"You’re on my nerves!\"",
            "d) \"Let’s meet in the corner.\""
        ),
        0
    ),
    Joke(
        "What's a cat's favorite color?",
        listOf("a) Purr-ple", "b) Black", "c) Red", "d) Meow-genta"),
        0
    ),
    Joke(
        "How do you catch a squirrel?",
        listOf(
            "a) Climb a tree and wait",
            "b) Set up an acorn trap",
            "c) Act like a nut",
            "d) Squirrel-nado"
        ),
        2
    )
)