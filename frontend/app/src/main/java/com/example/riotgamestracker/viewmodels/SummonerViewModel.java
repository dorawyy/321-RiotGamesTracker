package com.example.riotgamestracker.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.riotgamestracker.HttpManager;
import com.example.riotgamestracker.models.Summoner;

public class SummonerViewModel extends ViewModel {
    private MutableLiveData<Summoner> summonerData;
    private String name;

    public SummonerViewModel(SavedStateHandle savedStateHandle) {
        summonerData = new MutableLiveData<Summoner>();
        name = (String) savedStateHandle.get("name");
        loadSummonerData();
    }

    public MutableLiveData<Summoner> getSummonerData() {
        return summonerData;
    }

    private void loadSummonerData() {
        HttpManager httpManager = HttpManager.getInstance(null);
        httpManager.getSummoner(name, summonerData);
    }

    public void follow() {
        HttpManager httpManager = HttpManager.getInstance(null);
        httpManager.follow(name, null);
    }
}

