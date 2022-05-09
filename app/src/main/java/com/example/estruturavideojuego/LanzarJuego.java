package com.example.estruturavideojuego;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class LanzarJuego extends AppCompatActivity {
    private Juego juego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        juego = new Juego(this);
        setContentView(juego);

    }
}
