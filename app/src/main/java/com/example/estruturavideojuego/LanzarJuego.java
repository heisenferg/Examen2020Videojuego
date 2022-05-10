package com.example.estruturavideojuego;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LanzarJuego extends AppCompatActivity {
    private Juego juego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        juego = new Juego(this);
        setContentView(juego);
        hideSystemUI();
    }

    private void hideSystemUI() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {

            juego.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

            juego.setOnSystemUiVisibilityChangeListener(new
                                                                View.OnSystemUiVisibilityChangeListener() {
                                                                    @Override
                                                                    public void onSystemUiVisibilityChange(int visibility) {
                                                                        hideSystemUI();
                                                                    }
                                                                });
        }
    }
}


