package com.matchinggame.twooilyplumbers;

import android.graphics.Paint;

public enum NamedColor {
BLUE("Blue", 255, 0, 0, 255),
RED("Red", 255, 255, 0, 0),
GREEN("Green", 255, 0, 255, 0),
YELLOW("Yellow", 255, 255, 255, 0),
ORANGE("Orange", 255, 255, 165, 0),
PURPLE("Purple", 255, 75, 0, 130),
GRAY("Gray", 255, 128, 128, 128),
BLACK("Black", 255, 0, 0, 0),
GARNET("Garnet", 255, 156, 101, 104),
CRANBERRY("Cranberry", 255, 199, 83, 70),
DENIM("Denim", 255, 75, 82, 98),
TAFFY("Taffy", 255, 253, 200, 166),
BABYPINK("Baby Pink", 255, 252, 212, 210),
AMETHYST("Amethyst", 255, 198, 184, 219),
CHOCOLATE("Chocolate", 255, 140, 107, 88),
OLIVE("Olive", 255, 154, 156, 109),
HONEY("Honey", 255, 253, 189, 57),
HOTPINK("Hot Pink", 255, 255, 105, 180),
DARKRED("Dark Red", 255, 139, 0, 0),
CRIMSON("Crimson", 255, 255, 20, 63),
SALMON("Salmon", 255, 250, 128, 114),
GOLD("Gold", 255, 255, 215, 0),
LAVENDER("Lavender", 255, 230, 230, 250),
FUCHSIA("Fuchsia", 255, 255, 0, 255),
ROYALBLUE("Royal Blue", 255, 65, 105, 225),
TURQUOISE("Turquoise", 255, 64, 224, 208),
TEAL("Teal", 255, 0, 128, 128);


	private final String name;
	private final int value, a, r,g,b;
	NamedColor(String name, int a, int r, int g, int b){
		this.name = name;
		this.a = a;
		this.r = r;
		this.g = g;
		this.b = b;
		
		Paint paint = new Paint();
		paint.setARGB(a, r, g, b);
		
		//get the int value of the color
		this.value = paint.getColor();
	}
	public String toString(){
		return name;
	}
	public int getA(){return a;}
	public int getR(){return r;}
	public int getG(){return g;}
	public int getB(){return b;}
	public int getIntValue(){return value;}
	public boolean compare(String input){
		if(input.equals(name)){return true;}
		else{return false;}
	}
}
