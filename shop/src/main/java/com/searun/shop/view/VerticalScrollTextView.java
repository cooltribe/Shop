package com.searun.shop.view;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.searun.shop.data.ArticleDto;

import java.util.ArrayList;
import java.util.List;
/**
 * @author xushilin
 * 
 */
public class VerticalScrollTextView extends TextView {
	private Paint mPaint;
	private float mX;
	private Paint mPathPaint;	
	public int index = 0;
	private List<ArticleDto> list;
	public float mTouchHistoryY;
	private int mY;	
	ArticleDto articleDto;
	private float middleY;// y轴中间
	private static final int DY = 40; // 每一行的间隔
	public VerticalScrollTextView(Context context) {
		super(context);
		init(context);
	}
	public VerticalScrollTextView(Context context, AttributeSet attr) {
		super(context, attr);
		init(context);
	}
	public VerticalScrollTextView(Context context, AttributeSet attr, int i) {
		super(context, attr, i);
		init(context);
	}
	private void init(Context context) {
		setFocusable(true);
		DisplayMetrics displayMetrics = new DisplayMetrics();  
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);  
		float screenWidth = displayMetrics.widthPixels;  
		float screenHeight = displayMetrics.heightPixels; 
		Log.i("changkuan", screenWidth +"," + screenHeight);
		float radioWidth = screenWidth / 720;
		float radioHeight = screenHeight / 1208;
		
		if(list==null){
			list=new ArrayList<ArticleDto>();
//			Sentence sen=new Sentence(0,"暂时没有通知公告");
			ArticleDto articleDto = new ArticleDto();
			list.add(0, articleDto);
		}		
	
		// 非高亮部分
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setTextSize(16);
		mPaint.setColor(Color.BLACK);
		mPaint.setTypeface(Typeface.SERIF);
		
		// 高亮部分 
		mPathPaint = new Paint();
		mPathPaint.setAntiAlias(true);
		mPathPaint.setColor(Color.BLACK);
		Log.i("min", Math.min(radioWidth, radioHeight) + "");
		mPathPaint.setTextSize(25 * Math.min(radioWidth, radioHeight));
		mPathPaint.setTypeface(Typeface.SANS_SERIF);
	}
	
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(0xEFeffff);
		Paint p = mPaint;
		Paint p2 = mPathPaint;
		p.setTextAlign(Paint.Align.CENTER);
		if (index == -1)
			return;
		p2.setTextAlign(Paint.Align.LEFT);
		// 先画当前行，之后再画他的前面和后面，这样就保持当前行在中间的位置
		articleDto = list.get(index);
		if (articleDto.getTitle() != null) {
			canvas.drawText(articleDto.getTitle(), mX, middleY, p2);
		}
//		float tempY = middleY;
		// 画出本句之前的句子
//		for (int i = index - 1; i >= 0; i--) {			
//			tempY = tempY - DY;
//			if (tempY < 0) {
//				break;
//			}
//			canvas.drawText(list.get(i).getTitle(), mX, tempY, p);			
//		}
//		tempY = middleY;
//		// 画出本句之后的句子
//		for (int i = index + 1; i < list.size(); i++) {
//			// 往下推移
//			tempY = tempY + DY;
//			if (tempY > mY) {
//				break;
//			}
//			canvas.drawText(list.get(i).getTitle(), mX, tempY, p);			
//		}
	}
	protected void onSizeChanged(int w, int h, int ow, int oh) {
		super.onSizeChanged(w, h, ow, oh);
		mX = w * 0.05f; 
		mY = h;
		middleY = h * 0.7f;
	}

	public long updateIndex(int index) {	
		if (index == -1)
			return -1;
		this.index=index;		
		return index;
	}
	
	public List<ArticleDto> getList() {
		return list;
	}
	
	public void setList(List<ArticleDto> list) {
		this.list = list;
	}
	public void updateUI(){
		new Thread(new updateThread()).start();
	}
	
	public interface ClickListener{
		public void onClickListener(ArticleDto art);
	}
	
	public void setClickListener(ClickListener clickListener) {
		this.clickListener = clickListener;
	}
	ClickListener clickListener;
	
	class updateThread implements Runnable {
		long time = 3000; // 开始 的时间，不能为零，否则前面几句歌词没有显示出来
		int i=0;
		public void run() {
			while (true) {
				long sleeptime = updateIndex(i);
				time += sleeptime;
				mHandler.post(mUpdateResults);
				if (sleeptime == -1)
					return;
				try {
					clickListener.onClickListener(getList().get(i));
					Thread.sleep(time);
					i++;
					if(i==getList().size())
						i=0;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	Handler mHandler = new Handler();
	Runnable mUpdateResults = new Runnable() {
		public void run() {
			invalidate(); // 更新视图
		}
	};
	
}