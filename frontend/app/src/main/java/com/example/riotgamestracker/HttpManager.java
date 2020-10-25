package com.example.riotgamestracker;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class HttpManager {
    private RequestQueue queue;
    private final String serverUrl = "http://52.188.54.111:8081/";

    public HttpManager(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public void getPlayerProfile(String player, final MutableLiveData<JSONObject> data) {
        String url = serverUrl + "profile?player=" + player;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        data.postValue(response);
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

    public void getChampionWinRate(String champion, final MutableLiveData<JSONObject> data) {

        String url = serverUrl + "winrate?champion=" + champion;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        data.postValue(response);
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
