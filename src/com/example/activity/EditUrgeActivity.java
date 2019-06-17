package com.example.activity;
import com.example.util.HttpUtil;
import com.example.vo.UrgePeople;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;
public class EditUrgeActivity extends Activity {
	EditText name;
	EditText phone;
	ToggleButton Default;
	Button finish;
	Button delete;
	Integer id;
	boolean change;
	UrgePeople people;
	SharedPreferences mSharedPreferences;
	private String login_phone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_urge);
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
		people=(UrgePeople) getIntent().getSerializableExtra("people");
		name=(EditText) findViewById(R.id.edit_urge_edi1);
		phone=(EditText) findViewById(R.id.edit_urge_edi2);
		Default=(ToggleButton) findViewById(R.id.edit_urge_but2);
		finish=(Button) findViewById(R.id.edit_urge_btn3);
		delete=(Button) findViewById(R.id.edit_urge_but1);
		mSharedPreferences=getSharedPreferences("SharedPreferences",
				Context.MODE_PRIVATE);
		login_phone=mSharedPreferences.getString("login_phone", null);
		id=people.getEme_id();
		name.setText(people.getContact_name());
		phone.setText(people.getContact_phone());
		Default.setChecked(people.getDefault_phone()==1);
		change=(people.getDefault_phone()==1);
		CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //设置LineLayout的垂直布局
                }
                else{
                    //设置LineLatout的水平布局
                }
            }
        };
        Default.setOnCheckedChangeListener(listener);
        delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Login3(String.valueOf(people.getEme_id()),login_phone)) {
					Intent intent=new Intent(EditUrgeActivity.this,MyUrgeActivity.class);
					startActivity(intent);
					finish();
				}else {
					Toast.makeText(EditUrgeActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});
        finish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Default.isChecked()==change) {
					String strname=name.getText().toString().trim();
					String strphone=phone.getText().toString().trim();
					if(Login(String.valueOf(people.getEme_id()),strname,strphone)) {
						Intent intent=new Intent(EditUrgeActivity.this,MyUrgeActivity.class);
						startActivity(intent);
						finish();
					}else {
						Toast.makeText(EditUrgeActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
					}
				}else {
					if(Default.isChecked()) {
						String strname=name.getText().toString().trim();
						String strphone=phone.getText().toString().trim();
						if(Login2(login_phone,String.valueOf(people.getEme_id()),strname,strphone)) {
							Intent intent=new Intent(EditUrgeActivity.this,MyUrgeActivity.class);
							startActivity(intent);
							finish();
						}else {
							Toast.makeText(EditUrgeActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
							return;
						}
					}else {
						Toast.makeText(EditUrgeActivity.this, "必须有一个默认", Toast.LENGTH_SHORT).show();
						return;
					}
				}
			}
		});
	}
	private boolean Login(String eme_id,String contact_name,String contact_phone) {
			String result = query(eme_id,contact_name,contact_phone);
			if (result != null && result.equals("success")) {
				//Toast.makeText(LoginActivity.this,result, Toast.LENGTH_SHORT).show();
				return true;
			} else {
				// saveUserMsg(result);
				return false;
			}
		}
		private String query(String eme_id,String contact_name,String contact_phone) {
			String queryString ="action=1"+"&eme_id=" + eme_id+
					"&contact_name=" + contact_name
					+ "&contact_phone=" + contact_phone ;
			String url = HttpUtil.BASE_URL + "servlet/UpdateUrgeServlet?" + queryString;
			return HttpUtil.queryStringForPost(url);
		}
		private boolean Login2(String user_phone,String eme_id,String contact_name,String contact_phone) {
			String result = query2(user_phone,eme_id,contact_name,contact_phone);
			if (result != null && result.equals("success")) {
				//Toast.makeText(LoginActivity.this,result, Toast.LENGTH_SHORT).show();
				return true;
			} else {
				// saveUserMsg(result);
				return false;
			}
		}
      private String query2(String user_phone,String eme_id,String contact_name,String contact_phone) {
			String queryString ="action=2"+"&user_phone=" + user_phone+"&eme_id=" + eme_id+
					"&contact_name=" + contact_name
					+ "&contact_phone=" + contact_phone ;
			String url = HttpUtil.BASE_URL + "servlet/UpdateUrgeServlet?" + queryString;
			return HttpUtil.queryStringForPost(url);
		}
      private boolean Login3(String eme_id,String user_name) {
			String result = query3(eme_id,user_name);
			if (result != null && result.equals("success")) {
				//Toast.makeText(LoginActivity.this,result, Toast.LENGTH_SHORT).show();
				return true;
			} else {
				// saveUserMsg(result);
				return false;
			}
		}
		private String query3(String eme_id,String user_name) {
			String queryString ="action=3"+"&eme_id=" + eme_id+
					"&user_name=" + user_name ;
			String url = HttpUtil.BASE_URL + "servlet/UpdateUrgeServlet?" + queryString;
			return HttpUtil.queryStringForPost(url);
		}
}
