package com.example.activity;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.example.util.HttpUtil;
import com.example.vo.User;
import com.example.vo.UserUrge;
import com.example.dhew_system.MainActivity;
import com.example.dhew_system.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class LoginUrgeActivity extends Activity {
	EditText urgename;
	EditText urgephone;
	Button finish;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_urge);
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
		urgename=(EditText) findViewById(R.id.login_urge_edi2);
		urgephone=(EditText) findViewById(R.id.login_urge_edi3);
		finish=(Button) findViewById(R.id.login_urge_btn1);
		finish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String strurgename=urgename.getText().toString().trim();
				String strurgephone=urgephone.getText().toString().trim();
				if(strurgename.equals("")||strurgephone.equals("")) {
					Toast.makeText(LoginUrgeActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if(!isMobileNo(strurgephone)) {
					Toast.makeText(LoginUrgeActivity.this, "手机号码格式错误", Toast.LENGTH_SHORT).show();
					return;
				}
				if(!namecheck(strurgename)) {
					Toast.makeText(LoginUrgeActivity.this, "姓名为1到10位中文英文与数字", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent1=getIntent();
				String phone=intent1.getStringExtra("phone");

				String default_phone="1";
				if(Login(phone,strurgename,strurgephone,default_phone)) {
				Intent intent=new Intent(LoginUrgeActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
				}else {
					return;
				}
			}
		});
	}
private boolean Login(String user_phone,String contact_name,String contact_phone
		,String default_phone) {
		String result = query(user_phone, contact_name,contact_phone,default_phone);
		if (result != null && result.equals("success")) {
			//Toast.makeText(LoginActivity.this,result, Toast.LENGTH_SHORT).show();
			return true;
		} else {
			// saveUserMsg(result);
			return false;
		}
	}
	private String query(String user_phone,String contact_name,String contact_phone
			,String default_phone) {
		String queryString = "user_phone=" + user_phone + "&contact_name=" + contact_name
				+ "&contact_phone=" + contact_phone + "&default_phone=" + default_phone;
		String url = HttpUtil.BASE_URL + "servlet/UrgeServlet?" + queryString;
		return HttpUtil.queryStringForPost(url);
	}
	public boolean isMobileNo(String mobiles) {//手机号匹配
		Pattern p=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m=p.matcher(mobiles);
		return m.matches();
	}
	public boolean namecheck(String str) {//1到10位中文英文数字
		Pattern p=Pattern.compile("^[\\u4e00-\\u9fa5_a-zA-Z0-9_]{1,10}$");
		Matcher m=p.matcher(str);
		return m.matches();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_urge, menu);
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
