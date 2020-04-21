package com.bda.bdaqm.util;

import java.util.List;

import com.bda.bdaqm.electric.model.Statistics;

/**
 * 
 * @author 徐玮南
 * 有重复数值的二分查找算法
 */
public class BinarySerchUtils {
	
	public static int search(int target,List<Statistics> arr,int tag) {
		if(arr.size()==0) {
			return -1;
		}
		int left=0,right=arr.size()-1;
		while(left+1<right) {
			int mid=left+(right-left)/2;
			if(target>arr.get(mid).getUnitOrder()) {
				left=mid+1;
			}
			if(target<arr.get(mid).getUnitOrder()) {
				right=mid-1;
			}
			if(target==arr.get(mid).getUnitOrder()&&tag==0) {
				right=mid;
			}
			if(target==arr.get(mid).getUnitOrder()&&tag==1) {
				left=mid;
			}
		}
		if(arr.get(left).getUnitOrder()==arr.get(right).getUnitOrder()&&arr.get(right).getUnitOrder()==target&&left!=right) {
			return left+tag;
		}
		if(arr.get(left).getUnitOrder()==target) {
			return left;
		}
		if(arr.get(right).getUnitOrder()==target) {
			return right;
		}
		return -1;
	}
	
	

}
