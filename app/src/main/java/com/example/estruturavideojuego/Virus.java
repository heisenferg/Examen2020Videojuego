package com.example.estruturavideojuego;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

public class Virus {
    public float velocidad_Enemigo;
    public float coordenada_x, coordenada_y;

    private Juego juego;

    public Virus(Juego j) {
        Random randon = new Random();
        juego = j;

        coordenada_y = randon.nextInt(580);
        coordenada_x = randon.nextInt(190);
        velocidad_Enemigo = j.maxX / 5f / BucleJuego.MAX_FPS;
    }

    public void pintarVirus(Canvas c, Paint paint) {
        c.drawBitmap(juego.virus, coordenada_x, coordenada_y, paint);
    }

    public void actualizarPoscicion() {
        if(coordenada_x > juego.maxX || coordenada_x < 0 ){
            velocidad_Enemigo *=-1;
        }else if(coordenada_y > juego.maxY){
            velocidad_Enemigo *=-1;
        }
        coordenada_x += velocidad_Enemigo;
        coordenada_y += velocidad_Enemigo;




    }
    public int Alto () {
        return juego.virus.getHeight();
    }
    public int Ancho () {
        return juego.virus.getWidth();
    }

}
