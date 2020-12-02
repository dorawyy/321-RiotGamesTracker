package com.example.riotgamestracker.models;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MatchHistory {
    public boolean error;
    public String errorMessage;

    private ArrayList<PlayerMatchStats> winners;

    private ArrayList<PlayerMatchStats> losers;

    public MatchHistory(String errorMessage){
        this.error = true;
        this.errorMessage = errorMessage;
    }

    public MatchHistory(JSONObject histJsonIn) {
        this.error = false;

        try {
            JSONObject histJson = histJsonIn.getJSONObject("MatchHistory");

            JSONObject championNames = histJson.getJSONObject("championName");

            Iterator<String> indexes = championNames.keys();
            this.winners = new ArrayList<>();
            this.losers = new ArrayList<>();
            while (indexes.hasNext()) {
                String index = indexes.next();
                PlayerMatchStats stats = new PlayerMatchStats(index, histJson);

                if(stats.isWon()){
                    winners.add(stats);
                } else {
                    losers.add(stats);
                }
            }

        } catch (JSONException exception) {
            this.error = true;
            this.errorMessage = "Summoner has not played any matches yet";

            Log.d("Error", "MatchHistory: " + exception.getMessage());
        }
    }

    public ArrayList<PlayerMatchStats> getWinners() {
        return winners;
    }

    public ArrayList<PlayerMatchStats> getLosers() {
        return losers;
    }

    public static String getCharacter(String index, JSONObject histJson) {
        String retVal = null;

        if (histJson != null) {
            try {
                retVal = histJson.getJSONObject("championName").getString(index);

            } catch (JSONException exception) {
                Log.d("Error", "getCharacter: " + exception.getMessage());
            }
        }

        return retVal;
    }

    public static int getKills(String index, JSONObject histJson) {
        int retVal = -1;

        if (histJson != null) {
            try {
                retVal = histJson.getJSONObject("kills").getInt(index);

            } catch (JSONException exception) {
                Log.d("Error", "getKills: " + exception.getMessage());
            }
        }

        return retVal;
    }

    public static int getDeaths(String index, JSONObject histJson) {
        int retVal = -1;

        if (histJson != null) {
            try {
                retVal = histJson.getJSONObject("deaths").getInt(index);

            } catch (JSONException exception) {
                Log.d("Error", "getDeaths: " + exception.getMessage());
            }
        }

        return retVal;
    }

    public static int getChampLevel(String index, JSONObject histJson) {
        int retVal = -1;

        if (histJson != null) {
            try {
                retVal = histJson.getJSONObject("champLevel").getInt(index);

            } catch (JSONException exception) {
                Log.d("Error", "getChampLevel: " + exception.getMessage());
            }
        }

        return retVal;
    }

    public static int getTotalDamageDealt(String index, JSONObject histJson) {
        int retVal = -1;

        if (histJson != null) {
            try {
                retVal = histJson.getJSONObject("totalDamageDealt").getInt(index);

            } catch (JSONException exception) {
                Log.d("Error", "getTotalDamageDealt: " + exception.getMessage());
            }
        }

        return retVal;
    }

    public static int getAssists(String index, JSONObject histJson) {
        int retVal = -1;

        if (histJson != null) {
            try {
                retVal = histJson.getJSONObject("assists").getInt(index);

            } catch (JSONException exception) {
                Log.d("Error", "getAssists: " + exception.getMessage());
            }
        }

        return retVal;
    }

    public static int getGoldEarned(String index, JSONObject histJson) {
        int retVal = -1;

        if (histJson != null) {
            try {
                retVal = histJson.getJSONObject("goldEarned").getInt(index);

            } catch (JSONException exception) {
                Log.d("Error", "getGoldEarned: " + exception.getMessage());
            }
        }

        return retVal;
    }

    public static Boolean checkWin(String index, JSONObject histJson) {
        Boolean retVal = null;

        if (histJson != null) {
            try {
                retVal = histJson.getJSONObject("win").getBoolean(index);

            } catch (JSONException exception) {
                Log.d("Error", "checkWin: " + exception.getMessage());
            }
        }

        return retVal;
    }

    public static String getName(String index, JSONObject histJson) {
        String retVal = null;

        if (histJson != null) {
            try {
                retVal = histJson.getJSONObject("SummonerName").getString(index);

            } catch (JSONException exception) {
                Log.d("Error", "getName: " + exception.getMessage());
            }
        }

        return retVal;
    }
}
