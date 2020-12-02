package com.example.riotgamestracker.models;

import org.json.JSONObject;

// match stats for a single player
public class PlayerMatchStats {
    private String summonerName;
    private String character;
    private int kills;
    private int deaths;
    private int champLevel;
    private int damageDealt;
    private int assists;
    private int goldEarned;
    private boolean won;

    public PlayerMatchStats(String index, JSONObject histJson)
    {
        this.summonerName = MatchHistory.getName(index, histJson);
        this.character = MatchHistory.getCharacter(index, histJson);
        this.kills = MatchHistory.getKills(index, histJson);
        this.deaths = MatchHistory.getDeaths(index, histJson);
        this.champLevel = MatchHistory.getChampLevel(index, histJson);
        this.damageDealt = MatchHistory.getTotalDamageDealt(index, histJson);
        this.assists = MatchHistory.getAssists(index, histJson);
        this.goldEarned = MatchHistory.getGoldEarned(index, histJson);
        this.won = MatchHistory.checkWin(index, histJson);
    }

    public String getSummonerName() {
        return summonerName;
    }

    public String getCharacter() {
        return character;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getChampLevel() {
        return champLevel;
    }

    public int getDamageDealt() {
        return damageDealt;
    }

    public int getAssists() {
        return assists;
    }

    public int getGoldEarned() {
        return goldEarned;
    }

    public boolean isWon() {
        return won;
    }
}
