package ar.edu.utn.frba.dds.impactoambiental.models.notificaciones;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface WhatsAppApi {
  static WhatsAppApi create(String phoneNumberId) {
    return new Retrofit.Builder()
        .baseUrl("https://graph.facebook.com/v13.0/" + phoneNumberId + "/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WhatsAppApi.class);
  }

  @POST("messages")
  Call<Object> sendTemplate(@Body WhatsAppTemplate template,
                            @Header("Authorization") String authorization);
}
