package com.luckyba.myfiledemo.data.repository;

import androidx.lifecycle.LiveData;

import com.luckyba.myfiledemo.data.model.DictionaryProvider;
import com.luckyba.myfiledemo.data.model.ExternalStorageFilesModel;
import com.luckyba.myfiledemo.data.model.ImageFolder;
import com.luckyba.myfiledemo.data.model.InternalStorageFilesModel;
import com.luckyba.myfiledemo.data.model.MediaFileListModel;
import com.luckyba.myfiledemo.ui.main.view.viewholder.PictureHolder;

import java.util.ArrayList;
import java.util.List;

public interface MyFileRes {


    ArrayList<MediaFileListModel> getAllMedia (DictionaryProvider dir);

    ArrayList<ImageFolder> getAllFolderPicture (DictionaryProvider dir);

    ArrayList<ExternalStorageFilesModel> getAllExternalFile (String filepath);

    ArrayList<InternalStorageFilesModel> getAllInternalFile (String filepath);
}
