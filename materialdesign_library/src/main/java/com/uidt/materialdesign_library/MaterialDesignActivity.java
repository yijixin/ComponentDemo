package com.uidt.materialdesign_library;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uidt.common_base.base.BaseActivity;
import com.uidt.common_base.utils.ToastUtils;
import com.uidt.materialdesign_library.adapter.FeedAdapter;

/**
 * CoordinatorLayout + RecyclerView + AppBarLayout + ToolBar
 */
public class MaterialDesignActivity extends BaseActivity {

    private Toolbar toolbar;
    private RelativeLayout mSuspensionBar;
    private TextView mSuspensionTv;
    private ImageView mSuspensionIv;
    private int mSuspensionBarHeight;
    private int mCurrentPosition;

    @Override
    protected int layoutRes() {
        return R.layout.activity_material_design;
    }

    @Override
    protected View getTopView() {
        return toolbar;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("MaterialDesign");
        setSupportActionBar(toolbar);

        RecyclerView recyclerDesign = findViewById(R.id.recycler_design);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerDesign.setLayoutManager(linearLayoutManager);
        FeedAdapter feedAdapter = new FeedAdapter();
        recyclerDesign.setAdapter(feedAdapter);
        //宽高确定
        recyclerDesign.setHasFixedSize(true);

        //悬浮控件初始化
        mSuspensionBar = findViewById(R.id.suspensionBar);
        mSuspensionIv = findViewById(R.id.iv_avatar);
        mSuspensionTv = findViewById(R.id.tv_nickname);

        recyclerDesign.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //获取悬浮条高度
                mSuspensionBarHeight = mSuspensionBar.getHeight();
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //调整悬浮条的位置
                //获取下一个显示的item的位置进行调整
                View nextView = linearLayoutManager.findViewByPosition(mCurrentPosition + 1);
                if (nextView != null) {
                    if (nextView.getTop() < mSuspensionBarHeight) {
                        //调整悬浮条位置
                        mSuspensionBar.setTranslationY(nextView.getTop() - mSuspensionBarHeight);
                    } else {
                        //保持初始位置
                        mSuspensionBar.setTranslationY(0);
                    }
                }
                //判断是否需要更新悬浮条数据
                if (mCurrentPosition != linearLayoutManager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    //更新悬浮条内容
                    updateSuspension();
                }
            }
        });
    }

    private void updateSuspension() {
        //头像
        mSuspensionIv.setImageResource(getAvatarResource(mCurrentPosition));
        //名字
        mSuspensionTv.setText("NetEase" + (mCurrentPosition + 1));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.material_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                ToastUtils.getInstance(this).toastShow("添加", Gravity.CENTER);
                break;
            case R.id.change:
                ToastUtils.getInstance(this).toastShow("修改", Gravity.CENTER);
                break;
            default:
        }
        return true;
    }
}
