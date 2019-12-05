package com.uidt.module_custom_view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.graphics.PathParser;

import com.uidt.module_custom_view.R;
import com.uidt.module_custom_view.bean.ProvinceItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author yijixin on 2019-12-04
 */
public class MapView extends View {

    private int[] colorArray = new int[]{0xFF239BD7, 0xFF30A9E5, 0xFF80CBF1, 0xFF4087A3};
    private List<ProvinceItem> itemList;//所有省份信息
    private Paint mPaint;
    private ProvinceItem selectProvince;//当前选中的省份
    private RectF totalRectF;//整个地图的大小信息
    private float scale = 1.0f;

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;

    //第一个按下的手指的点
    private PointF startPoint = new PointF();
    //两个按下手指的触摸点的中点
    private PointF midPoint = new PointF();
    //初始的两个点按下的距离
    private float oriDis = 1f;
    private boolean actionClick = true;
    private float translateX;
    private float translateY;
    //是否需要显示省份名称
    private boolean shouldShowText = false;

    public MapView(Context context) {
        this(context, null);
    }

    public MapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        itemList = new ArrayList<>();
        loadThread.start();
    }

    private Thread loadThread = new Thread() {
        @Override
        public void run() {
            InputStream inputStream = getResources().openRawResource(R.raw.china);
            //获取DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //从factory获取DocumentBuilder实例
            DocumentBuilder builder;
            try {
                builder = factory.newDocumentBuilder();
                //解析输入流，获取Document实例
                Document document = builder.parse(inputStream);
                //获取每一个元素节点
                Element rootElement = document.getDocumentElement();
                //先找到Path 获取标签为path的所有数据的集合
                NodeList path = rootElement.getElementsByTagName("path");
                //确定整个View的上、下、左、右位置
                float left = -1;
                float right = -1;
                float top = -1;
                float bottom = -1;
                List<ProvinceItem> list = new ArrayList<>();
                for (int i = 0; i < path.getLength(); i++) {
                    Element element = (Element) path.item(i);
                    String pathData = element.getAttribute("d");
                    String name = element.getAttribute("title");
                    //将pathData转为Android所用的Path
                    Path path1 = PathParser.createPathFromPathData(pathData);

                    ProvinceItem provinceItem = new ProvinceItem(path1);
                    provinceItem.setName(name);
                    provinceItem.setDrawColor(colorArray[i % 4]);

                    //确定地图上下左右个位置坐标
                    RectF rectF = new RectF();
                    path1.computeBounds(rectF, true);
                    left = left == -1 ? rectF.left : Math.min(left, rectF.left);
                    right = right == -1 ? rectF.right : Math.max(right, rectF.right);
                    top = top == -1 ? rectF.top : Math.min(top, rectF.top);
                    bottom = bottom == -1 ? rectF.bottom : Math.max(bottom, rectF.bottom);

                    list.add(provinceItem);
                }

                itemList = list;
                totalRectF = new RectF(left, top, right, bottom);
                //刷新界面 因为此为子线程，可能测量已经完成，所以进行界面的刷新
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //布局和绘制都刷新
                        requestLayout();
                        invalidate();
                    }
                });
            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //控件宽高
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (totalRectF != null) {
            //地图的宽度
            float mapWidth = totalRectF.width();
            //计算缩放比例
            scale = width / mapWidth;
        }

        //设置宽高
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (itemList != null && itemList.size() > 0) {

            canvas.save();
            //画布缩放到中间位置
            canvas.scale(scale, scale);
            //在缩放后处理位移
            canvas.translate(translateX, translateY);

            for (ProvinceItem provinceItem : itemList) {
                if (provinceItem != selectProvince) {
                    provinceItem.drawItem(canvas, mPaint, false);
                } else {
                    provinceItem.drawItem(canvas, mPaint, true);
                }
            }

            //绘制文本
            if (shouldShowText) {
                mPaint.setColor(Color.RED);
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setTextSize(40);
                canvas.drawText(selectProvince.getName(), selectProvince.getClickPoint().x, selectProvince.getClickPoint().y, mPaint);
            }

            canvas.restore();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        float currentScaleCount = 0;//当前缩放系数
        float currentTranslateX = 0;//当前X滑动距离
        float currentTranslateY = 0;//当前Y滑动距离

        //& MotionEvent.ACTION_MASK 否则无法实现多点触控
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                //单点触碰
                startPoint.set(event.getX(), event.getY());
                //拖动模式
                mode = DRAG;
                //是否是点击事件
                actionClick = true;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //多点触碰
                oriDis = distance(event);
                if (oriDis > 10) {
                    midPoint = midPoint(event);
                    //缩放模式
                    mode = ZOOM;
                }
                actionClick = false;
                break;
            case MotionEvent.ACTION_MOVE:
                //滑动
                if (mode == ZOOM) {
                    //多指缩放
                    float newDis = distance(event);
                    if (Math.abs(newDis - oriDis) > 10) {
                        //缩放因子
                        float scaleInner = newDis / oriDis;
                        // scaleInner - 1 可大可小 缩放
                        currentScaleCount = scale + (scaleInner - 1);

                        if (currentScaleCount < 1) {
                            //防止缩放的太小
                            scale = 1;
                        } else {
                            scale = currentScaleCount;
                        }
                        oriDis = newDis;
                        invalidate();
                    }
                } else if (mode == DRAG) {
                    //单指拖动
                    if (Math.abs(x - startPoint.x) > 10 || Math.abs(y - startPoint.y) > 10) {
                        currentTranslateX = translateX + x - startPoint.x;
                        currentTranslateY = translateY + y - startPoint.y;
                        translateX = currentTranslateX;
                        translateY = currentTranslateY;
                        startPoint.set(x, y);
                        actionClick = false;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                //单点触碰
                mode = NONE;
                //触发点击事件时 actionClick = true
                if (actionClick) {
                    handleTouch(x / scale - translateX, y / scale - translateY);
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                //多点触碰
                mode = NONE;
                break;
            default:
        }
        return true;
    }

    private void handleTouch(float x, float y) {
        shouldShowText = false;

        if (itemList == null) {
            return;
        }

        ProvinceItem province = null;
        for (ProvinceItem provinceItem : itemList) {
            if (provinceItem.isTouch(x, y)) {
                provinceItem.setClickPoint(new PointF(x, y));
                province = provinceItem;
                shouldShowText = true;
            }
        }

        if (province != null) {
            selectProvince = province;
            postInvalidate();
        }
    }

    /**
     * 计算两个手指头之间的中心点的位置
     * x = (x1+x2)/2;
     * y = (y1+y2)/2;
     *
     * @param event 触摸事件
     * @return 返回中心点的坐标
     */
    private PointF midPoint(MotionEvent event) {
        float x = (event.getX(0) + event.getX(1)) / 2;
        float y = (event.getY(0) + event.getY(1)) / 2;
        return new PointF(x, y);
    }

    /**
     * 计算两个手指间的距离
     *
     * @param event 触摸事件
     * @return 放回两个手指之间的距离
     */
    private float distance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);//两点间距离公式
    }
}
