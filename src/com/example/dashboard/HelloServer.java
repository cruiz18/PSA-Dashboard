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
			message.what = Utils.main_fuel_out;// ƥ��handler
			
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
			message.what = Utils.main_reset;// ƥ��handler
			if (mHandler != null) {
				mHandler.sendMessage(message);
				msg = "/config/reset yes";
			}
			return new NanoHTTPD.Response(msg);
		}
		
		// 2.2.4 ����: ��������
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
		// 2.2.5 ���֣���ǰ���Ÿ���
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

		// 2.2.6 ������������
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
		// 2.2.8 ������������Ԥ��Ƶ��
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
		
        //2.3.1 ͨѶ¼��֪ͨ��ʾͨѶ¼�б�
        if (uri.equals("/control/contacts/list")) {
			
		}
        //2.3.2 ͨѶ¼������ͨѶ¼�б�cc�յ���ᷢ��������ȷ�ϲ����xxx
        if (uri.equals("/control/contacts/search")) {
			
		}
        
        //2.3.2 ͨѶ¼������������ѡ��
        if (uri.equals("")) {
			
		}
        	//2.6.5 ������ѡ��ȷ��·��action
      		if (uri.equals("/control/navigation/route/action")) {
      			String msg = "/control/navigation/route/action no";
      			Message message = new Message();
      			message.what = Utils.navi_action;
      			Utils.isNaviBegin = true;
      			return new NanoHTTPD.Response(msg);
      		}
        	//2.6.6 ������ͬ��������Ϣ
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
      			message.obj = bundle;// ���ݵĲ��� �ҿ�����bundle��
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
