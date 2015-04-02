package com.example.dashboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;
import android.widget.BaseAdapter;

public class Http {

	//public static String base_url = "http://10.60.37.183/psa/api";
	public static String base_url = "http://192.168.1.100/psa/api";
	
	public static String Register_url = base_url+"/config/register";
	
	
	
	public static String AirConStatus_url = base_url+"/control/aircon/status";
	public static String AirConTemperature_url = base_url+"/control/aircon/temperature";
	public static String AirConFlow_url = base_url+"/control/aircon/flow";
	
	public static String AudioVolume_url = base_url+"/control/audio/volume";
	public static String AudioPlaying_url = base_url+"/control/audio/playing";
	public static String MusicControl_url = base_url+"/control/music/control";
	public static String MusicNow_url = base_url+"/control/music/now";
	
	public static String radioControl_url = base_url+"/control/radio/control";
	public static String radioNow_url = base_url+"/control/radio/now";
	
	public static String contactsList_url = base_url+"/control/contacts/list";
	public static String teleCallin_url = base_url+"/control/telephone/callin";
	public static String teleCallout_url = base_url+"/control/telephone/callout";
	public static String teleNow_url = base_url+"/control/telephone/now";
	
	public static String addressSearch_url = base_url+"/control/address/search";
	public static String postNavigationDest_url = base_url+"/control/navigation/dest";
	public static String getNavigationDest_url = base_url+"/control/navigation/dest";
	public static String postNavigationSync_url = base_url+"/control/navigation/sync";
	public static String getNavigationSync_url = base_url+"/control/navigation/sync";
	public static String actionToMapServer_url = base_url+"/control/navigation/actionToMapServer";
	/**1. Config APIs**/
	
	/**
	 * 注册客户端
	 * @param ip 本机ip
	 * @param port 本机端口号
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void Register(String ip,String port) throws ClientProtocolException, IOException{
		List<BasicNameValuePair> mList = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair pair1 = new BasicNameValuePair("ip",ip);
		BasicNameValuePair pair2 = new BasicNameValuePair("port",port);
		BasicNameValuePair pair3 = new BasicNameValuePair("clientType", "dashboard");
		mList.add(pair1);
		mList.add(pair2);
		mList.add(pair3);
		Log.e("?", "reg");
		BasicHttp.getResponseforPost(Register_url, mList);
	}
	
	
	/**2. Control APIs**/
	
	/**
	 * 2.1.1开启/关闭空调
	 * @param status
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String AirConStatusPost(String status) throws ClientProtocolException, IOException{
		String result;
		List<BasicNameValuePair> mList = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair pair1 = new BasicNameValuePair("status",status);
		mList.add(pair1);
		result = BasicHttp.getResponseforPost(AirConStatus_url, mList);
		return result;
	}
	
	/**
	 * 2.1.2获取空调开关状态
	 * @return status
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String AirConStatusGet() throws ClientProtocolException, IOException{
		String result;
		result = BasicHttp.getResponseForGet(AirConStatus_url);
		return result;
	}
	
	/*2.1.3设置目标温度*/
	public static String AirConTemperaturePost(String temperature) throws ClientProtocolException, IOException{
		String temp;
		List<BasicNameValuePair> postTempList = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair postTempPair = new BasicNameValuePair("temperature",temperature);
		postTempList.add(postTempPair);
		temp = BasicHttp.getResponseforPost(AirConTemperature_url, postTempList);
		return temp;
		
	}
	
	/*2.1.4获取目标温度*/
	
	public static String AirConTemperatureGet() throws ClientProtocolException, IOException{
		String temp;
		temp = BasicHttp.getResponseForGet(AirConTemperature_url);
		return temp;

	}
	
	/*2.1.5空调送风*/
	
