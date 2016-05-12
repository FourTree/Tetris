package com.ophone.chapter4_4;

public interface GameListener {

	//游戏结束后调用此方法，参数为玩家的得分
	void gameOver(int mark);
	//删除行数
	void onRemove(int row);
}
