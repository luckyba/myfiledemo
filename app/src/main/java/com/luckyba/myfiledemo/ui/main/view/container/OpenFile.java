package com.luckyba.myfiledemo.ui.main.view.container;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.core.content.FileProvider;

import com.luckyba.myfiledemo.R;
import com.luckyba.myfiledemo.app.MyFileApplication;
import com.luckyba.myfiledemo.ui.main.view.activities.FullImageViewActivity;
import com.luckyba.myfiledemo.ui.main.view.activities.TextFileViewActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class OpenFile implements OpenFileHelper {
    private RelativeLayout footerAudioPlayer;
    private MediaPlayer mediaPlayer;

    @Override
    public void openPicture(Context context, String filepath) {
        Intent imageIntent = new Intent(MyFileApplication.getInstance(), FullImageViewActivity.class);
        imageIntent.putExtra("imagePath", filepath);
        context.startActivity(imageIntent);
    }

    @Override
    public void openVideo(Context context, String filepath) {
        Uri videoUri = FileProvider.getUriForFile(context, MyFileApplication.getInstance()
                .getPackageName() + ".provider", new File(filepath));
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(videoUri, "video/*");
        context.startActivity(intent);
    }

    @Override
    public void openAudio(Context context, String filePath, String fileName) {
        final Dialog audioPlayerDialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        audioPlayerDialog.setContentView(R.layout.custom_audio_player_dialog);
        footerAudioPlayer = (RelativeLayout) audioPlayerDialog.findViewById(R.id.id_layout_audio_player);
        TextView lblAudioFileName = (TextView) audioPlayerDialog.findViewById(R.id.ic_audio_file_name);
        ToggleButton toggleBtnPlayPause = (ToggleButton) audioPlayerDialog.findViewById(R.id.id_play_pause);
        toggleBtnPlayPause.setChecked(true);
        lblAudioFileName.setText(fileName);
        audioPlayerDialog.show();
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        footerAudioPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                audioPlayerDialog.dismiss();
            }
        });
        toggleBtnPlayPause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (mediaPlayer != null) {
                        mediaPlayer.start();
                    }
                } else {
                    if (mediaPlayer != null) {
                        mediaPlayer.pause();
                    }
                }
            }
        });
        audioPlayerDialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    audioPlayerDialog.dismiss();
                }
                return true;
            }
        });
    }

    @Override
    public void openDocument(Context context, String filepath, String filename, String type) {
        if (type.equals("pdf")) {
            File pdfFile = new File(filepath);
            PackageManager packageManager = context.getPackageManager();
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setType("application/pdf");
            List list = packageManager.queryIntentActivities(pdfIntent, PackageManager.MATCH_DEFAULT_ONLY);
            if (list.size() > 0 && pdfFile.isFile()) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.fromFile(pdfFile);
                intent.setDataAndType(uri, "application/pdf");
                context.startActivity(intent);
            } else {
                Toast.makeText(MyFileApplication.getInstance()
                        , context.getString(R.string.there_are_no_app_to_open_it), Toast.LENGTH_SHORT).show();
            }
        } else {
            Intent txtIntent = new Intent(MyFileApplication.getInstance(), TextFileViewActivity.class);
            txtIntent.putExtra("filePath", filepath);
            txtIntent.putExtra("fileName", filename);
            context.startActivity(txtIntent);
        }
    }

    @Override
    public void openInstallFile(Context context, String filepath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(filepath)), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void openExtractFile(Context context, String filePath, String fileName) {
        final Dialog extractZipDialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        extractZipDialog.setContentView(R.layout.custom_extract_zip_dialog);
        Button btnOkay = (Button) extractZipDialog.findViewById(R.id.btn_okay);
        Button btnCancel = (Button) extractZipDialog.findViewById(R.id.btn_cancel);
        final TextView lblFileName = (TextView) extractZipDialog.findViewById(R.id.id_file_name);
        lblFileName.setText(context.getString(R.string.do_you_want_to_extract, fileName));
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extractZipDialog.dismiss();
                lblFileName.setText("");
            }
        });
        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extractZipDialog.dismiss();

                byte[] buffer = new byte[1024];
                try {
                    File folder = new File(filePath);//create output directory is not exists
                    if (!folder.exists()) {
                        folder.mkdir();
                    }
                    ZipInputStream zis =
                            new ZipInputStream(new FileInputStream(filePath));//get the zip file content
                    ZipEntry ze = zis.getNextEntry(); //get the zipped file list entry
                    while (ze != null) {
                        String unzipFileName = ze.getName();
                        File newFile = new File(filePath + File.separator + unzipFileName);
                        //create all non exists folders
                        //else you will hit FileNotFoundException for compressed folder
                        new File(newFile.getParent()).mkdirs();
                        FileOutputStream fos = new FileOutputStream(newFile);
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                        fos.close();
                        ze = zis.getNextEntry();
                    }
                    zis.closeEntry();
                    zis.close();
                } catch (IOException ex) {

                    ex.printStackTrace();
                    extractZipDialog.dismiss();
                }
            }
        });

        extractZipDialog.show();
    }
}
