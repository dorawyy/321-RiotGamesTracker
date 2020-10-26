package com.example.riotgamestracker;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.riotgamestracker.models.MatchHistory;
import com.example.riotgamestracker.models.Summoner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpManager {
    private RequestQueue queue;
    private final String serverUrl = "http://52.188.54.111:8081/";

    public HttpManager(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public void getSummoner(String summoner, final MutableLiveData<Summoner> data) {
        String url = serverUrl + "summoner?name=" + summoner;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Summoner res = new Summoner();
                        try {
                            res.name = response.getJSONObject("Summoner").getString("name");
                            res.level = response.getJSONObject("Summoner").getInt("summonerLevel");

                            data.postValue(res);
                        } catch (JSONException exception) {
                            Log.d("Error", "onResponse: " + exception.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", "onErrorResponse: " + error.getMessage());
                    }
                });

        queue.add(request);
    }

    public void getMatchHistory(String summoner, final MutableLiveData<MatchHistory> data) {

        String url = serverUrl + "summoner?name=" + summoner;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        MatchHistory matchHistory = new MatchHistory();
                        matchHistory.history = new HashMap<>();

                        try {
                            JSONObject championNames = response.getJSONObject("MatchHistory").getJSONObject("championName");
                            JSONObject wins = response.getJSONObject("MatchHistory").getJSONObject("win");

                            Iterator<String> indexes = championNames.keys();
                            while (indexes.hasNext()) {
                                String index = indexes.next();

                                matchHistory.history.put(index,
                                        new Pair<>(championNames.getString(index), wins.getBoolean(index)));
                            }

                            data.postValue(matchHistory);

                        } catch (JSONException exception) {
                            Log.d("Error", "onResponse: " + exception.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", "onErrorResponse: " + error.getMessage());
                    }
                });

        queue.add(request);
    }

}
