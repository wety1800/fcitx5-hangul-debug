package jaygoo.widget.wlv;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class WaveLineView extends View {
    private Paint paint = new Paint();
    private int volume = 0;

    public WaveLineView(Context context) { super(context); }
    public WaveLineView(Context context, AttributeSet attrs) { super(context, attrs); }
    public WaveLineView(Context context, AttributeSet attrs, int defStyleAttr) { super(context, attrs, defStyleAttr); }

    public void setLineColor(int color) { paint.setColor(color); }
    public void setVolume(int volume) { this.volume = volume; invalidate(); }
    public void setBackGroundColor(int color) {}
    public void setSensibility(int sensibility) {}
    public void setMoveSpeed(float speed) {}
    public void startAnim() { invalidate(); }
    public void stopAnim() {}
    public void release() {}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2f);
        float cy = getHeight() / 2f;
        canvas.drawLine(0, cy - volume, getWidth(), cy + volume, paint);
    }
}