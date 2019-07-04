package com.ucs.adriel.galleryt3.pixabayApi;

import com.ucs.adriel.galleryt3.model.PixabayImages;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PixabayImagesInterface {
    @GET(" ")
    Call<PixabayImages> getAllPixabayImages();
}
