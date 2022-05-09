package com.example.estruturavideojuego;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

public class Virus {
    Bitmap virus;
    public static float xVirus, yVirus;
    private Juego j;
    Random aleatorio = new Random();


    public Virus(Juego juego){
        j = juego;
        posicionVirus();
        Log.d("Posicion", " de virus: X="+ xVirus + " y: " + yVirus);
    }


    public void posicionVirus(){
        xVirus = aleatorio.nextInt(j.AnchoPantalla/2);
        yVirus = aleatorio.nextInt(j.AltoPantalla/2);
    }

    public void pintarVirus(Canvas canvas, Paint paint){

        //En coordenadas le pongo entre 1.5 para adecuar
      /*  canvas.drawBitmap(j.virus, new Rect((int) puntero_misil, 0, (int) (puntero_misil + j.misilEnemigo.getWidth()/9), j.misilEnemigo.getHeight()),
                new Rect((int)coordenadaMisil, (int) coordenadaYMisil-j.misilEnemigo.getHeight(), (int)coordenadaMisil+j.misilEnemigo.getWidth()/9, (int) (j.misilEnemigo.getHeight()/1.5+coordenadaYMisil)-j.misilEnemigo.getHeight()),
                null);
        Log.d("MISIL: ", " Y Mi Misil: " + coordenadaYMisil +
                " X mi  misil: " + coordenadaMisil + " velocidad mi misil: " + velocidadMisil);
*/
        canvas.drawBitmap(j.virus, aleatorio.nextInt(j.AnchoPantalla),aleatorio.nextInt(j.AltoPantalla),paint);


    }



}
