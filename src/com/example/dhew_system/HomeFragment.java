package com.example.dhew_system;

import java.text.DecimalFormat;
import com.example.healthy.HeartActivity;
import com.example.healthy.HumidityActivity;
import com.example.healthy.IndorTempActivity;
import com.example.healthy.TempActivity;
import com.example.util.HttpUtil;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {
	static ConnectedThread1 connectedThread1 = null;
	private TextView username;
	private TextView fenxi;
	private TextView xinlv;
	private TextView tiwen;
	private TextView shiwen;
	private TextView shidu;
	private int xinlv_warn = 0;
	private int tiwen_warn = 0;
	private int shiwen_warn = 0;
	private int shidu_warn = 0;
	// 判断是否有本地登录记录
	SharedPreferences mSharedPreferences;
	private String login_phone;
	// 预警值设置
	private Double WarnNum;
	private String WarnURL = "http://47.95.212.214:8080/Dhew_System_Server/DefaultHeartServlet?user_phone=15600000001";
	// 发送健康指标
	private String sendData = "http://47.95.212.214:8080" + "/Dhew_System_Server/AddHealthInfoServlet?u"
			+ "ser_phone=%s&xinlv=%f&tem=%f&room=%f&hum=%f";
	// 健康值
	Double WARN_NUM = 80.0;
	Double xinlv1;
	Double shiwen1;
	Double tiwen1;
	Double shidu1;
	private int i = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_home_fragment, container, false);
		iniView(view);
		return view;
	}

	public void iniView(View view) {
		// 判断是否有本地登录记录
		mSharedPreferences = getActivity().getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
		login_phone = mSharedPreferences.getString("login_phone", null);
		// WarnNum = Double.valueOf(HttpUtil.queryStringForPost(WarnURL));
		username = (TextView) view.findViewById(R.id.username);
		username.setText("用户名" + login_phone);
		fenxi = (TextView) view.findViewById(R.id.fenxi);
		fenxi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), HeartActivity.class);// 跳转的Activity名
				startActivity(intent);
			}
		});
		xinlv = (TextView) view.findViewById(R.id.xinlv);
		xinlv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), HeartActivity.class);
				startActivity(intent);
			}
		});
		tiwen = (TextView) view.findViewById(R.id.tiwen);
		tiwen.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), TempActivity.class);
				startActivity(intent);
			}
		});
		shiwen = (TextView) view.findViewById(R.id.shiwen);
		shiwen.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), IndorTempActivity.class);
				startActivity(intent);
			}
		});
		shidu = (TextView) view.findViewById(R.id.shidu);
		shidu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), HumidityActivity.class);
				startActivity(intent);
			}
		});
		init1();
	}

	void init1() {
		if (connectedThread1 == null) {
			connectedThread1 = new ConnectedThread1();
			connectedThread1.start();
		}
	}

	public class ConnectedThread1 extends Thread {
		public void run() {
			while (true) {
				i++;
				if (i == 10) {
					HttpUtil.queryStringForGet(String.format(sendData, login_phone, xinlv1, tiwen1, shiwen1, shidu1));
					i = 0;
				}
				Intent intent1 = new Intent();
				intent1.setAction("xinlv");
				xinlv1 = (Double) (Math.random() * 20 + 70);
				if (xinlv1.compareTo(WARN_NUM) == 1) {
					xinlv_warn++;
					if (xinlv_warn == 10) {
						WARN_NUM = 90.0;
						Intent intent2 = new Intent();
						intent2.setAction("yujin");
						intent2.putExtra("name", "心率异常");
						intent2.putExtra("data", WARN_NUM.toString());
						getActivity().sendBroadcast(intent2);
					}
				}
				intent1.putExtra("name", String.valueOf(xinlv1));
				getActivity().sendBroadcast(intent1);
				Message message1 = handler1.obtainMessage();
				message1.obj = xinlv1.toString();
				handler1.sendMessage(message1);
				Intent intent2 = new Intent();
				intent2.setAction("tiwen");
				tiwen1 = (Double) (Math.random() * 1.5 + 36);
				intent2.putExtra("name", String.valueOf(tiwen1));
				getActivity().sendBroadcast(intent2);
				Message message2 = handler2.obtainMessage();
				message2.obj = tiwen1.toString();
				handler2.sendMessage(message2);
				Intent intent3 = new Intent();
				intent3.setAction("shiwen");
				shiwen1 = (Double) (Math.random() * 5 + 20);
				intent3.putExtra("name", String.valueOf(shiwen1));
				getActivity().sendBroadcast(intent3);
				Message message3 = handler3.obtainMessage();
				message3.obj = shiwen1.toString();
				handler3.sendMessage(message3);
				Intent intent4 = new Intent();
				intent4.setAction("shidu");
				shidu1 = (Double) (Math.random() * 20 + 20);
				intent4.putExtra("name", String.valueOf(shidu1));
				getActivity().sendBroadcast(intent4);
				Message message4 = handler4.obtainMessage();
				message4.obj = shidu1.toString();
				handler4.sendMessage(message4);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			String s = (String) msg.obj;
			double d = Double.valueOf(s);
			DecimalFormat df = new DecimalFormat("#.00");
			String str = df.format(d);
			xinlv1 = Double.valueOf(str);
			xinlv.setText("心率:" + str);
		}
	};
	Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {
			String s = (String) msg.obj;
			double d = Double.valueOf(s);
			DecimalFormat df = new DecimalFormat("#.00");
			String str = df.format(d);
			tiwen1 = Double.valueOf(str);
			tiwen.setText("体温:" + str);
		}
	};
	Handler handler3 = new Handler() {
		public void handleMessage(Message msg) {
			String s = (String) msg.obj;
			double d = Double.valueOf(s);
			DecimalFormat df = new DecimalFormat("#.00");
			String str = df.format(d);
			shiwen1 = Double.valueOf(str);
			shiwen.setText("室温:" + str);
		}
	};
	Handler handler4 = new Handler() {
		public void handleMessage(Message msg) {
			String s = (String) msg.obj;
			double d = Double.valueOf(s);
			DecimalFormat df = new DecimalFormat("#.00");
			String str = df.format(d);
			shidu1 = Double.valueOf(str);
			shidu.setText("湿度:" + str);
		}
	};
}
