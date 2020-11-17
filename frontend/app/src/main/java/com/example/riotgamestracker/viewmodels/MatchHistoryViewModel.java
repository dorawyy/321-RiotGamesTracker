package com.example.riotgamestracker.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.riotgamestracker.HttpManager;
import com.example.riotgamestracker.models.MatchHistory;

public class MatchHistoryViewModel extends ViewModel {
    private MutableLiveData<MatchHistory> matchHistoryData;

    public MatchHistoryViewModel(SavedStateHandle savedStateHandle) {
        matchHistoryData = new MutableLiveData<MatchHistory>();
        loadMatchHistoryData((String) savedStateHandle.get("name"));
    }

    public MutableLiveData<MatchHistory> getSummonerData() {
        return matchHistoryData;
    }

    private void loadMatchHistoryData(String name) {
        HttpManager httpManager = HttpManager.getInstance();
        httpManager.getMatchHistory(name, matchHistoryData);
    }
}