	public static String AirConFlowPost(String status)throws ClientProtocolException, IOException{
		String result;
		List<BasicNameValuePair> flowPostList = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair flowPost = new BasicNameValuePair("status",status);
		flowPostList.add(flowPost);
		result = BasicHttp.getResponseforPost(AirConFlow_url, flowPostList);
		return result;
	}
	
	
	/*2.1.6获取送风状态*/
	public static String AirConFlowGet() throws ClientProtocolException, IOException{
		String result;
		result = BasicHttp.getResponseForGet(AirConFlow_url);
		return result;
	}
	
	
	/*2.2.1设置音量*/
	public static String AudioVolumePost(String volume)throws ClientProtocolException, IOException{
		String vol;
		List<BasicNameValuePair> postAudioVolumeList = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair postAudioVolumePair = new BasicNameValuePair("volume",volume);
		postAudioVolumeList.add(postAudioVolumePair);
		vol = BasicHttp.getResponseforPost(AudioVolume_url, postAudioVolumeList);
		return vol;
	}
	
	/*2.2.2获取音量*/
	public static String AudioVolumeGet() throws ClientProtocolException,IOException{
		String vol;
		vol = BasicHttp.getResponseForGet(AudioVolume_url);
		return vol;
	}
	
	
	/*2.2.2获取播放状态*/
	
	public static String AudioPlayingGet() throws ClientProtocolException,IOException{
		String result;
		result = BasicHttp.getResponseForGet(AudioPlaying_url);
		return result;
	}
	
	
	/*2.2.3音乐控制*/
	public static String musicControlPost(String action) throws ClientProtocolException,IOException {
		String act;
		List<BasicNameValuePair> postMusicControlList = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair postMusicControlPair = new BasicNameValuePair("action",action);
		postMusicControlList.add(postMusicControlPair);
		act = BasicHttp.getResponseforPost(MusicControl_url, postMusicControlList);
		return act;
	}
	
	
	/*2.2.4获取歌曲列表&搜索歌曲  现在没有了*/
	
	
	/*2.2.5获取当前播放歌曲*/
	public static String musicNowGet() throws ClientProtocolException,IOException {
		String songNow;
		songNow = BasicHttp.getResponseForGet(MusicNow_url);
		return songNow;
		
	}
	
	/*2.2.6收音机控制*/
	
	public static String radioControlPost(String action) throws ClientProtocolException,IOException {
		String act;
		List<BasicNameValuePair> postradioControlList = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair postRadioControlPair = new BasicNameValuePair("action",action);
		postradioControlList.add(postRadioControlPair);
		act = BasicHttp.getResponseforPost(radioControl_url, postradioControlList);
		return act;
	}
	
	
	/*2.2.7  2.2.8 这个功能有吗？*/
	
	/*2.2.9获取当前播放频道*/
	public static String radioNowGet(String Radio) throws ClientProtocolException,IOException {
		String radioNow;
		radioNow = BasicHttp.getResponseForGet(radioNow_url);
		return radioNow;
		
	}
	
	
	
	/*2.3.1获取通讯录列表*/
	public static String contactsListGet() throws ClientProtocolException, IOException{
		String contact;
		contact = BasicHttp.getResponseForGet(contactsList_url);
		return contact;
	}
	
	/*2.3.2搜索通讯录列表*/
	public static String contactsSearchGet() throws ClientProtocolException, IOException{
		String contact;
		contact = BasicHttp.getResponseForGet(contactsList_url);
		return contact;
	}
	
	/*2.4.1模拟呼入响铃*/
	public static void telephonecallinPost(String name,String number) throws ClientProtocolException,IOException{
		//String teleName;
		//String teleNumber;
		List<BasicNameValuePair> telecallinList = new ArrayList<BasicNameValuePair>();
		
		BasicNameValuePair teleCallinPair1 = new BasicNameValuePair("name",name);
		BasicNameValuePair teleCallinPair2 = new BasicNameValuePair("number",number);
		telecallinList.add(teleCallinPair1);
		telecallinList.add(teleCallinPair2);
		
		BasicHttp.getResponseforPost(teleCallin_url, telecallinList);
	
	}
	
