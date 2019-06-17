package com.example.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.example.dhew_system.MainActivity;
import com.example.dhew_system.R;
import com.example.util.HttpUtil;

public class LoginActivity extends Activity {
	EditText phone;
	EditText password;
	Button login;
	Button register;
	SharedPreferences mSharedPreferences;
	SharedPreferences.Editor mEditor;
	private String login_phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mSharedPreferences = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
		login_phone = mSharedPreferences.getString("login_phone", null);
		if (login_phone != null) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		StrictMode.setThreadPolicy(
				new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork() // or
																											// .detectAll()
																											// for all
																											// detectable
																											// problems
						.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
				.detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		phone = (EditText) findViewById(R.id.login_user_edit);
		password = (EditText) findViewById(R.id.login_passwd_edit);
		login = (Button) findViewById(R.id.login_login_btn);
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String strphone = phone.getText().toString().trim();
				String strpassword = password.getText().toString().trim();
				if (strphone.equals("") || strpassword.equals("")) {
					Toast.makeText(LoginActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if (!isNumeric(strphone) || !isMobileNo(strphone)) {
					Toast.makeText(LoginActivity.this, "手机号码格式错误", Toast.LENGTH_SHORT).show();
					return;
				}
				if (!passcheck(strpassword)) {
					Toast.makeText(LoginActivity.this, "密码为6到16位字母与数字", Toast.LENGTH_SHORT).show();
					return;
				}
				if (Login(strphone, strpassword)) {
					mSharedPreferences = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
					mEditor = mSharedPreferences.edit();
					mEditor.putString("login_phone", strphone);
					mEditor.putString("login_password", strpassword);
					mEditor.commit();
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(LoginActivity.this, "账号密码不正确", Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});
		register = (Button) findViewById(R.id.login_register_btn);
		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this, LoginVerifyActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	private boolean Login(String uphone, String pwd) {
		String result = query(uphone, pwd);
		if (result != null && result.equals("success")) {
			// Toast.makeText(LoginActivity.this,result, Toast.LENGTH_SHORT).show();
			return true;
		} else {
			// saveUserMsg(result);
			return false;
		}
	}

	private String query(String account, String password) {
		String queryString = "account=" + account + "&password=" + password;
		String url = HttpUtil.BASE_URL + "servlet/LoginServlet?" + queryString;
		return HttpUtil.queryStringForPost(url);
	}

	public boolean isNumeric(String str) {// 数字检测
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isMobileNo(String mobiles) {// 手机号检测
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public boolean passcheck(String str) {// 6到16位字母和数字
		Pattern p = Pattern.compile("^[a-zA-Z0-9]{6,16}$");
		Matcher m = p.matcher(str);
		return m.matches();
	}
}
