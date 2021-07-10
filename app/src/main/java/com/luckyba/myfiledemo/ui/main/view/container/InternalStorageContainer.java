package com.luckyba.myfiledemo.ui.main.view.container;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luckyba.myfiledemo.R;
import com.luckyba.myfiledemo.app.MyFileApplication;
import com.luckyba.myfiledemo.data.model.InternalStorageFilesModel;
import com.luckyba.myfiledemo.ui.main.view.activities.FullImageViewActivity;
import com.luckyba.myfiledemo.ui.main.view.activities.TextFileViewActivity;
import com.luckyba.myfiledemo.ui.main.view.adapter.InternalStorageAdapter;
import com.luckyba.myfiledemo.ui.main.view.viewholder.Listener;
import com.luckyba.myfiledemo.ui.main.viewmodel.HomeViewModel;
import com.luckyba.myfiledemo.util.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InternalStorageContainer implements Listener {

    private static String TAG = "InternalStorageContainer";
    private View mContainer;
    private RecyclerView listView;
    private InternalStorageAdapter mAdapter;
    private HomeViewModel mHomeViewModel;
    private OpenFileHelper mOpenFileHelper;
    private ArrayList<String> arrayListFilePaths;
    private ArrayList<InternalStorageFilesModel> internalStorageFilesModels;
    private String fileExtension;
    private String rootPath;
    private View noMediaLayout;
    private TextView tvFilePath;



    public InternalStorageContainer(View view
            , InternalStorageAdapter adapter, HomeViewModel homeViewModel) {
        mAdapter = adapter;
        mHomeViewModel = homeViewModel;
        mContainer = view;
        initView();
    }

    private void initView() {
        tvFilePath = mContainer.findViewById(R.id.tv_file_path);
        noMediaLayout = mContainer.findViewById(R.id.no_media_layout);
        listView = mContainer.findViewById(R.id.list_home);

        arrayListFilePaths = new ArrayList<>();
        internalStorageFilesModels = new ArrayList<>();
        rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        mOpenFileHelper = new OpenFile();

        mAdapter.setListener(this);
        tvFilePath.setText(mContainer.getContext().getString(R.string.internal_storage));

        arrayListFilePaths.add(rootPath);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContainer.getContext());
        listView.setLayoutManager(layoutManager);
        listView.setHasFixedSize(true);
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.setVisibility(View.VISIBLE);
        listView.setAdapter(mAdapter);

    }

    @Override
    public void onclick(View view, int pos) {
        InternalStorageFilesModel internalStorageFilesModel = mAdapter.getListData().get(pos);
        if (internalStorageFilesModel.isCheckboxVisible()) {

        } else {
            fileExtension = internalStorageFilesModel.getFileName().substring(internalStorageFilesModel.getFileName().lastIndexOf(".") + 1);//file extension (.mp3,.png,.pdf)
            File file = new File(internalStorageFilesModel.getFilePath());
            openFile (file, internalStorageFilesModel);
        }
    }

    @Override
    public void onLongClick(View view, int pos) {

    }


    private void openFile(File file, InternalStorageFilesModel internalStorageFilesModel) {
        String fileName = internalStorageFilesModel.getFileName();
        String filePath = internalStorageFilesModel.getFilePath();
        Context context = mContainer.getContext();
        if (file.isDirectory()) {//check if selected item is directory
            if (file.canRead()) {//if directory is readable
                mAdapter.setData(null);
                arrayListFilePaths.add(filePath);
                getFilesList(filePath);
                mHomeViewModel.setData(filePath);
            } else {//Toast to your not openable type
                Toast.makeText(MyFileApplication.getInstance(), "Folder can't be read!", Toast.LENGTH_SHORT).show();
            }
            //if file is not directory open a application for file type
        } else if (Constants.listImageType.contains(fileExtension)) {
            mOpenFileHelper.openPicture(context, filePath);
        } else if (Constants.listAudioType.contains(fileExtension)) {
            mOpenFileHelper.openAudio(context, filePath, fileName);
        } else if (Constants.listDocsType.contains(fileExtension)) {
            mOpenFileHelper.openDocument(context, filePath, fileName, fileExtension);
        } else if (Constants.listExtractType.contains(fileExtension)) {
            mOpenFileHelper.openExtractFile(context, filePath, fileName);
        } else if (Constants.listVideoType.contains(fileExtension)) {
            mOpenFileHelper.openVideo(context, filePath);
        } else if (fileExtension.equals("apk")) {
            mOpenFileHelper.openInstallFile(context, filePath);
        } else {
            Toast.makeText(MyFileApplication.getInstance()
                    , mContainer.getContext().getString(R.string.cant_open_this_file), Toast.LENGTH_SHORT).show();

        }
    }

    private void getFilesList(String filePath) {
        rootPath = filePath;
        tvFilePath.setText(filePath);
        File f = new File(filePath);
        File[] files = f.listFiles();
        if (files != null) {
            if (files.length == 0) {
                noMediaLayout.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            } else {
                listView.setVisibility(View.VISIBLE);
                noMediaLayout.setVisibility(View.GONE);
            }
        }
    }
}
