package com.ljy.demo.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ljy.demo.R;

public class LoadingDialog extends Dialog {
    private LoadingDialog dialog;
    Activity activity;
    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Activity ac,Context context, int theme) {
        super(context, theme);
        dialog = this;
        activity = ac;
    }
    public void initDialog() {
        dialog.setContentView(R.layout.loading_progress);
        dialog.findViewById(R.id.message).setVisibility(View.GONE);
        ImageView img =  dialog.findViewById(R.id.spinnerImageView);
        TextView tv_message = dialog.findViewById(R.id.message);
        ((AnimationDrawable) img.getBackground()).start();
        // 按返回键是否取消
//        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        // 监听返回键处理
        dialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                activity.finish();
            }
        });
        // 设置居中
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        // 设置背景层透明度
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
    }

    public boolean isShowing() {
        return dialog == null ? false : dialog.isShowing();
    }
}
