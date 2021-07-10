package com.luckyba.myfiledemo.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

}
