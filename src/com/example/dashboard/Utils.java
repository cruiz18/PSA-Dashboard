package com.example.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import fi.iki.elonen.NanoHTTPD.DefaultAsyncRunner;

import android.R.integer;
import android.R.string;
import android.database.CursorJoiner.Result;
import android.net.NetworkInfo.State;
import android.util.Log;

public class Utils {

	/****/
	public static int main_fuel_out = 10001;
	public static int main_air_num = 0;
	public static int main_air_flow_status = 1;
	public static int main_air_flow_speed = 100000;
	public static int main_air_status = 2;
	public static int music_action = 3;
	public static int music_search = 4;
	public static int music_next = 5;
	public static int music_volume = 6;
	public static int musci_now = 700000;
	public static int contact_search = 700001;
	public static int contact_action = 700002;
	public static int phone_coming = 7;
	public static int phone_reject = 8;
	public static int main_phone_calling = 9;
	public static int phone_callout = 900001;
	public static int phone_answer = 900002;
	public static int phone_strange_answer = 910002;
	public static int phone_input = 900003;
	public static int main_phone_msg = 900004;
	public static int radio_control = 900000;
	public static int radio_search = 1000000;
	public static int radio_start = 10;
	public static int radio_channel = 11;
	public static int radio_yes = 12;
	public static int radio_next = 13;
	public static int msg_send = 14;
	public static int msg_sure = 15;
	public static int msg_input = 150000;
	public static int msg_edit = 1500001;
	public static int msg_check = 1500002;
	public static int navi_last = 16;
	public static int radio_now = 17;
	public static int main_reset = 18;
	public static int navi_action = 19;
	
	
	
	static int default_air_max = 30;
	static int default_air_min = 16;
	public static boolean isPhoneIncoming = false;
	public static boolean isMsgIncoming = false;
	public static boolean isCallouting = false;
	public static boolean isNaviBegin = false;
	
	public static int comingOrCalling = -1;
	public static int strangeOrDial = -1;
	
	public static int airMaxTempNum = 30;
	public static int airMinTempNum = 16;
	public static int airMaxFlowNum = 15;
	public static int airMinFlowNum = 0;
	public static boolean isNaviFirstBegin = false;
	public static boolean isFuelOut = false;//$FALL-THROUGH$;
	public static int reset = 213123;
	/**
	 * 判断空调数值是否正确
	 * @return
	 */
	public static boolean isAirNumRight(int num){
		
		if (default_air_min< num && num < default_air_max) {
			return true;
		}
		return false;
	}
	
    static int xCount = 0;
	/**
	 * 判断手势方向
	 * @param stack
	 * @param x
	 * @return -1 不滑动；
	 * 			0 左滑动；
	 * 			1 右滑动；
	 * 		
	 */
	public static int sildeUtil(Stack<Float> stack,float x){
			if (xCount == 3 && stack.size() < 2) {
				stack.push(x);
				xCount = 0;
			}if (stack.size() == 2) {
				float x2 = stack.pop();
				float x1 = stack.pop();
				if (Math.abs(x2-x1) < 30) {
					//变化量太小
					stack.clear();
					return -1;
				}
				if (x2 - x1 > 0) {
					//说明此时向右滑动
					stack.clear();
					Log.e("mBottomss", "右");
					return 1;
				}else {
					stack.clear();
					Log.e("mBottomss", "左");
					return 0;
				}
			}
		xCount++;
		return -1;
	}
	
	/**
	 * 判断滑动slide 是否在视野内
	 * 滑动slide 为空调
	 * @param x
	 * @return
	 */
	public static boolean isBottomAirSlideInField(double x){
		
		if (x >100 && x<450) {
			return true;
		}
		return false;
	}
	public static boolean isTopMusicSlideInField(double x){
		if (x >100 && x<400) {
			return true;
		}
		return false;
	}
	
	public static boolean isCenterMusicSlideInField(double x){
		if (x >300 && x<1300) {
			return true;
		}
		return false;
	}
	
	public static boolean isBottomMusicSlideInField(double x){
		if (x >320 && x<1200) {
			return true;
		}
		return false;
	}
	
	public static boolean isRadioSlideInField(double x){
		if (x>200 && x<1200) {
			return true;
		}
		return false;
	}
	
	public static int leftOrRight(double x,double y){ 
		//进行位置的判断
		if ((50<x && x <600) && (y>200 && y<800)) {
			//说明在左侧
			return 0;
		}if ((x>1000 && x<1400) && (y>200 && y<800)) {
			//说明在右侧
			return 1;
		}if ((1000> x && x>500) &&(1200>y && y>900)) {
			return 2;
		}
		return -1;
	}
	
