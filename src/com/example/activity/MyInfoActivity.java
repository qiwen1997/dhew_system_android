package com.example.activity;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.util.HttpUtil;
import com.example.vo.User;
import com.example.dhew_system.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
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
public class MyInfoActivity extends Activity {
    Calendar calendar=Calendar.getInstance();
	EditText nickname;
	EditText realname;
	RadioGroup sex;
	RadioButton csex;
	RadioButton boy;
	RadioButton girl;
	Button startdate;
	EditText height;
	EditText weight;
	EditText blood;
	Button save;
	SharedPreferences mSharedPreferences;
	private String login_phone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_info);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
				.build());
		nickname=(EditText) findViewById(R.id.my_info_edi2);
		realname=(EditText) findViewById(R.id.my_info_edi3);
		sex=(RadioGroup) findViewById(R.id.radio_sex);
		boy=(RadioButton) findViewById(R.id.boy);
		girl=(RadioButton) findViewById(R.id.girl);
		startdate=(Button) findViewById(R.id.order_startdate);
		height=(EditText) findViewById(R.id.my_info_edi6);
		weight=(EditText) findViewById(R.id.my_info_edi7);
		blood=(EditText) findViewById(R.id.my_info_edi8);
		save=(Button) findViewById(R.id.my_info_btn1);
		mSharedPreferences=getSharedPreferences("SharedPreferences",
				Context.MODE_PRIVATE);
		login_phone=mSharedPreferences.getString("login_phone", null);
		initView();
		startdate.setOnClickListener(new OnClickListener() {
			//ʱ��ѡ��
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatePickerDialog datepicker=new DatePickerDialog(MyInfoActivity.this, new OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						startdate.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
					}
				}, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
				datepicker.show();
			}
		});
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				csex=(RadioButton) findViewById(sex.getCheckedRadioButtonId());
				String strnickname=nickname.getText().toString().trim();
				String strrealname=realname.getText().toString().trim();
				String strcsex=csex.getText().toString().trim();
				String strdate=startdate.getText().toString().trim();
				String strheight=height.getText().toString().trim();
				String strweight=weight.getText().toString().trim();
				String strblood=blood.getText().toString().trim();
				if(strnickname.equals("")||strrealname.equals("")||strcsex.equals("")||strdate.equals("����������")||strheight.equals("")||strweight.equals("")||strblood.equals("")) {
					Toast.makeText(MyInfoActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if(!namecheck(strnickname)||!namecheck(strrealname)) {
					Toast.makeText(MyInfoActivity.this, "姓名与昵称为1到10位中文，英文字母和数字", Toast.LENGTH_SHORT).show();
					return;
				}
				if(!weightcheck(strheight)||!weightcheck(strweight)) {
					Toast.makeText(MyInfoActivity.this, "身高体重为0到1位小数的正数", Toast.LENGTH_SHORT).show();
					return;
				}
				User user=new User();
				user.setPhone(login_phone);
				user.setNickname(strnickname);
				user.setRealname(strrealname);
				user.setSex(strcsex);
				DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					date = sdf.parse(strdate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				user.setDate(date);
				user.setHeight(Double.valueOf(strheight));
				user.setWeight(Double.valueOf(strweight));
				user.setBlood(strblood);
				if (Login(login_phone, strnickname, strcsex, strdate, strheight, strweight, strblood, strrealname)) {
					Toast.makeText(MyInfoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
					Intent intent=new Intent(MyInfoActivity.this,MyselfActivity.class);
					startActivity(intent);
				}else {
					Toast.makeText(MyInfoActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	public void initView() {
		String url = HttpUtil.BASE_URL
				+ "/servlet/ClientMemberServlet?action=1&username=" + login_phone;
		String result = HttpUtil.queryStringForGet(url);
		System.out.println(result);
		try {
			JSONArray tables = new JSONArray(result);
			JSONObject obj_tmp = tables.getJSONObject(0);
			//memberVo.setName(obj_tmp.getString("name"));
			nickname.setText(obj_tmp.getString("user_name"));
			realname.setText(obj_tmp.getString("user_realname"));
			String sex=obj_tmp.getString("user_sex");
			if(sex.equals("男")) {
				boy.setChecked(true);
			}else if(sex.equals("女")){
				girl.setChecked(true);
			}
			startdate.setText(obj_tmp.getString("user_birth"));
			height.setText(obj_tmp.getString("user_height"));
			weight.setText(obj_tmp.getString("user_weight"));
			blood.setText(obj_tmp.getString("user_blood"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("mobile", "-----");
		}
	}
	private boolean Login(String user_phone, String user_name, String user_sex, String user_birth,
			String user_height, String user_weight, String user_blood, String user_realname) {
		String result = query(user_phone, user_name, user_sex, user_birth, user_height, user_weight,
				user_blood, user_realname);
		if (result != null && result.equals("success")) {
			// Toast.makeText(LoginActivity.this,result, Toast.LENGTH_SHORT).show();
			return true;
		} else {
			// saveUserMsg(result);
			return false;
		}
	}
	private String query(String user_phone, String user_name, String user_sex, String user_birth,
			String user_height, String user_weight, String user_blood, String user_realname) {
		String queryString ="user_phone=" + user_phone + "&user_name=" + user_name
				+ "&user_sex=" + user_sex + "&user_birth=" + user_birth + "&user_height=" + user_height
				+ "&user_weight=" + user_weight + "&user_blood=" + user_blood + "&user_realname=" + user_realname;
		String url = HttpUtil.BASE_URL + "servlet/ClientMemberServlet?action=2&" + queryString;
		return HttpUtil.queryStringForPost(url);
	}
	public boolean isNumeric(String str) {//数字检测
		Pattern pattern=Pattern.compile("[0-9]*");
		Matcher isNum=pattern.matcher(str);
		if(isNum.matches()) {
			return true;
		}else {
			return false;
		}
	}
	public boolean isMobileNo(String mobiles) {//手机号
		Pattern p=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m=p.matcher(mobiles);
		return m.matches();
	}
	public boolean passcheck(String str) {//6到16位字母和数字
		Pattern p=Pattern.compile("^[a-zA-Z0-9]{6,16}$");
		Matcher m=p.matcher(str);
		return m.matches();
	}
	public boolean namecheck(String str) {//1到10位中文，英文字母和数字
		Pattern p=Pattern.compile("^[\\u4e00-\\u9fa5_a-zA-Z0-9_]{1,10}$");
		Matcher m=p.matcher(str);
		return m.matches();
	}
	public boolean weightcheck(String str) {//0到1位小数的正数
		Pattern p=Pattern.compile("^[0-9]+(.[0-9]{0,1})?$");
		Matcher m=p.matcher(str);
		return m.matches();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_info, menu);
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
