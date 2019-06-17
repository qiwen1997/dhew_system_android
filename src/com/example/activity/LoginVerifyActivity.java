package com.example.activity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.example.util.HttpUtil;
import com.example.dhew_system.R;
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
public class LoginVerifyActivity extends Activity {
	Button next;// 中文乱码测试
	EditText phone;
	EditText pass1;
	EditText pass2;
	SharedPreferences mSharedPreferences;
	SharedPreferences.Editor mEditor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_verify);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		        .detectDiskReads()
		        .detectDiskWrites()
		        .detectNetwork()   // or .detectAll() for all detectable problems
		        .penaltyLog()
		        .build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		        .detectLeakedSqlLiteObjects()
		        .detectLeakedClosableObjects()
		        .penaltyLog()
		        .penaltyDeath()
		        .build());
		next = (Button) findViewById(R.id.login_verify_btn2);
		phone = (EditText) findViewById(R.id.login_verify_teleedi1);
		pass1 = (EditText) findViewById(R.id.login_verify_teleedi3);
		pass2 = (EditText) findViewById(R.id.login_verify_teleedi4);
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String strphone;
				String strpass1;
				String strpass2;
				strphone = phone.getText().toString().trim();
				strpass1 = pass1.getText().toString().trim();
				strpass2 = pass2.getText().toString().trim();
				if (strphone.equals("") || strpass1.equals("") || strpass2.equals("")) {
					Toast.makeText(LoginVerifyActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if (!isNumeric(strphone) || !isMobileNo(strphone)) {
					Toast.makeText(LoginVerifyActivity.this, "手机号码格式错误", Toast.LENGTH_SHORT).show();
					return;
				}
				if (!passcheck(strpass1)) {
					Toast.makeText(LoginVerifyActivity.this, "密码为6到16位字母与数字", Toast.LENGTH_SHORT).show();
					return;
				}
				if (!strpass1.equals(strpass2)) {
					Toast.makeText(LoginVerifyActivity.this, "两次密码输入不同", Toast.LENGTH_SHORT).show();
					return;
				}
				String id=Login(strphone, strpass1);
				if (!id.equals("0")&&!id.equals("-1")) {
					Intent intent = new Intent(LoginVerifyActivity.this, LoginDetailActivity.class);
					intent.putExtra("id", id);
					intent.putExtra("phone", strphone);
					intent.putExtra("password", strpass1);
					mSharedPreferences = getSharedPreferences("SharedPreferences",
							Context.MODE_PRIVATE);
					mEditor = mSharedPreferences.edit();
					mEditor.putString("login_phone", strphone);
					mEditor.putString("login_password", strpass1);
					mEditor.commit();
					startActivity(intent);
					finish();
				}else {
					Toast.makeText(LoginVerifyActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});
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
	private String Login(String uphone, String pwd) {
		String result = query(uphone, pwd);
		if (!result.equals("0") && !result.equals("-1")) {
			// Toast.makeText(LoginActivity.this,result, Toast.LENGTH_SHORT).show();
			return result;
		} else if (result.equals("-1")) {
			// saveUserMsg(result);
			Toast.makeText(LoginVerifyActivity.this, "该账号已经注册", Toast.LENGTH_SHORT).show();
			return result;
		} else {
			return result;
		}
	}
	private String query(String account, String password) {
		String queryString = "account=" + account + "&password=" + password;
		String url = HttpUtil.BASE_URL + "servlet/RegisterServlet?" + queryString;
		return HttpUtil.queryStringForPost(url);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_verify, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
