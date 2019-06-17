package com.example.activity;
import org.json.JSONArray;
import org.json.JSONObject;
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
import android.widget.TextView;
import android.widget.Toast;
public class CompanyActivity extends Activity {
	TextView comname;
	EditText number;
	SharedPreferences mSharedPreferences;
	Button save;
	String login_phone;
	String strname;
	String strnumber;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company);
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
		comname=(TextView) findViewById(R.id.company_edi2);
		number=(EditText) findViewById(R.id.company_edi3);
		save=(Button) findViewById(R.id.company_btn1);
		mSharedPreferences=getSharedPreferences("SharedPreferences",
				Context.MODE_PRIVATE);
		login_phone=mSharedPreferences.getString("login_phone", null);
		init();
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String strcomname=comname.getText().toString().trim();
				String strnumber=number.getText().toString().trim();
				if(Login(login_phone,strnumber)) {
					Intent intent=new Intent(CompanyActivity.this,MyselfActivity.class);
					startActivity(intent);
					finish();
				}else {
					Toast.makeText(CompanyActivity.this, "无此编号", Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});
	}
	private void init() {
		String url = HttpUtil.BASE_URL +"/servlet/CompanyServlet?x="+login_phone
				+"&action="+"0";
		String result = HttpUtil.queryStringForGet(url);
		try {
			JSONArray tables = new JSONArray(result);
				JSONObject obj_tmp = tables.getJSONObject(0);
				strname=obj_tmp.getString("dp_name");
				strnumber=obj_tmp.getString("dp_id");
				comname.setText(strname);
				number.setText(strnumber);
		} catch (Exception e) {
				// TODO Auto-generated catch block
			e.printStackTrace();	
			System.out.println("装填错误");
		}
	}
	private boolean Login(String user_phone,String dp_id) {
			String result = query(user_phone, dp_id);
			if (result != null && result.equals("success")) {
				//Toast.makeText(LoginActivity.this,result, Toast.LENGTH_SHORT).show();
				return true;
			} else {
				// saveUserMsg(result);
				return false;
			}
		}
		private String query(String user_phone,String dp_id) {
			String queryString = "action=1" +"&user_phone=" + user_phone + "&dp_id=" + dp_id;
			String url = HttpUtil.BASE_URL + "servlet/CompanyServlet?" + queryString;
			return HttpUtil.queryStringForPost(url);
		}
}
