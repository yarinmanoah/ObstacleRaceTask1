package com.example.obstacleracetask1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {
    private AppCompatImageButton game_BTN_left,game_BTN_right;
    private ImageView[] game_IMG_player;
    private ImageView[][] game_IMG_ios;
    private ShapeableImageView[] game_IMG_hearts;
    private ShapeableImageView space_IMG_background;
    public static final int DELAY = 1000;
    //public static final int vibrate = 500;

    final Handler handler = new Handler();
    private GameManger gameManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();

        Glide
                .with(this)
                .load("https://www.pngall.com/wp-content/uploads/2016/07/Space-PNG-HD.png").into((space_IMG_background));

        gameManager = new GameManger();
        initViews();
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTimer();
    }

    private void initViews() {
        game_BTN_left.setOnClickListener(v -> {
            if (game_IMG_player[1].isShown()) {
                game_IMG_player[0].setVisibility(View.VISIBLE);
                game_IMG_player[1].setVisibility(View.INVISIBLE);
                gameManager.setAndroidIndex(0);
            } else if (game_IMG_player[2].isShown()) {
                game_IMG_player[1].setVisibility(View.VISIBLE);
                game_IMG_player[2].setVisibility(View.INVISIBLE);
                gameManager.setAndroidIndex(1);

            }
        });
        game_BTN_right.setOnClickListener(view -> {
            if (game_IMG_player[0].isShown()) {
                game_IMG_player[0].setVisibility(View.INVISIBLE);
                game_IMG_player[1].setVisibility(View.VISIBLE);
                game_IMG_player[2].setVisibility(View.INVISIBLE);
                gameManager.setAndroidIndex(1);

            } else if (game_IMG_player[1].isShown()) {
                game_IMG_player[0].setVisibility(View.INVISIBLE);
                game_IMG_player[1].setVisibility(View.INVISIBLE);
                game_IMG_player[2].setVisibility(View.VISIBLE);
                gameManager.setAndroidIndex(2);

            }
        });
    }

    private void findView() {
        game_BTN_left = findViewById(R.id.game_BTN_left);
        game_BTN_right = findViewById(R.id.game_BTN_right);
        space_IMG_background = findViewById(R.id.space_IMG_background);
        initIosArr();
        initHeartArr();
        initAndroidArr();
    }

    private void initAndroidArr() {
        game_IMG_player = new ImageView[]{
                findViewById(R.id.game_IMG_PlayerLeft),
                findViewById(R.id.game_IMG_PlayerCenter),
                findViewById(R.id.game_IMG_PlayerRight),
        };
    }

    private void initHeartArr() {
        game_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_heart3),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart1),
        };
    }

    private void initIosArr() {
        game_IMG_ios = new ImageView[][]{
                {findViewById(R.id.game_IMG_ios1),
                        findViewById(R.id.game_IMG_ios2),
                        findViewById(R.id.game_IMG_ios3)},

                {findViewById(R.id.game_IMG_ios4),
                        findViewById(R.id.game_IMG_ios5),
                        findViewById(R.id.game_IMG_ios6)},

                {findViewById(R.id.game_IMG_ios7),
                        findViewById(R.id.game_IMG_ios8),
                        findViewById(R.id.game_IMG_ios9)},

                {findViewById(R.id.game_IMG_ios10),
                        findViewById(R.id.game_IMG_ios11),
                        findViewById(R.id.game_IMG_ios12)},

                {findViewById(R.id.game_IMG_ios13),
                        findViewById(R.id.game_IMG_ios14),
                        findViewById(R.id.game_IMG_ios15)}
        };
    }

    private void refreshHearts() {
        boolean[] lifes = gameManager.getLifes();
            for (int i = 0; i < lifes.length; i++) {
                if (lifes[i])
                    game_IMG_hearts[i].setVisibility(View.VISIBLE);
                else
                    game_IMG_hearts[i].setVisibility(View.INVISIBLE);
                }
    }



    private void refreshIosUI() {
        for (int i = 0; i < gameManager.getROWS(); i++) {
            for (int j = 0; j < gameManager.getCOLUMNS(); j++) {
                if (gameManager.isActive(i, j))
                    game_IMG_ios[i][j].setVisibility(View.VISIBLE);
                else game_IMG_ios[i][j].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void refreshUI() {
        gameManager.update();
        //if (gameManager.isFinish()) {
        //    vibrate();
        //    refreshHearts();
        //    refreshIosUI();
        //    Toast
        //            .makeText(this, "Game Over", Toast.LENGTH_SHORT)
        //            .show();
        //    stopTimer();
        //    finish();
        //} else {
            if (gameManager.isHit) {
                refreshHearts();
                Toast
                        .makeText(this, "HIT", Toast.LENGTH_SHORT)
                        .show();
                vibrate();
                gameManager.setHit(false);
            }
       // }
        refreshIosUI();
    }

    Runnable runnable = new Runnable() {
        public void run() {
            handler.postDelayed(this, DELAY);
            refreshUI();
        }
    };


    private void startTimer() {
        handler.postDelayed(runnable, DELAY);
    }

    private void stopTimer() {
        handler.removeCallbacks(runnable);
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }
}


