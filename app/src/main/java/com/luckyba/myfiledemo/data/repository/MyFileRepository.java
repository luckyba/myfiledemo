package com.luckyba.myfiledemo.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.luckyba.myfiledemo.data.model.DictionaryProvider;
import com.luckyba.myfiledemo.data.model.ExternalStorageFilesModel;
import com.luckyba.myfiledemo.data.model.ImageFolder;
import com.luckyba.myfiledemo.data.model.InternalStorageFilesModel;
import com.luckyba.myfiledemo.data.model.MediaFileListModel;
import com.luckyba.myfiledemo.ui.main.view.viewholder.PictureHolder;

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


    @Override
    public ArrayList<ImageFolder> getAllFolderPicture(DictionaryProvider dir) {
        ArrayList<ImageFolder> picFolders = new ArrayList<>();
        ArrayList<String> picPaths = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(dir.getUri(), dir.getProjection()
                , dir.getSelectionClause(), dir.getSelectionArgs(), dir.getSortOrder());
        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            do{
                ImageFolder folds = new ImageFolder();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                //String folderpaths =  datapath.replace(name,"");
                String folderpaths = datapath.substring(0, datapath.lastIndexOf(folder+"/"));
                folderpaths = folderpaths+folder+"/";
                if (!picPaths.contains(folderpaths)) {
                    picPaths.add(folderpaths);

                    folds.setPath(folderpaths);
                    folds.setFolderName(folder);
                    folds.setFirstPic(datapath);//if the folder has only one picture this line helps to set it as first so as to avoid blank image in itemview
                    folds.addpics();
                    picFolders.add(folds);
                }else{
                    for(int i = 0;i<picFolders.size();i++){
                        if(picFolders.get(i).getPath().equals(folderpaths)){
                            picFolders.get(i).setFirstPic(datapath);
                            picFolders.get(i).addpics();
                        }
                    }
                }
            }while(cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 0;i < picFolders.size();i++){
            Log.d("picture folders",picFolders.get(i).getFolderName()+" and path = "+picFolders.get(i).getPath()+" "+picFolders.get(i).getNumberOfPics());
        }

        //reverse order ArrayList
       /* ArrayList<imageFolder> reverseFolders = new ArrayList<>();

        for(int i = picFolders.size()-1;i > reverseFolders.size()-1;i--){
            reverseFolders.add(picFolders.get(i));
        }*/

        return picFolders;
    }
}
