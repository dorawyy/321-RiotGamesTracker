package com.example.riotgamestracker.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.riotgamestracker.HttpManager;
import com.example.riotgamestracker.models.MatchHistory;
import com.example.riotgamestracker.models.Summoner;

public class MatchHistoryViewModel extends ViewModel {
    private SavedStateHandle mState;
    private MutableLiveData<MatchHistory> matchHistoryData;

    public MatchHistoryViewModel(SavedStateHandle savedStateHandle) {
        mState = savedStateHandle;
        matchHistoryData = new MutableLiveData<MatchHistory>();
        loadMatchHistoryData((String) mState.get("name"));
    }

    public MutableLiveData<MatchHistory> getSummonerData() {
        return matchHistoryData;
    }

    private void loadMatchHistoryData(String name) {
        HttpManager httpManager = HttpManager.getInstance(null);
        httpManager.getMatchHistory(name, matchHistoryData);
    }
}
