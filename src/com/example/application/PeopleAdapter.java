package com.example.application;
import java.util.List;
import com.example.dhew_system.R;
import com.example.vo.UrgePeople;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
public class PeopleAdapter extends ArrayAdapter<UrgePeople>{
	private int resourceId;
	public PeopleAdapter(Context context, int textViewResourceId, List<UrgePeople> objects) {
		super(context, textViewResourceId, objects);
		resourceId=textViewResourceId;
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		UrgePeople people=getItem(position);
		View view=LayoutInflater.from(getContext()).inflate(resourceId, parent,false);
		TextView peoplename=(TextView) view.findViewById(R.id.list_tex1);
		TextView peopletele=(TextView) view.findViewById(R.id.list_tex2);
		TextView Default=(TextView) view.findViewById(R.id.list_tex3);
		peoplename.setText(people.getContact_name());
		peopletele.setText(people.getContact_phone());
		if(people.getDefault_phone()==1) {
			Default.setText("默认");
		}
		return view;
	}
}