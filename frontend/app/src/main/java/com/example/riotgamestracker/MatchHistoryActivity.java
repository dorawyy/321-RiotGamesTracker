package com.example.riotgamestracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.riotgamestracker.models.MatchHistory;
import com.example.riotgamestracker.models.PlayerMatchStats;
import com.example.riotgamestracker.viewmodels.MatchHistoryViewModel;

import java.util.ArrayList;

public class MatchHistoryActivity extends AppCompatActivity {
    private MatchHistoryViewModel matchHistoryViewModel;

    View matchHistoryView;
    View matchHistorySpinner;
    TextView matchHistoryErrorText;

    ListView matchHistoryListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_history);

        matchHistoryView = findViewById(R.id.matchHistoryView);
        matchHistorySpinner = findViewById(R.id.matchHistorySpinner);
        matchHistoryErrorText = (TextView)findViewById(R.id.matchHistoryErrorText);

        matchHistoryListView = (ListView)findViewById(R.id.matchHistoryListView);

        Bundle viewModelData = new Bundle();
        viewModelData.putString("name", getIntent().getStringExtra("name"));
        matchHistoryViewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this, viewModelData)).get(MatchHistoryViewModel.class);

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        matchHistoryViewModel.getSummonerData().observe(this, newData -> {
            matchHistorySpinner.setVisibility(View.GONE);
            if(newData.error){
                if(newData.errorMessage != null && !newData.errorMessage.isEmpty()){
                    matchHistoryErrorText.setText(newData.errorMessage);
                } else {
                    matchHistoryErrorText.setText("Error loading match history");
                }
                matchHistoryErrorText.setVisibility(View.VISIBLE);
                return;
            }

            ArrayList<Object> listViewData = new ArrayList<>();
            listViewData.add("Winners");
            listViewData.addAll(newData.getWinners());
            listViewData.add("Losers");
            listViewData.addAll(newData.getLosers());
            MatchHistoryAdapter adapter = new MatchHistoryAdapter(this, listViewData);
            matchHistoryListView.setAdapter(adapter);

            matchHistoryView.setVisibility(View.VISIBLE);
        });
    }

    class MatchHistoryAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<Object> data;

        private static final int TYPE_HEADER = 0;
        private static final int TYPE_ITEM = 1;

        public MatchHistoryAdapter(Context context, ArrayList<Object> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int rowType = getItemViewType(position);
            if (convertView == null) {
                switch (rowType) {
                    case TYPE_HEADER:
                        convertView = LayoutInflater.from(context).
                                inflate(R.layout.match_history_header, parent, false);
                        break;
                    case TYPE_ITEM:
                        convertView = LayoutInflater.from(context).
                                inflate(R.layout.match_history_row_item, parent, false);
                        break;
                }
            }

            if(getItem(position) instanceof String){
                ((TextView) convertView.findViewById(R.id.matchHistoryHeaderText)).setText((String)getItem(position));
            }
            else if(getItem(position) instanceof PlayerMatchStats){
                PlayerMatchStats stats = (PlayerMatchStats) getItem(position);
                System.out.println("CHARACTER " + stats.getCharacter() + " DAMAGE " + stats.getDamageDealt());
                ((TextView) convertView.findViewById(R.id.matchHistoryCharacterText)).setText(stats.getCharacter());
                String kda = String.format("%d/%d/%d", stats.getKills(), stats.getDeaths(), stats.getAssists());
                ((TextView) convertView.findViewById(R.id.matchHistoryKDA)).setText(kda);
                ((TextView) convertView.findViewById(R.id.matchHistoryDamageDealt)).setText(Integer.toString(stats.getDamageDealt()));
                ((TextView) convertView.findViewById(R.id.matchHistoryGoldEarned)).setText(Integer.toString(stats.getGoldEarned()));
            }
            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            if(getItem(position) instanceof String){
                return TYPE_HEADER;
            }
            return TYPE_ITEM;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }
    }
}