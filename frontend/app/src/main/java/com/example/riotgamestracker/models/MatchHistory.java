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
    @Deprecated
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

    public int getKills(int index) {
        int retVal = -1;

        if (this.histJson != null) {
            try {
                retVal = this.histJson.getJSONObject("kills").getInt(Integer.toString(index));

            } catch (JSONException exception) {
                Log.d("Error", "getKills: " + exception.getMessage());
            }
        }

        return retVal;
    }

    public int getDeaths(int index) {
        int retVal = -1;

        if (this.histJson != null) {
            try {
                retVal = this.histJson.getJSONObject("deaths").getInt(Integer.toString(index));

            } catch (JSONException exception) {
                Log.d("Error", "getDeaths: " + exception.getMessage());
            }
        }

        return retVal;
    }

    public int getChampLevel(int index) {
        int retVal = -1;

        if (this.histJson != null) {
            try {
                retVal = this.histJson.getJSONObject("champLevel").getInt(Integer.toString(index));

            } catch (JSONException exception) {
                Log.d("Error", "getChampLevel: " + exception.getMessage());
            }
        }

        return retVal;
    }

    public int getTotalDamageDealt(int index) {
        int retVal = -1;

        if (this.histJson != null) {
            try {
                retVal = this.histJson.getJSONObject("totalDamageDealt").getInt(Integer.toString(index));

            } catch (JSONException exception) {
                Log.d("Error", "getTotalDamageDealt: " + exception.getMessage());
            }
        }

        return retVal;
    }

    public int getAssists(int index) {
        int retVal = -1;

        if (this.histJson != null) {
            try {
                retVal = this.histJson.getJSONObject("assists").getInt(Integer.toString(index));

            } catch (JSONException exception) {
                Log.d("Error", "getAssists: " + exception.getMessage());
            }
        }

        return retVal;
    }

    public int getGoldEarned(int index) {
        int retVal = -1;

        if (this.histJson != null) {
            try {
                retVal = this.histJson.getJSONObject("goldEarned").getInt(Integer.toString(index));

            } catch (JSONException exception) {
                Log.d("Error", "getGoldEarned: " + exception.getMessage());
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
                Log.d("Error", "checkWin: " + exception.getMessage());
            }
        }

        return retVal;
    }
}
