package com.uidt.materialdesign_library.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uidt.materialdesign_library.R;

/**
 * @author yijixin on 2019-12-02
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_material_design_item, parent, false);
        return new FeedViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder feedViewHolder, int position) {
        //会在绑定到ViewHolder时调用
        //头像
        feedViewHolder.mIvAvatar.setImageResource(getAvatarResource(position));
        //名字
        feedViewHolder.mTvNickname.setText("NetEase" + (position + 1));
        //内容
        feedViewHolder.mIvContent.setImageResource(getContentResource(position));
    }

    /**
     * 获取头像资源ID
     */
    private int getAvatarResource(int position) {
        switch (position % 4) {
            case 0:
                return R.mipmap.avatar1;
            case 1:
                return R.mipmap.avatar2;
            case 2:
                return R.mipmap.avatar3;
            case 3:
                return R.mipmap.avatar4;
            default:
        }
        return 0;
    }

    /**
     * 获取内容资源ID
     */
    private int getContentResource(int position) {
        switch (position % 4) {
            case 0:
                return R.mipmap.taeyeon_one;
            case 1:
                return R.mipmap.taeyeon_two;
            case 2:
                return R.mipmap.taeyeon_three;
            case 3:
                return R.mipmap.taeyeon_four;
            default:
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {

        ImageView mIvAvatar;
        ImageView mIvContent;
        TextView mTvNickname;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            mIvAvatar = itemView.findViewById(R.id.iv_avatar);
            mIvContent = itemView.findViewById(R.id.iv_content);
            mTvNickname = itemView.findViewById(R.id.tv_nickname);
        }
    }
}
