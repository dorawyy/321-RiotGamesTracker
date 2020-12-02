package com.example.riotgamestracker.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.riotgamestracker.HttpManager;
import com.example.riotgamestracker.models.DataWrapper;
import com.example.riotgamestracker.models.Summoner;

public class SummonerViewModel extends ViewModel {
    private MutableLiveData<Summoner> summonerData;
    private MutableLiveData<DataWrapper<Boolean>> following;
    private MutableLiveData<DataWrapper<String>> recommendedChamp;
    private String name;

    public SummonerViewModel(SavedStateHandle savedStateHandle) {
        summonerData = new MutableLiveData<>();
        following = new MutableLiveData<>();
        recommendedChamp = new MutableLiveData<>();
        name = savedStateHandle.get("name");
        loadSummonerData(savedStateHandle.get("deviceId"));
    }

    public MutableLiveData<Summoner> getSummonerData() {
        return summonerData;
    }

    public MutableLiveData<DataWrapper<Boolean>> getFollowing() {
        return following;
    }

    public MutableLiveData<DataWrapper<String>> getRecommendedChamp() {
        return recommendedChamp;
    }

    private void loadSummonerData(String deviceId) {
        HttpManager httpManager = HttpManager.getInstance();
        httpManager.getSummoner(name, summonerData);
        httpManager.recommendedChamp(name, recommendedChamp);
        httpManager.checkFollowing(name, deviceId, following);
    }

    public void follow(String deviceId) {
        HttpManager httpManager = HttpManager.getInstance();
        httpManager.follow(name, deviceId, following);
    }
}

