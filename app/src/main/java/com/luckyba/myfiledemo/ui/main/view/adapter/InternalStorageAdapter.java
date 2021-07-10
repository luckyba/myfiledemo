package com.luckyba.myfiledemo.ui.main.view.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luckyba.myfiledemo.R;
import com.luckyba.myfiledemo.data.model.InternalStorageFilesModel;
import com.luckyba.myfiledemo.ui.main.view.MainActivity;
import com.luckyba.myfiledemo.ui.main.view.viewholder.CommonViewHolder;
import com.luckyba.myfiledemo.ui.main.view.viewholder.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InternalStorageAdapter extends RecyclerView.Adapter<CommonViewHolder> {
    ArrayList<InternalStorageFilesModel> mListData;
    Listener mListener;
    public InternalStorageAdapter() {
        super();
    }

    public void setListener (Listener listener) {
        mListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.media_list_item_view, parent, false);
        return new CommonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {

        holder.itemView.setOnClickListener(v->mListener.onclick(v, position));
        holder.itemView.setOnLongClickListener(v -> {mListener.onLongClick(v, position); return false;});

        InternalStorageFilesModel data = mListData.get(position);
        holder.nameItem.setText(data.getFileName());
        String fileExtension = data.getFileName().substring(data.getFileName().lastIndexOf(".") + 1);
        File file = new File(data.getFilePath());
        if (file.isDirectory()) {//if list item folder the set icon
            holder.iconItem.setImageResource(R.drawable.ic_outline_folder_24);
        } else if (fileExtension.equals("png") || fileExtension.equals("jpeg") || fileExtension.equals("jpg")) {//if list item any image then
            File imgFile = new File(data.getFilePath());
            if (imgFile.exists()) {
                int THUMB_SIZE = 64;
                Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(data.getFilePath()),
                        THUMB_SIZE, THUMB_SIZE);
                holder.iconItem.setImageBitmap(ThumbImage);
            }
        } else if (fileExtension.equals("pdf")) {
            holder.iconItem.setImageResource(R.drawable.ic_pdf_file);
        } else if (fileExtension.equals("mp3")) {
            holder.iconItem.setImageResource(R.drawable.ic_outline_audiotrack_24);
        } else if (fileExtension.equals("txt")) {
            holder.iconItem.setImageResource(R.drawable.ic_text_file);
        } else if (fileExtension.equals("zip") || fileExtension.equals("rar")) {
            holder.iconItem.setImageResource(R.drawable.ic_zip_folder);
        } else if (fileExtension.equals("html") || fileExtension.equals("xml")) {
            holder.iconItem.setImageResource(R.drawable.ic_html_file);
        } else if (fileExtension.equals("mp4") || fileExtension.equals("3gp") || fileExtension.equals("wmv") || fileExtension.equals("avi")) {
            Bitmap bMap = ThumbnailUtils.createVideoThumbnail(data.getFilePath(), MediaStore.Video.Thumbnails.MICRO_KIND);
            holder.iconItem.setImageBitmap(bMap);
        } else if (fileExtension.equals("apk")) {
            holder.iconItem.setImageResource(R.drawable.ic_apk);
        } else {
            holder.iconItem.setImageResource(R.drawable.ic_un_supported_file);
        }
        if(data.isCheckboxVisible()){
            holder.checkBox.setVisibility(View.VISIBLE);
        }else{
            holder.checkBox.setVisibility(View.GONE);
        }
        if(data.isSelected()){
            holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public void setData(ArrayList<InternalStorageFilesModel> data) {
        mListData = data;
    }

    public ArrayList<InternalStorageFilesModel> getListData () {
        return mListData;
    }
}
