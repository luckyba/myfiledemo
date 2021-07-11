package com.luckyba.myfiledemo.ui.main.viewmodel;

import android.app.Application;
import android.os.Environment;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.luckyba.myfiledemo.app.MyFileApplication;
import com.luckyba.myfiledemo.data.model.DictionaryProvider;
import com.luckyba.myfiledemo.data.model.InternalStorageFilesModel;
import com.luckyba.myfiledemo.data.model.MediaFileListModel;
import com.luckyba.myfiledemo.data.repository.MyFileRepository;
import com.luckyba.myfiledemo.util.Constants;

import java.util.ArrayList;

public class AudioViewModel extends ViewModel {


    private final MutableLiveData<ArrayList<MediaFileListModel>> internal;
    private MyFileRepository myFileRepository;
    public AudioViewModel() {
        super();
        internal = new MutableLiveData<>();
        myFileRepository = new MyFileRepository(MyFileApplication.getInstance());
        internal.setValue(myFileRepository.getAllMedia(Constants.AudioDir));
    }

    public LiveData<ArrayList<MediaFileListModel>> getInternalData() {
        return internal;
    }

    public void setData(DictionaryProvider audioDir) {
        internal.postValue(myFileRepository.getAllMedia(audioDir));
    }
}