package com.luckyba.myfiledemo.ui.main.view;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luckyba.myfiledemo.R;
import com.luckyba.myfiledemo.data.model.InternalStorageFilesModel;
import com.luckyba.myfiledemo.data.model.MediaFileListModel;
import com.luckyba.myfiledemo.ui.main.view.adapter.InternalStorageAdapter;
import com.luckyba.myfiledemo.ui.main.view.container.InternalStorageContainer;
import com.luckyba.myfiledemo.ui.main.viewmodel.AudioViewModel;
import com.luckyba.myfiledemo.ui.main.viewmodel.HomeViewModel;
import com.luckyba.myfiledemo.util.PermissionUtils;

import java.util.ArrayList;

public class AudioFragment extends Fragment {

    private View mRootView;
    private InternalStorageAdapter mAdapter;
    private InternalStorageContainer mContainer;
    private AudioViewModel audioViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);
        if (!PermissionUtils.shouldAskForPermission(getActivity(), PermissionUtils.List_permission)) {
            init();
        } else {
            PermissionUtils.requestPermissions(this, PermissionUtils.List_permission, PermissionUtils.MY_FILE_REQUEST_CODE);
        }
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!PermissionUtils.shouldAskForPermission(getActivity(), PermissionUtils.List_permission)) {
            init();
        }
    }

    void init() {
//        mAdapter = new InternalStorageAdapter();
//        audioViewModel = new ViewModelProvider(this).get(AudioViewModel.class);
//        mContainer = new InternalStorageContainer(mRootView, mAdapter, audioViewModel);
//
//        audioViewModel.getInternalData().observe(getViewLifecycleOwner(), new Observer<ArrayList<MediaFileListModel>>() {
//            @Override
//            public void onChanged(@Nullable ArrayList<MediaFileListModel> s) {
//                Log.d("lucky ", " size "+ s.size());
//                mAdapter.setData(s);
//                mAdapter.notifyDataSetChanged();
//            }
//        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionUtils.MY_FILE_REQUEST_CODE)
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted. Continue the action or workflow
                // in your app.
                init();

            } else {

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Permission necessary");
                alertBuilder.setMessage("External storage permission is necessary \n " +
                        "Go to Setting to Enable the permission");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {

                        PermissionUtils.goToAppSettings(getActivity());
                        // Explain to the user that the feature is unavailable because
                        // the features requires a permission that the user has denied.
                        // At the same time, respect the user's decision. Don't link to
                        // system settings in an effort to convince the user to change
                        // their decision.
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
            }

        // Other 'case' lines to check for other
        // permissions this app might request.
    }
}