package com.example.qinyu.doubanread.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;
import java.util.jar.Attributes;

/**
 * Created by Qinyu on 2018/4/25.
 */

public class BarView extends View {
    private int mHeight, mWidth;//宽高
    private Paint mPaint;//扇形的画笔
    private Paint mTextPaint;//画文字的画笔

    private int centerX, centerY;//中心坐标

    //"其他"的value
    private double rest;

    private int maxNum=16;//最大块数
    private int statNum=4;//统计块数

    String others = "其他";
    double total;//总和
    double[] datas;//数据
    String[] texts;//文字

    private int[] mColors = {
            Color.parseColor("#FF5050"), Color.parseColor("#ff8c00"),
            Color.parseColor("#40ff40"), Color.parseColor("#3090ff"), Color.parseColor("#cc30ff")
    };

    private int mTextSize;//文字大小

    public BarView(Context context){
        super(context);
    }
    public BarView(Context context, AttributeSet attributeset){
        super(context,attributeset);
        init();
    }
    private void init(){
        mPaint = new Paint();
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setAntiAlias(true);

        mTextSize = 40;
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setStrokeWidth(3);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.BLACK);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight=MeasureSpec.getSize(heightMeasureSpec);
        mWidth=MeasureSpec.getSize(widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(datas==null || datas.length==0) return;

        centerX = (getRight() - getLeft()) / 2;
        centerY = (getBottom() - getTop()) / 2;

        canvas.save();
        drawBar(canvas);
        canvas.restore();

        canvas.save();
        drawText(canvas);
        canvas.restore();
    }

    private void drawBar(Canvas canvas){
        float weidth=mWidth/(maxNum*2+5);
        float bottom=mHeight / (float)4;
        float top=mHeight / (float)10;
        float left=weidth*3;
        int count=0;
        for(int i=0;i<(statNum<datas.length ? statNum : datas.length);i++){
            int num=(int) (datas[i] / total * maxNum);
            mPaint.setColor(mColors[i % mColors.length]);
            for(int j=0;j<num;j++){
                canvas.drawRect(left,top,left+weidth,bottom,mPaint);
                left=left+weidth*2;
                count++;
            }
        }

        rest = 0;
        for (int i = maxNum; i < datas.length; i++) {
            rest += datas[i];
        }
        for(;count<maxNum;count++){
            mPaint.setColor(Color.LTGRAY);
            canvas.drawRect(left,top,left+weidth,bottom,mPaint);
            left=left+weidth*2;
        }
    }

    private void drawText(Canvas canvas){
        float weidth=mWidth/(maxNum*2+5);
        float top=mHeight / (float)3;
        float left=weidth*3;
        float size=mTextSize;
        for(int i=0;i<(statNum<datas.length ? statNum : datas.length);i++){
            mPaint.setColor(mColors[i % mColors.length]);
            canvas.drawRect(left,top,left+size*3/4,top+size*3/4,mPaint);
            canvas.drawText(texts[i],left+size,top+size*3/4,mTextPaint);
            if(i%2 == 1){
                left=weidth*3;
                top+=size*1.5;
            }else
                left=mWidth/2;
        }

    }

    //
    public abstract class BarViewAdapter<T> {

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

    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
        mTextPaint.setTextSize(mTextSize);
        invalidate();
    }
}
