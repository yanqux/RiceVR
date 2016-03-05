package rice.ec.vr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 自定义的圆角矩形ImageView，可以直接当组件在布局中使用。
 * @author caizhiming
 *
 */
public class XCRoundRectImageView extends ImageView{

    private Paint paint;
    private final int DefaultRound = 6; //圆角宽度（单位dip）
    private int RoundPx;

    public XCRoundRectImageView(Context context) {
        this(context,null);
    }

    public XCRoundRectImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XCRoundRectImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint  = new Paint();
        init(context);
    }

    private void init(Context context)
    {
        RoundPx = rice.ec.riceutils.Display.dip2px(context, DefaultRound);
    }

    /**
     * 绘制圆角矩形图片。对进行了圆角处理之后的图截取左半部分，原图截取右半部分，然后拼接成一张图。
     * @author lyc
     */
    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();
        if (null != drawable) {
            Bitmap orginBitmap = ((BitmapDrawable) drawable).getBitmap();
            Bitmap roundBitmap = getRoundBitmap(orginBitmap, RoundPx);

            Rect rectSrcLeft = new Rect(0, 0, roundBitmap.getWidth()/2-RoundPx, roundBitmap.getHeight());
            Rect rectDestLeft = new Rect(0, 0, getWidth()/2-RoundPx, getHeight());
            paint.reset();
            canvas.drawBitmap(roundBitmap, rectSrcLeft, rectDestLeft, paint);

            Rect rectSrcRight = new Rect(orginBitmap.getWidth()/2-RoundPx, 0, orginBitmap.getWidth(), orginBitmap.getHeight());
            Rect rectDestRight = new Rect(getWidth()/2-RoundPx, 0, getWidth(), getHeight());
            paint.reset();
            canvas.drawBitmap(orginBitmap, rectSrcRight, rectDestRight, paint);

        } else {
            super.onDraw(canvas);
        }
    }

    /**
     * 获取圆角矩形图片方法
     * @param bitmap
     * @param roundPx
     * @return Bitmap
     * @author lyc
     */
    private Bitmap getRoundBitmap(Bitmap bitmap, int roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        int x = bitmap.getWidth();

        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;

    }
}