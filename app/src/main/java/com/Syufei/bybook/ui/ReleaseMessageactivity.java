package com.Syufei.bybook.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lzhihua.bycar.R;
import com.lzhihua.bycar.bean.CommunityBean;
import com.lzhihua.bycar.common.BaseActivity;
import com.lzhihua.bycar.network.DataSuccessListenter;
import com.lzhihua.bycar.repo.CommunityRepo;
import com.lzhihua.bycar.ui.adapter.ReleaseAdapter;
import com.lzhihua.bycar.util.UITools;

import java.util.ArrayList;
import java.util.List;

public class ReleaseMessageactivity extends BaseActivity implements ReleaseAdapter.ReleaseListener {
    private ReleaseAdapter adapter;
    private RecyclerView recyclerView;
    private EditText editText;
    private TextView submit;
    private ImageView addImage;
    private ImageView back;

    //从相册获得图片
//    Bitmap bitmap;
    //图片路径
//    private String path ;

    //判断返回到的Activity
    private static final int IMAGE_REQUEST_CODE = 0;
    private List<String> imagePaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_message);
        setWhiteStatusBar();
        adapter=new ReleaseAdapter(this);
        imagePaths=new ArrayList<>();
        adapter.setmList(imagePaths);
        recyclerView=findViewById(R.id.release_recycler);
        editText=findViewById(R.id.release_edit);
        back=findViewById(R.id.release_back);
        submit=findViewById(R.id.release_submit);
        adapter.setListener(this);
        addImage=findViewById(R.id.release_add_photo);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(adapter);
        submit.setOnClickListener(view -> {
            String text=editText.getText().toString().trim();
            if (imagePaths.size()==0){
                UITools.showToast(ReleaseMessageactivity.this,"选择一张图片吧.");
            }
            if (!TextUtils.isEmpty(text) && imagePaths.size()>0){
                showProgress();
                CommunityRepo.createMoment(text, adapter.getmList(), new DataSuccessListenter() {
                    @Override
                    public void onDataSuccess(Object obj) {
                        dismissProgress();
                        CommunityBean.CreateCommentResp createCommentRespc=(CommunityBean.CreateCommentResp) obj;
                        Intent intent=new Intent();
                        intent.putExtra("result",createCommentRespc);
                        setResult(RESULT_OK,intent);
                        finish();
                    }

                    @Override
                    public void onError(String error) {
                        dismissProgress();
                        UITools.showToast(ReleaseMessageactivity.this,"发送失败，稍后重试");
                    }
                });
            }
        });
        addImage.setOnClickListener(view -> {
            openPhoto();
        });
        back.setOnClickListener(view -> {
            finish();
        });
    }

    public void openPhoto(){
        if(ContextCompat.checkSelfPermission(ReleaseMessageactivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ReleaseMessageactivity.this,new String[]{
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
                        String path=cursor.getString(columnIndex);
                        adapter.addItem(path);
                        if (adapter.getSize()>=9){
                            addImage.setVisibility(View.GONE);
                        }
                        cursor.close();
//                        Bitmap bitmap= BitmapFactory.decodeFile(path);
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
    @Override
    public void updateAdd(int size) {
        if (size>=9){
            addImage.setVisibility(View.GONE);
        }else{
            addImage.setVisibility(View.VISIBLE);
        }
    }
}