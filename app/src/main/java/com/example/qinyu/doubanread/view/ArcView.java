package com.example.qinyu.doubanread.view;

import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

import java.util.List;
/**
 * Created by Qinyu on 2018/4/24.
 */

public class ArcView extends View {

    private int mHeight, mWidth;//宽高
    private Paint mPaint;//扇形的画笔
    private Paint mTextPaint;//画文字的画笔

    private int centerX, centerY;//中心坐标

    //"其他"的value
    private double rest;

    private int maxNum;//最大块数

    String others = "其他";
    double total;//总和
    double[] datas;//数据
    String[] texts;//文字

    private int[] mColors = {
            Color.parseColor("#FF5050"), Color.parseColor("#ff8c00"),
            Color.parseColor("#40ff40"), Color.parseColor("#3090ff"), Color.parseColor("#cc30ff")
    };

    private int mTextSize;//文字大小

    private int radius = 1000;//半径

    public ArcView(Context context) {
        super(context);
    }

    public ArcView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setTextSize(40);
        mTextPaint.setStrokeWidth(3);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.BLACK);

        mTextSize = 30;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //无数据
        if (datas == null || datas.length == 0) return;

        centerX = (getRight() - getLeft()) / 2;
        centerY = (getBottom() - getTop()) / 2;
        int min = mHeight > mWidth ? mWidth : mHeight;
        if (radius > min / 2) {
            radius = (int) ((min - getPaddingTop() - getPaddingBottom()) / 3.5);
        }

        //扇形
        canvas.save();
        drawCircle(canvas);
        canvas.restore();

        //线与文字
        canvas.save();
        drawLineAndText(canvas);
        canvas.restore();

    }
    private void drawLineAndText(Canvas canvas) {
        int start = 0;
        canvas.translate(centerX, centerY);//平移画布到中心
        mPaint.setStrokeWidth(4);
        for (int i = 0; i < (maxNum < datas.length ? maxNum : datas.length); i++) {

            float angles = (float) ((datas[i] * 1.0f / total) * 360);
            drawLine(canvas, start, angles, texts[i], mColors[i % mColors.length]);
            start += angles;
        }
        //画其他
        if (start < 359)
            drawLine(canvas, start, 360 - start, others, Color.LTGRAY);

    }

    private void drawLine(Canvas canvas, int start, float angles, String text, int color) {
        mPaint.setColor(color);
        float stopX, stopY;
        stopX = (float) ((radius + 40) * Math.cos((2 * start + angles) / 2 * Math.PI / 180));
        stopY = (float) ((radius + 40) * Math.sin((2 * start + angles) / 2 * Math.PI / 180));

        canvas.drawLine((float) ((radius - 20) * Math.cos((2 * start + angles) / 2 * Math.PI / 180)),
                (float) ((radius - 20) * Math.sin((2 * start + angles) / 2 * Math.PI / 180)),
                stopX, stopY, mPaint
        );
        //画横线
        int dx;//判断横线
        int endX;
        if (stopX > 0) {
            endX = (centerX - getPaddingRight() - 20);
        } else {
            endX = (-centerX + getPaddingLeft() + 20);
        }
        canvas.drawLine(stopX, stopY,
                endX, stopY, mPaint
        );
        dx = (int) (endX - stopX);

        //文字大小
        Rect rect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        int w = rect.width();
        int h = rect.height();
        int offset = 20;//文字偏移量

        canvas.drawText(text, 0, text.length(), dx > 0 ? stopX + offset : stopX - w - offset, stopY + h, mTextPaint);

        //测量百分
        String percentage = angles / 3.60 + "";
        percentage = percentage.substring(0, percentage.length() > 4 ? 4 : percentage.length()) + "%";
        mTextPaint.getTextBounds(percentage, 0, percentage.length(), rect);
        w = rect.width() - 10;

        canvas.drawText(percentage, 0, percentage.length(), dx > 0 ? stopX + offset : stopX - w - offset, stopY - 5, mTextPaint);

    }

    private void drawCircle(Canvas canvas) {
        RectF rect = new RectF((float) (centerX - radius), centerY - radius,
                centerX + radius, centerY + radius);

        int start = 0;
        for (int i = 0; i < (maxNum < datas.length ? maxNum : datas.length); i++) {
            float angles = (float) ((datas[i] * 1.0f / total) * 360);
            mPaint.setColor(mColors[i % mColors.length]);
            canvas.drawArc(rect, start, angles, true, mPaint);
            start += angles;
        }
        //其他
        rest = 0;
        for (int i = maxNum; i < datas.length; i++) {
            rest += datas[i];
        }
        float angles = (float) 360 - start;
        mPaint.setColor(Color.LTGRAY);
        canvas.drawArc(rect, start, angles, true, mPaint);

    }



    //setter
    public void setColors(int[] mColors) {
        this.mColors = mColors;
        invalidate();
    }

    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
        mTextPaint.setTextSize(mTextSize);
        invalidate();
    }

    public void setRadius(int radius) {
        this.radius = radius;
        setTextSize(radius / 6);
        invalidate();
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
        invalidate();
    }

    public void setOthersText(String others) {
        this.others = others;
    }

    public abstract class ArcViewAdapter<T> {

        public void setData(List<T> list) {
            datas = new double[list.size()];
            texts = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                total += getValue(list.get(i));
                datas[i] = getValue(list.get(i));
                texts[i] = getText(list.get(i));
            }

        }

        //数字
        public abstract double getValue(T t);

        //描述
        public abstract String getText(T t);
    }

}