	static String [] string3 = new String[]{"JAZZ","FUNK","ROCK"}; //状态1
	static String [] string4 = new String[]{"ROCK","JAZZ","FUNK"}; //状态2
	static String [] string5 = new String[]{"FUNK","ROCK","JAZZ"}; //状态3
	static List<String[]> list2 = new ArrayList<String[]>();
	public static void centerMusicSlideInitial(){
		list2.add(string3);
		list2.add(string4);
		list2.add(string5);
	}
	public static String[] centerMusicSlide(int direction,String[] now){
		String [] result = null;
		//一共有0 1 2 三个状态
		if (direction == 0) {
			//左滑
			for (int i = 0; i < list2.size(); i++) {
				if (list2.get(i)[0].equals(now[0])) {
					//如果找到了当前状态
					switch (i) {
					case 0:
						//0左滑为2状态
						result = list2.get(2);
						break;
					case 1:
						//1左滑为0状态
						result = list2.get(0);
						break;
					case 2:
						//2左滑为1状态
						result = list2.get(1);
						break;
					default:
						break;
					}
				}
			}
			
		}if (direction == 1) {
			//右滑
			for (int i = 0; i < list2.size(); i++) {
				if (list2.get(i)[0].equals(now[0])) {
					//如果找到了当前状态
					switch (i) {
					case 0:
						//0右滑为1状态
						result = list2.get(1);
						break;
					case 1:
						//1右滑为2状态
						result = list2.get(2);
						break;
					case 2:
						//2右滑为0状态
						result = list2.get(0);
						break;
					default:
						break;
					}
				}
			}
		}
		return result;
	}
	
	static String [] string0 = new String[]{"环保","舒适","自动"}; //状态1
	static String [] string1 = new String[]{"自动","环保","舒适"}; //状态2
	static String [] string2 = new String[]{"舒适","自动","环保"}; //状态3
	static List<String[]>list1 = new ArrayList<String[]>();
	public static void bottomAirSlideInitial(){
		list1.add(string0);
		list1.add(string1);
		list1.add(string2);
	}
	public static String[] bottomAirSlide(int direction,String[] now){
		String [] result = null;
		//一共有0 1 2 三个状态
		if (direction == 0) {
			//左滑
			for (int i = 0; i < list1.size(); i++) {
				Log.e("list", String.valueOf(list1.get(i).length));
				if (list1.get(i)[0].equals(now[0])) {
					//如果找到了当前状态
					switch (i) {
					case 0:
						//0左滑为2状态
						result = list1.get(2);
						break;
					case 1:
						//1左滑为0状态
						result = list1.get(0);
						break;
					case 2:
						//2左滑为1状态
						result = list1.get(1);
						break;
					default:
						break;
					}
				}
			}
			
		}if (direction == 1) {
			//右滑
			for (int i = 0; i < list1.size(); i++) {
				if (list1.get(i)[0].equals(now[0])) {
					//如果找到了当前状态
					switch (i) {
					case 0:
						//0右滑为1状态
						result = list1.get(1);
						break;
					case 1:
						//1右滑为2状态
						result = list1.get(2);
						break;
					case 2:
						//2右滑为0状态
						result = list1.get(0);
						break;
					default:
						break;
					}
				}
			}
		}
		return result;
	}
	
	public static String[] radioSlide(int direction,String [] now){
		String [] result = null;
		if (direction == 0) {
			//左
			 Log.e("now[0]", now[0]);
			 float a1 = (float) (Float.valueOf(now[0])-0.1);
			 float a2 = (float) (Float.valueOf(now[0])-0.1);
			 float a3 = (float) (Float.valueOf(now[0])-0.1);
			 float a4 = (float) (Float.valueOf(now[0])-0.1);
			 float a5 = (float) (Float.valueOf(now[0])-0.1);
			 Log.e("a1", String.valueOf(a1));
			 result = new String[5];
			 result[0] = getSubStr(String.valueOf(a1));
			 result[1] = getSubStr(String.valueOf(a2));
			 result[2] = getSubStr(String.valueOf(a3));
			 result[3] = getSubStr(String.valueOf(a4));
			 result[4] = getSubStr(String.valueOf(a5));
		}if (direction == 1) {
			//右
			float a1 = (float) (Float.valueOf(now[0]) + 0.1);
			float a2 = (float) (Float.valueOf(now[0]) + 0.1);
			float a3 = (float) (Float.valueOf(now[0]) + 0.1);
			float a4 = (float) (Float.valueOf(now[0]) + 0.1);
			float a5 = (float) (Float.valueOf(now[0]) + 0.1);
			result = new String[5];
			result[0] = getSubStr(String.valueOf(a1));
			result[1] = getSubStr(String.valueOf(a2));
			result[2] = getSubStr(String.valueOf(a3));
			result[3] = getSubStr(String.valueOf(a4));
			result[4] = getSubStr(String.valueOf(a5));
		}
		return result;
	}
	
	private static  String getSubStr(String str){
		char tmp;
		String result = str;
		if (str.length() > 3) {
			result = str.substring(0,4);
		}
		return result;
	}
	
	public static String getMusicID(String name){
		
		String id = "0";
		if (name.equals("安静")) {
			id = "0";
		}
		if (name.equals("残酷月光")) {
			id = "1";
		}
		if (name.equals("传奇")) {
			id = "2";
		}
		if (name.equals("好久不见")) {
			id = "3";
		}
		if (name.equals("龙卷风")) {
			id = "4";
		}
		return id;
	}
}
