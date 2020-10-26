package com.example.riotgamestracker.models;

import android.util.Pair;

import java.util.Map;

public class MatchHistory {
    // Index : (Character, win)
    public Map<String, Pair<String, Boolean>> history;
}
