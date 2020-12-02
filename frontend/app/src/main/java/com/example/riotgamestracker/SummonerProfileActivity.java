package com.example.riotgamestracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.espresso.idling.CountingIdlingResource;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.riotgamestracker.viewmodels.SummonerViewModel;
import com.squareup.picasso.Picasso;

public class SummonerProfileActivity extends AppCompatActivity {
    private SummonerViewModel summonerViewModel;

    private View summonerProfileView;
    private View summonerProfileSpinner;
    private TextView summonerNameText;
    private TextView summonerLevelText;
    private TextView summonerErrorText;
    private Button summonerFollowButton;

    private TextView recommendedChampExplanationText;
    private TextView recommendedChampText;

    CountingIdlingResource espressoTestIdlingResource = new CountingIdlingResource("Network_Call");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summoner_profile);

        summonerProfileView = findViewById(R.id.summonerProfileView);
        summonerProfileSpinner = findViewById(R.id.summonerProfileSpinner);
        summonerNameText = (TextView)findViewById(R.id.summonerNameText);
        summonerLevelText = (TextView)findViewById(R.id.summonerLevelText);
        summonerErrorText = (TextView)findViewById(R.id.summonerErrorText);
        summonerFollowButton = (Button)findViewById(R.id.summonerFollowButton);
        recommendedChampExplanationText = (TextView)findViewById(R.id.recommendedChampExplanationText);
        recommendedChampText = (TextView)findViewById(R.id.recommendedChampText);

        recommendedChampExplanationText.setText("Analyzing " + getIntent().getStringExtra("name") + "'s games...");

        espressoTestIdlingResource.increment();

        Bundle viewModelData = new Bundle();
        viewModelData.putString("name", getIntent().getStringExtra("name"));
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String deviceId = sharedPref.getString(getString(R.string.fcm_token_key), "");
        viewModelData.putString("deviceId", deviceId);
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
            Picasso.get().load("http://ddragon.leagueoflegends.com/cdn/10.24.1/img/profileicon/" + newData.profileIconId + ".png").into((ImageView)findViewById(R.id.profileIcon));
            summonerProfileView.setVisibility(View.VISIBLE);

            espressoTestIdlingResource.decrement();
        });

        summonerViewModel.getFollowing().observe(this, newData -> {
            if(newData.getError() != null && !newData.getError().isEmpty()){

            } else {
                if(newData.getData()){
                    summonerFollowButton.setText("Unfollow");
                } else {
                    summonerFollowButton.setText("Follow");
                }
            }
        });

        summonerViewModel.getRecommendedChamp().observe(this, champ -> {
            if(champ.getError() != null && !champ.getError().isEmpty()){
                recommendedChampExplanationText.setText(champ.getError());
            } else {
                recommendedChampExplanationText.setText("Based on analysis of the last 20 games, we recommend the following champ:");
                recommendedChampText.setText(champ.getData());
                Picasso.get().load("http://ddragon.leagueoflegends.com/cdn/10.24.1/img/champion/" + champ.getData() + ".png").into((ImageView)findViewById(R.id.champIcon));
            }
        });
    }

    public void matchHistory(View v) {
        Intent intent = new Intent(SummonerProfileActivity.this, MatchHistoryActivity.class);
        intent.putExtra("name", summonerViewModel.getSummonerData().getValue().name);
        SummonerProfileActivity.this.startActivity(intent);
    }

    public void follow(View v) {
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String deviceId = sharedPref.getString(getString(R.string.fcm_token_key), "");
        summonerViewModel.follow(deviceId);
    }

    public CountingIdlingResource getEspressoIdlingResource() {
        return espressoTestIdlingResource;
    }
}