package com.luckyba.myfiledemo.ui.main.view.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.luckyba.myfiledemo.R;

public class FolderHolder extends RecyclerView.ViewHolder{
    public ImageView folderPic;
    public TextView folderName;
    //set textview for foldersize
    public TextView folderSize;

    public CardView folderCard;

    public FolderHolder(@NonNull View itemView) {
        super(itemView);
        folderPic = itemView.findViewById(R.id.folderPic);
        folderName = itemView.findViewById(R.id.folderName);
        folderSize=itemView.findViewById(R.id.folderSize);
        folderCard = itemView.findViewById(R.id.folderCard);
    }
}
