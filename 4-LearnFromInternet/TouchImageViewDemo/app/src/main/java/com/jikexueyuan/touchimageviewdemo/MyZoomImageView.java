package com.jikexueyuan.touchimageviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

/*跟大家分享一个我写的图片缩放的例子，主要功能是图片双击放大缩小，两指滑动放大缩小，有最大最小缩放比例，
压缩有回弹，应该是比较全面了。（在我一哥们写的基础上改的，黑嘿嘿）
*/
public class MyZoomImageView extends ImageView {
    /**
     * 原始的Matrix
     */
    private Matrix srcMatrix = new Matrix();
    /**
     * 图片处理的矩阵
     */
    private Matrix mMatrix = new Matrix();
    /**
     * 图片矩阵的原始数据
     */
    private float[] mValues = new float[9];
    /**
     * 缩放的最小值
     */
    private float ZOOM_MIN = 0;
    /**
     * 缩放的最大值
     */
    private float ZOOM_MAX = 0;
    /**
     * 缩放默认值
     */
    private float ZOOM_DEFAULT = 0;
    /**
     * 双击之后的缩放值
     */
    private float ZOOM_DOUBLECLICK = 0;
    /**
     * 中心坐标
     */
    private PointF mid = new PointF();
    /**
     * 手势
     */
    private GestureDetector mGestrueDetector = null;
    /**
     * 图片原宽度
     */
    private int mImageWidth = 0;
    /**
     * 图片原高度
     */
    private int mImageHeight = 0;
    /**
     * 用于记录开始时候的坐标位置
     */
    private PointF startPoint = new PointF();
    /**
     * 平移模式
     */
    private static final int MODE_DRAG = 1;
    /**
     * 放大缩小照片模式
     */
    private static final int MODE_ZOOM = 2;
    /**
     * 不支持Matrix
     */
    private static final int MODE_UNABLE = 3;
    private int mMode = 0;
    /**
     * 缩放开始时的手指间距
     */
    private float mStartDis;

