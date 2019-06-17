package com.example.activity;

import com.example.dhew_system.HomeFragment;
import com.example.dhew_system.R;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyselfActivity extends Fragment {
	Button myinfo;
	Button myurge;
	Button mycar;
	Button company;
	Button zhuxiao;
	SharedPreferences mSharedPreferences;
	SharedPreferences.Editor mEditor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_myself, container, false);
		iniView(view);
		return view;
	}

	public void iniView(View view) {
		myinfo = (Button) view.findViewById(R.id.myself_but1);
		myinfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), MyInfoActivity.class);
				startActivity(intent);
			}
		});
		myurge = (Button) view.findViewById(R.id.myself_but2);
		myurge.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), MyUrgeActivity.class);
				startActivity(intent);
			}
		});
		mycar = (Button) view.findViewById(R.id.myself_but3);
		mycar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), MyCarActivity.class);
				startActivity(intent);
			}
		});
		company = (Button) view.findViewById(R.id.myself_but4);
		company.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), CompanyActivity.class);
				startActivity(intent);
			}
		});
		zhuxiao = (Button) view.findViewById(R.id.myself_but8);
		zhuxiao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mSharedPreferences = getActivity().getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
				mEditor = mSharedPreferences.edit();
				mEditor.putString("login_phone", null);
				mEditor.putString("login_password", null);
				mEditor.commit();
				HomeFragment.ConnectedThread1.interrupted();
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
				getActivity().finish();
			}
		});
	}
}
