package com.jibou.jibouAccueil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import api.ApiService;
import api.RetrofitClient;
import api.Utilisateur;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Jibou_Inscription extends AppCompatActivity {
    private static final String TAG = "Jibou_Inscription";
    private EditText Nom_utilisateur;
    private EditText Prenom_utilisateur;
    private EditText Email_utilisateur;
    private EditText Password_utilisateur;
    private Button Inscrire;
    private ImageView Retour;
    private LinearLayout google_button;
    private TextView Se_connecter;

    //url de l'api
    private static final String URL = "http://192.168.100.37:5000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jibou_inscription);
        Nom_utilisateur = findViewById(R.id.register_firstname);
        Prenom_utilisateur = findViewById(R.id.register_lastname);
        Email_utilisateur = findViewById(R.id.register_email);
        Password_utilisateur = findViewById(R.id.register_password);
        Inscrire = findViewById(R.id.register_button);
        Retour = findViewById(R.id.back_arrow);
        google_button = findViewById(R.id.google_sign_in_button);
        Se_connecter = findViewById(R.id.connect_link_text);


        //si l'utilisateur clique sur le bouton retour, il sera redirigé vers la page de connexion
        Retour.setOnClickListener(v -> {
            startActivity(new Intent(Jibou_Inscription.this, Jibou_connection.class));
        });

        //si l'utilisateur clique sur le texte se connecter, il sera redirigé vers la page de connexion
        Se_connecter.setOnClickListener(v -> {
            startActivity(new Intent(Jibou_Inscription.this, Jibou_connection.class));
        });

        //si l'utilisateur clique sur le bouton google, il sera redirigé vers la page de connexion
        google_button.setOnClickListener(v -> {
            Toast.makeText(this, "Connexion avec Google", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(Jibou_Inscription.this, Jibou_connection.class));
        });

        //si l'utilisateur clique sur le bouton s'inscrire, verifier les données de l'utilisateur si elles sont correctes
        //l'utilisateur sera redirigé vers la page de connexion
        Inscrire.setOnClickListener(v -> {
            Log.d(TAG, "onCreate: " + v);
            Inscrire(v);
            });

    }

    public void Inscrire(View view) {
        String nom = Nom_utilisateur.getText().toString();
        String prenom = Prenom_utilisateur.getText().toString();
        String email = Email_utilisateur.getText().toString();
        String mot_de_passe = Password_utilisateur.getText().toString();
        String date_inscription = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mot_de_passe.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        } else if (!isEmailValid(email)) {
            Toast.makeText(this, "Email invalide", Toast.LENGTH_SHORT).show();
        } else if (!isPasswordValid(mot_de_passe)) {
            Toast.makeText(this, "Mot de passe invalide", Toast.LENGTH_SHORT).show();
        } else {
            ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
            Call<Utilisateur> call = apiService.createUtilisateur(nom, prenom, email, mot_de_passe, date_inscription);
            call.enqueue(new retrofit2.Callback<Utilisateur>() {
                @Override
                public void onResponse(Call<Utilisateur> call, retrofit2.Response<Utilisateur> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(Jibou_Inscription.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onResponse: " + response.body());
                        startActivity(new Intent(Jibou_Inscription.this, Jibou_connection.class));
                    } else {
                        Log.d(TAG, "onResponse: " + response.errorBody());
                        Toast.makeText(Jibou_Inscription.this, "Erreur lors de l'inscription", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Utilisateur> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    Toast.makeText(Jibou_Inscription.this, "Erreur lors de l'inscription", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

}
