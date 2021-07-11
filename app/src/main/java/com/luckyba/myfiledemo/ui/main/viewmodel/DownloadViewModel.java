package com.luckyba.myfiledemo.ui.main.viewmodel;

import android.app.Application;
import android.os.Environment;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.luckyba.myfiledemo.app.MyFileApplication;
import com.luckyba.myfiledemo.data.model.InternalStorageFilesModel;
import com.luckyba.myfiledemo.data.repository.MyFileRepository;

import java.util.ArrayList;

public class DownloadViewModel extends ViewModel {


    private final MutableLiveData<ArrayList<InternalStorageFilesModel>> internal;
    private MyFileRepository myFileRepository;
    public DownloadViewModel() {
        super();
        internal = new MutableLiveData<>();
        myFileRepository = new MyFileRepository(MyFileApplication.getInstance());
        internal.setValue(myFileRepository.getAllInternalFile(Environment.getExternalStorageDirectory().getAbsolutePath()));
    }

    public LiveData<ArrayList<InternalStorageFilesModel>> getInternalData() {
        return internal;
    }

    public void setData(String path) {
        internal.postValue(myFileRepository.getAllInternalFile(path));
    }
}