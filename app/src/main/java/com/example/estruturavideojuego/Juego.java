package com.example.estruturavideojuego;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.google.android.material.navigationrail.NavigationRailMenuView;

import java.util.ArrayList;
import java.util.Random;

public class Juego extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener  {

    private SurfaceHolder holder;
    private BucleJuego bucle;
    public int numeroVirus;
    public int virusMatados=0;
    private Activity activity;
    public Bitmap virus;
    public int maxX,maxY;
    Random aleatorio;
    private ArrayList<Virus> virusArrayList = new ArrayList<Virus>();
    int contadorVirus=0;
    private static final String TAG = Juego.class.getSimpleName();
    public boolean hayToque=false;
    private ArrayList<Toque> toques = new ArrayList<Toque>();
    int touchX, touchY, index;

    public Juego(Activity context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        activity = context;
        numeroVirus = MainActivity.aleatorio;
        Log.d("ELLL", " es " + numeroVirus);
        setOnTouchListener(this);

    }

    public void cargarVirus(){
        virus = BitmapFactory.decodeResource(getResources(), R.drawable.virusillo);
        virus.createScaledBitmap(virus, 80, 80, false);
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // se crea la superficie, creamos el game loop

        // Para interceptar los eventos de la SurfaceView
        getHolder().addCallback(this);
        //Tamaño del Canvas
        Canvas c=holder.lockCanvas();
        maxX = c.getWidth();
        maxY = c.getHeight();
        holder.unlockCanvasAndPost(c);

        // creamos el game loop
        bucle = new BucleJuego(getHolder(), this);

        // Hacer la Vista focusable para que pueda capturar eventos
        setFocusable(true);

        aleatorio = new Random();
        cargarVirus();


        //comenzar el bucle
        bucle.start();

    }

    /**
     * Este método actualiza el estado del juego. Contiene la lógica del videojuego
     * generando los nuevos estados y dejando listo el sistema para un repintado.
     */
   // int contadorVirusMuertos;
    public void actualizar() {

        //contadorVirus = MainActivity.aleatorio-virusMatados;
Log.d("Numero de virus: " , " son " + numeroVirus);
        // Poner virus

        for (int i=0; i< numeroVirus; i++){
            crearNuevoVirus();
        }

        for (Virus vi : virusArrayList){
            vi.actualizarPoscicion();
        }

        for (Virus vi: virusArrayList){
            if (hayToque){
                if (touchX>=vi.coordenada_x && touchX<=vi.coordenada_x+virus.getWidth()
                && touchY>=vi.coordenada_y && touchY<=vi.coordenada_y+virus.getHeight()){
                    virusMatados++;
                    virusArrayList.remove(vi);
                }
            }
        }


    }

    /**
     * Este método dibuja el siguiente paso de la animación correspondiente
     */
    public void renderizar(Canvas canvas) {

        canvas.drawColor(Color.RED);

        //pintar mensajes que nos ayudan
        Paint p=new Paint();
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setColor(Color.BLACK);
        p.setTextSize(50);
        canvas.drawText("MATA AL VIRUS", 100, 150, p);
        canvas.drawText("Virus restantes: " + (contadorVirus-virusMatados), 100, maxY-250,p );
        canvas.drawText("Frame "+bucle.iteraciones+";"+"Tiempo "+bucle.tiempoTotal,150,300,p);


        for (Virus vi: virusArrayList){
            vi.pintarVirus(canvas, p);
        }

        if(hayToque){
            canvas.drawCircle(touchX, touchY, 50, p);
        }


    }



    public void crearNuevoVirus(){
        if (numeroVirus>contadorVirus){
            Log.d("Contador: ", " numeroVirus=" + numeroVirus + " contador " + contadorVirus);
            virusArrayList.add(new Virus(this));
            contadorVirus++;
        }
    }


    public void pintarVirus(){
        for (int i =0;i<numeroVirus;i++) {

         //   Log.d("xVirus: ", xVirus + " yVirus: " + yVirus);
          //  xVirus = aleatorio.nextInt(AnchoPantalla);
          //  yVirus = aleatorio.nextInt(AltoPantalla);
         //   Log.d("Numero ", +i+ " de virus: " + numeroVirus+"xVirus: " +xVirus + " yVirus: " + yVirus);
            Virus v = new Virus(this);
            virusArrayList.add(v);
            Log.d("Array: ", "tamaño " + virusArrayList.size());
        }
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Juego destruido!");
        // cerrar el thread y esperar que acabe
        boolean retry = true;
        while (retry) {
            try {
                bucle.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                hayToque = true;
                touchX = (int) event.getX(index);
                touchY = (int) event.getY(index);
                synchronized (this) {
                    toques.add(index, new Toque(index, touchX, touchY));
                }
                Log.i(Juego.class.getSimpleName(), "Pulsado dedo " + index + ".");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                synchronized (this) {
                    toques.remove(index);
                }
                Log.i(Juego.class.getSimpleName(), "Soltado dedo " + index + ".");
                break;
            case MotionEvent.ACTION_UP:
                synchronized (this) {
                    toques.remove(index);
                }
                Log.i(Juego.class.getSimpleName(), "Soltado dedo " + index + ".ultimo.");
                hayToque = false;
                break;
        }
return true;
    }
}
