package com.example.application;
import java.util.List;
import com.example.dhew_system.R;
import com.example.vo.MyCar;
import com.example.vo.UrgePeople;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
public class CarAdapter extends ArrayAdapter<MyCar>{
	private int resourceId;
	public CarAdapter(Context context, int textViewResourceId, List<MyCar> objects) {
		super(context, textViewResourceId, objects);
		resourceId=textViewResourceId;
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MyCar car=getItem(position);
		View view=LayoutInflater.from(getContext()).inflate(resourceId, parent,false);
		TextView brand=(TextView) view.findViewById(R.id.list_tex1);
		TextView plate=(TextView) view.findViewById(R.id.list_tex2);
		TextView Default=(TextView) view.findViewById(R.id.list_tex3);
		brand.setText(car.getBrand());
		plate.setText(car.getPlate());
		if(car.getDefault()==1) {
			Default.setText("默认");
		}
		return view;
	}
}