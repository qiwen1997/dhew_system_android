package com.example.activity;
import com.example.util.HttpUtil;
import com.example.vo.MyCar;
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
import android.widget.ToggleButton;
public class EditCarActivity extends Activity {
	EditText plate;
	EditText type;
	EditText brand;
	ToggleButton Default;
	Button finish;
	Button delete;
	Integer id;
	boolean change;
	MyCar car;
	SharedPreferences mSharedPreferences;
	private String login_phone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_car);
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
		car=(MyCar) getIntent().getSerializableExtra("car");
		plate=(EditText) findViewById(R.id.edit_car_edi1);
		type=(EditText) findViewById(R.id.edit_car_edi2);
		brand=(EditText) findViewById(R.id.edit_car_edi3);
		Default=(ToggleButton) findViewById(R.id.edit_car_but2);
		finish=(Button) findViewById(R.id.edit_car_btn3);
		delete=(Button) findViewById(R.id.edit_car_but1);
		mSharedPreferences=getSharedPreferences("SharedPreferences",
				Context.MODE_PRIVATE);
		login_phone=mSharedPreferences.getString("login_phone", null);
		plate.setText(car.getPlate());
		type.setText(car.getType());
		brand.setText(car.getBrand());
		Default.setChecked(car.getDefault()==1);
		change=(car.getDefault()==1);
		 delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(Login3(String.valueOf(car.getCar_id()),login_phone)) {
						Intent intent=new Intent(EditCarActivity.this,MyCarActivity.class);
						startActivity(intent);
						finish();
					}else {
						Toast.makeText(EditCarActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
						return;
					}
				}
			});
	        finish.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(Default.isChecked()==change) {
						String strplate=plate.getText().toString().trim();
						String strbrand=brand.getText().toString().trim();
						String strtype=type.getText().toString().trim();
						if(Login(String.valueOf(car.getCar_id()),strbrand,strplate,strtype)) {
							Intent intent=new Intent(EditCarActivity.this,MyCarActivity.class);
							startActivity(intent);
							finish();
						}else {
							Toast.makeText(EditCarActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
							return;
						}
					}else {
						if(Default.isChecked()) {
							String strplate=plate.getText().toString().trim();
							String strbrand=brand.getText().toString().trim();
							String strtype=type.getText().toString().trim();
							if(Login2(login_phone,String.valueOf(car.getCar_id()),strbrand,strplate,strtype)) {
								Intent intent=new Intent(EditCarActivity.this,MyCarActivity.class);
								startActivity(intent);
								finish();
							}else {
								Toast.makeText(EditCarActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
								return;
							}
						}else {
							Toast.makeText(EditCarActivity.this, "必须有一个默认", Toast.LENGTH_SHORT).show();
							return;
						}
					}
				}
			});
		}
	private boolean Login(String car_id,String brand,String plate,String type) {
		String result = query(car_id,brand,plate,type);
		if (result != null && result.equals("success")) {
			//Toast.makeText(LoginActivity.this,result, Toast.LENGTH_SHORT).show();
			return true;
		} else {
			// saveUserMsg(result);
			return false;
		}
	}
	private String query(String car_id,String brand,String plate,String type) {
		String queryString ="action=1"+"&car_id=" + car_id+
				"&brand=" + brand
				+ "&plate=" + plate+ "&type=" +type ;
		String url = HttpUtil.BASE_URL + "servlet/UpdateCarServlet?" + queryString;
		return HttpUtil.queryStringForPost(url);
	}
	private boolean Login2(String user_phone,String car_id,String brand,String plate,String type) {
		String result = query2(user_phone,car_id,brand,plate,type);
		if (result != null && result.equals("success")) {
			//Toast.makeText(LoginActivity.this,result, Toast.LENGTH_SHORT).show();
			return true;
		} else {
			// saveUserMsg(result);
			return false;
		}
	}
  private String query2(String user_phone,String car_id,String brand,String plate,String type) {
		String queryString ="action=2"+"&user_phone=" + user_phone+"&car_id=" + car_id+
				"&brand=" + brand
				+ "&plate=" + plate + "&type=" + type;
		String url = HttpUtil.BASE_URL + "servlet/UpdateCarServlet?" + queryString;
		return HttpUtil.queryStringForPost(url);
	}
  private boolean Login3(String car_id,String user_name) {
		String result = query3(car_id,user_name);
		if (result != null && result.equals("success")) {
			//Toast.makeText(LoginActivity.this,result, Toast.LENGTH_SHORT).show();
			return true;
		} else {
			// saveUserMsg(result);
			return false;
		}
	}
	private String query3(String car_id,String user_name) {
		String queryString ="action=3"+"&car_id=" + car_id+
				"&user_name=" + user_name ;
		String url = HttpUtil.BASE_URL + "servlet/UpdateCarServlet?" + queryString;
		return HttpUtil.queryStringForPost(url);
	}
}
