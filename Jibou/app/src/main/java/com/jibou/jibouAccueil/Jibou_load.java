package com.jibou.jibouAccueil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Jibou_load extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jibou_laod);

//        chagement de page apres 3 secondes
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Jibou_load.this, Jibou_Accueil.class);
                startActivity(intent);
                Toast.makeText(Jibou_load.this, "Bienvenue sur Jibou", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, 3000);
    }
}
