package com.example.testgif;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class MySerfaceView extends SurfaceView implements Runnable , SurfaceHolder.Callback{

	private Thread thread; // SurfaceView通常需要自己单独的线程来播放动画
    private SurfaceHolder surfaceHolder;
    private GifRender obj;
    private boolean isRunning = true;
    private Context context;

	public MySerfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public MySerfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MySerfaceView(Context context) {
		super(context);
		this.context = context;
		this.surfaceHolder = this.getHolder();
        this.surfaceHolder.addCallback(this);
        this.obj = new GifRender(context);
        obj.setMovieResource(R.drawable.catch_me_disappear);
	}
	

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		 Toast.makeText(context, "SurfaceView已经创建", Toast.LENGTH_LONG).show();
         this.thread = new Thread(this);
         this.thread.start();
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Toast.makeText(context, "SurfaceView已经销毁", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void run() {
		boolean isAlive = true;
        while (isRunning && isAlive) {
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            if (canvas != null) {

                long now = android.os.SystemClock.uptimeMillis();

                if (obj.mMovieStart != 0 && now - obj.mMovieStart > obj.getMovie().duration()) {
                    actionStop();
                }
                obj.draw(canvas, 300, 300);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
//        coverManager.removeViews();
		
	}
	

    public void actionStop() {
        this.isRunning = false;
    }

	
}
