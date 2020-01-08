package com.ljy.demo.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ljy.demo.R;


/**
 *    author : Android Liang_liang
 *    time   : 2018/12/02
 *    desc   : 对话框使用案例
 */

public class ShowToastUtil {

    private static TextView mTextView;
    private static ImageView mImageView;
    static Toast toastStart;

    public static void showToast(Context context, String message, int cnt) {

        //加载Toast布局
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.sucess_view_toast, null);
        //初始化布局控件
        mTextView = (TextView) toastRoot.findViewById(R.id.title_text);
        mImageView = (ImageView) toastRoot.findViewById(R.id.toast_img);
        //为控件设置属性
        mTextView.setText(message);
        mImageView.setImageResource(R.mipmap.sucess_layout);
        //Toast的初始化
        //防止多次点击
        if (toastStart !=null) {
            toastStart.setView(toastRoot);
        } else {
            toastStart = new Toast(context);
            toastStart.setGravity(Gravity.CENTER, 0, 0);
            toastStart.setDuration(Toast.LENGTH_LONG);
            toastStart.setView(toastRoot);
        }
        toastStart.show();
    }



}
