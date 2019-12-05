package com.uidt.module_custom_view.bean;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;

/**
 * @author yijixin on 2019-12-04
 * 解析svg信息
 */
public class ProvinceItem {
    private Path path;
    private String name;
    private int drawColor; //板块颜色
    private PointF clickPoint; //显示省份信息

    public ProvinceItem(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawColor() {
        return drawColor;
    }

    public void setDrawColor(int drawColor) {
        this.drawColor = drawColor;
    }

    public PointF getClickPoint() {
        return clickPoint;
    }

    public void setClickPoint(PointF clickPoint) {
        this.clickPoint = clickPoint;
    }

    /**
     * 绘制
     */
    public void drawItem(Canvas canvas, Paint mPaint,boolean isSelected) {
        if (isSelected) {
            //绘制内部颜色
            mPaint.clearShadowLayer();
            mPaint.setStrokeWidth(1);
            mPaint.setColor(drawColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawPath(path,mPaint);

            //绘制边界
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(0xff0e8ef4);
            canvas.drawPath(path,mPaint);
        } else {
            mPaint.setStrokeWidth(2);
            mPaint.setColor(Color.BLACK);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setShadowLayer(8,0,0,0xFFFFFF);
            canvas.drawPath(path,mPaint);

            mPaint.clearShadowLayer();
            mPaint.setColor(drawColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawPath(path,mPaint);
        }
    }

    /**
     * 判断是否点击是否到当前省份区域
     */
    public boolean isTouch(float x, float y) {
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);

        Region region = new Region();

        region.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));

        return region.contains((int) x, (int) y);
    }
}
