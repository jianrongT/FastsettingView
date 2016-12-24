package jianrt.fastsettingview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class FastSettingView extends View {

    private Paint paint = new Paint();
    private Paint paintpoint = new Paint();
    private float[] each_wight = new float[11];
    private int bottom;
    private float[] xx = new float[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1};
    private float[] yy = new float[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1};
    private float wight;
    private float height;

    @SuppressLint("ResourceAsColor")
    public FastSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(1);

        paintpoint.setColor(Color.LTGRAY);
        paintpoint.setAntiAlias(true);
        paintpoint.setStrokeWidth(6f);

    }

    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        wight = right - left;
        height = bottom - top;
        this.bottom = bottom;
        float tempweight = wight / 11;
        for (int i = 0; i < 11; i++) {
            each_wight[i] = tempweight * i;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(0, height / 2, wight, height / 2, paint);
        for (int i = 0; i < 11; i++) {
            canvas.drawLine(each_wight[i], 0, each_wight[i], height, paint);
        }

        for (int i = 1; i < 11; i++) {
            if (xx[i] != -1 || yy[i] != -1) {
                canvas.drawCircle(xx[i], yy[i], 12, paintpoint);
                if (i == 10) {
                } else {
                    if (xx[i + 1] != -1 || yy[i + 1] != -1) {
                        canvas.drawLine(xx[i], yy[i], xx[i + 1], yy[i + 1],
                                paintpoint);
                    }
                }
            }
        }

        for (int i = 0; i < 11; i++) {
            if (xx[i] != -1) {
                cancalViewListener.isEnable(true);

                break;
            } else {
                cancalViewListener.isEnable(false);

            }
        }

    }

    public void setCancalViewListener(CancalViewListener cancalViewListener) {
        this.cancalViewListener = cancalViewListener;
    }

    private CancalViewListener cancalViewListener = null;

    public interface CancalViewListener {
        public void isEnable(boolean isok);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO �Զ����ɵķ������
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < 11; i++) {
                    if (eventX >= each_wight[i] - 30
                            && eventX <= each_wight[i] + 30) {
                        if (eventY < 0) {
                            yy[i] = 0;
                        } else if (eventY > bottom) {
                            yy[i] = bottom;
                        } else {
                            yy[i] = eventY;
                        }
                        xx[i] = each_wight[i];
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                seekbarvalue(yy);
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public double[] seekbarvalue(float[] yy) {
        double[] tempvalue = new double[10];
        for (int i = 1; i < 11; i++) {
            if (yy[i] == -1) {
                tempvalue[i - 1] = 0.50;
            } else {
                tempvalue[i - 1] = 1 - yy[i] / bottom;
            }
        }
        return tempvalue;
    }

    public void clearValues() { // ��ԭ���е�
        for (int i = 0; i < 11; i++) {
            xx[i] = -1;
            yy[i] = -1;
        }
        invalidate();
    }

    public double[] getResult() { // �õ�10��Yֵ
        return seekbarvalue(yy);
    }

}
