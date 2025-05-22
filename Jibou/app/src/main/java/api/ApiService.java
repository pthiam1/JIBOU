package api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("utilisateurs")
    Call<List<Utilisateur>> getUtilisateurs();

    @POST("utilisateurs")
    Call<Utilisateur> createUtilisateur(
            @Field("nom") String nom,
            @Field("prenom") String prenom,
            @Field("email") String email,
            @Field("mot_de_passe") String mot_de_passe,
            @Field("date_inscription") String date_inscription
    );

}
