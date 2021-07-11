package com.luckyba.myfiledemo.ui.main.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luckyba.myfiledemo.R;
import com.luckyba.myfiledemo.ui.main.view.viewholder.CommonListener;
import com.luckyba.myfiledemo.ui.main.view.viewholder.PictureHolder;

import java.util.ArrayList;

import static androidx.core.view.ViewCompat.setTransitionName;

public class PictureAdapter extends RecyclerView.Adapter<PictureHolder> {

    private ArrayList<PictureFacer> pictureList;
    private Context pictureContx;
    private final CommonListener mListerner;

    /**
     *
     * @param pictureList ArrayList of pictureFacer objects
     * @param pictureContx The Activities Context
     * @param picListerner An interface for listening to clicks on the RecyclerView's items
     */
    public PictureAdapter(ArrayList<PictureFacer> pictureList, Context pictureContx, CommonListener picListerner) {
        this.pictureList = pictureList;
        this.pictureContx = pictureContx;
        this.mListerner = picListerner;
    }

    @NonNull
    @Override
    public PictureHolder onCreateViewHolder(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View cell = inflater.inflate(R.layout.pic_holder_item, container, false);
        return new PictureHolder(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull final PictureHolder holder, final int position) {

        final PictureFacer image = pictureList.get(position);

        Glide.with(pictureContx)
                .load(image.getPicturePath())
                .apply(new RequestOptions().centerCrop())
                .into(holder.picture);

        setTransitionName(holder.picture, String.valueOf(position) + "_image");

        holder.picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListerner.onclick(v, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }
}
