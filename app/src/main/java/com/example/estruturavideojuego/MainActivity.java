package com.example.estruturavideojuego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static int aleatorio=0;
    TextView textoVirus;
    String url;
    ImageView imagenVirus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textoVirus = findViewById(R.id.losVirus);
        Random random = new Random();
        while(aleatorio==0){
            aleatorio = random.nextInt(5);
        }
        url = "https://fp.cloud.riberadeltajo.es/mensaje"+aleatorio+".txt";
        Log.d("ERROR: ", " url= " + url);
cargar();
        imagenVirus = findViewById(R.id.virus);
        imagenVirus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LanzarJuego.class);
                startActivity(i);
            }
        });


    }

    public void cargar(){
        textoVirus.setMovementMethod(new ScrollingMovementMethod());
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new TextoVirus().execute(url);
        } else {
            textoVirus.setText("No se ha podido establecer conexión a internet");
        }
    }

    public class TextoVirus extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            // params viene del método execute() call: params[0] es la url.
            try {
                return descargaUrl(url);
            } catch (IOException e) {
                return "Imposible cargar la web! URL mal formada";
            }
        }



        @Override
        protected void onPostExecute(String result) {
            textoVirus.setText(result);
        }



        // Dada una URL, establece una conexión HttpUrlConnection y devuelve
        // el contenido de la página web con un InputStream, y que se transforma a un String.

        private String descargaUrl(String myurl) throws IOException {
            InputStream is = null;

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milisegundos */);
                conn.setConnectTimeout(15000 /* milisegundos */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // comienza la consulta
                conn.connect();
                int response = conn.getResponseCode();
                is = conn.getInputStream();

                // convertir el InputStream a string
                return leerTexto(is);


            } finally {
                if (is != null) {
                    //Nos aseguramos de cerrar el inputStream.
                    is.close();
                }
            }
        }


        private String leerTexto(InputStream is) {
            try {
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                int i = is.read();
                while (i != -1) {
                    bo.write(i);
                    i = is.read();
                }
                return bo.toString();
            } catch (IOException e) {
                return "";
            }
        }

    }

}