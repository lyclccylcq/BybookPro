package com.Syufei.bybook.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;

import com.lzhihua.bycar.R;
import com.lzhihua.bycar.bean.LoginBean;
import com.lzhihua.bycar.commonui.PopupDialog;
import com.lzhihua.bycar.databinding.LoginBinding;
import com.lzhihua.bycar.network.DataSuccessListenter;
import com.lzhihua.bycar.repo.LoginRepo;
import com.lzhihua.bycar.util.SharedPrefTools;
import com.lzhihua.bycar.util.UITools;

public class ManagerLoginDialog extends PopupDialog {
    private LoginBinding loginBinding;
    private LoginDialog.DialogListener listener;
    private boolean isSuccess=false;
    private int type=-1;

    public void setListener(LoginDialog.DialogListener listener) {
        this.listener = listener;
    }

    public ManagerLoginDialog(Context context){
        this(context, R.layout.login,0);
    }
    public ManagerLoginDialog(Context context, int resId, int heightType) {
        super(context,resId, heightType);
        loginBinding=LoginBinding.inflate(LayoutInflater.from(context));
        loginBinding.getRoot().setBackground(mDialog.getContext().getDrawable(R.drawable.round_dialog));
        mDialog.setContentView(loginBinding.getRoot());
        mDialog.setCancelable(false);
        loginBinding.loginShowPass.setVisibility(View.GONE);
        loginBinding.loginRememberPass.setChecked((Boolean) SharedPrefTools.get(context,"manager_login_remember",false));
        loginBinding.loginRememberPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPrefTools.put(context,"manager_login_remember",b);
            }
        });
        loginBinding.loginUserName.setText((String) SharedPrefTools.get(context,"manager_id",""));
        loginBinding.loginPsw.setText((String) SharedPrefTools.get(context,"manager_psw",""));
        loginBinding.loginLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = loginBinding.loginUserName.getText().toString().trim();
                String password = loginBinding.loginPsw.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    login(name, password);
                } else {
                    UITools.showToast(mDialog.getContext(), "账号或者密码不能为空");
                }
            }
        });
    }
    private void login(String name, String password) {
        LoginRepo.requestLogin(name, password, new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                LoginBean.LoginResponse loginResponse = (LoginBean.LoginResponse) obj;
                if (loginResponse != null && loginResponse.getStatus().equals("success") && loginResponse.getData().getType()==0) {
                    SharedPrefTools.put(mDialog.getContext(), "bycar_token", loginResponse.getData().getToken());
                    boolean isRemember= (boolean) SharedPrefTools.get(context,"manager_login_remember",false);
                    if (isRemember){
                        SharedPrefTools.put(context,"manager_id",loginBinding.loginUserName.getText().toString());
                        SharedPrefTools.put(context,"manager_psw",loginBinding.loginPsw.getText().toString());
                    }else{
                        SharedPrefTools.put(context,"manager_id","");
                        SharedPrefTools.put(context,"manager_psw","");
                    }
                    UITools.showToast(mDialog.getContext(), "登录成功");
                    type=loginResponse.getData().getType();
                    isSuccess=true;
                    dismiss();
                } else {
                    UITools.showToast(mDialog.getContext(), "登录失败，稍后重试");
                }
            }

            @Override
            public void onError(String error) {
                UITools.showToast(mDialog.getContext(), "登录失败，稍后重试");
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        listener.onDismiss(isSuccess,type);
    }
}
