package com.jibou.jibouAccueil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Jibou_Accueil extends AppCompatActivity {

    private static final String TAG = "Jibou_Accueil";
    private ImageView jibouLogo;
    private Button jibouConnectionButton;
    private Button jibouInscriptionButton;
    private TextView jibouCommentCaMarche;


    //url de l'api
    private static final String URL = "http://192.168.100.37:5000/utilisateurs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_jibou_accueil);
        jibouLogo = findViewById(R.id.logo_jibou);
        jibouConnectionButton = findViewById(R.id.connexion_button);
        jibouInscriptionButton = findViewById(R.id.inscription_button);
        jibouCommentCaMarche = findViewById(R.id.comment_ca_marche);

        //si l'utilisateur clique sur le bouton connexion il sera redirigé vers la page de connexion
        jibouConnectionButton.setOnClickListener(v -> {
            startActivity(new Intent(Jibou_Accueil.this, Jibou_connection.class));
        });

        //si l'utilisateur clique sur le bouton inscription il sera redirigé vers la page d'inscription
        jibouInscriptionButton.setOnClickListener(v -> {
            startActivity(new Intent(Jibou_Accueil.this, Jibou_Inscription.class));
        });
    }
}