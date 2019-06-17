package com.example.healthy;
import java.util.ArrayList;
import java.util.List;
import com.example.dhew_system.R;
import com.example.dhew_system.R.id;
import com.example.dhew_system.R.layout;
import com.example.dhew_system.R.menu;
import com.example.healthy.HeartActivity.MyReceiver;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
public class IndorTempActivity extends Activity {
	CharView lineChartView;
	MyReceiver myReceiver;
	List<Double> list ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_indor_temp);
		this.setTitle("室温");
		lineChartView = (CharView) findViewById(R.id.CharView);
		list = new ArrayList<Double>();
		for(int i = 0; i < 24; i ++)
			list.add(70.0+i);
	        //给折线图设置数据，和颜色
	    lineChartView.setDatas(list, Color.BLACK);
	    myReceiver = new MyReceiver(this);
		myReceiver.registerAction("shiwen");
	}
	class MyReceiver extends BroadcastReceiver {
        Context context;    
        public MyReceiver(Context c){ 
            context = c;
        }
        //动态注册  
        public void registerAction(String action){
            IntentFilter filter = new IntentFilter();    
            filter.addAction(action);    
            context.registerReceiver(this,filter);    
        }    
        @Override    
        public void onReceive(Context context, Intent intent) {    
               //接收广播内容. 
        	list.remove(0);
        	list.add(Double.valueOf(intent.getExtras().getString("name")));
        	lineChartView.setDatas(list, Color.BLACK);
            }    
        } 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.indor_temp, menu);
		return true;
	}
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return false;
        }
        return super.onKeyDown(keyCode, event);
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
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(myReceiver);  
		super.onDestroy();
	}
}
