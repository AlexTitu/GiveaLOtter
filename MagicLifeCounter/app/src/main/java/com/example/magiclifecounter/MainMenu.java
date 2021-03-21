package com.example.magiclifecounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    Button button2Players, button3Players, button4Players, button5Players, button6Players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        button2Players = findViewById(R.id.button2Players);
        button3Players = findViewById(R.id.button3Players);
        button4Players = findViewById(R.id.button4Players);
        button5Players = findViewById(R.id.button5Players);
        button6Players = findViewById(R.id.button6Players);

        button2Players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open2Players();
            }
        });
    }

    private void open2Players() {
        Intent intent;
        intent = new Intent(this, TwoPlayers.class);
        finish();
        startActivity(intent);

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();

        finish();
        System.exit(0);
    }

}