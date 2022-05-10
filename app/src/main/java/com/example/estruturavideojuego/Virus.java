package com.example.estruturavideojuego;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

public class Virus {
    Bitmap virus;
    public static float xVirus, yVirus;
    private Juego j;
    Random aleatorio = new Random();

    public static float getxVirus() {
        return xVirus;
    }

    public static void setxVirus(float xVirus) {
        Virus.xVirus = xVirus;
    }

    public static float getyVirus() {
        return yVirus;
    }

    public static void setyVirus(float yVirus) {
        Virus.yVirus = yVirus;
    }

    public Virus(Juego juego, float pX, float pY){
        j = juego;
       // posicionVirus();
        xVirus=pX;
        yVirus=pY;
        Log.d("Posicion", " de virus: X="+ xVirus + " y: " + yVirus);
    }


    public void pintarVirus(Canvas canvas){
      //  canvas.drawBitmap(j.virus, aleatorio.nextInt(j.AnchoPantalla),aleatorio.nextInt(j.AltoPantalla),paint);
        canvas.drawBitmap(j.virus, xVirus,yVirus,null);
        Log.d("Posiciones: ", " xVirus= "+ xVirus + " yVirus= " + yVirus);
    }



}
