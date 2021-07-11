package com.luckyba.myfiledemo.ui.main.viewmodel;

import android.app.Application;
import android.os.Environment;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.luckyba.myfiledemo.app.MyFileApplication;
import com.luckyba.myfiledemo.data.model.DictionaryProvider;
import com.luckyba.myfiledemo.data.model.ImageFolder;
import com.luckyba.myfiledemo.data.repository.MyFileRepository;
import com.luckyba.myfiledemo.util.Constants;

import java.util.ArrayList;

public class ImagesViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<ImageFolder>> internal;
    private MyFileRepository myFileRepository;
    public ImagesViewModel() {
        super();
        internal = new MutableLiveData<>();
        myFileRepository = new MyFileRepository(MyFileApplication.getInstance());
        internal.setValue(myFileRepository.getAllFolderPicture(Constants.ImageDir));
    }

    public LiveData<ArrayList<ImageFolder>> getFolderPictureData() {
        return internal;
    }

    public void setData(DictionaryProvider imageDir) {
        internal.postValue(myFileRepository.getAllFolderPicture(imageDir));
    }
}