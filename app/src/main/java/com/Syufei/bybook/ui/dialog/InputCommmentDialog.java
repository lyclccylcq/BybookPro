package com.Syufei.bybook.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;

import com.Syufei.bybook.R;
import com.Syufei.bybook.commonui.PopupDialog;
import com.Syufei.bybook.databinding.InputCommentDialogBinding;

public class InputCommmentDialog extends PopupDialog {
    private InputCommentDialogBinding binding;
    public InputCommmentDialog(Context context){
        this(context, R.layout.input_comment_dialog,1);
    }
    public InputCommmentDialog(Context context, int resId, int heightType) {
        super(context, resId, heightType);
        binding=InputCommentDialogBinding.inflate(LayoutInflater.from(context));
        mDialog.setContentView(binding.getRoot());
        binding.inputCommentTop.titleBack.setOnClickListener(view -> {
            dismiss();
        });
        binding.inputCommentSend.setOnClickListener(view -> {
            String commentString=binding.inputCommentEdit.getText().toString().trim();
            if (!TextUtils.isEmpty(commentString)){
                Bundle bundle=new Bundle();
                bundle.putString("input_comment",commentString);
                setData(bundle);
                binding.inputCommentEdit.setText("");
                dismiss();
            }
        });
    }
}
