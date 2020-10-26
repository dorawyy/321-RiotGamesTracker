package com.example.riotgamestracker.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

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
        Summoner summoner = new Summoner();
        summoner.name = name;
        summoner.level = 123;
        summonerData.postValue(summoner);
    }
}

