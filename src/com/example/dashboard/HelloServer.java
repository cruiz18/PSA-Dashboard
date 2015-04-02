package com.example.dashboard;

import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.ServerRunner;

/**
 * An example of subclassing NanoHTTPD to make a custom HTTP server.
 */
public class HelloServer extends NanoHTTPD {
    Handler mHandler;
	public HelloServer() {
        super(8080);
        Log.e("HelloServer", "new");
    }

    @Override 
    public Response serve(IHTTPSession session) {
        Log.e("serve", "started");
    	Method method = session.getMethod();
        String uri = session.getUri();
		Log.e("server", method + " '" + uri + "' ");
		String localMsg = "not find";
		/** Music **/
		
		if (uri.equals("/control/fuel/out")) {
			String msg = "/control/fuel/out";
			Message message = new Message();
			Bundle bundle = new Bundle();
			message.what = Utils.main_fuel_out;// 匹配handler
			
			if (mHandler != null) {
				mHandler.sendMessage(message);
				msg = "/config/reset yes";
			}
			return new NanoHTTPD.Response(msg);	
		}
		
		if (uri.equals("/config/reset")) {
			String msg = "/config/reset no";
			Message message = new Message();
			Bundle bundle = new Bundle();
			message.what = Utils.main_reset;// 匹配handler
			if (mHandler != null) {
				mHandler.sendMessage(message);
				msg = "/config/reset yes";
			}
			return new NanoHTTPD.Response(msg);
		}
		
		// 2.2.4 音乐: 搜索歌曲
		if (uri.equals("/control/music/search")) {
			String msg = "/control/music/search no";
			Message message = new Message();
			message.what = Utils.music_search;
			message.obj = session.getParms().get("key");
			if (mHandler != null) {
				mHandler.sendMessage(message);
				msg = "/control/music/search yes";
			}
			return new NanoHTTPD.Response(msg);
		}
		// 2.2.5 音乐：当前播放歌曲
		if (uri.equals("/control/music/now")) {
			String msg = "/control/music/now no";
			Message message = new Message();
			message.what = Utils.musci_now;
			message.obj = session.getParms().get("name");
			if (mHandler != null) {
				mHandler.sendMessage(message);
				msg = "/control/music/now yes";
			}
			return new NanoHTTPD.Response(msg);
		}

		// 2.2.6 收音机：控制
		if (uri.equals("/control/radio/control")) {
			String msg = "/control/radio/control no";
			Message message = new Message();
			message.what = Utils.radio_control;
			Bundle bundle = new Bundle();
			bundle.putString("action", session.getParms().get("action"));
			bundle.putString("radioType", session.getParms().get("radioType"));
			message.obj = bundle;
			if (mHandler != null) {
				mHandler.sendMessage(message);
				msg = "/control/radio/control yes";
			}
			return new NanoHTTPD.Response(msg);
		}
		// 2.2.8 收音机：搜索预存频道
		if (uri.equals("/control/radio/search")) {
			String msg = "/control/radio/search no";
			Message message = new Message();
			message.what = Utils.radio_search;
			if (mHandler != null) {
				mHandler.sendMessage(message);
				msg = "/control/radio/search yes";
			}
			return new NanoHTTPD.Response(msg);
		}
		
		if (uri.equals("/control/radio/now")) {
			String msg = "/control/radio/now no";
			Message message = new Message();
			message.what = Utils.radio_now;
			message.obj = session.getParms().get("frequency");
			if (mHandler != null) {
				mHandler.sendMessage(message);
				msg = "/control/radio/now yes";
			}
			return new NanoHTTPD.Response(msg);
		}
		
        //2.3.1 通讯录：通知显示通讯录列表
        if (uri.equals("/control/contacts/list")) {
			
		}
        //2.3.2 通讯录：搜索通讯录列表：cc收到后会发出声音：确认拨打给xxx
        if (uri.equals("/control/contacts/search")) {
			
		}
        
        //2.3.2 通讯录：操作：上下选定
        if (uri.equals("")) {
			
		}
        	//2.6.5 导航：选择并确认路径action
      		if (uri.equals("/control/navigation/route/action")) {
      			String msg = "/control/navigation/route/action no";
      			Message message = new Message();
      			message.what = Utils.navi_action;
      			Utils.isNaviBegin = true;
      			return new NanoHTTPD.Response(msg);
      		}
        	//2.6.6 导航：同步导航信息
      		if (uri.equals("/control/navigation/sync")) {
      			String msg = "/control/navigation/sync no";
      			Message message = new Message();
      			Bundle bundle = new Bundle();
      			message.what = Utils.navi_last;
      			bundle.putString("speed", session.getParms().get("speed"));
      			bundle.putString("message", session.getParms().get("message"));
      			//bundle.putString("isLast", session.getParms().get("boolean"));
      			bundle.putString("distanceToNext",
      					session.getParms().get("distanceToNext"));
      			message.obj = bundle;// 传递的参数 我可以用bundle绑定
      			if (mHandler != null) {
      				mHandler.sendMessage(message);
      				msg = "/control/navigation/sync yes";
      			}
      			return new NanoHTTPD.Response(msg);
      		}
        
        return new NanoHTTPD.Response(localMsg); 
    }

    public void setHandler(Handler handler){
    	mHandler = handler;
    }
}
