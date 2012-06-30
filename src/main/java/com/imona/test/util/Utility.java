package com.imona.test.util;

import java.util.Random;

public class Utility {
	public static int createRandomId(){
		
		Random randomGenerator = new Random();
		
		int randomInt = randomGenerator.nextInt(10000);
		
		return randomInt;
	}
}
