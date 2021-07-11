package com.luckyba.myfiledemo.ui.main.view.viewholder;


import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luckyba.myfiledemo.R;

public class PictureHolder extends RecyclerView.ViewHolder{

    public ImageView picture;

    public PictureHolder(@NonNull View itemView) {
        super(itemView);

        picture = itemView.findViewById(R.id.image);
    }
}

