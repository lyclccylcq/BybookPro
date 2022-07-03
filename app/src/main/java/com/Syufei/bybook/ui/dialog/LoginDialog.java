package com.Syufei.bybook.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lzhihua.bycar.R;
import com.lzhihua.bycar.bean.CarBean;
import com.lzhihua.bycar.bean.LoginBean;
import com.lzhihua.bycar.common.BaseActivity;
import com.lzhihua.bycar.commonui.PopupDialog;
import com.lzhihua.bycar.network.DataSuccessListenter;
import com.lzhihua.bycar.repo.LoginRepo;
import com.lzhihua.bycar.ui.MainActivity;
import com.lzhihua.bycar.ui.ManagerActivity;
import com.lzhihua.bycar.util.SharedPrefTools;

public class LoginDialog extends PopupDialog {
    private int TYPE = 0;//0:注册 1:登录 2:忘记密码
    //登陆
    private ViewStub loginStub;
    private View loginView;
    //注册
    private ViewStub registerStub;
    private View registerView;
    //    忘记密码
    private ViewStub forgetStub;
    private View forgetView;

    private int type=-1;

    private DialogListener listener;

    public void setListener(DialogListener listener) {
        this.listener = listener;
    }

    public LoginDialog(Context context, int type) {

        super(context, R.layout.activity_login, 0);
        mDialog.setCancelable(false);
        registerStub = (ViewStub) mView.findViewById(R.id.login_create_user);
        loginStub = (ViewStub) mView.findViewById(R.id.login_login_user);
        forgetStub = (ViewStub) mView.findViewById(R.id.forget_psw);
        //可能存在token过期的情况需要重新登陆，首次进入默认未注册
        switch (type) {
            case 0:
                registerView = registerStub.inflate();
                initRegister();
                break;
            case 1:
                loginView = loginStub.inflate();
                initLogin();
                break;
        }
    }

    private void initRegister() {
//        createUserBinding=CreateUserBinding.inflate(LayoutInflater.from(context));
        Button changeToLogin = (Button) registerView.findViewById(R.id.create_user_change_login);
        Button registerBtn = (Button) registerView.findViewById(R.id.create_user_register);
        EditText createUserName = registerView.findViewById(R.id.create_user_name);
        EditText createUserPsw = registerView.findViewById(R.id.create_user_psw);
        EditText createUserPswConfirm = registerView.findViewById(R.id.create_user_psw_confirm);
        EditText createUserEmail = registerView.findViewById(R.id.create_user_email);
        changeToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerStub.setVisibility(View.GONE);
                loginView = loginStub.inflate();
                initLogin();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = createUserName.getText().toString().trim();
                String password = createUserPsw.getText().toString().trim();
                String passConf = createUserPswConfirm.getText().toString().trim();
                String mail = createUserEmail.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(passConf) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(mail)) {
                    if (!passConf.equals(password)) {
                        showToast("密码前后不一致，请重新确认");
                    } else {
                        registe(mail, password, name);
                    }
                } else {
                    showToast("信息不完整，请输入完整的账户名、邮箱、密码");
                }
            }
        });
    }

    private void initLogin() {
        String userId = (String) SharedPrefTools.get(context, "user_id", "");
        String userPass = (String) SharedPrefTools.get(context, "user_pass", "");
        Boolean isRemember = (Boolean) SharedPrefTools.get(context, "is_remember", false);
        TextView loginBtn = (TextView) loginView.findViewById(R.id.login_login_btn);
        TextView forgetBtn = (TextView) loginView.findViewById(R.id.login_forget_psw);
        EditText loginUserName = loginView.findViewById(R.id.login_user_name);
        EditText loginPsw = loginView.findViewById(R.id.login_psw);
        loginUserName.setText(userId);
        loginPsw.setText(userPass);
        CheckBox showPass = loginView.findViewById(R.id.login_show_pass);
        CheckBox rememberPass = loginView.findViewById(R.id.login_remember_pass);
        rememberPass.setChecked(isRemember);
        TextView backRegister=(TextView) loginView.findViewById(R.id.back_register);

        showPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    loginPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    loginPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = loginUserName.getText().toString().trim();
                String password = loginPsw.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    login(name, password, rememberPass.isChecked());
                } else {
                    showToast("账号或者密码不能为空");
                }
            }
        });

        backRegister.setOnClickListener(view -> {
            registerStub.setVisibility(View.GONE);
            registerView=registerStub.inflate();
            initRegister();
        });
    }

    private void login(String name, String password, final boolean isRemember) {
        LoginRepo.requestLogin(name, password, new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                LoginBean.LoginResponse loginResponse = (LoginBean.LoginResponse) obj;
                if (loginResponse != null && loginResponse.getStatus().equals("success") && loginResponse.getData().getType()==1) {
                    SharedPrefTools.put(mDialog.getContext(), "bycar_token", loginResponse.getData().getToken());
                    if(isRemember){
                        SharedPrefTools.put(context,"user_id",name);
                        SharedPrefTools.put(context,"user_pass",password);
                    }
                    SharedPrefTools.put(context,"is_remember",isRemember);
                    SharedPrefTools.put(context,"is_login",true);

                    showToast("登录成功");
                    mDialog.dismiss();
                    BaseActivity.login(context);
                    if (listener!=null){
                        listener.onDismiss(true,loginResponse.getData().getType());
                    }
                } else if(loginResponse != null && loginResponse.getStatus().equals("success") && loginResponse.getData().getType()==0){
                    if(isRemember){
                        SharedPrefTools.put(context,"user_id",name);
                        SharedPrefTools.put(context,"user_pass",password);
                    }
                    Intent intent=new Intent(context, ManagerActivity.class);
                    intent.putExtra("is_login_manager",true);
                    context.startActivity(intent);
                    ((MainActivity) context).finish();
                }else {
                    showToast("登录失败，稍后重试");
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void registe(String mail, String password, String name) {
        LoginRepo.requestRegister(mail, password, name, new DataSuccessListenter() {
            @Override
            public void onDataSuccess(Object obj) {
                CarBean.CommonResponse commonResponse = (CarBean.CommonResponse) obj;
                if (commonResponse.getStatus().equals("success")) {
                    showToast("注册成功");
                    SharedPrefTools.put(context,"user_id",mail);
                    SharedPrefTools.put(context,"user_pass",password);
                    registerStub.setVisibility(View.GONE);
                    loginView = loginStub.inflate();
                    initLogin();
                } else {
                    showToast("注册失败，请稍后重试");
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    public void dismiss() {
        mDialog.dismiss();
        listener.onDismiss(true,type);
    }

    private void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public interface DialogListener {
        void onDismiss(boolean isSuccess,int type);
    }
}