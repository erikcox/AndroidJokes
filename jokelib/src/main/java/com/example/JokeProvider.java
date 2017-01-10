package com.example;

import java.util.Random;

public class JokeProvider {
    // Array of jokes
    private static final String[] jokes = {"Why do programmers always mix up Halloween and Christmas? Because Oct 31 == Dec 25!",
            "\"Knock, knock.\" \"Who’s there?\" <very long pause….> \"Java.\"",
            "A SQL query goes into a bar, walks up to two tables and asks, \"Can I join you?\"",
            "How many programmers does it take to change a light bulb? None, that's a hardware problem.",
            "Whats the object-oriented way to become wealthy? Inheritance.",
            "Unix is user friendly. It's just very particular about who its friends are."
    };

    // Method to return a random joke from the jokes array
    public static String getJoke() {
        int size = jokes.length;

        Random r = new Random();
        int index = r.nextInt(size);

        return jokes[index];
    }
}
