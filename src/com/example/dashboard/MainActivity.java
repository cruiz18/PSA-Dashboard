package com.example.dashboard;



import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.http.client.ClientProtocolException;

import android.R.drawable;
import android.R.integer;
import android.R.menu;
import android.R.string;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class MainActivity extends Activity implements OnClickListener {
	private TextView textDashSpeed;
	private TextView textDashSpeedUnit;
	private TextView textDashFunction;
	private TextView textDashGear;
	private TextView radioRightText;
	private TextView radioLeftText;
	private TextView radioMidText;
	private TextView naviDirection;
	private TextView naviDistance;
	private TextView naviRightDistance;
	private TextView musicArtistText;
	private TextView musicTitleText;
	private TextView turnGuideText;
	private TextView naviDisKmText;
	private ImageView dashNaviComprass;
	private ImageView dashNormalLeft;
	private ImageView dashBigLeft;
	private ImageView dashFirstMidIcon;
	private ImageView naviIcon;
	private RelativeLayout dashNormalRight;
	private RelativeLayout dashBigRight;
	private RelativeLayout dashRadioRight;
	private RelativeLayout dashMid;
	private RelativeLayout naviLayout;
	//private RelativeLayout naviRightLayout;
	private RelativeLayout musicRightLayout;
	
	private Button dashSelectBtn;
	private Typeface dashNeLi;
	
	private int count;
	private RelativeLayout dashIcon;
	
	//测试按钮
	private Button testNavi;
	private Button testMusic;
	private Button testRadio;
	private Button testPhone;
	private HelloServer server;
	RegisterTask mRegisterTask;
	boolean hasFinished = true;
	public static int Music_Search = 0;
	public static int Music_Now = 1;
	public static int Radio_Handler = 2;
	private int lastMsg = -1;
	private ImageView fuelOut;

	Handler mFuelHandler = new Handler(){
	
		public void handleMessage(android.os.Message msg) 
		{
			if(msg.what == 1){
				//fuelOut.setAlpha(1f);
			}if (msg.what == 0) {
				//fuelOut.setAlpha(0.3f);
				
			}if (msg.what == -1) {
				//fuelOut.setVisibility(View.INVISIBLE);
			}
		};
	};
	
	Handler mMainHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			if (msg.what == Utils.main_fuel_out) {
		
				if (Utils.isFuelOut == false) {

					fuelOut.setVisibility(View.VISIBLE);
					Utils.isFuelOut = true;
				}else {
					fuelOut.setVisibility(View.INVISIBLE);
					Utils.isFuelOut = false;
				}
				Message message = new Message();
				/**for (int i = 0; i < 10; i++) {
					if (i % 2 == 0) {
						message.what = 1;
						mFuelHandler.sendMessageDelayed(message, 500);
					}else {
						message.what = 0;
						mFuelHandler.sendMessageDelayed(message, 500);
					}
					if (i == 10) {
						message.what = -1;
						mFuelHandler.sendMessageDelayed(message,500);
					}
				}**/
			}
			
			if (msg.what == Utils.music_search) {
				String name = (String)msg.obj;
				name = getNameById(name);
				musicArtistText.setText(getSingerByName(name));
				musicTitleText.setText(name);
				musicShow();
				
			}if (msg.what == Utils.musci_now) {
				String name = (String)msg.obj;
				name = getNameById(name);
				musicArtistText.setText(getSingerByName(name));
				musicTitleText.setText(name);
				musicShow();
				
			}
			if (msg.what == Utils.radio_now) {
				String result = (String)msg.obj;
				Log.e("radio now", result);
				radioMidText.setText(findRadioByIndex(Integer.valueOf(result)));
				radioShow();
			}
			
			if (msg.what == 321) {
				Bundle result = (Bundle) msg.obj;
				String action = result.getString("action");
				Log.e("handler", action);
				if (action.equals("play")) {
					radioShow();
				}
				if (action.equals("pause")) {
					//pause()
					radioShow();
				}
				if (action.equals("next")) {
					String now = radioMidText.getText().toString();
					int index = findRadioIndex(now);
					index ++;
					if (index > 4) {
						index = 0;
					}
					radioMidText.setText(findRadioByIndex(index));
					radioShow();
				}
				if (action.equals("previous")) {
					String now = radioMidText.getText().toString();
					int index = findRadioIndex(now);
					index --;
					if (index < 0) {
						index = 4;
					}
					radioMidText.setText(findRadioByIndex(index));
					radioShow();
				}
				
				
			}if (msg.what == Utils.radio_search) {
				radioMidText.setText("101.7");
			}
			
			if (msg.what == Utils.main_reset) {
				//Intent mIntent = new Intent(MainActivity.this,MainActivity.class);
				//mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//startActivity(mIntent);
				//MainActivity.this.finish();
				musicShow();
				dashNaviComprass.setImageResource(R.drawable.straight);
			}
			
			if (msg.what == Utils.navi_last) {
				Bundle data = (Bundle)msg.obj;
				int message = Integer.valueOf(data.getString("message"));
				String speed = data.getString("speed");
				String distance = data.getString("distanceToNext");
				char [] tmp1 = distance.toCharArray();
				int index1 = 0;
				for (int i = 0; i < tmp1.length; i++) {
					index1++;
					if (tmp1[i] == '.') {
						break;
					}
				}
				distance = distance.substring(0,index1-1);
				if (Integer.valueOf(distance) > 1000) {
					//转换为km
					int tmpDis = Integer.valueOf(distance);
					int tmpDis1 = tmpDis / 1000; //获得km
					int tmpDis2 = (tmpDis - 1000);
					tmpDis2 = tmpDis2 / 100;  //获得百米
					distance = String.valueOf(tmpDis1)+"."
							+String.valueOf(tmpDis2);
					naviDistance.setText(distance);
					naviDisKmText.setText("千米");
				}else {
					naviDistance.setText(distance);
					naviDisKmText.setText("米");
				}
				//naviDistance.setText(distance);
				
				
				char [] tmp = speed.toCharArray();
				int index = 0;
				for (int i = 0; i < tmp.length; i++) {
					index++;
					if (tmp[i] == '.') {
						break;
					}
				}
				speed = speed.substring(0,index-1);
				int tmpS = Integer.valueOf(speed);
				if (tmpS <0) {
					tmpS = 0;
				}
				speed = String.valueOf(tmpS);
				textDashSpeed.setText(String.valueOf(speed));
				if (message == 0) {
					Utils.isNaviBegin = true;
					naviShow();
				}
				if (Utils.isNaviBegin) {
					naviDistance.setText(distance);
					naviShow();
					if (lastMsg != message) {
						lastMsg = message;
						int tmpMsg = lastMsg % 1000;
						int tmpDir = lastMsg / 1000;
					Log.e("navi", String.valueOf(message));
					naviDirection.setVisibility(View.INVISIBLE);

					switch (tmpMsg) {
					case 101:
						turnGuideText.setText("直行");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_straight_hl);
						dashNaviComprass.setImageResource(R.drawable.straight);
						break;
					case 102:
						turnGuideText.setText("右转");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_right_hl);
						dashNaviComprass.setImageResource(R.drawable.right);
						
						break;
					case 103:
						turnGuideText.setText("右转上高架");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_right_hl);
						dashNaviComprass.setImageResource(R.drawable.right);
						break;
					case 104:
						turnGuideText.setText("保持直行");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_straight_hl);
						dashNaviComprass.setImageResource(R.drawable.straight);

						break;
					case 105:
						turnGuideText.setText("靠右行驶");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_right_hl);
						dashNaviComprass.setImageResource(R.drawable.right);
						
						break;
					case 106:
						turnGuideText.setText("前方左侧汇入车辆");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_left_hl);
						dashNaviComprass.setImageResource(R.drawable.left);
						break;
					case 107:
						turnGuideText.setText("靠右行驶");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_right_hl);
						dashNaviComprass.setImageResource(R.drawable.right);
						
						break;
					case 108:
						turnGuideText.setText("靠右下高架");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_right_hl);
						
						dashNaviComprass.setImageResource(R.drawable.right);
						
						break;
					case 109:
						turnGuideText.setText("保持直行");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_straight_hl);
						dashNaviComprass.setImageResource(R.drawable.straight);
						
						break;
					case 110:
						turnGuideText.setText("保持直行");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_straight_hl);
						dashNaviComprass.setImageResource(R.drawable.straight);
						
						break;
					case 111:
						turnGuideText.setText("靠右行驶");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_right_hl);
						dashNaviComprass.setImageResource(R.drawable.right);
						
						break;
					case 112:
						turnGuideText.setText("前方右转");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_right_hl);
						dashNaviComprass.setImageResource(R.drawable.right);
						
						break;
					case 113:
						turnGuideText.setText("保持直行");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_straight_hl);
						dashNaviComprass.setImageResource(R.drawable.straight);
						
						break;
					case 114:
						turnGuideText.setText("保持直行");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_straight_hl);
						dashNaviComprass.setImageResource(R.drawable.straight);
						
						break;
					case 201:
						turnGuideText.setText("保持直行");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_straight_hl);
						dashNaviComprass.setImageResource(R.drawable.straight);
						
						break;
					case 202:
						turnGuideText.setText("前方左转");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_left_hl);
						dashNaviComprass.setImageResource(R.drawable.left);
						
						break;
					case 203:
						turnGuideText.setText("直走");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_straight_hl);
						dashNaviComprass.setImageResource(R.drawable.straight);
						
						break;
					case 204:
						turnGuideText.setText("保持直行");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_straight_hl);
						dashNaviComprass.setImageResource(R.drawable.straight);
						break;
					case 205:
						turnGuideText.setText("直走");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_straight_hl);
						dashNaviComprass.setImageResource(R.drawable.straight);
						break;
					case 206:
						turnGuideText.setText("前方右上高架");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_right_hl);
						dashNaviComprass.setImageResource(R.drawable.right);
						
						break;
					case 207:
						turnGuideText.setText("靠右行驶");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_right_hl);
						dashNaviComprass.setImageResource(R.drawable.right);
						
						break;
					case 208:
						turnGuideText.setText("直走");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_straight_hl);
						dashNaviComprass.setImageResource(R.drawable.straight);
						
						break;
					case 209:
						turnGuideText.setText("靠右下高架");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_right_hl);
						dashNaviComprass.setImageResource(R.drawable.right);
						
						break;
					case 210:
						turnGuideText.setText("靠右下高架");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_right_hl);
						dashNaviComprass.setImageResource(R.drawable.right);
						
						break;
					case 211:
						turnGuideText.setText("保持直行");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_straight_hl);
						dashNaviComprass.setImageResource(R.drawable.straight);
						
						break;
					case 212:
						
						turnGuideText.setText("保持直行");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_straight_hl);
						dashNaviComprass.setImageResource(R.drawable.straight);
						break;
					case 301:
						
						turnGuideText.setText("前方左转");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_left_hl);
						dashNaviComprass.setImageResource(R.drawable.left);
						
						break;
					case 302:
						
						turnGuideText.setText("保持直行");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_straight_hl);
						dashNaviComprass.setImageResource(R.drawable.straight);
						break;
					case 303:
						
						turnGuideText.setText("保持直行");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_straight_hl);
						dashNaviComprass.setImageResource(R.drawable.straight);
						break;
					case 304:
						
						turnGuideText.setText("保持直行");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_straight_hl);
						dashNaviComprass.setImageResource(R.drawable.straight);
						break;
					case 305:
						
						turnGuideText.setText("右转");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_right_hl);
						dashNaviComprass.setImageResource(R.drawable.right);
						
						break;
					case 306:
						
						turnGuideText.setText("直走");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_straight_hl);
						dashNaviComprass.setImageResource(R.drawable.straight);
						break;
					case 307:
						turnGuideText.setText("直走");
						naviIcon.setBackgroundResource(R.drawable.navi_turn_straight_hl);
						dashNaviComprass.setImageResource(R.drawable.straight);
						break;
						
					}
					switch (tmpDir) {
					case 1:
						
						break;
					case 2:
						break;
					case 3:
						break;
					case 4:
						break;
					default:
						break;
					}
				  }
				else {
					
				}	
				}
			}
			
		};
	};
	
	private void fuelout(){
		
	}
	
	private int findRadioIndex(String name){
		int a = 0;
		if (name.equals("87.9")) {
			a = 0; 
		}if (name.equals("94.0")) {
			a = 1;
		}if (name.equals("99.0")) {
			a = 2;
		}if (name.equals("101.7")) {
			a = 3;
		}if (name.equals("103.7")) {
			a = 4;
		}
		return a ;
	}
	
	private String findRadioByIndex(int index){
		String tmp = "87.9";
		switch (index) {
		case 0:
			tmp = "87.9";
			break;
		case 1:
			tmp = "94.0";
			break;
		case 2:
			tmp = "99.0";
			break;
		case 3:
			tmp = "101.7";
			break;
		case 4:
			tmp = "103.7";
			break;
		default:
			break;	
		}
		return tmp;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initial();
		server = new HelloServer();
		mRegisterTask = new RegisterTask();
		server.setHandler(mMainHandler);
		if (hasFinished) {
			mRegisterTask.execute();	
		}
	}

	private void initial() {
		// TODO Auto-generated method stub
		dashNeLi = Typeface.createFromAsset(getAssets(),"fonts/nexalight.ttf");
		textDashSpeed = (TextView)this.findViewById(R.id.dash_speed);
		textDashSpeedUnit = (TextView)this.findViewById(R.id.dash_speed_unit);
		textDashFunction = (TextView)this.findViewById(R.id.dash_hint);
		textDashGear = (TextView)this.findViewById(R.id.dash_gear);
		turnGuideText = (TextView)findViewById(R.id.text_navi_hint);
		radioRightText = (TextView)this.findViewById(R.id.text_radio_right);
		radioRightText.setVisibility(View.INVISIBLE);
		radioLeftText = (TextView)this.findViewById(R.id.text_radio_left);
		radioLeftText.setVisibility(View.INVISIBLE);
		radioMidText = (TextView)this.findViewById(R.id.text_radio_mid);
		naviDirection = (TextView)this.findViewById(R.id.text_navi_direction);
		naviDistance = (TextView)this.findViewById(R.id.text_navi_distance);
		naviRightDistance = (TextView)this.findViewById(R.id.text_road_distance);
		musicArtistText = (TextView)this.findViewById(R.id.music_artist);
		naviDisKmText = (TextView)this.findViewById(R.id.text_naviing_distance);
		musicTitleText = (TextView)this.findViewById(R.id.music_title);
		dashNormalLeft = (ImageView)this.findViewById(R.id.dash_normal_left_circle);
		dashBigLeft = (ImageView)this.findViewById(R.id.dash_big_left_circle);
		dashFirstMidIcon = (ImageView)this.findViewById(R.id.dash_board_mid);
		dashIcon = (RelativeLayout)this.findViewById(R.id.dash_icon);
		dashNormalRight = (RelativeLayout)this.findViewById(R.id.dash_relative2);
		dashBigRight = (RelativeLayout)this.findViewById(R.id.dash_navi_right_layout);
		dashRadioRight = (RelativeLayout)this.findViewById(R.id.dash_radio_right_layout);
		dashMid = (RelativeLayout)this.findViewById(R.id.mid_layout);
		naviLayout = (RelativeLayout)this.findViewById(R.id.navi_layout);
		fuelOut = (ImageView)findViewById(R.id.navi_fuel_out);
		//fuelOut.setVisibility(View.INVISIBLE);
		//naviRightLayout = (RelativeLayout)this.findViewById(R.id.dash_navi_right_layout);
		musicRightLayout = (RelativeLayout)this.findViewById(R.id.dash_music_right_layout);
		naviIcon = (ImageView)findViewById(R.id.navi_dir_icon);
		textDashSpeed.setTypeface(dashNeLi);
		textDashSpeedUnit.setTypeface(dashNeLi);  
		textDashGear.setTypeface(dashNeLi);
		radioRightText.setTypeface(dashNeLi);
		radioLeftText.setTypeface(dashNeLi);
		radioMidText.setTypeface(dashNeLi);
		naviDirection.setTypeface(dashNeLi);
		naviDistance.setTypeface(dashNeLi);
		naviRightDistance.setTypeface(dashNeLi);
		musicArtistText.setTypeface(dashNeLi);
		musicTitleText.setTypeface(dashNeLi);
		dashSelectBtn = (Button)this.findViewById(R.id.dash_change_btn);
		dashSelectBtn.setOnClickListener(this);
		
		/********测试按钮**********/
		testNavi = (Button)this.findViewById(R.id.test1);
		testMusic = (Button)this.findViewById(R.id.test2);
		testRadio = (Button)this.findViewById(R.id.test3);
		testPhone = (Button)this.findViewById(R.id.test4);
		testNavi.setOnClickListener(this);
		testMusic.setOnClickListener(this);
		testRadio.setOnClickListener(this);
		testPhone.setOnClickListener(this);
		dashNaviComprass = (ImageView)findViewById(R.id.dash_compass_icon);
		
	}

	

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.dash_change_btn:
			count += 1;
			if(count==4)
			{
				textDashFunction.setText("导航");
				dashIcon.setBackgroundResource(R.drawable.dash_navi_icon);
				
				count = 0;
			}
			if(count==1)
			{
				textDashFunction.setText("音乐");
				dashIcon.setBackgroundResource(R.drawable.dash_music_icon);
			}
			if(count==2)
			{
				textDashFunction.setText("电台");
				dashIcon.setBackgroundResource(R.drawable.dash_radio_icon);
			}
			if(count==3)
			{
				textDashFunction.setText("电话");
				dashIcon.setBackgroundResource(R.drawable.dash_phone_icon);
			}
			break;
		case R.id.test1:
			//navi
			Utils.isNaviBegin = true;
			naviShow();
			break;
		case R.id.test2:
			//yinyue
			musicShow();
			break;
		case R.id.test3:
			//radio
			radioShow();
			break;
		case R.id.test4:
			break;

		default:
			break;
		}
			
	}
	
	private void naviShow(){
		dashNormalLeft.setVisibility(View.INVISIBLE);
		dashBigLeft.setVisibility(View.VISIBLE);
		textDashGear.setVisibility(View.VISIBLE);
		dashNormalRight.setVisibility(View.INVISIBLE);
		dashMid.setVisibility(View.INVISIBLE);
		//dashBigRight.setVisibility(View.VISIBLE);
		//dashRadioRight.setVisibility(View.VISIBLE);
		naviLayout.setVisibility(View.VISIBLE);
		//naviRightLayout.setVisibility(View.VISIBLE);
		//musicRightLayout.setVisibility(View.VISIBLE);
		//fuelOut.setVisibility(View.INVISIBLE);
	}
	
	private void musicShow(){
		if (! Utils.isNaviBegin) {
			dashRadioRight.setVisibility(View.INVISIBLE);
			dashBigLeft.setVisibility(View.INVISIBLE);
			naviLayout.setVisibility(View.INVISIBLE);
			
			musicRightLayout.setVisibility(View.VISIBLE);
			dashMid.setVisibility(View.VISIBLE);
			dashNormalLeft.setVisibility(View.VISIBLE);	
		}else {
			dashNormalLeft.setVisibility(View.INVISIBLE);
			dashNormalRight.setVisibility(View.INVISIBLE);
			dashRadioRight.setVisibility(View.INVISIBLE);
			dashMid.setVisibility(View.INVISIBLE);
			
			dashBigLeft.setVisibility(View.VISIBLE);
			textDashGear.setVisibility(View.VISIBLE);
			naviLayout.setVisibility(View.VISIBLE);
			musicRightLayout.setVisibility(View.VISIBLE);
		}
	}
	
	private void radioShow(){
		if(! Utils.isNaviBegin)
	  {
		Log.e("dash", "radioShow");
		musicRightLayout.setVisibility(View.INVISIBLE);
		dashBigLeft.setVisibility(View.INVISIBLE);
		dashNormalRight.setVisibility(View.INVISIBLE);
		
		dashNormalLeft.setVisibility(View.VISIBLE);
		dashRadioRight.setVisibility(View.VISIBLE);
		dashMid.setVisibility(View.VISIBLE);
		
		naviLayout.setVisibility(View.INVISIBLE);
		}else {
			dashNormalLeft.setVisibility(View.INVISIBLE);
			dashNormalRight.setVisibility(View.INVISIBLE);
			dashMid.setVisibility(View.INVISIBLE);
			dashBigRight.setVisibility(View.INVISIBLE);
			musicRightLayout.setVisibility(View.INVISIBLE);
			
			dashBigLeft.setVisibility(View.VISIBLE);
			dashRadioRight.setVisibility(View.VISIBLE);
			naviLayout.setVisibility(View.VISIBLE);
			textDashGear.setVisibility(View.VISIBLE);
			
		}
	}
	
	
	private String getLocalIpAddress() throws UnknownHostException {
        WifiManager wifiManager = (WifiManager) this.getSystemService(android.content.Context.WIFI_SERVICE );
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String tmp = String.valueOf(String.format("%d.%d.%d.%d",
                (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff)));
        Log.e("ip", tmp);
        return tmp;
       /** return InetAddress.getByName(String.format("%d.%d.%d.%d",
                        (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                        (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff)));**/
	}
	
	class RegisterTask extends AsyncTask<Void, Integer, Integer>
	{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			hasFinished = false;
			Log.e("?", "1");
		}
		
		@Override
		protected Integer doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				Http.Register(getLocalIpAddress(), "8080");
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 1;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.e("?", "2");
			hasFinished = true;
			try {
				server.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}

	
	private String getNameById(String id){
		String result = "安静";
		if (id.equals("0")) {
			result = "安静";
		}if (id.equals("1")) {
			result = "残酷月光"; 
		}if (id.equals("2")) {
			result = "传奇";
		}if (id.equals("3")) {
			result = "好久不见";
		}if (id.equals("4")) {
			result = "龙卷风";
		}
		return result;
	}
	private String getSingerByName(String name){
		String result = "周杰伦";
		if (name.equals("安静")) {
			result = "周杰伦";
		}if (name.equals("残酷月光")) {
			result = "林宥嘉"; 
		}if (name.equals("传奇")) {
			result = "王菲";
		}if (name.equals("好久不见")) {
			result = "陈奕迅";
		}if (name.equals("龙卷风")) {
			result = "周杰伦";
		}
		return result;
	}
}
