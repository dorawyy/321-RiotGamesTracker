package com.example.riotgamestracker.models;

// match stats for a single player
public class PlayerMatchStats {
    String character;
    int kills;
    int deaths;
    int champLevel;
    int damageDealt;
    int assists;
    int goldEarned;
    boolean won;

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
