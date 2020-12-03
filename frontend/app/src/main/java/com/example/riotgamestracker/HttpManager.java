package com.example.riotgamestracker;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.riotgamestracker.models.DataWrapper;
import com.example.riotgamestracker.models.MatchHistory;
import com.example.riotgamestracker.models.Summoner;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class HttpManager {
    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static HttpManager instance;
    private final String serverUrl = "http://52.149.183.181:8081/";

    private final OkHttpClient client;

    public HttpManager() {
        client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public static synchronized HttpManager getInstance() {
        if (instance == null) {
            instance = new HttpManager();
        }
        return instance;
    }


    public void getSummoner(String summoner, final MutableLiveData<Summoner> data) {
        String url = serverUrl + "summoner?name=" + summoner;

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                try {
                    JSONObject responseJson = new JSONObject(response.body().string());
                    Summoner res = new Summoner();
                    res.name = responseJson.getJSONObject("Summoner").getString("name");
                    res.level = responseJson.getJSONObject("Summoner").getInt("summonerLevel");
                    res.profileIconId = responseJson.getJSONObject("Summoner").getInt("profileIconId");

                    data.postValue(res);
                } catch (JSONException exception) {
                    Summoner res = new Summoner();
                    Log.d("Error", "onResponse: " + exception.getMessage());
                    res.error = true;
                    res.errorMessage = "Summoner not found";
                    data.postValue(res);
                }
            }

            @Override public void onFailure(Call call, IOException e) {
                Summoner res = new Summoner();
                res.error = true;
                res.errorMessage = "Summoner not found";
                data.postValue(res);
            }
        });
    }

    public void getMatchHistory(String summoner, final MutableLiveData<MatchHistory> data) {
        String url = serverUrl + "summoner?name=" + summoner;

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                try {
                    MatchHistory matchHistory = new MatchHistory(new JSONObject(response.body().string()));
                    data.postValue(matchHistory);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override public void onFailure(Call call, IOException e) {
                MatchHistory res = new MatchHistory(e.getMessage());
                data.postValue(res);
            }
        });
    }

    public void recommendedChamp(String summoner, final MutableLiveData<DataWrapper<String>> data){
        String url = serverUrl + "recommend?name=" + summoner + "&games=20";

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                String body = response.body().string();
                if(body.length()>30){
                    data.postValue(new DataWrapper<>(null, "Play more games to get a champ recommendation!"));
                } else {
                    data.postValue(new DataWrapper<>(body, null));
                }
            }

            @Override public void onFailure(Call call, IOException e) {
                data.postValue(new DataWrapper<>(null, "Play more games to get a champ recommendation!"));
            }
        });
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
                following.postValue(new DataWrapper<>(response.body().string().equals("true"), null));
            }

            @Override public void onFailure(Call call, IOException e) {
                following.postValue(new DataWrapper<>(null, e.getMessage()));
            }
        });
    }

    public void checkFollowing(String summoner, String deviceId, final MutableLiveData<DataWrapper<Boolean>> data){
        String url = serverUrl + "checkFollowing?name=" + summoner + "&device=" + deviceId;

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                data.postValue(new DataWrapper<>(response.body().string().equals("true"), null));
            }

            @Override public void onFailure(Call call, IOException e) {
                data.postValue(new DataWrapper<>(null, e.getMessage()));
            }
        });
    }
}
