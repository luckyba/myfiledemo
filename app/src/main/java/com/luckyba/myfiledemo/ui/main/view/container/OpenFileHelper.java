package com.luckyba.myfiledemo.ui.main.view.container;

import android.content.Context;

public interface OpenFileHelper {
    void openPicture (Context context, String filePath);
    void openVideo (Context context, String filePath);
    void openAudio (Context context, String filePath, String fileName);
    void openDocument(Context context, String filePath, String fileName, String type);

    void openInstallFile (Context context, String filePath);
    void openExtractFile (Context context, String filePath, String fileName);
}
