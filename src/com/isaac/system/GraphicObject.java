package com.isaac.system;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class GraphicObject {

	private Bitmap bitmap;
	private int x;
	private int y;

	public GraphicObject(Bitmap bitmap) {
		this.bitmap = bitmap;
		x = 0;
		y = 0;
	}

	// 이미지 그리지
	public void Draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x, y, null);
	}

	// 좌표 설정
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
