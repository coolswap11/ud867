package com.udacity.gradle.jokesource;

import java.util.Random;

public class JokeSource {

    private static final String[] JOKES = {"A recent worldwide survey showed that out of 2,146,703,436 people, 94% were too lazy to actually read that number.",
            "Whoever invented knock knock jokes should get a no bell prize.", "How come oysters never donate to charity? Because they are shellfish."};

    /**
     * Tells a random joke.
     */
    public static String makeMeLaugh() {
        Random rand = new Random();
        return JOKES[rand.nextInt(JOKES.length)];
    }
}
