package com.ophone.chapter4_4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class Brick extends View {

	//����ש��Ĵ�С
	public static final int WIDTH = 17;
	public static final int PADDING = 1;
	//�󶥵�(x,y)
	public int left;
	public int top;
	//����ģ��
	private Shape shape;
	//��Ļ
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
			//���data[i]������0�����Ʒ���
			if (data[i] != 0) {
				int r = i / 4;
				int c = i % 4;
				int l = x + c * WIDTH;
				int t = y + r * WIDTH;
				paint.setColor(Color.parseColor("#f7faf3"));
				//���������ķ���
				canvas.drawRect(l, t, l + WIDTH, t + WIDTH, paint);
				//��������С�ķ��񣬿���ȥ���Ǹ�С����������һ���߿�
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
		//�����ƶ���left��С
		this.left = this.left - WIDTH;
		int w = getMarginLeft();
		//����Ƿ�ͱ������������ײ
		if (left + w * WIDTH < parent.getLeftBorder()) {
			this.left = this.left + WIDTH;
			return;
		}
		//����Ƿ�������ש����ײ
		if (collide()) {
			this.left = this.left + WIDTH;
			return;
		}
	}

	public void moveRight() {
		//�����ƶ���left����
		this.left = this.left + WIDTH;
		int w = getMarginRight();
		//����Ƿ��뱳�����ұ߽���ײ
		if (left + (4 - w) * WIDTH > parent.getRightBorder()) {
			this.left = this.left - WIDTH;
			return;
		}
		//����Ƿ�������ש����ײ
		if (collide()) {
			this.left = this.left - WIDTH;
			return;
		}
	}

	private boolean collide() {
		boolean collide = false;
		//ʹ�ñ����ĸ�������ײ���
		int[][] bricks = parent.copyBricks();
		int c = left / Brick.WIDTH;
		int r = top / Brick.WIDTH;
		int[] data = getShape().getData();
		//��ש��ͱ�����ֵ���е���
		for (int i = 0; i < data.length; i++) {
			if (data[i] == 1) {
				int _r = i / 4;
				int _c = i % 4;
				if (r + _r < Screen.ROW & c + _c < Screen.COL & c + _c >= 0
						& r + _r >= 0){
					bricks[r + _r][c + _c] = bricks[r + _r][c + _c] + 1;
					if(bricks[r+_r][c+_c]>1)
						//����ϲ�֮����������д���1��ֵ��������ײ��
						return true;
				}
			}
		}
		return collide;
	}

	public boolean moveDown() {
		//�����ƶ���top����
		this.top = this.top + WIDTH;
		//����Ƿ�������ש����ײ
		if (collide()) {
			//�����ײ����ָ�topֵ������ש��merge��������
			this.top = this.top - WIDTH;
			parent.onMerge(this);
			return true;
		}
		//����Ƿ��뱳������ײ���ײ
		int h = getMarginBottom();
		if (top + (4 - h) * WIDTH > parent.getBottomBorder()) {
			//�����ײ����ָ�topֵ������ש��merge��������
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
			//�Ƿ�͵ײ���ײ
			return true;
		}
		int w = getMarginRight();
		// �Ƿ����ұ߽���ײ
		if (left + (4 - w) * WIDTH > parent.getRightBorder()) {
			return true;
		}
		int l = getMarginLeft();
		// �Ƿ�����߽���ײ
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
		//��������ש��
		while (!moveDown())
			;

	}

	public void next() {
		//�ݴ浱ǰש�飬��ײ��ָ�
		Shape back = shape;
		shape = shape.next();
		//����Ƿ���߽���ײ
		if (collideBorder()) {
			shape = back;
			return;
		}
		//����Ƿ���ש����ײ
		if (collide()) {
			shape = back;
			return;
		}
	}

}
