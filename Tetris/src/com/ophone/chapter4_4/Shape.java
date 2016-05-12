package com.ophone.chapter4_4;

import java.util.Random;

public final class Shape {

	private static Random random = new Random();

	//当变换砖块形状时，查询此表
	private static final int[] NEXT = { 0, 2, 1, 4, 5, 6, 3, 8, 9, 10, 7, 12,
			13, 14, 11, 16, 15, 18, 17 };

	//Shape数组
	public static final Shape[] SHAPES = {
		  // 0号砖块, 下一个是0号
		  // OO
		  // OO
			new Shape(0, new int[] { 0, 0, 0, 0,
									 0, 1, 1, 0,
									 0, 1, 1, 0,
									 0, 0, 0, 0 }, 1, 1, 1, 1),

			// 1号砖块, 下一个是2号
			// OOOO
			new Shape(1, new int[] { 0, 0, 0, 0, 
									 1, 1, 1, 1,
									 0, 0, 0, 0, 
									 0, 0, 0, 0 }, 1, 0, 2, 0),

			// 2号砖块, 下一个是1号
			// O
			// O
			// O
			// O
			new Shape(2, new int[] { 0, 1, 0, 0,
									 0, 1, 0, 0,
									 0, 1, 0, 0, 
									 0, 1, 0, 0 }, 0, 2, 0, 1),

			// 3号砖块, 下一个是4号
			// O
			// O
			// OO
			new Shape(3, new int[] { 0, 1, 0, 0,
									 0, 1, 0, 0,
									 0, 1, 1, 0,
									 0, 0, 0, 0 }, 0, 1, 1, 1),

			// 4号砖块,下一个是5号
			// OOO
			// O
			new Shape(4, new int[] { 0, 0, 0, 0,
									 1, 1, 1, 0,
									 1, 0, 0, 0,
									 0, 0, 0, 0 }, 1, 1, 1, 0),

			// 5号砖块, 下一个是6号
			// OO
			//  O
			//  O
			new Shape(5, new int[] { 1, 1, 0, 0,
									 0, 1, 0, 0,
									 0, 1, 0, 0,
									 0, 0, 0, 0 }, 0, 2, 1, 0),

			// 6号砖块, 下一个是3号
			//   O
			// OOO
			new Shape(6, new int[] { 0, 0, 1, 0,
									 1, 1, 1, 0,
									 0, 0, 0, 0,
									 0, 0, 0, 0 }, 0, 1, 2, 0),

			// 7号砖块, 下一个是8号
			//  O
			//  O
			// OO
			new Shape(7, new int[] { 0, 1, 0, 0,
									 0, 1, 0, 0,
									 1, 1, 0, 0,
									 0, 0, 0, 0 }, 0, 2, 1, 0),

			// 8号砖块, 下一个是9号
			// O
			// OOO
			new Shape(8, new int[] { 1, 0, 0, 0,
									 1, 1, 1, 0,
									 0, 0, 0, 0,
									 0, 0, 0, 0 }, 0, 1, 2, 0),

			// 9号砖块, 下一个是10号
			// OO
			// O
			// O
			new Shape(9, new int[] { 0, 1, 1, 0,
									 0, 1, 0, 0,
									 0, 1, 0, 0,
									 0, 0, 0, 0 }, 0, 1, 1, 1),

			// 10号砖块, 下一个是7号
			// OOO
			//   O
			new Shape(10, new int[] { 0, 0, 0, 0,
									  1, 1, 1, 0,
									  0, 0, 1, 0,
									  0, 0, 0, 0 }, 1, 1, 1, 0),

			// 11号砖块, 下一个是12
			//  O
			// OOO
			new Shape(11, new int[] { 0, 1, 0, 0,
									  1, 1, 1, 0,
									  0, 0, 0, 0,
									  0, 0, 0, 0 }, 0, 1, 2, 0),

			// 12号砖块, 下一个是13
			// O
			// OO
			// O
			new Shape(12, new int[] { 0, 1, 0, 0,
									  0, 1, 1, 0,
									  0, 1, 0, 0,
									  0, 0, 0, 0 }, 0, 1, 1, 1),

			// 13号砖块, 下一个是14
			// OOO
			//  O
			new Shape(13, new int[] { 0, 0, 0, 0,
									  1, 1, 1, 0,
									  0, 1, 0, 0,
									  0, 0, 0, 0 }, 1, 1, 1, 0),

			// 14号砖块, 下一个是11
			//  O
			// OO
			//  O
			new Shape(14, new int[] { 0, 1, 0, 0,
									  1, 1, 0, 0,
									  0, 1, 0, 0,
									  0, 0, 0, 0 }, 0, 2, 1, 0),
			// shape of 15, next=16
			//  OO
			// OO
			new Shape(15, new int[] { 0, 0, 0, 0,
									  0, 1, 1, 0,
									  1, 1, 0, 0,
									  0, 0, 0, 0 }, 1, 1, 1, 0),
			// shape of 16, next=15
			// O
			// OO
			//  O
			new Shape(16, new int[] { 0, 1, 0, 0,
									  0, 1, 1, 0,
									  0, 0, 1, 0,
									  0, 0, 0, 0 }, 0, 1, 1, 1),

			// shape of 17, next=18
			// OO
			//  OO
			new Shape(17, new int[] { 0, 0, 0, 0,
									  1, 1, 0, 0,
									  0, 1, 1, 0,
									  0, 0, 0, 0 }, 1, 1, 1, 0),

			// shape of 18, next=17
			//  O
			// OO
			// O
			new Shape(18, new int[] { 0, 0, 1, 0,
									  0, 1, 1, 0,
									  0, 1, 0, 0,
									  0, 0, 0, 0 }, 0, 1, 1, 1) };

	//砖块的索引
	private int index;
	//4*4的二维数组
	private int[] data;
	//上下左右的margin
	private int marginTop;
	private int marginRight;
	private int marginBottom;
	private int marginLeft;

	private Shape(final int index, final int[] data, int mt, int mr, int mb,
			int ml) {
		this.index = index;
		this.data = data;
		this.marginTop = mt;
		this.marginRight = mr;
		this.marginBottom = mb;
		this.marginLeft = ml;
	}

	//随机生成一个砖块
	public static Shape random() {
		int index = (random.nextInt() >>> 1) % Shape.SHAPES.length;
		return Shape.SHAPES[index];
	}

	public int getIndex() {
		return index;
	}

	public int[] getData() {
		return data;
	}

	public int marginTop() {
		return marginTop;
	}

	public int marginLeft() {
		return marginLeft;
	}

	public int marginRight() {
		return marginRight;
	}

	public int marginBottom() {
		return marginBottom;
	}
	//下一个砖块
	public Shape next() {
		return SHAPES[NEXT[index]];
	}

}