package com.ljy.demo.ui.dialog;


import android.view.View;
import android.widget.TextView;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentActivity;
import com.ljy.base.BaseDialog;
import com.ljy.demo.R;
import com.ljy.demo.common.MyDialogFragment;


public class NonetDialog  {
    public static final class Builder
            extends MyDialogFragment.Builder<Builder>
            implements View.OnClickListener {

        private final TextView mTitleView;
        private final TextView mCancelView;
        private final View mLineView;
        private final TextView mConfirmView;


        private OnListener mListener;
        private boolean mAutoDismiss = true;

        @SuppressWarnings("all")
        public Builder(FragmentActivity activity) {
            super(activity);
            setContentView(R.layout.dialog_nonet);
            setAnimStyle(BaseDialog.AnimStyle.IOS);

            mTitleView = findViewById(R.id.tv_time_title);
            mCancelView = findViewById(R.id.tv_time_cancel);
            mLineView = findViewById(R.id.v_time_line);
            mConfirmView = findViewById(R.id.tv_time_confirm);

            mCancelView.setOnClickListener(this);
            mConfirmView.setOnClickListener(this);

        }

        public Builder setTitle(@StringRes int id) {
            return setTitle(getString(id));
        }
        public Builder setTitle(CharSequence text) {
            mTitleView.setText(text);
            return this;
        }

        public Builder setConfirm(@StringRes int id) {
            return setConfirm(getString(id));
        }
        public Builder setConfirm(CharSequence text) {
            mConfirmView.setText(text);

            mLineView.setVisibility((text == null || "".equals(text.toString())) ? View.GONE : View.VISIBLE);
            return this;
        }

        public Builder setCancel(@StringRes int id) {
            return setCancel(getString(id));
        }
        public Builder setCancel(CharSequence text) {
            mCancelView.setText(text);
            return this;
        }
        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }
        public Builder setAutoDismiss(boolean dismiss) {
            mAutoDismiss = dismiss;
            return this;
        }

        /**
         * {@link View.OnClickListener}
         */

        @Override
        public void onClick(View v) {
            if (mAutoDismiss) {
                dismiss();
            }

            if (mListener != null) {
                if (v == mConfirmView) {
                    mListener.onSelected(getDialog());
                } else if (v == mCancelView) {
                    mListener.onCancel(getDialog());
                }
            }
        }
    }

    public interface OnListener {


        void onSelected(BaseDialog dialog);

        /**
         * 点击取消时回调
         */
        void onCancel(BaseDialog dialog);
    }
}
