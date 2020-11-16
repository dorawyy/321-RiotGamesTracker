package com.example.riotgamestracker;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.riotgamestracker.models.DataWrapper;
import com.example.riotgamestracker.models.MatchHistory;
import com.example.riotgamestracker.models.Summoner;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class HttpManager {
    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static HttpManager instance;
    private RequestQueue queue;
    private final String serverUrl = "http://52.149.183.181:8081/";
    private final DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(
            10000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    private OkHttpClient client;

    public HttpManager(Context context) {
        queue = Volley.newRequestQueue(context);
        client = new OkHttpClient();
    }

    public static synchronized HttpManager getInstance(Context context) {
        if (instance == null) {
            instance = new HttpManager(context);
        }
        return instance;
    }


    public void getSummoner(String summoner, final MutableLiveData<Summoner> data) {
        String url = serverUrl + "summoner?name=" + summoner;
        System.out.println(url);

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
                            res.error = true;
                            res.errorMessage = exception.getMessage();
                            data.postValue(res);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Summoner res = new Summoner();
                        res.error = true;
                        res.errorMessage = error.getMessage();
                        data.postValue(res);
                        Log.d("Error", "onErrorResponse: " + error.getMessage());
                    }
                });

        request.setRetryPolicy(retryPolicy);
        queue.add(request);
    }

    public void getMatchHistory(String summoner, final MutableLiveData<MatchHistory> data) {

        String url = serverUrl + "summoner?name=" + summoner;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        MatchHistory matchHistory = new MatchHistory(response);

                        data.postValue(matchHistory);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        MatchHistory res = new MatchHistory(error.getMessage());
                        data.postValue(res);
                        Log.d("Error", "onErrorResponse: " + error.getMessage());
                    }
                });

        request.setRetryPolicy(retryPolicy);
        queue.add(request);
    }
    public void recommendedChamp(String summoner, final MutableLiveData<DataWrapper<String>> data){
        data.postValue(new DataWrapper<>("Champion " + summoner, "No error"));
    }

    public void follow(String summoner, String deviceId, final MutableLiveData<DataWrapper<Boolean>> following){
        String url = serverUrl + "follow?name=" + summoner;

        JSONObject body = new JSONObject();
        try {
            body.put("device", deviceId);
        } catch (JSONException e) {
            e.printStackTrace();
            following.postValue(new DataWrapper<>(false, e.getMessage()));
            return;
        }

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(RequestBody.create(body.toString(), MEDIA_TYPE_JSON))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()){
                        following.postValue(new DataWrapper<>(false, response.message()));
                        return;
                    }

                    following.postValue(new DataWrapper<>(true, null));
                }
            }

            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                following.postValue(new DataWrapper<>(false, e.getMessage()));
            }
        });
    }

}
