package com.example.dhew_system;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.example.activity.MyUrgeActivity;
import com.example.util.HttpUtil;
import com.example.dhew_system.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class AddUrgeActivity extends Activity {
	EditText urgename;
	EditText urgephone;
	Button finish;
	SharedPreferences mSharedPreferences;
	String phone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_urge);
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
		urgename=(EditText) findViewById(R.id.add_urge_edi2);
		urgephone=(EditText) findViewById(R.id.add_urge_edi3);
		finish=(Button) findViewById(R.id.add_urge_btn1);
		mSharedPreferences=getSharedPreferences("SharedPreferences",
				Context.MODE_PRIVATE);
		phone=mSharedPreferences.getString("login_phone", null);
        finish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String strurgename=urgename.getText().toString().trim();
				String strurgephone=urgephone.getText().toString().trim();
				if(strurgename.equals("")||strurgephone.equals("")) {
					Toast.makeText(AddUrgeActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if(!isMobileNo(strurgephone)) {
					Toast.makeText(AddUrgeActivity.this, "手机号码格式错误", Toast.LENGTH_SHORT).show();
					return;
				}
				if(!namecheck(strurgename)) {
					Toast.makeText(AddUrgeActivity.this, "姓名为1到10位中文英文与数字", Toast.LENGTH_SHORT).show();
					return;
				}
				String default_phone="0";
				if(Login(phone,strurgename,strurgephone,default_phone)) {
					Intent intent=new Intent(AddUrgeActivity.this,MyUrgeActivity.class);
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
}
