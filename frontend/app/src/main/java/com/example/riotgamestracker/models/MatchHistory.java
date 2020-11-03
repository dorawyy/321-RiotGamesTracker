package com.example.riotgamestracker.models;

import android.util.Log;
import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MatchHistory {
    // Index : (Character, win)
    @Deprecated
    public Map<String, Pair<String, Boolean>> history;

    public boolean error;
    public String errorMessage;

    private JSONObject histJson;
    private int size;

    private ArrayList<PlayerMatchStats> winners;

    private ArrayList<PlayerMatchStats> losers;

    public MatchHistory(String errorMessage){
        this.error = true;
        this.errorMessage = errorMessage;
    }

    public MatchHistory(JSONObject histJson) {
        this.history = new HashMap<>();
        this.error = false;

        try {
            this.histJson = histJson.getJSONObject("MatchHistory");

            JSONObject championNames = this.histJson.getJSONObject("championName");
            JSONObject wins = this.histJson.getJSONObject("win");

            Iterator<String> indexes = championNames.keys();
            this.size = 0;
            while (indexes.hasNext()) {
                String index = indexes.next();

                this.history.put(index, new Pair<>(championNames.getString(index), wins.getBoolean(index)));
                this.size++;
            }

        } catch (JSONException exception) {
            this.error = true;
            this.errorMessage = exception.getMessage();

            Log.d("Error", "MatchHistory: " + exception.getMessage());
        }

        this.winners = new ArrayList<>();
        this.losers = new ArrayList<>();
        for(int i=0; i<size(); i++){
            PlayerMatchStats stats = new PlayerMatchStats();

            stats.character = getCharacter(i);
            stats.kills = getKills(i);
            stats.deaths = getDeaths(i);
            stats.champLevel = getChampLevel(i);
            stats.damageDealt = getTotalDamageDealt(i);
            stats.assists = getAssists(i);
            stats.goldEarned = getGoldEarned(i);
            stats.won = checkWin(i);

            if(stats.won){
                winners.add(stats);
            } else {
                losers.add(stats);
            }
        }
    }

    public int size() {
        return this.size;
    }

    public ArrayList<PlayerMatchStats> getWinners() {
        return winners;
    }

    public ArrayList<PlayerMatchStats> getLosers() {
        return losers;
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
