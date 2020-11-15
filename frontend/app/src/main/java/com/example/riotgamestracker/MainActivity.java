package com.example.riotgamestracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private EditText search;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            System.out.println("Fetching FCM registration token failed");
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
//                        Log.d(TAG, token);
                        System.out.println("FCM TOKEN " + token);
                        Context context = getApplicationContext();
                        SharedPreferences sharedPref = context.getSharedPreferences(
                                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(getString(R.string.fcm_token_key), token);
                        editor.apply();
                    }
                });

        FirebaseMessaging.getInstance().subscribeToTopic("notifications")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "GREAT SUCCESS";
                        if (!task.isSuccessful()) {
                            msg = "NOT SUCCESSFUL";
                        }
                        System.out.println(msg);
//                        Log.d(TAG, msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


        search = (EditText) findViewById(R.id.search);
        searchButton = (Button) findViewById(R.id.searchButton);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No Code Necessary
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()==0){
                    searchButton.setEnabled(false);
                } else {
                    searchButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No Code Necessary
            }
        });

        HttpManager.getInstance(getApplicationContext());
    }


    public void btnClick(View v) {
        String name = search.getText().toString();
        if(name.length()==0){
            return;
        }
//        String anotherName = "John Doe";
//        model.getCurrentName().setValue(anotherName);
        Intent myIntent = new Intent(MainActivity.this, SummonerProfileActivity.class);
        myIntent.putExtra("name", name);
        MainActivity.this.startActivity(myIntent);
    }

}