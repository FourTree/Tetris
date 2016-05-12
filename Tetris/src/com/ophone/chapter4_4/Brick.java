package com.ophone.chapter4_4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class Brick extends View {

	//定义砖块的大小
	public static final int WIDTH = 17;
	public static final int PADDING = 1;
	//左顶点(x,y)
	public int left;
	public int top;
	//数据模型
	private Shape shape;
	//屏幕
	private Screen parent;
	private TextPaint paint = new TextPaint();

	public Brick(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Brick(Context context) {
		super(context);
	}

	public Brick(Screen screen) {
		this(screen.getContext());
		parent = screen;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int x = left;
		int y = top;
		int[] data = shape.getData();
		for (int i = 0; i < data.length; i++) {
			//如果data[i]不等于0，绘制方格
			if (data[i] != 0) {
				int r = i / 4;
				int c = i % 4;
				int l = x + c * WIDTH;
				int t = y + r * WIDTH;
				paint.setColor(Color.parseColor("#f7faf3"));
				//绘制外面大的方格
				canvas.drawRect(l, t, l + WIDTH, t + WIDTH, paint);
				//绘制里面小的方格，看上去像是给小方格增加了一个边框
				paint.setColor(Color.parseColor("#4c8e0b"));
				canvas.drawRect(l + PADDING, t + PADDING, l + WIDTH - PADDING,
						t + WIDTH - PADDING, paint);
			}
		}
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public void moveLeft() {
		//向左移动，left减小
		this.left = this.left - WIDTH;
		int w = getMarginLeft();
		//检查是否和背景的最左边碰撞
		if (left + w * WIDTH < parent.getLeftBorder()) {
			this.left = this.left + WIDTH;
			return;
		}
		//检查是否与其他砖块碰撞
		if (collide()) {
			this.left = this.left + WIDTH;
			return;
		}
	}

	public void moveRight() {
		//向右移动，left增加
		this.left = this.left + WIDTH;
		int w = getMarginRight();
		//检查是否与背景最右边界相撞
		if (left + (4 - w) * WIDTH > parent.getRightBorder()) {
			this.left = this.left - WIDTH;
			return;
		}
		//检查是否与其他砖块碰撞
		if (collide()) {
			this.left = this.left - WIDTH;
			return;
		}
	}

	private boolean collide() {
		boolean collide = false;
		//使用背景的副本做碰撞检测
		int[][] bricks = parent.copyBricks();
		int c = left / Brick.WIDTH;
		int r = top / Brick.WIDTH;
		int[] data = getShape().getData();
		//将砖块和背景的值进行叠加
		for (int i = 0; i < data.length; i++) {
			if (data[i] == 1) {
				int _r = i / 4;
				int _c = i % 4;
				if (r + _r < Screen.ROW & c + _c < Screen.COL & c + _c >= 0
						& r + _r >= 0){
					bricks[r + _r][c + _c] = bricks[r + _r][c + _c] + 1;
					if(bricks[r+_r][c+_c]>1)
						//如果合并之后的数组中有大于1的值，代表碰撞了
						return true;
				}
			}
		}
		return collide;
	}

	public boolean moveDown() {
		//向下移动，top增加
		this.top = this.top + WIDTH;
		//检查是否与其他砖块碰撞
		if (collide()) {
			//如果碰撞，则恢复top值，并将砖块merge到背景中
			this.top = this.top - WIDTH;
			parent.onMerge(this);
			return true;
		}
		//检查是否与背景的最底部碰撞
		int h = getMarginBottom();
		if (top + (4 - h) * WIDTH > parent.getBottomBorder()) {
			//如果碰撞，则恢复top值，并将砖块merge到背景中
			this.top = this.top - WIDTH;
			parent.onMerge(this);
			return true;
		}
		return false;
	}

	private boolean collideBorder() {
		int t = getMarginTop();
		if (top + t * WIDTH < parent.getTopBorder()) {
			return true;
		}
		int h = getMarginBottom();
		if (top + (4 - h) * WIDTH > parent.getBottomBorder()) {
			//是否和底部碰撞
			return true;
		}
		int w = getMarginRight();
		// 是否与右边界碰撞
		if (left + (4 - w) * WIDTH > parent.getRightBorder()) {
			return true;
		}
		int l = getMarginLeft();
		// 是否与左边界碰撞
		if (left + l * WIDTH < parent.getLeftBorder()) {
			return true;
		}
		return false;
	}

	private int getMarginTop() {
		return shape.marginTop();
	}

	private int getMarginBottom() {
		return shape.marginBottom();
	}

	private int getMarginRight() {
		return shape.marginRight();
	}

	private int getMarginLeft() {
		return shape.marginLeft();
	}

	public void fall() {
		//快速落下砖块
		while (!moveDown())
			;

	}

	public void next() {
		//暂存当前砖块，碰撞后恢复
		Shape back = shape;
		shape = shape.next();
		//检查是否与边界碰撞
		if (collideBorder()) {
			shape = back;
			return;
		}
		//检查是否与砖块碰撞
		if (collide()) {
			shape = back;
			return;
		}
	}

}