    public MyZoomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public MyZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public MyZoomImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
    }

    private void init(Context context) {
        System.out.println("init");
//      viewTreeObserver = getViewTreeObserver();
//      viewTreeObserver
//              .addOnGlobalLayoutListener(new MyGrobalLayoutListener());
        setScaleType(ScaleType.MATRIX);
        mGestrueDetector = new GestureDetector(context, new MyGestureListener());
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        // TODO Auto-generated method stub
        super.setImageBitmap(bm);
        // 有时会调用setImageBitmap(null)
        if (bm == null)
            return;
        mImageWidth = bm.getWidth();
        mImageHeight = bm.getHeight();

        fitScreen();

        // 保存当前的图片尺寸信息
        mMatrix.set(getImageMatrix());
        mMatrix.getValues(mValues);
        //保存原始矩阵
        srcMatrix.set(mMatrix);

        ZOOM_MIN = mValues[Matrix.MSCALE_X] * 2 / 3;
        ZOOM_DEFAULT = mValues[Matrix.MSCALE_X];
        ZOOM_DOUBLECLICK = mValues[Matrix.MSCALE_X] * 2;
        ZOOM_MAX = mValues[Matrix.MSCALE_X] * 3;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void fitScreen() {
        System.out.println("fitScreen--->" + getWidth() + "   " + mImageWidth);
        float scaleX = (float) getWidth() / (float) mImageWidth;
        mMatrix.set(getImageMatrix());
        mMatrix.postScale(scaleX, scaleX);
        // 图片与下边界 的距离
        float marginY = getHeight() - scaleX * mImageHeight;
        if (marginY > 0) {
            // 说明纵向铺不满
            mMatrix.postTranslate(0, marginY / 2f);
        }
        // Matrix是从（0,0）左上角开始做的变换
        setImageMatrix(mMatrix);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /** 处理单点、多点触摸 **/
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            // 按下事件
            case MotionEvent.ACTION_DOWN:
                // 设置拖动模式
                mMode = MODE_DRAG;
                startPoint.set(event.getX(), event.getY());
                mid.set(getWidth() / 2, getHeight() / 2);
                break;
            // 多点触摸
            case MotionEvent.ACTION_POINTER_DOWN:
//          killThread = true;
                if (mMode == MODE_UNABLE)
                    return true;
                mMode = MODE_ZOOM;
                mStartDis = getDistance(event);
                midPoint(mid, event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mMode == MODE_DRAG) {
                    float dx = event.getX() - startPoint.x; // 得到x轴的移动距离
                    float dy = event.getY() - startPoint.y; // 得到y轴的移动距离

                    // 避免和双击冲突,大于10f才算是拖动
                    if (Math.sqrt(dx * dx + dy * dy) > 10f) {
                        startPoint.set(event.getX(), event.getY());
                        // 在当前基础上移动
                        mMatrix.set(getImageMatrix());
                        float[] values = new float[9];
                        mMatrix.getValues(values);

                        dx = checkDxBound(values, dx);
                        dy = checkDyBound(values, dy);
                        mMatrix.postTranslate(dx, dy);
                        setImageMatrix(mMatrix);
                    }

                } else if (mMode == MODE_ZOOM) {
                    float endDis = getDistance(event);
                    if (endDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                        float scale = endDis / mStartDis;// 得到缩放倍数
                        setZoom(scale);
                        mStartDis = endDis;// 重置距离
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                // break;
            case MotionEvent.ACTION_UP:
                mMode = 0;
                break;
            // 多点松开
            case MotionEvent.ACTION_POINTER_UP:
                mMode = 0;
                resetToDefault();
            default:
                break;
        }
        return mGestrueDetector.onTouchEvent(event);
    }

    private void resetToDefault() {
        float[] values = new float[9];
        getImageMatrix().getValues(values);
        // 获取当前X轴缩放级别
        float scale = values[Matrix.MSCALE_X];
        System.out.println(scale + "   " + ZOOM_DEFAULT);
        if (scale < ZOOM_DEFAULT) {
            setImageMatrix(srcMatrix);
        }
    }

//  private boolean killThread = false;
//  class ResetThread extends Thread{
//      private float scale = 0;
//      public ResetThread(float scale){
//          this.scale = scale;
//      }
//      @Override
//      public void run() {
//          while(!this.isInterrupted()&&!killThread){
//              if(scale<ZOOM_DEFAULT){
//                  scale = scale+0.1f;
//                  Message msg = Message.obtain();
//                  msg.obj = scale;
//                  myHandler.sendMessage(msg);
//              }else if(scale >ZOOM_DEFAULT){
//                  Message msg = Message.obtain();
//                  msg.obj = ZOOM_DEFAULT;
//                  myHandler.sendMessage(msg);
//                  break;
//              }
//              try {
//                  Thread.sleep(10);
//              } catch (InterruptedException e) {
//                  // TODO Auto-generated catch block
//                  e.printStackTrace();
//              }
//          }
//      }
//  }

    /**
     * 计算两个手指间的距离
     *
     * @param event
     * @return
     */
    private float getDistance(MotionEvent event) {
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);
        /** 使用勾股定理返回两点之间的距离 */
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    // 取手势中心点
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /**
     * 和当前矩阵对比，检验dx，使图像移动后不会超出ImageView边界
     *
     * @param values
     * @param dx
     * @return
     */
    private float checkDxBound(float[] values, float dx) {
        float width = getWidth();
        if (mImageWidth * values[Matrix.MSCALE_X] < width) {
            // 当控件能容纳整张图片时，让图片保持正中
            dx = (getWidth() - mImageWidth * values[Matrix.MSCALE_X]) / 2
                    - values[Matrix.MTRANS_X];
        } else if (values[Matrix.MTRANS_X] + dx > 0)
            dx = -values[Matrix.MTRANS_X];
        else if (values[Matrix.MTRANS_X] + dx < -(mImageWidth
                * values[Matrix.MSCALE_X] - width))
            dx = -(mImageWidth * values[Matrix.MSCALE_X] - width)
                    - values[Matrix.MTRANS_X];
        return dx;
    }

    /**
     * 和当前矩阵对比，检验dy，使图像移动后不会超出ImageView边界
     *
     * @param values
     * @param dy
     * @return
     */
    private float checkDyBound(float[] values, float dy) {
        // 获取ImageView控件高度
        float height = getHeight();
        // mImageHeight=sitImageBitmap方法中获取的图片高度
        // values[Matrix.MSCALE_Y]当前Y轴缩放级别计算出当前Y轴的显示高度
        if (mImageHeight * values[Matrix.MSCALE_Y] < height) {
            // 图片的纵向全部显示了
            dy = (getHeight() - mImageHeight * values[Matrix.MSCALE_Y]) / 2
                    - values[Matrix.MTRANS_Y];
        } else if (values[Matrix.MTRANS_Y] + dy > 0) {

            dy = -values[Matrix.MTRANS_Y];
        } else if (values[Matrix.MTRANS_Y] + dy < -(mImageHeight
                * values[Matrix.MSCALE_Y] - height)) {

            dy = -(mImageHeight * values[Matrix.MSCALE_Y] - height)
                    - values[Matrix.MTRANS_Y];
        }
        return dy;
    }

    /**
     * 双击放缩图片
     */
    public void onDoubleClick(MotionEvent e) {
        float[] values = new float[9];
        getImageMatrix().getValues(values);
        // 获取当前X轴缩放级别
        float scale = values[Matrix.MSCALE_X];
        float tmp;
        if (scale > ZOOM_DEFAULT) {
            tmp = ZOOM_DEFAULT / scale;
        } else {
            tmp = ZOOM_DOUBLECLICK / scale;
        }
        // 将缩放中心设置为双击中心
        mid.set(e.getX(), e.getY());
        setZoom(tmp);
    }

    // scale是相对于当前（不是最初）的大小来进行变化的倍率
    private void setZoom(float scale) {
        // TODO Auto-generated method stub

        float[] values = new float[9];
        // 不要getImageMatrix().getValues(values)
        mMatrix.set(getImageMatrix());
        mMatrix.getValues(values);

        // 限制放大的最大倍数
        if (scale * values[Matrix.MSCALE_X] > ZOOM_MAX) {
            scale = ZOOM_MAX / values[Matrix.MSCALE_X];
        }
        // 限制缩小的最小倍数
        if (scale * values[Matrix.MSCALE_X] < ZOOM_MIN) {
            scale = ZOOM_MIN / values[Matrix.MSCALE_X];
        }
        if (scale == 1) {
            return;
        }
        mMatrix.postScale(scale, scale, mid.x, mid.y);
        mMatrix.getValues(values);
        float dx = checkDxBound(values, 0);
        float dy = checkDyBound(values, 0);
        mMatrix.postTranslate(dx, dy);

        setImageMatrix(mMatrix);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            // 捕获Down事件
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            // 触发双击事件
            onDoubleClick(e);
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            // TODO Auto-generated method stub
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            // TODO Auto-generated method stub
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            // TODO Auto-generated method stub
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            // TODO Auto-generated method stub
            super.onShowPress(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            // TODO Auto-generated method stub
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            // TODO Auto-generated method stub
            return super.onSingleTapConfirmed(e);
        }

    }
}
