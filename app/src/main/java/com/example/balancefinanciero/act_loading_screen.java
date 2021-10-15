package com.example.balancefinanciero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class act_loading_screen extends AppCompatActivity {
ProgressBar pb_carga;
Handler handler=new Handler();
Intent intent;
int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_loading_screen);
        pb_carga=findViewById(R.id.progressBar_id);
        cargarPantalla();
    }

    private void cargarPantalla() {

        Thread hilo=new Thread(new Runnable() {
            @Override
            public void run() {
                while(i<=100){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pb_carga.setProgress(i);
                        }
                    });
                    try {
                        Thread.sleep(30);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    if (i==100){
                        intent=new Intent(act_loading_screen.this, act_principal.class);
                        startActivity(intent);
                    }
                    i++;
                }
            }
        });
        hilo.start();//ejecuta el hilo
    }

}