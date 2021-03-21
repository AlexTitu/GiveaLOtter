package com.example.magiclifecounter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class TwoPlayers extends AppCompatActivity implements FragmentP1.OnFragmentInteractionListener, FragmentP2.OnFragmentInteractionListener, FragmentColors.OnFragmentInteractionListener{

    private Button minusP1, minusP2, plusP1, plusP2;
    private TextView healthP1, healthP2, fadeP2, fadeP1;
    private ImageButton imageButtonP1, imageButtonP2,lifeReset, backButton, statsButtonP1, statsButtonP2;
    private RelativeLayout relativeLayoutP1, relativeLayoutP2;
    private FrameLayout fragmentContainer;


    int changeColorP1 = 0, changeColorP2 = 0;

    private static final String KEY_COLORP1 = "PLAYER1_COLOR";
    private static final String KEY_COLORP2 = "PLAYER2_COLOR";
    private static final String KEY_P1HP = "PLAYER1_HP";
    private static final String KEY_P2HP = "PLAYER2_HP";
    private static final String KEY_COMMANDERDMGP1 ="COMMANDER_DAMAGE_P1";
    private static final String KEY_POISONCOUNTERSP1 = "POISON_COUNTER_P1";
    private static final String KEY_COMMANDERDMGP2 ="COMMANDER_DAMAGE_P2";
    private static final String KEY_POISONCOUNTERSP2 = "POISON_COUNTER_P2";

    private String commanderDamageP1 = "0", poisonCountersP1 = "0", commanderDamageP2 = "0", poisonCountersP2 = "0";
    private String colorPlayer = "red";
    private int currentPlayer = 1;

    private CountDownTimer countDownTimer;
    private long timeLeftinMilliseconds = 1500;
    private boolean timerRunningP1 = false, timerRunningP2 = false, p1fademode = true, p2fademode = true;
    private int player1tick, player2tick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_players);

        minusP1 = findViewById(R.id.minusP1);
        minusP2 = findViewById(R.id.minusP2);
        plusP1 = findViewById(R.id.plusP1);
        plusP2 = findViewById(R.id.plusP2);
        healthP1 = findViewById(R.id.healthP1);
        healthP2 = findViewById(R.id.healthP2);
        imageButtonP1 = findViewById(R.id.imgButtonP1);
        imageButtonP2 = findViewById(R.id.imgButtonP2);
        relativeLayoutP1 = findViewById(R.id.Player1);
        relativeLayoutP2 = findViewById(R.id.Player2);
        backButton = findViewById(R.id.backButton);
        lifeReset = findViewById(R.id.lifeReset);
        statsButtonP1 = findViewById(R.id.statsP1);
        statsButtonP2 = findViewById(R.id.statsP2);
        fragmentContainer = findViewById(R.id.fragmentContainer);
        fadeP2 = findViewById(R.id.fadeP2);
        fadeP2.setVisibility(View.INVISIBLE);
        fadeP1 = findViewById(R.id.fadeP1);
        fadeP1.setVisibility(View.INVISIBLE);
        player1tick = 0;
        player2tick = 0;

        Context context = getApplicationContext();

        if( Integer.parseInt(healthP1.getText().toString()) < 1 ) healthP1.setTextColor(ContextCompat.getColor(context,R.color.red));
        else healthP1.setTextColor(ContextCompat.getColor(context,R.color.white));
        if( Integer.parseInt(healthP2.getText().toString()) < 1 ) healthP2.setTextColor(ContextCompat.getColor(context,R.color.red));
        else healthP2.setTextColor(ContextCompat.getColor(context,R.color.white));

        statsButtonP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragmentP1();
            }
        });

        statsButtonP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragmentP2();
            }
        });

        lifeReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                if ( changeColorP1 == 4 ) healthP1.setTextColor(ContextCompat.getColor(context,R.color.black));
                else healthP1.setTextColor(ContextCompat.getColor(context,R.color.white));
                healthP1.setText("40");
                if ( changeColorP2 == 4 ) healthP2.setTextColor(ContextCompat.getColor(context,R.color.black));
                else healthP2.setTextColor(ContextCompat.getColor(context,R.color.white));
                healthP2.setText("40");
            }
        });

        plusP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseHP(1);
                if (p1fademode == false ) {
                    p1fademode = true;
                }
                player1tick++;
                fadeP1.setVisibility(View.VISIBLE);
                if ( player1tick > -1) fadeP1.setText("+" + player1tick);
                else fadeP1.setText("" + player1tick);
                if ( timerRunningP1 ) countDownTimer.cancel();
                startCountdown(1);
            }
        });
        plusP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p2fademode == false ) {
                    p2fademode = true;
                }
                player2tick++;
                fadeP2.setVisibility(View.VISIBLE);
                if(player2tick > -1 ) fadeP2.setText("+" + player2tick);
                else fadeP2.setText("" + player2tick);
                increaseHP(2);
                if ( timerRunningP2 ) countDownTimer.cancel();
                startCountdown(2);
            }
        });

        minusP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseHP(1);
                if (p1fademode == true) {
                    p1fademode = false;
                }
                player1tick--;
                fadeP1.setVisibility(View.VISIBLE);
                if ( player1tick > -1) fadeP1.setText("+" + player1tick);
                else fadeP1.setText("" + player1tick);
                if ( timerRunningP1 ) countDownTimer.cancel();
                startCountdown(1);
            }
        });
        minusP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p2fademode == true ) {
                    p2fademode = false;
                }
                decreaseHP(2);
                player2tick--;
                fadeP2.setVisibility(View.VISIBLE);
                if(player2tick > -1 ) fadeP2.setText("+" + player2tick);
                else fadeP2.setText("" + player2tick);
                if ( timerRunningP2 ) countDownTimer.cancel();
                startCountdown(2);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainMenu();
            }
        });

        imageButtonP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayer = 1;
                startFragmentColorsRotated();
            }
        });

        imageButtonP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPlayer = 2;
                startFragmentColors();
            }
        });

        restoreSharedPreferences();
    }

    //---------------METHODS----------//


    //---------------------------TIMER-----------------------------//


    public void startCountdown(int player){

        if ( player == 1 ) timerRunningP1 = true;
        if ( player == 2 ) timerRunningP2 = true;

       countDownTimer = new CountDownTimer(timeLeftinMilliseconds,500) {
           @Override
           public void onTick(long millisUntilFinished) {

           }

           @Override
           public void onFinish() {
               if ( player == 1 ) {
                   player1tick = 0;
                   fadeP1.setVisibility(View.INVISIBLE);
                   timerRunningP1 = false;
                   fadeP1.setText("0");
               }
               if ( player == 2 ) {
                   player2tick = 0;
                   fadeP2.setVisibility(View.INVISIBLE);
                   timerRunningP2 = false;
                   fadeP2.setText("0");
               }
           }
       }.start();

    }



    //------------------------------------SET COLORS-----------------------------------//

    private void setPlayerColor(int player) {
        if ( player == 1 ){
            Context context = getApplicationContext();
            switch (colorPlayer){
                case "red": relativeLayoutP1.setBackgroundColor(ContextCompat.getColor(context,R.color.red));
                    healthP1.setTextColor(ContextCompat.getColor(context,R.color.white));
                    changeColorP1 = 1;
                    break;
                case "green": relativeLayoutP1.setBackgroundColor(ContextCompat.getColor(context,R.color.green_forest));
                    healthP1.setTextColor(ContextCompat.getColor(context,R.color.white));
                    changeColorP1 = 2;
                    break;
                case "black": relativeLayoutP1.setBackgroundColor(ContextCompat.getColor(context,R.color.black));
                    healthP1.setTextColor(ContextCompat.getColor(context,R.color.white));
                    changeColorP1 = 3;
                    break;
                case "white": relativeLayoutP1.setBackgroundColor(ContextCompat.getColor(context,R.color.mtgwhite));
                    healthP1.setTextColor(ContextCompat.getColor(context,R.color.black));
                    changeColorP1 = 4;
                    break;
                case "blue": relativeLayoutP1.setBackgroundColor(ContextCompat.getColor(context,R.color.blue));
                    healthP1.setTextColor(ContextCompat.getColor(context,R.color.white));
                    changeColorP1 = 5;
                    break;
                default: colorPlayer = "1";
            }

        }
        if ( player == 2 ){
            Context context = getApplicationContext();
            switch (colorPlayer){
                case "red": relativeLayoutP2.setBackgroundColor(ContextCompat.getColor(context,R.color.red));
                    healthP2.setTextColor(ContextCompat.getColor(context,R.color.white));
                    changeColorP2 = 1;
                    break;
                case "green": relativeLayoutP2.setBackgroundColor(ContextCompat.getColor(context,R.color.green_forest));
                    healthP2.setTextColor(ContextCompat.getColor(context,R.color.white));
                    changeColorP2 = 2;
                    break;
                case "black": relativeLayoutP2.setBackgroundColor(ContextCompat.getColor(context,R.color.black));
                    healthP2.setTextColor(ContextCompat.getColor(context,R.color.white));
                    changeColorP2 = 3;
                    break;
                case "white": relativeLayoutP2.setBackgroundColor(ContextCompat.getColor(context,R.color.mtgwhite));
                    healthP2.setTextColor(ContextCompat.getColor(context,R.color.black));
                    changeColorP2 = 4;
                    break;
                case "blue": relativeLayoutP2.setBackgroundColor(ContextCompat.getColor(context,R.color.blue));
                    healthP2.setTextColor(ContextCompat.getColor(context,R.color.white));
                    changeColorP2 = 5;
                    break;
                default: colorPlayer = "red";
                    changeColorP2 = 1;
                    break;
            }
        }
    }


    private void openMainMenu() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    private void increaseHP(int player) {
        int HP;
        Context context = getApplicationContext();
        if(player == 1){
          HP = Integer.parseInt(healthP1.getText().toString());
          HP++;
          healthP1.setText(""+HP);
          if( HP < 1 ) healthP1.setTextColor(ContextCompat.getColor(context,R.color.crimson_red));
          else if ( changeColorP1 == 4 ) healthP1.setTextColor(ContextCompat.getColor(context,R.color.black));
          else healthP1.setTextColor(ContextCompat.getColor(context,R.color.white));
        }
        if(player == 2){
            HP = Integer.parseInt(healthP2.getText().toString());
            HP++;
            healthP2.setText(""+HP);
            if( HP < 1 ) healthP2.setTextColor(ContextCompat.getColor(context,R.color.crimson_red));
            else if ( changeColorP2 == 4 ) healthP2.setTextColor(ContextCompat.getColor(context,R.color.black));
            else healthP2.setTextColor(ContextCompat.getColor(context,R.color.white));
        }

    }

    private void decreaseHP(int player) {
        int HP;
        Context context = getApplicationContext();
        if(player == 1){
            HP = Integer.parseInt(healthP1.getText().toString());
            HP--;
            healthP1.setText(""+HP);
            if( HP < 1 ) healthP1.setTextColor(ContextCompat.getColor(context,R.color.crimson_red));
            else if ( changeColorP1 == 4 ) healthP1.setTextColor(ContextCompat.getColor(context,R.color.black));
            else healthP1.setTextColor(ContextCompat.getColor(context,R.color.white));
        }
        if(player == 2){
            HP = Integer.parseInt(healthP2.getText().toString());
            HP--;
            healthP2.setText(""+HP);
            if( HP < 1 ) healthP2.setTextColor(ContextCompat.getColor(context,R.color.crimson_red));
            else if ( changeColorP2 == 4 ) healthP2.setTextColor(ContextCompat.getColor(context,R.color.black));
            else healthP2.setTextColor(ContextCompat.getColor(context,R.color.white));
        }
    }

    protected void onPause() {
        saveSharedPreferences();
        super.onPause();
    }

    //--------------SAVE LIVES NUMBERS------------------//

    private void saveSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_COLORP1,changeColorP1);
        editor.putInt(KEY_COLORP2,changeColorP2);
        editor.putString(KEY_P1HP,healthP1.getText().toString());
        editor.putString(KEY_P2HP,healthP2.getText().toString());
        editor.putString(KEY_COMMANDERDMGP1,commanderDamageP1);
        editor.putString(KEY_POISONCOUNTERSP1,poisonCountersP1);
        editor.putString(KEY_COMMANDERDMGP2,commanderDamageP2);
        editor.putString(KEY_POISONCOUNTERSP2,poisonCountersP2);

        editor.apply();

    }

    private void restoreSharedPreferences() {

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        changeColorP1 = sharedPreferences.getInt(KEY_COLORP1,0);
        changeColorP2 = sharedPreferences.getInt(KEY_COLORP2,0);
        healthP1.setText(sharedPreferences.getString(KEY_P1HP,"20"));
        healthP2.setText(sharedPreferences.getString(KEY_P2HP,"20"));
        updateView(changeColorP1,changeColorP2,healthP1.getText().toString(),healthP2.getText().toString());
        commanderDamageP1 = sharedPreferences.getString(KEY_COMMANDERDMGP1,"0");
        poisonCountersP1 = sharedPreferences.getString(KEY_POISONCOUNTERSP1,"0");
        commanderDamageP2 = sharedPreferences.getString(KEY_COMMANDERDMGP2,"0");
        poisonCountersP2 = sharedPreferences.getString(KEY_POISONCOUNTERSP2,"0");
    }

    //-------------------UPDATE LIVES COLORS-------------//

    public void updateView(int changeColorP1, int changeColorP2, String savedHealthP1, String savedHealthP2){

        Context context = getApplicationContext();

        switch (changeColorP2){
            case 1: relativeLayoutP2.setBackgroundColor(ContextCompat.getColor(context,R.color.red));
                healthP2.setTextColor(ContextCompat.getColor(context,R.color.white));
                break;
            case 2: relativeLayoutP2.setBackgroundColor(ContextCompat.getColor(context,R.color.green_forest));
                healthP2.setTextColor(ContextCompat.getColor(context,R.color.white));
                break;
            case 3: relativeLayoutP2.setBackgroundColor(ContextCompat.getColor(context,R.color.black));
                healthP2.setTextColor(ContextCompat.getColor(context,R.color.white));
                break;
            case 4: relativeLayoutP2.setBackgroundColor(ContextCompat.getColor(context,R.color.mtgwhite));
                healthP2.setTextColor(ContextCompat.getColor(context,R.color.black));
                break;
            case 5: relativeLayoutP2.setBackgroundColor(ContextCompat.getColor(context,R.color.blue));
                healthP2.setTextColor(ContextCompat.getColor(context,R.color.white));
                break;
        }

        switch (changeColorP1){
            case 1: relativeLayoutP1.setBackgroundColor(ContextCompat.getColor(context,R.color.red));
                healthP1.setTextColor(ContextCompat.getColor(context,R.color.white));
                break;
            case 2: relativeLayoutP1.setBackgroundColor(ContextCompat.getColor(context,R.color.green_forest));
                healthP1.setTextColor(ContextCompat.getColor(context,R.color.white));
                break;
            case 3: relativeLayoutP1.setBackgroundColor(ContextCompat.getColor(context,R.color.black));
                healthP1.setTextColor(ContextCompat.getColor(context,R.color.white));
                break;
            case 4: relativeLayoutP1.setBackgroundColor(ContextCompat.getColor(context,R.color.mtgwhite));
                healthP1.setTextColor(ContextCompat.getColor(context,R.color.black));
                break;
            case 5: relativeLayoutP1.setBackgroundColor(ContextCompat.getColor(context,R.color.blue));
                healthP1.setTextColor(ContextCompat.getColor(context,R.color.white));
                break;
        }
        healthP1.setText(savedHealthP1);
        healthP2.setText(savedHealthP2);
    }

    //----------------------------FRAGMENT 1------------------------------------//

    private void startFragmentP1() {

        FragmentP1 fragmentP1 = FragmentP1.newInstance(poisonCountersP1, commanderDamageP1);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragmentContainer, fragmentP1, "Fragment_P1").commit();

    }

    @Override
    public void onFragmentInteraction(String sendBackCommanderDmgP1, String sendBackPoisonP1) {
        commanderDamageP1 = sendBackCommanderDmgP1;
        poisonCountersP1 = sendBackPoisonP1;
    }

    @Override
    public void onFragmentClose() {
        onBackPressed();
    }

    /// -----------------------------------FRAGMENT 2 -------------------------------------------//

    private void startFragmentP2() {

        FragmentP2 fragmentP2 = FragmentP2.newInstance(poisonCountersP2, commanderDamageP2);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragmentContainer, fragmentP2, "Fragment_P2").commit();

    }

    @Override
    public void onFragmentInteraction2(String sendBackCommanderDmgP2, String sendBackPoisonP2) {
        commanderDamageP2 = sendBackCommanderDmgP2;
        poisonCountersP2 = sendBackPoisonP2;
    }

    @Override
    public void onFragmentClose2() {
        onBackPressed();
    }

    ///----------------------------------FRAGMENT COLORS-----------------------------------//
    private void startFragmentColors() {

        FragmentColors fragmentColors = FragmentColors.newInstance(colorPlayer, 0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.executePendingTransactions();

        if( fragmentManager.findFragmentByTag("Fragment_colors") == null ) {

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.add(R.id.fragmentContainer, fragmentColors, "Fragment_colors").commit();

        } else {
            closeColorFragment();
        }
    }

    private void startFragmentColorsRotated() {

        FragmentColors fragmentColors = FragmentColors.newInstance(colorPlayer, 180);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.executePendingTransactions();

        if( fragmentManager.findFragmentByTag("Fragment_colors") == null ) {

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.add(R.id.fragmentContainer, fragmentColors, "Fragment_colors").commit();

        } else {
            closeColorFragment();
        }
    }

    @Override
    public void onColorInteraction(String sendColor) {
        colorPlayer = sendColor;
        setPlayerColor(currentPlayer);

        onBackPressed();
    }

    @Override
    public void closeColorFragment(){
        onBackPressed();
    }
}