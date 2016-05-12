package com.ophone.chapter4_4;

import java.util.Random;

public final class Shape {

	private static Random random = new Random();

	//���任ש����״ʱ����ѯ�˱�
	private static final int[] NEXT = { 0, 2, 1, 4, 5, 6, 3, 8, 9, 10, 7, 12,
			13, 14, 11, 16, 15, 18, 17 };

	//Shape����
	public static final Shape[] SHAPES = {
		  // 0��ש��, ��һ����0��
		  // OO
		  // OO
			new Shape(0, new int[] { 0, 0, 0, 0,
									 0, 1, 1, 0,
									 0, 1, 1, 0,
									 0, 0, 0, 0 }, 1, 1, 1, 1),

			// 1��ש��, ��һ����2��
			// OOOO
			new Shape(1, new int[] { 0, 0, 0, 0, 
									 1, 1, 1, 1,
									 0, 0, 0, 0, 
									 0, 0, 0, 0 }, 1, 0, 2, 0),

			// 2��ש��, ��һ����1��
			// O
			// O
			// O
			// O
			new Shape(2, new int[] { 0, 1, 0, 0,
									 0, 1, 0, 0,
									 0, 1, 0, 0, 
									 0, 1, 0, 0 }, 0, 2, 0, 1),

			// 3��ש��, ��һ����4��
			// O
			// O
			// OO
			new Shape(3, new int[] { 0, 1, 0, 0,
									 0, 1, 0, 0,
									 0, 1, 1, 0,
									 0, 0, 0, 0 }, 0, 1, 1, 1),

			// 4��ש��,��һ����5��
			// OOO
			// O
			new Shape(4, new int[] { 0, 0, 0, 0,
									 1, 1, 1, 0,
									 1, 0, 0, 0,
									 0, 0, 0, 0 }, 1, 1, 1, 0),

			// 5��ש��, ��һ����6��
			// OO
			//  O
			//  O
			new Shape(5, new int[] { 1, 1, 0, 0,
									 0, 1, 0, 0,
									 0, 1, 0, 0,
									 0, 0, 0, 0 }, 0, 2, 1, 0),

			// 6��ש��, ��һ����3��
			//   O
			// OOO
			new Shape(6, new int[] { 0, 0, 1, 0,
									 1, 1, 1, 0,
									 0, 0, 0, 0,
									 0, 0, 0, 0 }, 0, 1, 2, 0),

			// 7��ש��, ��һ����8��
			//  O
			//  O
			// OO
			new Shape(7, new int[] { 0, 1, 0, 0,
									 0, 1, 0, 0,
									 1, 1, 0, 0,
									 0, 0, 0, 0 }, 0, 2, 1, 0),

			// 8��ש��, ��һ����9��
			// O
			// OOO
			new Shape(8, new int[] { 1, 0, 0, 0,
									 1, 1, 1, 0,
									 0, 0, 0, 0,
									 0, 0, 0, 0 }, 0, 1, 2, 0),

			// 9��ש��, ��һ����10��
			// OO
			// O
			// O
			new Shape(9, new int[] { 0, 1, 1, 0,
									 0, 1, 0, 0,
									 0, 1, 0, 0,
									 0, 0, 0, 0 }, 0, 1, 1, 1),

			// 10��ש��, ��һ����7��
			// OOO
			//   O
			new Shape(10, new int[] { 0, 0, 0, 0,
									  1, 1, 1, 0,
									  0, 0, 1, 0,
									  0, 0, 0, 0 }, 1, 1, 1, 0),

			// 11��ש��, ��һ����12
			//  O
			// OOO
			new Shape(11, new int[] { 0, 1, 0, 0,
									  1, 1, 1, 0,
									  0, 0, 0, 0,
									  0, 0, 0, 0 }, 0, 1, 2, 0),

			// 12��ש��, ��һ����13
			// O
			// OO
			// O
			new Shape(12, new int[] { 0, 1, 0, 0,
									  0, 1, 1, 0,
									  0, 1, 0, 0,
									  0, 0, 0, 0 }, 0, 1, 1, 1),

			// 13��ש��, ��һ����14
			// OOO
			//  O
			new Shape(13, new int[] { 0, 0, 0, 0,
									  1, 1, 1, 0,
									  0, 1, 0, 0,
									  0, 0, 0, 0 }, 1, 1, 1, 0),

			// 14��ש��, ��һ����11
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

	//ש�������
	private int index;
	//4*4�Ķ�ά����
	private int[] data;
	//�������ҵ�margin
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

	//�������һ��ש��
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
	//��һ��ש��
	public Shape next() {
		return SHAPES[NEXT[index]];
	}

}