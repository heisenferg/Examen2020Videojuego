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
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class Juego extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private BucleJuego bucle;
    public int numeroVirus;
    public int virusMatados=0;
    private Activity activity;
    public int AnchoPantalla,AltoPantalla;
    public Bitmap virus;
    public float xVirus,yVirus;
    Random aleatorio = new Random();
    private ArrayList<Virus> virusArrayList = new ArrayList<Virus>();

    private static final String TAG = Juego.class.getSimpleName();

    public Juego(Activity context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        activity = context;
        numeroVirus = MainActivity.aleatorio;


        dimesionesPantalla();
        pintarVirus();

        cargarVirus();


    }

    public void cargarVirus(){
        virus = BitmapFactory.decodeResource(getResources(), R.drawable.virusillo);
        virus.createScaledBitmap(virus, 80, 80, false);
    }

    public void dimesionesPantalla(){
        if(Build.VERSION.SDK_INT > 13) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            AnchoPantalla = size.x;
            AltoPantalla = size.y;
        }
        else{
            Display display = activity.getWindowManager().getDefaultDisplay();
            AnchoPantalla = display.getWidth();  // deprecated
            AltoPantalla = display.getHeight();  // deprecated
        }
        Log.i(Juego.class.getSimpleName(), "alto:" + AltoPantalla + "," + "ancho:" + AnchoPantalla);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // se crea la superficie, creamos el game loop

        // Para interceptar los eventos de la SurfaceView
        getHolder().addCallback(this);


        // creamos el game loop
        bucle = new BucleJuego(getHolder(), this);

        // Hacer la Vista focusable para que pueda capturar eventos
        setFocusable(true);


        //comenzar el bucle
        bucle.start();

    }

    /**
     * Este método actualiza el estado del juego. Contiene la lógica del videojuego
     * generando los nuevos estados y dejando listo el sistema para un repintado.
     */
    public void actualizar() {

       // numeroVirus = MainActivity.aleatorio-virusMatados;

        // Poner virus


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
        canvas.drawText("Virus restantes: " + numeroVirus, 100, AltoPantalla-250,p );
        //canvas.drawText("Frame "+bucle.iteraciones+";"+"Tiempo "+bucle.tiempoTotal,150,150,p);

        // Dibujamos los enemigos
        for(Virus vi: virusArrayList){
            vi.pintarVirus(canvas,p);
            Log.d("Pintado", " virus: "+ virusArrayList.size() + " xVirus: " + vi.xVirus);
        }
        /*for (int i = 0; i<=virusArrayList.size(); i++){
            Virus virus=new Virus(this);
            virus.pintarVirus(canvas,p);
        }*/
    }



    public void pintarVirus(){
        Log.d("Numero", " de virus: " + numeroVirus);
        for (int i = 0; i<numeroVirus; i++){
            Log.d("xVirus: " , xVirus + " yVirus: " + yVirus);
            Virus viruses = new Virus(this);
            virusArrayList.add(viruses);
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


}
