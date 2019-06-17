package com.example.dhew_system;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.UUID;
import com.example.activity.LoginActivity;
import com.example.activity.LoginDetailActivity;
import com.example.activity.LoginUrgeActivity;
import com.example.activity.MyselfActivity;
import com.example.activity.WarnActivity;
import com.example.healthy.IndorTempActivity;
import com.example.util.HttpUtil;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
	MyReceiver myReceiver;
	private BluetoothAdapter BA;
	private BluetoothSocket btSocket = null;
	// 蓝牙设备MAC地址
	private static String address = new String("98:D3:41:FD:4B:E2");
	// 蓝牙串口通信UID
	private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private OutputStream outStream = null;
	public InputStream inStream = null;
	// 判断是否有本地登录记录
	SharedPreferences mSharedPreferences;
	private String login_phone;
	// UI Object
	private TextView txt_topbar;
	private TextView txt_channel;
	private TextView txt_message;
	private TextView txt_better;
	private TextView txt_setting;
	private FrameLayout ly_content;
	private HomeFragment home;
	private MyselfActivity My;
	private WarnActivity Warn;
	FragmentTransaction fTransaction;
	// Fragment Object
	private MyFragment fg2;
	private FragmentManager fManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		// 判断是否有本地登录记录
		mSharedPreferences = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
		login_phone = mSharedPreferences.getString("login_phone", null);
		if (login_phone == null) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
		myReceiver = new MyReceiver(this);
		myReceiver.registerAction("yujin");
		fManager = getFragmentManager();
		bindViews();
		if (Warn == null)
			txt_better.performClick();
		txt_channel.performClick(); // 模拟一次点击，既进去后选择第一项
		// init();
	}

	// UI组件初始化与事件绑定
	private void bindViews() {
		txt_topbar = (TextView) findViewById(R.id.txt_topbar);
		txt_channel = (TextView) findViewById(R.id.txt_channel);
		txt_message = (TextView) findViewById(R.id.txt_message);
		txt_better = (TextView) findViewById(R.id.txt_better);
		txt_setting = (TextView) findViewById(R.id.txt_setting);
		ly_content = (FrameLayout) findViewById(R.id.ly_content);
		txt_channel.setOnClickListener(this);
		txt_message.setOnClickListener(this);
		txt_better.setOnClickListener(this);
		txt_setting.setOnClickListener(this);
	}

	// 重置所有文本的选中状态
	private void setSelected() {
		txt_channel.setSelected(false);
		txt_message.setSelected(false);
		txt_better.setSelected(false);
		txt_setting.setSelected(false);
	}

	// 隐藏所有Fragment
	private void hideAllFragment(FragmentTransaction fragmentTransaction) {
		if (home != null)
			fragmentTransaction.hide(home);
		if (fg2 != null)
			fragmentTransaction.hide(fg2);
		if (Warn != null)
			fragmentTransaction.hide(Warn);
		if (My != null)
			fragmentTransaction.hide(My);
	}

	@Override
	public void onClick(View v) {
		fTransaction = fManager.beginTransaction();
		hideAllFragment(fTransaction);
		switch (v.getId()) {
		case R.id.txt_channel:
			/**
			 * 首页跳转 其他跳转都依次为标准 类型均为Fragment
			 */
			setSelected();
			txt_channel.setSelected(true);
			txt_topbar.setText("首页");
			if (home == null) {
				home = new HomeFragment();
				fTransaction.add(R.id.ly_content, home);
			} else {
				fTransaction.show(home);
			}
			break;
		case R.id.txt_message:// 发现
			setSelected();
			txt_message.setSelected(true);
			txt_topbar.setText("发现");
			if (fg2 == null) {
				fg2 = new MyFragment("第二个Fragment");
				fTransaction.add(R.id.ly_content, fg2);
			} else {
				fTransaction.show(fg2);
			}
			break;
		case R.id.txt_better:// 预警
			setSelected();
			txt_better.setSelected(true);
			txt_topbar.setText("预警");
			if (Warn == null) {
				Warn = new WarnActivity();
				fTransaction.add(R.id.ly_content, Warn);
			} else {
				fTransaction.show(Warn);
			}
			break;
		case R.id.txt_setting:// 我的
			setSelected();
			txt_setting.setSelected(true);
			txt_topbar.setText("我的");
			if (My == null) {
				My = new MyselfActivity();
				fTransaction.add(R.id.ly_content, My);
			} else {
				fTransaction.show(My);
			}
			break;
		}
		fTransaction.commit();
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
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			// 接收广播内容.
			String name = intent.getExtras().getString("name");
			String data = intent.getExtras().getString("data");
			handler1.sendMessage(handler1.obtainMessage());
			Message m = handler2.obtainMessage();
			m.obj = data;
			handler2.sendMessage(m);
		}
	}

	Handler handler1 = new Handler() {
		@Override
		public void handleMessage(Message m) {
			fTransaction = fManager.beginTransaction();
			hideAllFragment(fTransaction);
			setSelected();
			txt_better.setSelected(true);
			txt_topbar.setText("预警");
			if (Warn == null) {
				Warn = new WarnActivity();
				fTransaction.add(R.id.ly_content, Warn);
			} else {
				fTransaction.show(Warn);
			}
			fTransaction.commit();
		}
	};
	Handler handler2 = new Handler() {
		@Override
		public void handleMessage(Message m) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent intent1 = new Intent();
			intent1.setAction("yujindata");
			intent1.putExtra("name", "1");
			intent1.putExtra("data", (String) m.obj);
			sendBroadcast(intent1);
			Toast.makeText(MainActivity.this, "发送数据成功", 10).show();
		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(myReceiver);
		super.onDestroy();
	}
}