	/*2.4.2电话呼出*/
	public static void telephonecalloutPost(String name,String number) throws ClientProtocolException,IOException{
		//String teleName;
		//String teleNumber;
		List<BasicNameValuePair> telecalloutList = new ArrayList<BasicNameValuePair>();
		
		BasicNameValuePair teleCalloutPair1 = new BasicNameValuePair("name",name);
		BasicNameValuePair teleCalloutPair2 = new BasicNameValuePair("number",number);
		telecalloutList.add(teleCalloutPair1);
		telecalloutList.add(teleCalloutPair2);
		
		BasicHttp.getResponseforPost(teleCallout_url, telecalloutList);
	
	}
	
	/*2.4.3电话 接听挂机*/
	public static String telephoneAnswerPost(String answer) throws ClientProtocolException,IOException{
		String result;
		List<BasicNameValuePair> teleAnswerList = new ArrayList<BasicNameValuePair>();
		
		BasicNameValuePair teleAnswerPair = new BasicNameValuePair("answer",answer);
		teleAnswerList.add(teleAnswerPair);
		result = BasicHttp.getResponseforPost(teleCallout_url, teleAnswerList);
		return result;
	
	}
	
	/*2.4.4当前通话状态*/
	public static String telephoneNowGet()throws ClientProtocolException,IOException{
		String result;
		result = BasicHttp.getResponseForGet(teleNow_url);
		return result;
	}
	
	/*2.6.1获取地址薄列表？是什么意思*/
	
	/*2.6.2搜索地址薄列表*/
	public String addressSearchGet() throws ClientProtocolException,IOException{
		String result;
		result = BasicHttp.getResponseForGet(addressSearch_url);
		return result;
	}
	
	
	
	/*2.6.3 导航：设置目的地*/
	public static String navigationDestPost(String addressIndex) throws ClientProtocolException,IOException{
		String result;
		List<BasicNameValuePair> navigationDestList = new ArrayList<BasicNameValuePair>();
		
		BasicNameValuePair navigationDestPair = new BasicNameValuePair("addressIndex",addressIndex);
		navigationDestList.add(navigationDestPair);
		result = BasicHttp.getResponseforPost(teleCallout_url, navigationDestList);
		return result;
	
	}
	

	/*2.6.4 导航：获取目的地*/
	public String navigationDestGet() throws ClientProtocolException,IOException{
		String result;
		result = BasicHttp.getResponseForGet(getNavigationDest_url);
		return result;
	
	}
	
	
	/*2.6.5 导航：同步导航信息*/
	public static void navigationSync(String speed,String message,String isLast,String x,String y,String distanceToNext) 
			throws ClientProtocolException, IOException{
		List<BasicNameValuePair> navigationSyncList = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair pair1 = new BasicNameValuePair("speed",speed);
		BasicNameValuePair pair2 = new BasicNameValuePair("message",message);
		BasicNameValuePair pair3 = new BasicNameValuePair("isLast", isLast);
		BasicNameValuePair pair4 = new BasicNameValuePair("x", x);
		BasicNameValuePair pair5 = new BasicNameValuePair("y", y);
		BasicNameValuePair pair6 = new BasicNameValuePair("distanceToNext", distanceToNext);
		
		navigationSyncList.add(pair1);
		navigationSyncList.add(pair2);
		navigationSyncList.add(pair3);
		navigationSyncList.add(pair4);
		navigationSyncList.add(pair5);
		navigationSyncList.add(pair6);
		
		BasicHttp.getResponseforPost(postNavigationSync_url, navigationSyncList);
	}
	
	
	
	
	/*2.6.6 导航：获取同步导航信息*/
	public String navigationSyncGet() throws ClientProtocolException,IOException{
		String result;
		result = BasicHttp.getResponseForGet(getNavigationSync_url);
		return result;

	}
	
	
	/*2.6.7 导航：发送动作到地图服务器*/
	public String navigationActionToMapServerPost(String actionId) throws ClientProtocolException,IOException{
		String action;
		List<BasicNameValuePair> navigationActionToMapServerList = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair navigationActionToMapServerPair = new BasicNameValuePair("actionId",actionId);
		navigationActionToMapServerList.add(navigationActionToMapServerPair);
		action = BasicHttp.getResponseforPost(actionToMapServer_url, navigationActionToMapServerList);
		return action;
		
	}
	
	
	
}
