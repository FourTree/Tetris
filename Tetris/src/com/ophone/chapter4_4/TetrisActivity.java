package com.ophone.chapter4_4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class TetrisActivity extends Activity implements GameListener,
		Comparator<Score> {

	private Screen screen = null;
	private int mark;
	private ToneGenerator tone = null;
	//在内存中存储玩家排行榜，学完第六章可以修改为Content Provider
	private List<Score> marks = new ArrayList<Score>();

	public static final int SHOW_SCORE_INPUT = 0;
	public static final int SHOW_PLAYER_RANK = 1;
	public static final int SHOW_HELP = 2;

	public static final int OPTION_ITEM_RANK = 0;
	public static final int OPTION_ITEM_HELP = 1;
	public static final int OPTION_ITEM_EXIT = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (screen == null)
			screen = new Screen(this);
		screen.setGameListener(this);
		tone = new ToneGenerator(AudioManager.STREAM_MUSIC,
				ToneGenerator.MAX_VOLUME);
		setContentView(screen);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		tone.release();
	}

	//当电话等事件发生，暂停游戏
	@Override
	protected void onPause() {
		super.onPause();
		screen.pause(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	//当游戏结束，此方法被回调，输入玩家姓名
	public void gameOver(int mark) {
		this.mark = mark;
		showDialog(SHOW_SCORE_INPUT);
	}

	private void insert(String playerName) {
		Score score = new Score();
		score.name = playerName;
		score.score = this.mark;
		marks.add(score);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case SHOW_SCORE_INPUT: {
			//输入玩家姓名
			LayoutInflater inflater = LayoutInflater.from(this);
			final View view = inflater.inflate(R.layout.score_input, null);
			dialog = new AlertDialog.Builder(this).setView(view)
					.setPositiveButton(R.string.button_ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									//插入玩家姓名和得分
									EditText text = (EditText) view
											.findViewById(R.id.player_name);
									String name = text.getText().toString();
									insert(name);
								}
							}).setNegativeButton(R.string.button_cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									dismissDialog(SHOW_SCORE_INPUT);
								}
							}).create();
			break;
		}
		case SHOW_PLAYER_RANK: {
			//显示排行榜
			if (marks.size() > 0) {
				ArrayAdapter<Score> adapter = new ArrayAdapter<Score>(this,
						R.layout.score_item, marks);
				adapter.sort(this);
				dialog = new AlertDialog.Builder(this)
						.setAdapter(adapter, null).setTitle(
								R.string.option_rank).create();
			} else {
				dialog = new AlertDialog.Builder(this).setMessage(
						R.string.no_rank).create();
			}
			break;
		}
		case SHOW_HELP:
			//显示帮助信息
			dialog = new AlertDialog.Builder(this).setMessage(
					R.string.help_message).setTitle(R.string.option_help)
					.create();
			break;
		}
		return dialog;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, OPTION_ITEM_RANK, 0, R.string.option_rank);
		menu.add(0, OPTION_ITEM_HELP, 1, R.string.option_help);
		menu.add(0, OPTION_ITEM_EXIT, 2, R.string.quit);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemID = item.getItemId();
		switch (itemID) {
		case OPTION_ITEM_HELP:
			showDialog(SHOW_HELP);
			break;
		case OPTION_ITEM_RANK:
			showDialog(SHOW_PLAYER_RANK);
			return true;
		case OPTION_ITEM_EXIT:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onRemove(int row) {
		// 当有砖块删除，播放音效
		tone.startTone(ToneGenerator.TONE_PROP_BEEP2);
	}

	public int compare(Score object1, Score object2) {
		if (object1.score > object2.score)
			return -1;
		else if (object1.score < object2.score)
			return 1;
		else
			return 0;
	}

}