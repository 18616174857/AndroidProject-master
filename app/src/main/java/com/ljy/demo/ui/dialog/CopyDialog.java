package com.ljy.demo.ui.dialog;

import android.view.Gravity;

import androidx.fragment.app.FragmentActivity;

import com.ljy.base.BaseDialog;
import com.ljy.demo.R;
import com.ljy.demo.common.MyDialogFragment;

/**
 *    author : Android Liang_liang
 *    time   : 2018/10/18
 *    desc   : 可进行拷贝的副本
 */
public final class CopyDialog {

    public static final class Builder
            extends MyDialogFragment.Builder<Builder> {

        public Builder(FragmentActivity activity) {
            super(activity);

            setContentView(R.layout.dialog_copy);
            setAnimStyle(BaseDialog.AnimStyle.BOTTOM);
            setGravity(Gravity.BOTTOM);
        }
    }
}