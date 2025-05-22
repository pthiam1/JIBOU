package com.jibou.jibouAccueil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import api.ApiService;
import api.RetrofitClient;
import api.Utilisateur;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//RetrofitClie

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Jibou_connection  extends AppCompatActivity {
    private static final String TAG = "Jibou_connection";
    private EditText Email_utilisateur;
    private EditText Password_utilisateur;
    private TextView Mot_de_passe_oublie;
    private CheckBox Se_souvenir_de_moi;
    private Button Se_connecter;
    private ImageView Retour;

    private SharedPreferences sharedPreferences; //pour sauvegarder les données de l'utilisateur



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jibou_connecter);
        Email_utilisateur = findViewById(R.id.mail_input);
        Password_utilisateur = findViewById(R.id.mot_de_passe_input);
        Mot_de_passe_oublie = findViewById(R.id.mot_de_passe_oublie);
        Se_souvenir_de_moi = findViewById(R.id.se_souvenir_de_moi);
        Se_connecter = findViewById(R.id.connexion_button);
        Retour = findViewById(R.id.back_arrow);
        sharedPreferences = getSharedPreferences("Jibou", MODE_PRIVATE);


        //si l'utilisateur clique sur le texte mot de passe oublié il sera redirigé vers la page de réinitialisation du mot de passe
        Mot_de_passe_oublie.setOnClickListener(v -> {
            startActivity(new Intent(Jibou_connection.this, Jibou_PassWordOublié.class));
        });


        Retour.setOnClickListener(v -> {
            startActivity(new Intent(Jibou_connection.this, Jibou_Accueil.class));
        });

        //si l'utilisateur clique sur le bouton se connecter , verifier les données de l'utilisateur si elles sont correctes
        //l'utilisateur sera redirigé vers la page Profil
        Se_connecter.setOnClickListener(v -> {
            Se_connecter(v);
            Log.d(TAG, "onCreate: " + Email_utilisateur.getText().toString());
        });

    }

    public void Se_connecter(View view) {
            String email = Email_utilisateur.getText().toString();
            String mot_de_passe = Password_utilisateur.getText().toString();
            if (email.isEmpty() || mot_de_passe.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            }
            //Vérifier si l'adresse email est valide
//            isValideEmail(email);
            //V
        // érifier si le mot de passe est valide
//            isValidePassword(mot_de_passe);
            ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
            Call<List<Utilisateur>> call = apiService.getUtilisateurs();
            call.enqueue(new Callback<List<Utilisateur>>() {
                @Override
                public void onResponse(Call<List<Utilisateur>> call, Response<List<Utilisateur>> response) {
                    if (response.isSuccessful()) {
                        List<Utilisateur> utilisateurs = response.body();
                        for (Utilisateur utilisateur : utilisateurs) {
                            if (utilisateur.getEmail() != null && utilisateur.getMot_de_passe() != null) {
                                if (utilisateur.getEmail().equals(email) && utilisateur.getMot_de_passe().equals(mot_de_passe)) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("email", email);
                                    editor.putString("mot_de_passe", mot_de_passe);
                                    editor.apply();
                                    Toast.makeText(Jibou_connection.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onResponse: " + utilisateur.toString());
                                    startActivity(new Intent(Jibou_connection.this, Jibou_Profil.class));
                                }else {
                                    Toast.makeText(Jibou_connection.this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(Jibou_connection.this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Utilisateur>> call, Throwable t) {
                    Log.d(TAG, "Ecchec de la connexion: " + t.getMessage());
                    Toast.makeText(Jibou_connection.this, "Echec de la connexion", Toast.LENGTH_SHORT).show();
                }
            });
            }


        //Vérifier si l'adresse email est valide
        void isValideEmail(String email) {
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if (email.matches(emailPattern)) {
                Toast.makeText(getApplicationContext(), "Adresse email valide", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Adresse email invalide", Toast.LENGTH_SHORT).show();
            }
        }

        //Vérifier si le mot de passe est valide et respecte les conditions ie 8 caractères, une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial
        void isValidePassword(String password) {
            String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
            if (password.matches(passwordPattern)) {
                Toast.makeText(getApplicationContext(), "Mot de passe valide", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Mot de passe invalide", Toast.LENGTH_SHORT).show();
            }
        }
}
