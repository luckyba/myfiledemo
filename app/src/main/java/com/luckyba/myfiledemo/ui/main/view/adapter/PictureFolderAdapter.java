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
import com.luckyba.myfiledemo.data.model.ImageFolder;
import com.luckyba.myfiledemo.ui.main.view.viewholder.CommonListener;
import com.luckyba.myfiledemo.ui.main.view.viewholder.FolderHolder;

import java.util.ArrayList;

public class PictureFolderAdapter extends RecyclerView.Adapter<FolderHolder>{

    private ArrayList<ImageFolder> mListFolders = new ArrayList<>();
    private Context mContext;
    private CommonListener listenToClick;

    public PictureFolderAdapter() {
    }

    public void setListener (CommonListener listen) {
        this.listenToClick = listen;
    }

    public void setData(ArrayList<ImageFolder> imageFolders) {
        mListFolders.clear();
        mListFolders = imageFolders;
    }

    @NonNull
    @Override
    public FolderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext= parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View cell = inflater.inflate(R.layout.picture_folder_item, parent, false);
        return new FolderHolder(cell);

    }

    @Override
    public void onBindViewHolder(@NonNull FolderHolder holder, int position) {
        final ImageFolder folder = mListFolders.get(position);

        Glide.with(mContext)
                .load(folder.getFirstPic())
                .apply(new RequestOptions().centerCrop())
                .into(holder.folderPic);

        //setting the number of images
        String text = ""+folder.getFolderName();
        String folderSizeString=""+folder.getNumberOfPics()+" Media";
        holder.folderSize.setText(folderSizeString);
        holder.folderName.setText(text);
        holder.folderPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenToClick.onclick(v, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListFolders.size();
    }


}

