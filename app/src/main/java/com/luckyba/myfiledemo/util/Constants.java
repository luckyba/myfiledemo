package com.luckyba.myfiledemo.util;

import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

import com.luckyba.myfiledemo.data.model.DictionaryProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.Q)
public class Constants {
    public static final List listAudioType = Collections.unmodifiableList(
            Arrays.asList("mp3", "wav", "ogg"));

    public static final List listVideoType = Collections.unmodifiableList(
            Arrays.asList("mp4", "3gp", "wmv", "avi", "mov", "mp4", "wmv"));

    public static final List listDocsType = Collections.unmodifiableList(
            Arrays.asList("txt", "html", "xml", "pdf", "xlsx", "ppt", "pptx", "odt", "ods", "odp", "rtf", "doc"));

    public static final List listImageType = Collections.unmodifiableList(
            Arrays.asList("png", "jpeg", "jpg"));

    public static final List listExtractType = Collections.unmodifiableList(
            Arrays.asList("zip", "rar"));

    public static final DictionaryProvider AudioDir = new DictionaryProvider(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            , new String[]{MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATA}, null, null,
            "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");

    public static final DictionaryProvider ImageDir = new DictionaryProvider(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            new String[]{MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME
                    ,MediaStore.Images.Media.BUCKET_ID}, null, null,
            "LOWER(" + MediaStore.Images.Media.TITLE + ") ASC");

    public static final DictionaryProvider VideoDir = new DictionaryProvider(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            new String[]{MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DATA}, null, null,
            "LOWER(" + MediaStore.Video.Media.TITLE + ") ASC");

}