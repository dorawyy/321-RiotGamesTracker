package com.example.riotgamestracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.example.riotgamestracker.viewmodels.MatchHistoryViewModel;

public class MatchHistoryActivity extends AppCompatActivity {
    private MatchHistoryViewModel matchHistoryViewModel;

//    View matchHistoryView;
//    View matchHistorySpinner;
    TextView winnersListText;
    TextView losersListText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_history);

//        matchHistoryView = findViewById(R.id.matchHistoryView);
//        matchHistorySpinner = findViewById(R.id.matchHistorySpinner);
        winnersListText = (TextView)findViewById(R.id.winnersListText);
        losersListText = (TextView)findViewById(R.id.losersListText);

        Bundle viewModelData = new Bundle();
        viewModelData.putString("name", getIntent().getStringExtra("name"));
        matchHistoryViewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this, viewModelData)).get(MatchHistoryViewModel.class);

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        matchHistoryViewModel.getSummonerData().observe(this, newData -> {
            String winnersText = "";
            String losersText = "";
            for(String s : newData.history.keySet()){
                Pair<String, Boolean> pair = newData.history.get(s);
                if(pair.second){
                    winnersText += (pair.first + "\n");
                } else {
                    losersText += (pair.first + "\n");
                }
            }
            winnersListText.setText(winnersText);
            losersListText.setText(losersText);

//            matchHistorySpinner.setVisibility(View.GONE);
//            matchHistoryView.setVisibility(View.VISIBLE);
        });
    }
}