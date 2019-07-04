package com.ucs.adriel.galleryt3.adapter;

import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ucs.adriel.galleryt3.MainActivity;
import com.ucs.adriel.galleryt3.model.Hit;
import com.ucs.adriel.galleryt3.R;

import java.io.IOException;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<Hit> images;
    public Adapter(List<Hit> images) {
        this.images = images;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_list_item,viewGroup,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position)
    {
        viewHolder.setData(images.get(position));
    }
    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        private ImageView imgView;
        private Button btnApplyWP;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imgView = itemView.findViewById(R.id.imgView);
            btnApplyWP = itemView.findViewById(R.id.btnApplyWP);
        }
        private void setData(Hit img) {
            Picasso.get().load(img.getLargeImageURL()).into(imgView);//esse carinha é async

           btnApplyWP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(view.getContext());

                    try {
                        Bitmap bmp = ((BitmapDrawable)imgView.getDrawable()).getBitmap();
                        if(bmp == null)
                        {
                            Toast.makeText(view.getContext(),"Não foi possível aplicar o papel de parede",Toast.LENGTH_LONG).show();
                            return;
                        }
                        wallpaperManager.setBitmap(bmp);
                        Toast.makeText(view.getContext(),"Papel de parede aplicado com sucesso",Toast.LENGTH_LONG).show();
                       view.getContext().startActivity(new Intent(view.getContext(), MainActivity.class));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }});
        }
       public void onClick(final View view) {
          /*  Toast.makeText(view.getContext(),"Você selecionou " + images.get(getLayoutPosition()).getId(),Toast.LENGTH_LONG).show();

            final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    Toast.makeText(view.getContext(),"ook",Toast.LENGTH_LONG).show();
                }
            });
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //opção não
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();*/
        }
    }
}