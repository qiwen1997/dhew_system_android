package com.example.dhew_system;
import com.example.activity.MyCarActivity;
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
public class AddCarActivity extends Activity {
	EditText brand;
	EditText plate;
	EditText type;
	Button save;
	SharedPreferences mSharedPreferences;
	String login_phone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_car);
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
		brand=(EditText) findViewById(R.id.add_car_edi3);
		plate=(EditText) findViewById(R.id.add_car_edi1);
		type=(EditText) findViewById(R.id.add_car_edi2);
		save=(Button) findViewById(R.id.add_car_btn3);
		mSharedPreferences=getSharedPreferences("SharedPreferences",
				Context.MODE_PRIVATE);
		login_phone=mSharedPreferences.getString("login_phone", null);
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String strbrand=brand.getText().toString().trim();
				String strplate=plate.getText().toString().trim();
				String strtype=type.getText().toString().trim();
				if(strbrand.equals("")||strplate.equals("")||strtype.equals("")) {
					Toast.makeText(AddCarActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if(Login(login_phone,strbrand,strplate,strtype)) {
					Intent intent=new Intent(AddCarActivity.this,MyCarActivity.class);
					startActivity(intent);
					finish();
				}else {
					Toast.makeText(AddCarActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});
	}
	private boolean Login(String user_phone,String brand,String plate
			,String type) {
			String result = query(user_phone, brand,plate,type);
			if (result != null && result.equals("success")) {
				//Toast.makeText(LoginActivity.this,result, Toast.LENGTH_SHORT).show();
				return true;
			} else {
				// saveUserMsg(result);
				return false;
			}
		}
		private String query(String user_phone,String brand,String plate
				,String type) {
			String queryString = "user_phone=" + user_phone + "&brand=" + brand
					+ "&plate=" +  plate + "&type=" + type;
			String url = HttpUtil.BASE_URL + "servlet/CarServlet?" + queryString;
			return HttpUtil.queryStringForPost(url);
		}
}
