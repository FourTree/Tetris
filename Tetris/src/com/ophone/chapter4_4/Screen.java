package com.ophone.chapter4_4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

public class Screen extends View implements Runnable {

	// Event ID
	public static final int EVENT_DOWN = 0;
	// Guess
	public static final int CLICK = 10;
	public static final int UP_LIMIT = 50;
	private VelocityTracker tracker = null;
	private int clickX = -1;
	private int clickY = -1;
	private int mLeft = 0;
	private int mTop = 0;
	private int mBottom = 0;
	private int duration = 1000;
	private Thread thread = null;
	//��Ϸ��״̬�����볣��
	private int state = 0;
	public static final int READY = 0;
	public static final int RUNNING = 1;
	public static final int FALLING = 2;
	public static final int PAUSE = -1;
	// ����������������
	public static final int COL = 10;
	public static final int ROW = 24;
	public static final int BORDER = 5;
	private Paint paint = new Paint();
	private Brick next;
	private Brick current;
	private int score;
	private GameListener listener;
	private int right;
	//�洢������ש��ֵ��0����1
	private int[][] bricks = new int[ROW][COL];
	//����ש��ֵ�ĸ���
	private int[][] copy = new int[ROW][COL];

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int action = msg.what;
			switch (action) {
			case EVENT_DOWN: {
				if (state == RUNNING)
					down();
				break;
			}
			default:
				break;
			}
		}

	};

	public Screen(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Screen(Context context) {
		super(context);
		reset();
	}

	//��ʼ����Ϸ����
	private void reset() {
		//���㷽����������ı߽�
		mLeft = 0;
		mTop = 0;
		mBottom = mTop + ROW * Brick.WIDTH;
		right = Brick.WIDTH * COL + mLeft;
		//��ʼ����һ������
		next = new Brick(this);
		next.setShape(Shape.random());
		next.left = right + BORDER;
		next.top = mTop;
		//��ʼ����ǰ����
		current = new Brick(this);
		current.setShape(Shape.random());
		current.left = 3 * Brick.WIDTH + mLeft;
		current.top = mTop;
		int mt = current.getShape().marginTop();
		current.top -= mt * Brick.WIDTH;
		//��ʼ�������������������
		initializeBricks();
		state = READY;
		thread = null;
		this.score = 0;
		//������Ļ����ͼƬ
		setBackgroundResource(R.drawable.sea);
	}

	private void initializeBricks() {
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				bricks[i][j] = 0;
			}
		}
	}

	public void setGameListener(GameListener listener) {
		this.listener = listener;
	}

	public int getLeftBorder() {
		return mLeft;
	}

	public int getRightBorder() {
		return right;
	}

	public int getTopBorder() {
		return 0;
	}

	public int getBottomBorder() {
		return mBottom;
	}

	public int[][] copyBricks() {
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				copy[i][j] = bricks[i][j];
			}
		}
		return copy;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(Color.CYAN);
		int _w = ((WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
		paint.setColor(Color.BLACK);
		//����������Ϸ����ָ���
		canvas.drawLine(right, mTop, right, mTop + getHeight(), paint);
		if (next != null) {
			//����һ��ש��
			next.draw(canvas);
		}
		int color = paint.getColor();
		int m_left = next.left;
		int m_top = next.top + Brick.WIDTH * 6;
		paint.setColor(Color.parseColor("#FFFFFF"));
		paint.setTextSize(20);
		//���÷�
		canvas.drawText(score + "", m_left, m_top, paint);
		int s_left = next.left;
		int s_top = m_top + Brick.WIDTH * 2;
		//����Ϸש�飬��ͣ����Ϸ��...
		canvas.drawText(getStateText(state), s_left, s_top, paint);
		if (current != null)
			//����ǰ��ש��
			current.draw(canvas);
		paint.setColor(color);
		color = paint.getColor();
		//����Ļ�ϵ�ש��
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				if (bricks[i][j] == 1) {
					int r = i;
					int c = j;
					int l = c * Brick.WIDTH;
					int t = r * Brick.WIDTH;
					
					paint.setColor(Color.parseColor("#f7faf3"));
					canvas.drawRect(l, t, l + Brick.WIDTH, t + Brick.WIDTH,
							paint);
					paint.setColor(Color.parseColor("#4c8e0b"));
					canvas.drawRect(l + 1, t + 1, l + Brick.WIDTH - 1, t
							+ Brick.WIDTH - 1, paint);
				}
			}
		}
		paint.setColor(color);
	}

	private String getStateText(int state) {
		int id = -1;
		switch (state) {
		case READY:
			id = R.string.ready;
			break;
		case RUNNING:
			id = R.string.playing;
			break;
		case PAUSE:
			id = R.string.pause;
			break;
		}
		return getContext().getResources().getString(id);
	}

	public void down() {
		current.moveDown();
		invalidate();
	}

	private void remove() {
		int[] mark = new int[4];
		int count = 0;
		//�ж��Ƿ������ж�����ש���
		for (int i = ROW - 1; i >= 0; i--) {
			int total = 0;
			for (int j = 0; j < COL; j++) {
				total += bricks[i][j];
				if (total == 10) {
					mark[count++] = i;
				}
			}
		}

		if (count > 0) {
			for (int i = 0; i < count; i++) {
				//ɾ������ש��
				removeBricks(mark[i] + i);
			}
			//֪ͨ������ɾ����count��ש��
			if (listener != null)
				listener.onRemove(count);
		}
		//�ӷ�
		score += count * 10;
		addScore(score);
	}

	private void removeBricks(int row) {
		for (int i = row; i >= 0; i--) {
			int p = i - 1;
			if (p >= 0) {
				for (int j = 0; j < COL; j++) {
					bricks[i][j] = bricks[p][j];
				}
			} else {
				for (int j = 0; j < COL; j++) {
					bricks[0][j] = 0;
				}
			}
		}
	}

	//�ж��Ƿ���Ϸ����
	private boolean end() {
		for (int i = 0; i < COL; i++) {
			if (bricks[0][i] == 1)
				return true;
		}
		return false;
	}

	public void onMerge(Brick b) {
		//��ש�������ֵ�뱳��merge
		int c = b.left / Brick.WIDTH;
		int r = b.top / Brick.WIDTH;
		int[] data = b.getShape().getData();
		for (int i = 0; i < data.length; i++) {
			if (data[i] == 1) {
				int _r = i / 4;
				int _c = i % 4;
				if (r + _r < ROW & c + _c < COL & c + _c >= 0 & r + _r >= 0)
					bricks[r + _r][c + _c] = 1;
			}
		}
		// end
		remove();
		if (end()) {
			if (listener != null) {
				listener.gameOver(score);
			}
			reset();
		} else {
			current.setShape(next.getShape());
			current.left = 3 * Brick.WIDTH + mLeft;
			current.top = mTop;
			int mt = current.getShape().marginTop();
			current.top -= mt * Brick.WIDTH;
			next.setShape(Shape.random());
			state = RUNNING;
			invalidate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (tracker == null)
			tracker = VelocityTracker.obtain();
		//��event���뵽tracker���Ա����
		tracker.addMovement(event);
		int action = event.getAction();
		switch (action) {
		//ACTION_DOWNʱ��¼clickX��clickY��ֵ
		case MotionEvent.ACTION_DOWN:
			clickX = (int) event.getX();
			clickY = (int) event.getY();
			break;
		//ACTION_UP����ʼ����
		case MotionEvent.ACTION_UP:
			tracker.computeCurrentVelocity(1000);
			//���y�᷽����ٶ�
			float vy = tracker.getYVelocity();
			//���x�᷽����ٶ�
			float vx = tracker.getXVelocity();
			//���µ�x,y����
			int x = (int) event.getX();
			int y = (int) event.getY();
			if (Math.abs(vy) > ViewConfiguration.getMinimumFlingVelocity()
					& vy < 0 & Math.abs(y - clickY) > UP_LIMIT) {
				//�������������ж�Ϊ���ϻ���
				if (state == READY) {
					start();
				} else {
					pause(false);
				}
				tracker.recycle();
				break;
			}
			//��Ϸ����������
			if (state == RUNNING) {
				if (Math.abs(x - clickX) < CLICK
						& Math.abs(y - clickY) <= CLICK) {
					//�ж�Ϊ��Ļ����������任ש����״
					next();
					tracker.recycle();
					break;
				}
				if (Math.abs(vy) > ViewConfiguration.getMinimumFlingVelocity()
						& y - clickY > UP_LIMIT & Math.abs(vy) > Math.abs(vx)) {
					//�ж�Ϊ�»���������ש��ֱ������
					fall();
					tracker.recycle();
					break;
				}
				if (Math.abs(vx) > Math.abs(vy)) {
					//�ж�Ϊ�����ƶ�����
					if (vx > 0)
						right();
					else
						left();
				}
			}
			tracker.recycle();
			break;
		default:
			break;

		}
		return true;
	}

	private void addScore(int score) {
		int level = score / 1000;
		duration = 1000 - level * 100;
		if (duration < 100)
			duration = 100;
	}

	private void next() {
		current.next();
		invalidate();
	}

	private void right() {
		handler.removeMessages(EVENT_DOWN);
		current.moveRight();
		invalidate();
	}

	private void left() {
		handler.removeMessages(EVENT_DOWN);
		current.moveLeft();
		invalidate();
	}

	private void fall() {
		state = FALLING;
		handler.removeMessages(EVENT_DOWN);
		current.fall();
		invalidate();
	}

	//������Ϸ
	private void start() {
		state = RUNNING;
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	public void pause(boolean force) {
		if (force)
			state = PAUSE;
		else
			state = -state;
		invalidate();
	}

	public void run() {
		while (state != READY) {
			if (state != PAUSE) {
				handler.removeMessages(EVENT_DOWN);
				//ÿ��һ�뷢��һ��EVENT_DOWN�¼�
				handler.sendMessage(Message.obtain(handler, EVENT_DOWN));
			}
			try {
				Thread.sleep(duration);
			} catch (InterruptedException ex) {
			}
		}
	}
}
