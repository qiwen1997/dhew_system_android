package com.example.healthy;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
/**
 * 折线图
 */
public class CharView extends View {
    private int mWidth;
    private int mHeight;
    private List<Double> mDatas = new ArrayList<Double>();
    private int paintColors;
    private Paint mPaint;
    public CharView(Context context) {
        this(context, null);
    }
    public CharView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public CharView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    /**
     * 设置数据
     *
     * @param datas  集合里面还有每条线的集合数据
     * @param colors 表示线的颜色集合
     */
    public void setDatas(List<Double> datas, int colors) {
        if (datas != null && datas.size() != 0) {
            mDatas.clear();
            mDatas.addAll(datas);
            paintColors = colors;
            //重绘视图
            postInvalidate();
        }
    }
    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(2f);
        mPaint.setColor(Color.GREEN);
        setBackgroundColor(Color.WHITE);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDatas != null) {  //传人数据后再实现重绘
            mPaint.setColor(paintColors);
            //画底下和顶上的线
            drawBackGroudLines(canvas);
            //画左边的标尺线
            DrawLineLeft(canvas);
            //画下边的标尺线
            drawLineAndTextButtom(canvas);
            //绘制上面的文字提示
            drawTextTop(canvas);
        } else {
            canvas.drawText("正在绘制页面", 100, 100, mPaint);
        }
    }
    /**
     * 绘制底下的横线和文字
     *
     * @param canvas
     */
    private void drawLineAndTextButtom(Canvas canvas) {
        canvas.drawLine(0, mHeight , mWidth, mHeight , mPaint);
        //canvas.drawLine(0, mHeight - 50, mWidth, mHeight - 50, mPaint);
        //直线底下的文字
        for (int i = 1; i <= 24; i++) {
            String textString = i + ":00";
            mPaint.setTextSize(10);
            canvas.drawText(textString,i * (mWidth / 24) - 10, mHeight - 10, mPaint);
            //在文字上面画一条小直线
            canvas.drawLine(i * (mWidth / 24), mHeight ,i * (mWidth / 24), mHeight - 10, mPaint);
            //canvas.drawLine(40 + i * (mWidth / 24), mHeight - 50, 40 + i * (mWidth / 24), mHeight - 70, mPaint);
        }
    }
    /**
     * 绘制左边的坐标线
     *
     * @param canvas
     */
    private void DrawLineLeft(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);//实心
        canvas.drawLine(0, 0, 0, mHeight, mPaint);//左边的线
        //canvas.drawLine(50, 0, 50, mHeight, mPaint);
        canvas.drawLine(mWidth, 0, mWidth, mHeight, mPaint);//右边的线
        //直线边上的文字
        for (int i = 0; i < 7; i++) {
            String textString = i * 20 + "";
            mPaint.setTextSize(20);
            canvas.drawText(textString, 10, mHeight - i * 20 * (mHeight / 130), mPaint);
        }
    }
    /**
     * 绘制上面的文字提示
     *
     * @param canvas
     */
    private void drawTextTop(Canvas canvas) {
        drawLineChart(canvas, mDatas);
        //画文字
        //mPaint.setTextSize(60);
        //canvas.drawText("心跳的频率", 50, 50, mPaint);
    }
    /**
     * 传人点的集合数据，绘制线条
     *
     * @param canvas
     * @param datas
     */
    private void drawLineChart(Canvas canvas, List<Double> datas) {
        int size = datas.size();
        if (size != 0 && size != 1) {
            float[] points = new float[size * 4];
            //float pointWidth = (float) mWidth / (float) (size - 1);
            float pointHeight = (float) (mHeight / 130);
            // 起点和落点的 x，y 坐标！
            //Y方向其实是向下偏移了（mHeight-2y）的长度，也就是在原来的基础上添加偏移量的值，即可得到目的的Y
            //比如（x，y）---》（x，mHeight-y）
            //根据API限定距离顶部是y的距离，目的是距离底部是y的距离，而距离顶部是（mHeight-y）的距离，是不是偏移了（mHeight-2y）的长度！
            //  （mHeight-2y）+y= mHeight-y；
            float startX = 0;//-(mWidth / 24)
            float startY = (float) (mHeight - datas.get(0) * pointHeight);
            float stopX, stopY;
            for (int i = 0; i < datas.size(); i++) {
                stopX = startX + (mWidth / 24);     //x轴的坐标每次添加一个单位长度。
                stopY = (float) (mHeight - datas.get(i) * pointHeight);
                // 将起点和落点加入集合
                addPoint(points, i, startX, startY, stopX, stopY);
                // 将落点的值记录下来作为下一次的起点
                startX = stopX;
                startY = stopY;
            }
            canvas.drawLines(points, mPaint);
        }
    }
    /**
     * 将起点和落点加入集合
     *
     * @param points 添加到哪个集合，所有点坐标的最终数组
     * @param i      添加的是哪个点组合的数据，代表的是点的个数
     * @param startX 起点 X 坐标
     * @param startX 起点 Y 坐标
     * @param stopX  落点 X 坐标
     * @param stopY  落点 Y 坐标
     */
    private void addPoint(float[] points, int i, float startX, float startY, float stopX, float stopY) {
        int index = i * 4;
        // 起点
        points[index] = startX;
        points[index + 1] = startY;
        // 落点
        points[index + 2] = stopX;
        points[index + 3] = stopY;
    }
    /**
     * //绘制底部和顶部的线
     */
    private void drawBackGroudLines(Canvas canvas) {
        canvas.drawLine(0, 0, mWidth, 0, mPaint);
        canvas.drawLine(0, mHeight, mWidth, mHeight, mPaint);
    }
    /**
     * 得到View的宽度和高度
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("TAG2", "onSizeChanged");
        mWidth = getWidth();
        System.out.println(mWidth);
        mHeight = getHeight();
        System.out.println(mHeight);
    }
}
