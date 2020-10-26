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

    View summonerProfileView;
    View summonerProfileSpinner;
    TextView summonerNameText;
    TextView summonerLevelText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summoner_profile);

        summonerProfileView = findViewById(R.id.summonerProfileView);
        summonerProfileSpinner = findViewById(R.id.summonerProfileSpinner);
        summonerNameText = (TextView)findViewById(R.id.summonerNameText);
        summonerLevelText = (TextView)findViewById(R.id.summonerLevelText);

        Bundle viewModelData = new Bundle();
        viewModelData.putString("name", getIntent().getStringExtra("name"));
        summonerViewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this, viewModelData)).get(SummonerViewModel.class);

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        summonerViewModel.getSummonerData().observe(this, newData -> {
            // Update the UI, in this case, a TextView.
//            textView.setText(newName);
            summonerNameText.setText(newData.name);
            summonerLevelText.setText("Level: " + newData.level);

            summonerProfileSpinner.setVisibility(View.GONE);
            summonerProfileView.setVisibility(View.VISIBLE);
        });
    }

    public void matchHistory(View v) {
        Intent intent = new Intent(SummonerProfileActivity.this, MatchHistoryActivity.class);
        SummonerProfileActivity.this.startActivity(intent);
    }
}