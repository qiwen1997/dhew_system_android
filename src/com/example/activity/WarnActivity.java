package com.example.activity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.dhew_system.MainActivity;
import com.example.dhew_system.R;
import com.example.util.HttpUtil;
import com.example.vo.WarnInfoVo;
import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WarnActivity extends Fragment {
	private TextView mCountNumber;
	private Button mStart;
	private TextView positionTextView;
	private LocationManager locationManager;
	private String provider;
	private EditText positionEditText;
	private EditText emeMessageEdit;
	SharedPreferences mSharedPreferences;
	private String user_phone;
	private EditText emeNameEdit;
	private EditText emePhoneEdit;
	MyReceiver myReceiver;
	private Button send_true;
	private Button send_false;
	TimeCount timer = new TimeCount(12000, 1000);
	private boolean send_use = true;
	private Integer num;

	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onTick(long millisUntilFinished) {
			mCountNumber.setText(String.valueOf(millisUntilFinished / 1000));
		}

		@Override
		public void onFinish() {
			// 倒计时结束后发送消息
			if (send_use == true) {
				user_phone = mSharedPreferences.getString("login_phone", null);
				WarnInfoVo warnInfo = new WarnInfoVo();
				warnInfo.setContact_name(emeNameEdit.getText().toString());
				warnInfo.setContact_phone(emePhoneEdit.getText().toString());
				warnInfo.setUser_phone(user_phone);
				warnInfo.setWarn_data(emeMessageEdit.getText().toString());
				warnInfo.setWarn_loc(positionEditText.getText().toString());
				if (sendWarn(user_phone, warnInfo).equals("success")) {
					Toast.makeText(getActivity(), "发送成功", 10).show();
				}
				send_use = false;
			}
		}
	};

	LocationListener locationListener = new LocationListener() {
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onLocationChanged(Location location) {
			// 更新当前设备的位置信息
			showLocation(location);
		}
	};

	private void showLocation(Location location) {
		String currentPosition = "latitude is " + location.getLatitude() + "\n" + "longitude is "
				+ location.getLongitude();
		positionTextView.setText(currentPosition);
		String chinesePostiion = getLocationAddress(location);
		positionEditText.setText(chinesePostiion);
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.menu_main, menu); return true; }
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		// noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 将经纬度转换成中文地址
	 *
	 * @param location
	 * @return
	 */
	private String getLocationAddress(Location location) {
		String add = "获取地理位置失败";
		Geocoder geoCoder = new Geocoder(getActivity().getBaseContext(), Locale.CHINESE);
		try {
			List<Address> addresses = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
			// return addresses.toString();
			Address address = addresses.get(0);
			Log.i("WarnActivity", "getLocationAddress: " + address.toString());
			int maxLine = address.getMaxAddressLineIndex();
			if (maxLine >= 2) {
				add = address.getAddressLine(0) + address.getAddressLine(1);
			} else {
				add = address.getAddressLine(0);
			}
		} catch (IOException e) {
			add = "获取地理位置异常";
			e.printStackTrace();
		}
		return add;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// ActivityCompat.requestPermissions(getActivity(),new
		// String[]{Manifest.permission.SEND_SMS} , -1);
		View view = inflater.inflate(R.layout.activity_warn, container, false);
		iniView(view);
		return view;
	}

	public void iniView(View view) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()
				.detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(
				new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build());
		emeNameEdit = (EditText) view.findViewById(R.id.eme_name_edit);
		emePhoneEdit = (EditText) view.findViewById(R.id.eme_phone_edit);
		positionTextView = (TextView) view.findViewById(R.id.location_text);
		positionEditText = (EditText) view.findViewById(R.id.location_edit);
		mCountNumber = (TextView) view.findViewById(R.id.auto_send);
		emeMessageEdit = (EditText) view.findViewById(R.id.eme_message_edit);
		mSharedPreferences = getActivity().getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
		user_phone = mSharedPreferences.getString("login_phone", null);
		String result = getEme(user_phone);
		try {
			JSONObject jsonResult = new JSONObject(result);
			emeNameEdit.setText(jsonResult.getString("contact_name"));
			emePhoneEdit.setText(jsonResult.getString("contact_phone"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		send_true = (Button) view.findViewById(R.id.send_truebutton);
		send_true.setOnClickListener(new OnClickListener() {
			// 发送消息
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 此处应发送消息
				user_phone = mSharedPreferences.getString("login_phone", null);
				WarnInfoVo warnInfo = new WarnInfoVo();
				warnInfo.setContact_name(emeNameEdit.getText().toString());
				warnInfo.setContact_phone(emePhoneEdit.getText().toString());
				warnInfo.setUser_phone(user_phone);
				warnInfo.setWarn_data(emeMessageEdit.getText().toString());
				warnInfo.setWarn_loc(positionEditText.getText().toString());
				if (sendWarn(user_phone, warnInfo).equals("success")) {
					Toast.makeText(getActivity(), "发送成功", 10).show();
				}
			}
		});
		send_false = (Button) view.findViewById(R.id.send_falsebutton);
		send_false.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// handler.removeCallbacksAndMessages(null);
				// 此处应调整为跳转至健康监测界面
				timer.cancel();
				Intent intent = new Intent(getActivity(), MainActivity.class);
				startActivity(intent);
			}
		});
	}

	public void init1() {
		locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		List<String> providerList = locationManager.getProviders(true);
		if (providerList.contains(LocationManager.GPS_PROVIDER)) {
			provider = LocationManager.GPS_PROVIDER;
		} else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
			provider = LocationManager.NETWORK_PROVIDER;
		} else {
			Toast.makeText(getActivity(), "位置权限没有打开", Toast.LENGTH_SHORT).show();
			positionEditText.setText("位置权限没有打开");
			return;
		}
		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null) {
			Toast.makeText(getActivity(), "获取位置权限", Toast.LENGTH_SHORT).show();
			// timer.start();
			showLocation(location);
		} else {
			Toast.makeText(getActivity(), "位置为空", Toast.LENGTH_SHORT).show();
			positionEditText.setText("位置为空");
			// timer.start();
		}
		locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		myReceiver = new MyReceiver(getActivity());
		myReceiver.registerAction("yujindata");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		send_use = true;
		super.onResume();
	}

	class MyReceiver extends BroadcastReceiver {
		Context context;

		public MyReceiver(Context c) {
			context = c;
		}

		// 动态注册
		public void registerAction(String action) {
			IntentFilter filter = new IntentFilter();
			filter.addAction(action);
			context.registerReceiver(this, filter);
			Toast.makeText(getActivity(), "注册接听器成功", 10).show();
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			// 接收广播内容.
			Message m = handler1.obtainMessage();
			m.what = Integer.valueOf(intent.getExtras().getString("name"));
			m.obj = intent.getExtras().getString("data");
			handler1.sendMessage(m);
		}
	}

	Handler handler1 = new Handler() {
		@Override
		public void handleMessage(Message m) {
			Toast.makeText(getActivity(), "获取数据成功", 10).show();
			num = m.what;
			setWarnDate((String) m.obj, user_phone);
			// init1();
			timer.start();
		}
	};

	public String sendWarn(String user_phone, WarnInfoVo warn) {
		String queryString = "user_phone=" + user_phone + "&contact_name=" + warn.getContact_name() + "&contact_phone="
				+ warn.getContact_phone() + "&warn_data=" + warn.getWarn_data() + "&warn_loc=" + warn.getWarn_loc();
		String url = HttpUtil.BASE_URL + "SendWarnServlet?" + queryString;
		String message = warn.getWarn_data();
		if (message != null && warn.getContact_phone() != null && !warn.getContact_phone().equals("")) {
			SmsManager sms = SmsManager.getDefault();
			List<String> texts = sms.divideMessage(message);
			for (String text : texts) {
				sms.sendTextMessage(warn.getContact_phone(), user_phone, text, null, null);
			}
		}
		return HttpUtil.queryStringForPost(url);
	}

	public String getEme(String user_phone) {
		String url = HttpUtil.BASE_URL + "WarnGetEmergentServlet?user_phone=" + user_phone;
		Toast.makeText(getActivity(), user_phone, Toast.LENGTH_SHORT).show();
		return HttpUtil.queryStringForPost(url);
	}

	public void setWarnDate(String action, String user_phone) {
		String data;
		if (num == 1) {
			data = "紧急联系人您好，手机号为" + user_phone + "的驾驶员心率异常，数值为" + action + "请注意关注其驾驶状态";
		} else if (action.equals("2")) {
			data = "紧急联系人您好，手机号为" + user_phone + "的驾驶员车内温度异常，数值为" + 40 + "请注意关注其驾驶状态";
		} else if (action.equals("3")) {
			data = "紧急联系人您好，手机号为" + user_phone + "的驾驶员体温异常，数值为" + 40 + "请注意关注其驾驶状态";
		} else {
			data = "预警信息测试";
		}
		emeMessageEdit.setText(data);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		// handler.removeCallbacksAndMessages(null);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// handler.removeCallbacksAndMessages(null);
		send_use = true;
		timer.cancel();
		if (locationManager != null) {
			// 关闭程序时将监听器移除
			locationManager.removeUpdates(locationListener);
		}
		getActivity().unregisterReceiver(myReceiver);
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
}
