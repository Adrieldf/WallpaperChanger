package com.ucs.adriel.galleryt3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.ucs.adriel.galleryt3.adapter.Adapter;
import com.ucs.adriel.galleryt3.model.Hit;
import com.ucs.adriel.galleryt3.model.PixabayImages;
import com.ucs.adriel.galleryt3.pixabayApi.PixabayImagesClient;
import com.ucs.adriel.galleryt3.pixabayApi.PixabayImagesInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineGalleryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Hit> hits;
    private int resultsCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_gallery);

        //set recycler
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        //get images
        PixabayImagesInterface apiService = PixabayImagesClient.getClient().create(PixabayImagesInterface.class);
        Call<PixabayImages> call;

        call = apiService.getAllPixabayImages();
        call.enqueue(new Callback<PixabayImages>() {
            @Override
            public void onResponse(Call<PixabayImages> call, Response<PixabayImages> response) {
                if(response != null)
                {
                    hits = response.body().getHits();
                    recyclerView.setAdapter(new Adapter(hits));
                    resultsCount = hits.size();
                    Toast.makeText(OnlineGalleryActivity.this,resultsCount + " imagens encontradas", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PixabayImages> call, Throwable t) {
                // Log error here since request failed
                Log.e("deu erro", t.toString());
            }
        });
    }
}
