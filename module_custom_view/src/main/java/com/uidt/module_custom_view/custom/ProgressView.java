package com.uidt.module_custom_view.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.uidt.module_custom_view.R;

/**
 * @author yijixin on 2019-12-02
 * 小船进度条
 */
public class ProgressView extends View {

    /**
     * 1、用贝塞尔曲线绘制一个周期的水波
     * 2、水波动画
     */

    private float mMaxProgress;//最大进度值
    private float mCurrentProgress;//当前进度值
    private Paint mPaint;
    private Path mPath;
    private PathMeasure mPathMeasure;
    private int width;
    private int height;
    private float mPathOffset;//水波偏移

    //周期宽度
    private float perCycleWidth;
    //周期长度
    private float perCycleLength;
    //总共多少段
    private int perCount = 6;
    private float[] pos;
    private float[] tan;

    private Bitmap boatBitmap;

    public float getMaxProgress() {
        return mMaxProgress;
    }

    public void setMaxProgress(float mMaxProgress) {
        this.mMaxProgress = mMaxProgress;
    }

    public float getCurrentProgress() {
        return mCurrentProgress;
    }

    public void setCurrentProgress(float mCurrentProgress) {
        if (mCurrentProgress > mMaxProgress) {
            mCurrentProgress = mMaxProgress;
        } else if (mCurrentProgress <= 0) {
            mCurrentProgress = 0;
        }
        this.mCurrentProgress = mCurrentProgress;
    }

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mMaxProgress = 100;
        mCurrentProgress = 0;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(4);
        mPaint.setColor(0x771234ff);
        mPaint.setTextSize(40);

        mPath = new Path();
        mPathMeasure = new PathMeasure();

        //pos中存放x,y坐标
        pos = new float[2];
        tan = new float[2];

        boatBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.boat);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //重置
        mPath.reset();
        //获取周期宽度
        perCycleWidth = w / perCount;
        //绘制一段正弦曲线
        mPath.moveTo(0, h / 2);

        //使其后部长度+2.形成波浪
        for (int i = 0; i < perCount + 2; i++) {
            mPath.rQuadTo(perCycleWidth / 4, -perCycleWidth / 4, perCycleWidth / 2, 0);
            mPath.rQuadTo(perCycleWidth / 4, perCycleWidth / 4, perCycleWidth / 2, 0);
        }

        //使其闭合长度
        //pathMeasure测量长度
        mPathMeasure.setPath(mPath, false);
        //获取每一个周期路径的长度
        perCycleLength = mPathMeasure.getLength() / (perCount + 2);

        //闭合
        mPath.lineTo(w + perCycleWidth * 2, h);
        mPath.lineTo(0, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //测量位置
        mPathMeasure.getPosTan(mPathOffset, pos, tan);

        canvas.save();
        //第一条水波
        //偏移
        canvas.translate(-pos[0], 0);
        canvas.drawPath(mPath, mPaint);

        canvas.save();
        //第二条水波
        //偏移
        canvas.translate(-pos[0], 0);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();

        //再次测量加上当前的偏移量（小船的位置）还与当前的进度值有关
        float boatOffset = mCurrentProgress / mMaxProgress * perCycleLength * perCount + mPathOffset;//小船的位置
        mPathMeasure.getPosTan(boatOffset, pos, tan);

        canvas.translate(-boatBitmap.getWidth() / 2, -boatBitmap.getHeight());//位置
        float degree = (float) (Math.atan2(tan[1], tan[0]) * 180 / Math.PI);

        //在此不跟着小船旋转
        //进度值显示
        canvas.drawText((int) ((mCurrentProgress / mMaxProgress) * 100) + "%",
                pos[0] + ((mCurrentProgress > mMaxProgress / 2) ? -boatBitmap.getWidth() / 2 : boatBitmap.getWidth() / 2),
                pos[1]
                , mPaint);

        canvas.rotate(degree, pos[0] + boatBitmap.getWidth() / 2, pos[1] + boatBitmap.getHeight());

        //在此设置进度可以跟着小船旋转

        canvas.drawBitmap(boatBitmap, pos[0], pos[1], mPaint);

        canvas.restore();
        mPathOffset += 1;
        if (mPathOffset >= perCycleLength) {
            mPathOffset = 0;
        }
        invalidate();
    }
}
