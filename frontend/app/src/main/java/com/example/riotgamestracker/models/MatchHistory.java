package com.example.riotgamestracker.models;

import android.util.Log;
import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MatchHistory {
    // Index : (Character, win)
    public Map<String, Pair<String, Boolean>> history;
    private JSONObject histJson;
    private int size;

    public MatchHistory(JSONObject histJson) {
        this.history = new HashMap<>();

        try {
            this.histJson = histJson.getJSONObject("MatchHistory");

            JSONObject championNames = histJson.getJSONObject("championName");
            JSONObject wins = histJson.getJSONObject("win");

            Iterator<String> indexes = championNames.keys();
            this.size = 0;
            while (indexes.hasNext()) {
                String index = indexes.next();

                this.history.put(index, new Pair<>(championNames.getString(index), wins.getBoolean(index)));
                this.size++;
            }

        } catch (JSONException exception) {
            Log.d("Error", "onResponse: " + exception.getMessage());
        }
    }

    public int size() {
        return this.size;
    }

    public String getCharacter(int index) {
        String retVal = null;

        if (this.histJson != null) {
            try {
                retVal = this.histJson.getJSONObject("championName").getString(Integer.toString(index));

            } catch (JSONException exception) {
                Log.d("Error", "getCharacter: " + exception.getMessage());
            }
        }

        return retVal;
    }

    public Boolean checkWin(int index) {
        Boolean retVal = null;

        if (this.histJson != null) {
            try {
                retVal = this.histJson.getJSONObject("win").getBoolean(Integer.toString(index));

            } catch (JSONException exception) {
                Log.d("Error", "getCharacter: " + exception.getMessage());
            }
        }

        return retVal;
    }
}
