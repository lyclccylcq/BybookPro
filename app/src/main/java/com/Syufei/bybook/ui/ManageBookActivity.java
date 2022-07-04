package com.Syufei.bybook.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.Syufei.bybook.R;
import com.Syufei.bybook.bean.BookBean;
import com.Syufei.bybook.common.BaseActivity;
import com.Syufei.bybook.databinding.ActivityManageCarBinding;
import com.Syufei.bybook.network.DataSuccessListenter;
import com.Syufei.bybook.repo.ManagerRepo;
import com.Syufei.bybook.repo.TryBookRepo;
import com.Syufei.bybook.ui.adapter.ManagerCarAdapter;
import com.Syufei.bybook.ui.presenter.UIShowListener;
import com.Syufei.bybook.util.UITools;

public class ManageBookActivity extends BaseActivity implements UIShowListener , SwipeRefreshLayout.OnRefreshListener {
    private ManagerCarAdapter managerCarAdapter;
    private ActivityManageCarBinding manageCarBinding;
    private int selectIndex = 0;//0:车辆列表  1:添加车辆
    private static final int IMAGE_REQUEST_CODE = 1;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTransparentStatusBar();
        manageCarBinding = ActivityManageCarBinding.inflate(getLayoutInflater());
        setContentView(manageCarBinding.getRoot());
        managerCarAdapter = new ManagerCarAdapter(this);
        managerCarAdapter.setUiShowListener(this);
        manageCarBinding.manageCarRecycler.setAdapter(managerCarAdapter);
        manageCarBinding.manageCarRecycler.setLayoutManager(new LinearLayoutManager(this));
        manageCarBinding.manageCarSelectAdd.setOnClickListener(view -> {
            if (selectIndex == 0) {
                selectIndex = 1;
                updateSelect();
            }
        });
        manageCarBinding.manageCarSelectList.setOnClickListener(view -> {
            if (selectIndex == 1) {
                selectIndex = 0;
                updateSelect();
            }
        });
        manageCarBinding.manageCarTopBar.titleBack.setOnClickListener(view -> {
            finish();
        });
        manageCarBinding.manageCarAddcar.addCarDialogCarImg.setOnClickListener(view -> {
            if (manageCarBinding.manageCarAddcar.addCarDialogCarDel.getVisibility()==View.VISIBLE){
                manageCarBinding.manageCarAddcar.addCarDialogCarDel.setVisibility(View.GONE);
            }else if (manageCarBinding.manageCarAddcar.addCarDialogCarDel.getVisibility()==View.GONE){
                openPhoto();
            }
        });
        manageCarBinding.manageCarAddcar.addCarDialogCarDel.setOnClickListener(view -> {
            Drawable drawable=getResources().getDrawable(R.drawable.img_ic);
            manageCarBinding.manageCarAddcar.addCarDialogCarImg.setImageDrawable(drawable);
            manageCarBinding.manageCarAddcar.addCarDialogCarDel.setVisibility(View.GONE);
        });
        manageCarBinding.manageCarAddcar.getRoot().setOnClickListener(view -> {
            if (manageCarBinding.manageCarAddcar.addCarDialogCarDel.getVisibility()==View.VISIBLE){
                manageCarBinding.manageCarAddcar.addCarDialogCarDel.setVisibility(View.GONE);
            }
        });
        manageCarBinding.manageCarAddcar.addCarDialogCarImg.setOnLongClickListener(view -> {
            manageCarBinding.manageCarAddcar.addCarDialogCarDel.setVisibility(View.VISIBLE);
            return true;
        });
        manageCarBinding.manageCarAddcar.addCarDialogCarSubmit.setOnClickListener(view -> {
            String name=manageCarBinding.manageCarAddcar.addCarDialogCarName.getText().toString().trim();
            String version=manageCarBinding.manageCarAddcar.addCarDialogCarVersion.getText().toString().trim();
            String description=manageCarBinding.manageCarAddcar.addCarDialogCarDescription.getText().toString().trim();
            String price=manageCarBinding.manageCarAddcar.addCarDialogCarPrice.getText().toString().trim();
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(version)
                    && !TextUtils.isEmpty(description) &&!TextUtils.isEmpty(price)){
                ManagerRepo.addCar(name, version, price, description, new DataSuccessListenter() {
                    @Override
                    public void onDataSuccess(Object obj) {
                        BookBean.CommonResponse commonResponse=(BookBean.CommonResponse) obj;
                        if (commonResponse!=null && commonResponse.getStatus().equals("success")){
                            UITools.showToast(ManageBookActivity.this,"创建成功");
                            requestData();
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }else{
                UITools.showToast(ManageBookActivity.this,"信息不完整");
            }
        });
        manageCarBinding.manageCarRefresh.setOnRefreshListener(this);
        manageCarBinding.manageCarRefresh.setProgressBackgroundColorSchemeColor(Color.parseColor( "#ffffff"));
        manageCarBinding.manageCarRefresh.setColorSchemeColors(Color.parseColor("#00bcbc"));
        updateSelect();
        requestData();
    }

    private void updateSelect() {
        String mainColor = "#383a51";
        String tipColor = "#00bcbc";
        manageCarBinding.manageCarSelectAdd.setTextColor(Color.parseColor(selectIndex == 0 ? mainColor : tipColor));
        manageCarBinding.manageCarAddcar.getRoot().setVisibility(selectIndex == 1 ? View.VISIBLE : View.GONE);
        manageCarBinding.manageCarSelectList.setTextColor(Color.parseColor(selectIndex == 1 ? mainColor : tipColor));
        manageCarBinding.manageCarRefresh.setVisibility(selectIndex == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showProgress() {
        super.showProgress();
    }

    @Override
    public void dismissProgress() {
        super.dismissProgress();
    }

    @Override
    public void requestData() {
        showProgress();
        TryBookRepo.getBooklist(new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                dismissProgress();
                manageCarBinding.manageCarRefresh.setRefreshing(false);
                BookBean.BookList carList =( BookBean.BookList) obj;
                managerCarAdapter.setCarList(carList.getData().getList());
            }

            @Override
            public void onError(String error) {
                dismissProgress();
            }
        });
    }

    @Override
    public void onRefresh() {
        requestData();
    }

    public void openPhoto(){
        if(ContextCompat.checkSelfPermission(ManageBookActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ManageBookActivity.this,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,IMAGE_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case IMAGE_REQUEST_CODE:
                if (resultCode==RESULT_OK){
                    try {
                        Uri selectedImage=data.getData();
                        String[] filePathColumn={MediaStore.Images.Media.DATA};
                        Cursor cursor=getContentResolver().query(selectedImage,filePathColumn
                                ,null,null,null);
                        cursor.moveToFirst();
                        int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
                        path=cursor.getString(columnIndex);
                        Bitmap bitmap= BitmapFactory.decodeFile(path);
                        manageCarBinding.manageCarAddcar.addCarDialogCarImg.setImageBitmap(bitmap);
                        cursor.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}