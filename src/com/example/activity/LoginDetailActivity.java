package com.example.activity;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.example.util.HttpUtil;
import com.example.dhew_system.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
public class LoginDetailActivity extends Activity {
	Calendar calendar = Calendar.getInstance();
	EditText nickname;
	EditText realname;
	RadioGroup sex;
	RadioButton csex;
	Button startdate;
	EditText height;
	EditText weight;
	EditText blood;
	Button next;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_detail);
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
		nickname = (EditText) findViewById(R.id.login_detail_edi2);
		realname = (EditText) findViewById(R.id.login_detail_edi3);
		sex = (RadioGroup) findViewById(R.id.radio_sex);
		startdate = (Button) findViewById(R.id.order_startdate);
		height = (EditText) findViewById(R.id.login_detail_edi6);
		weight = (EditText) findViewById(R.id.login_detail_edi7);
		blood = (EditText) findViewById(R.id.login_detail_edi8);
		next = (Button) findViewById(R.id.login_detail_btn1);
		startdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatePickerDialog datepicker = new DatePickerDialog(LoginDetailActivity.this, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						startdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
				datepicker.show();
			}
		});
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				csex = (RadioButton) findViewById(sex.getCheckedRadioButtonId());
				String strnickname = nickname.getText().toString().trim();
				String strrealname = realname.getText().toString().trim();
				String strcsex = csex.getText().toString().trim();
				String strdate = startdate.getText().toString().trim();
				String strheight = height.getText().toString().trim();
				String strweight = weight.getText().toString().trim();
				String strblood = blood.getText().toString().trim();
				if (strnickname.equals("") || strrealname.equals("") || strcsex.equals("") || strdate.equals("出生年月日")
						|| strheight.equals("") || strweight.equals("") || strblood.equals("")) {
					Toast.makeText(LoginDetailActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if (!namecheck(strnickname) || !namecheck(strrealname)) {
					Toast.makeText(LoginDetailActivity.this, "昵称和姓名为1到10位中文英文与数字", Toast.LENGTH_SHORT).show();
					return;
				}
				if (!weightcheck(strheight) || !weightcheck(strweight)) {
					Toast.makeText(LoginDetailActivity.this, "身高与体重为0到1位小数的正数", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent1 = getIntent();
				String id = intent1.getStringExtra("id");
				String strphone = intent1.getStringExtra("phone");
				String strpass = intent1.getStringExtra("password");
				if (Login(id, strphone, strnickname, strcsex, strdate, strheight, strweight, strblood, strrealname)) {
					Intent intent = new Intent(LoginDetailActivity.this, LoginUrgeActivity.class);
					intent.putExtra("phone", strphone);
					intent.putExtra("password", strpass);
					intent.putExtra("nickname", strnickname);
					intent.putExtra("realname", strrealname);
					intent.putExtra("sex", strcsex);
					intent.putExtra("date", strdate);
					intent.putExtra("height", strheight);
					intent.putExtra("weight", strweight);
					intent.putExtra("blood", strblood);
					// Toast.makeText(LoginDetailActivity.this,strphone+" "+strpass+" "+strcsex,
					// Toast.LENGTH_SHORT).show();
					startActivity(intent);
					finish();
				}else {
					return;
				}
			}
		});
	}
	private boolean Login(String user_id, String user_phone, String user_name, String user_sex, String user_birth,
			String user_height, String user_weight, String user_blood, String user_realname) {
		String result = query(user_id, user_phone, user_name, user_sex, user_birth, user_height, user_weight,
				user_blood, user_realname);
		if (result != null && result.equals("success")) {
			// Toast.makeText(LoginActivity.this,result, Toast.LENGTH_SHORT).show();
			return true;
		} else {
			// saveUserMsg(result);
			return false;
		}
	}
	private String query(String user_id, String user_phone, String user_name, String user_sex, String user_birth,
			String user_height, String user_weight, String user_blood, String user_realname) {
		String queryString = "user_id=" + user_id + "&user_phone=" + user_phone + "&user_name=" + user_name
				+ "&user_sex=" + user_sex + "&user_birth=" + user_birth + "&user_height=" + user_height
				+ "&user_weight=" + user_weight + "&user_blood=" + user_blood + "&user_realname=" + user_realname;
		String url = HttpUtil.BASE_URL + "servlet/InfoServlet?" + queryString;
		return HttpUtil.queryStringForPost(url);
	}
	public boolean isNumeric(String str) {// 数字匹配
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isMobileNo(String mobiles) {// 手机号匹配
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	public boolean passcheck(String str) {// 6到16位字母和数字
		Pattern p = Pattern.compile("^[a-zA-Z0-9]{6,16}$");
		Matcher m = p.matcher(str);
		return m.matches();
	}
	public boolean namecheck(String str) {// 1到10位中文，英文字母和数字
		Pattern p = Pattern.compile("^[\\u4e00-\\u9fa5_a-zA-Z0-9_]{1,10}$");
		Matcher m = p.matcher(str);
		return m.matches();
	}
	public boolean weightcheck(String str) {// 0到1位小数的正数
		Pattern p = Pattern.compile("^[0-9]+(.[0-9]{0,1})?$");
		Matcher m = p.matcher(str);
		return m.matches();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_detail, menu);
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
