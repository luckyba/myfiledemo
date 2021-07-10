package com.luckyba.myfiledemo.ui.main.view.viewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luckyba.myfiledemo.R;

public class CommonViewHolder extends RecyclerView.ViewHolder {
    public TextView nameItem;
    public ImageView iconItem;
    public CheckBox checkBox;
    public CommonViewHolder(@NonNull View itemView) {
        super(itemView);
        nameItem = itemView.findViewById(R.id.name_item);
        iconItem = itemView.findViewById(R.id.icon_item);
        checkBox = itemView.findViewById(R.id.check_box);
    }
}
