package com.example.activity;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.application.PeopleAdapter;
import com.example.dhew_system.AddUrgeActivity;
import com.example.util.HttpUtil;
import com.example.vo.UrgePeople;
import com.example.dhew_system.R;
import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
public class MyUrgeActivity extends Activity {
    List<UrgePeople> list=new ArrayList<UrgePeople>();
    Button insert;
    SharedPreferences mSharedPreferences;
    String login_phone;
    ListView listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_urge);
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
		insert=(Button) findViewById(R.id.my_urge_btn1);
		mSharedPreferences=getSharedPreferences("SharedPreferences",
				Context.MODE_PRIVATE);
		login_phone=mSharedPreferences.getString("login_phone", null);
		listview=(ListView) findViewById(R.id.list_view);
		initpeople();
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				UrgePeople people=list.get(position);
				Intent intent=new Intent(MyUrgeActivity.this,EditUrgeActivity.class);
				intent.putExtra("people", people);
				startActivity(intent);
				finish();
			}
		});
		insert.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MyUrgeActivity.this,AddUrgeActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
	private void initpeople() {
		String url = HttpUtil.BASE_URL +"/servlet/GetUrgeServlet?action="+login_phone;
		String result = HttpUtil.queryStringForGet(url);
		try {
			JSONArray tables = new JSONArray(result);
			for(int i=0;i<tables.length();i++)
			{
				JSONObject obj_tmp = tables.getJSONObject(i);
				UrgePeople vo=new UrgePeople();
				vo.setEme_id(Integer.parseInt(obj_tmp.getString("eme_id")));
				vo.setUser_phone(obj_tmp.getString("user_phone"));
				vo.setContact_name(obj_tmp.getString("contact_name"));
				vo.setContact_phone(obj_tmp.getString("contact_phone"));
				vo.setDefault_phone(Integer.parseInt(obj_tmp.getString("default_phone")));
				list.add(vo);
			}
			listview.setAdapter(new PeopleAdapter(MyUrgeActivity.this,R.layout.list_item,list));
		} catch (Exception e) {
				// TODO Auto-generated catch block
			e.printStackTrace();	
			System.out.println("装填错误");
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_urge, menu);
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
