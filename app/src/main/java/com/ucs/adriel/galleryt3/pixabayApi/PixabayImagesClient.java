package com.ucs.adriel.galleryt3.pixabayApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PixabayImagesClient {
    public static final String BASE_URL = "https://pixabay.com/api/?key=12910008-7dadaa78e83b4ce14813b818b";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
