package com.ljy.demo.ui.adapter;



import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ljy.demo.R;
import com.ljy.demo.common.MyRecyclerViewAdapter;
import com.ljy.image.ImageLoader;

import java.util.List;

import butterknife.BindView;

public class UpLoadAdapter extends MyRecyclerViewAdapter<String> {
    private final List<String> mSelectPhoto;

    public UpLoadAdapter(Context context, List<String> data) {
        super(context);
        mSelectPhoto = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    final class ViewHolder extends MyRecyclerViewAdapter.ViewHolder {

        @BindView(R.id.iv_photo_image)
        ImageView mImageView;

        ViewHolder() {
            super(R.layout.item_test_image);
        }

        @Override
        public void onBindView(int position) {
            ImageLoader.with(getContext())
                    .load(getItem(position))
                    .into(mImageView);
        }
    }

    @Override
    protected RecyclerView.LayoutManager getDefaultLayoutManager(Context context) {
        return new GridLayoutManager(context, 3);
    }
}
