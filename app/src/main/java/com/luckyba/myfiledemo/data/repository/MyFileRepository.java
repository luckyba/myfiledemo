package com.luckyba.myfiledemo.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.luckyba.myfiledemo.data.model.DictionaryProvider;
import com.luckyba.myfiledemo.data.model.ExternalStorageFilesModel;
import com.luckyba.myfiledemo.data.model.InternalStorageFilesModel;
import com.luckyba.myfiledemo.data.model.MediaFileListModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyFileRepository implements MyFileRes {

    Context mContext;
    public MyFileRepository(Context context) {
        mContext = context;
    }

    @Override
    public ArrayList<MediaFileListModel> getAllMedia(DictionaryProvider dir) {
        List<MediaFileListModel> mediaFileListModels = new ArrayList<>();
        final Cursor mCursor = mContext.getContentResolver().query(
                dir.getUri(),
                dir.getProjection(), dir.getSelectionClause(), dir.getSelectionArgs(),
                dir.getSortOrder());
        if (mCursor != null) {
            if (mCursor.getCount() != 0) {
                if (mCursor.moveToFirst()) {
                    do {
                        MediaFileListModel mediaFileListModel = new MediaFileListModel();
                        mediaFileListModel.setFileName(mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)));
                        mediaFileListModel.setFilePath(mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));

                        try {
                            File file = new File(mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
                            long length = file.length();
                            length = length / 1024;
                            if (length >= 1024) {
                                length = length / 1024;
                                mediaFileListModel.setFileSize(length + " MB");
                            } else {
                                mediaFileListModel.setFileSize(length + " KB");
                            }
                            Date lastModDate = new Date(file.lastModified());
                            mediaFileListModel.setFileCreatedTime(lastModDate.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            mediaFileListModel.setFileSize("unknown");
                        }
                        mediaFileListModels.add(mediaFileListModel);
                    } while (mCursor.moveToNext());
                }
                mCursor.close();
            }

        }
        return null;
    }

    @Override
    public ArrayList<ExternalStorageFilesModel> getAllExternalFile(String filePath) {
        ArrayList<ExternalStorageFilesModel> externalStorageFilesModelArrayList = new ArrayList<>();

        try {
            File f = new File(filePath);
            File[] files = f.listFiles();

            if (files.length != 0) {
                for (File file : files) {
                    ExternalStorageFilesModel model = new ExternalStorageFilesModel();
                    model.setFileName(file.getName());
                    model.setFilePath(file.getPath());
                    model.setCheckboxVisible(false);
                    model.setSelected(false);
                    if (file.isDirectory()) {
                        model.setDir(true);
                    } else {
                        model.setDir(false);
                    }

                    externalStorageFilesModelArrayList.add(model);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return externalStorageFilesModelArrayList;
    }

    @Override
    public ArrayList<InternalStorageFilesModel> getAllInternalFile(String filePath) {
        ArrayList<InternalStorageFilesModel> internalStorageFilesModelList = new ArrayList<>();

        try {
            File f = new File(filePath);
            File[] files = f.listFiles();

            if (files != null && files.length != 0) {
                for (File file : files) {
                    InternalStorageFilesModel model = new InternalStorageFilesModel();
                    model.setFileName(file.getName());
                    model.setFilePath(file.getPath());
                    model.setCheckboxVisible(false);
                    model.setSelected(false);
                    if (file.isDirectory()) {
                        model.setDir(true);
                    } else {
                        model.setDir(false);
                    }

                    internalStorageFilesModelList.add(model);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return internalStorageFilesModelList;
    }
}
