package com.example.activity;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.example.application.CarAdapter;
import com.example.application.PeopleAdapter;
import com.example.dhew_system.AddCarActivity;
import com.example.util.HttpUtil;
import com.example.vo.MyCar;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
public class MyCarActivity extends Activity {
private List<MyCar> list=new ArrayList<MyCar>();
   Button insert;
   ListView listview;
   SharedPreferences mSharedPreferences;
   String login_phone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_car);
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
		insert=(Button) findViewById(R.id.my_car_btn1);
		mSharedPreferences=getSharedPreferences("SharedPreferences",
				Context.MODE_PRIVATE);
		login_phone=mSharedPreferences.getString("login_phone", null);
		listview=(ListView) findViewById(R.id.list_view);
		initcar();
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				MyCar car=list.get(position);
				Intent intent=new Intent(MyCarActivity.this,EditCarActivity.class);
				intent.putExtra("car", car);
				startActivity(intent);
				finish();
			}
		});
		insert.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MyCarActivity.this,AddCarActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
	private void initcar() {
		String url = HttpUtil.BASE_URL +"/servlet/GetCarServlet?action="+login_phone;
		String result = HttpUtil.queryStringForGet(url);
		try {
			JSONArray tables = new JSONArray(result);
			for(int i=0;i<tables.length();i++)
			{
				JSONObject obj_tmp = tables.getJSONObject(i);
				MyCar vo=new MyCar();
				vo.setCar_id(Integer.parseInt(obj_tmp.getString("car_id")));
				vo.setUser_phone(obj_tmp.getString("user_phone"));
				vo.setBrand(obj_tmp.getString("brand"));
				vo.setPlate(obj_tmp.getString("plate"));
				vo.setType(obj_tmp.getString("type"));
				vo.setDefault(Integer.parseInt(obj_tmp.getString("default_phone")));
				list.add(vo);
			}
			listview.setAdapter(new CarAdapter(MyCarActivity.this,R.layout.list_item,list));
		} catch (Exception e) {
				// TODO Auto-generated catch block
			e.printStackTrace();	
			System.out.println("装填错误");
		}
	}
}
