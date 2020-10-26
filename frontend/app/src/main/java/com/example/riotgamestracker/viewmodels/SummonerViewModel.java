package com.example.riotgamestracker.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.riotgamestracker.HttpManager;
import com.example.riotgamestracker.models.Summoner;

public class SummonerViewModel extends ViewModel {
    private SavedStateHandle mState;
    private MutableLiveData<Summoner> summonerData;

    public SummonerViewModel(SavedStateHandle savedStateHandle) {
        mState = savedStateHandle;
        summonerData = new MutableLiveData<Summoner>();
        loadSummonerData((String) mState.get("name"));
    }

    public MutableLiveData<Summoner> getSummonerData() {
        return summonerData;
    }

    private void loadSummonerData(String name) {
        HttpManager httpManager = HttpManager.getInstance(null);
        httpManager.getSummoner(name, summonerData);
    }
}

