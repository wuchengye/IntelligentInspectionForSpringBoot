package com.bda.bdaqm.util;

public class MapUtils {

	public static int getSuitableSize(int size){
		int result = 16;
		size = size * 4 / 3;
		while(result < size){
			result *= 2;
		}
		return result;
	}
	
}
