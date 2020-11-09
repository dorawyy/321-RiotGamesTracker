package com.example.riotgamestracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.riotgamestracker.viewmodels.SummonerViewModel;

public class SummonerProfileActivity extends AppCompatActivity {
    private SummonerViewModel summonerViewModel;

    private View summonerProfileView;
    private View summonerProfileSpinner;
    private TextView summonerNameText;
    private TextView summonerLevelText;
    private TextView summonerErrorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summoner_profile);

        summonerProfileView = findViewById(R.id.summonerProfileView);
        summonerProfileSpinner = findViewById(R.id.summonerProfileSpinner);
        summonerNameText = (TextView)findViewById(R.id.summonerNameText);
        summonerLevelText = (TextView)findViewById(R.id.summonerLevelText);
        summonerErrorText = (TextView)findViewById(R.id.summonerErrorText);

        Bundle viewModelData = new Bundle();
        viewModelData.putString("name", getIntent().getStringExtra("name"));
        summonerViewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this, viewModelData)).get(SummonerViewModel.class);

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        summonerViewModel.getSummonerData().observe(this, newData -> {
            summonerProfileSpinner.setVisibility(View.GONE);
            if(newData.error){
                if(newData.errorMessage != null && !newData.errorMessage.isEmpty()){
                    summonerErrorText.setText(newData.errorMessage);
                } else {
                    summonerErrorText.setText("Error loading profile");
                }
                summonerErrorText.setVisibility(View.VISIBLE);
                return;
            }
            summonerNameText.setText(newData.name);
            summonerLevelText.setText("Level: " + newData.level);
            summonerProfileView.setVisibility(View.VISIBLE);
        });
    }

    public void matchHistory(View v) {
        Intent intent = new Intent(SummonerProfileActivity.this, MatchHistoryActivity.class);
        intent.putExtra("name", summonerViewModel.getSummonerData().getValue().name);
        SummonerProfileActivity.this.startActivity(intent);
    }
}