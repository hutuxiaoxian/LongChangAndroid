package com.hutu.longchang.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hutu.longchang.R;
import com.hutu.longchang.widget.HeadTitleView;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public abstract class BaseFragment extends Fragment implements Constant {

	public String userName = null;
	public String userId = null;
	public Resources mRes = null;
	public Activity mActivity = null;
	public FragmentManager mFragmentManager = null;
	public View mView = null;
	public FragmentTransaction fragmentTrans;
	public HeadTitleView mHeadView;
	public Button mHeadLeftBtn, mHeadRagitBtn;
	public TextView mHeadTitleText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mActivity = this.getActivity();
		mRes = mActivity.getResources();
		mFragmentManager = this.getActivity().getSupportFragmentManager();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initTitle();
		init();
		initListener();
		return mView;
	}

	public void initTitle() {
		mHeadView = (HeadTitleView) mView.findViewById(R.id.trade_manager_head);
		mHeadView.setVisible(HeadTitleView.mRgiteVisbel);
		mHeadLeftBtn = (Button) mView.findViewById(R.id.titlebar_back);
		mHeadLeftBtn.setOnClickListener(onClick);
		mHeadRagitBtn = (Button) mView.findViewById(R.id.titlebar_action);
		mHeadRagitBtn.setOnClickListener(onClick);
		mHeadTitleText = (TextView) mView.findViewById(R.id.titlebar_text);
	}

	public OnClickListener onClick = new OnClickListener() {

		@Override
		public void onClick(View paramView) {
			int id = paramView.getId();
			switch (id) {
			case R.id.titlebar_back:
				onPrevious();
				break;
			case R.id.titlebar_action:
				onNext();
				break;
			default:
				break;
			}
		}
	};

	public abstract void init();

	public abstract void initListener();

	public void setTitle(String titleName) {
		mHeadTitleText.setText(titleName);
	}

	public void headVisble(int visble) {
		mHeadView.setVisible(visble);
	}

	/**
	 * 返回点击事件
	 */
	public void onPrevious() {
	}

	public void onNext() {

	}

	/**
	 * 添加Fragment
	 * 
	 * @param fragment
	 * @param id
	 * @param tag
	 */
	public void addFragment(Fragment fragment, int id, String tag) {
		fragmentTrans = mFragmentManager.beginTransaction();
		fragmentTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		fragmentTrans.add(id, fragment, tag);
		fragmentTrans.addToBackStack(null);
		fragmentTrans.commit();
	}

	/**
	 * 删除当前fragment
	 */
	public void removeFragment(BaseFragment fragment) {
		fragmentTrans = mFragmentManager.beginTransaction();
		fragmentTrans.remove(fragment);
		fragmentTrans.commit();
	}

	/**
	 * 删除Fragment
	 * 
	 * @param tag
	 */
	public void removeFragment(String tag) {
		fragmentTrans = mFragmentManager.beginTransaction();
		Fragment fragment = mFragmentManager.findFragmentByTag(tag);
		if (null != fragment && fragment.isAdded()) {
			fragmentTrans.remove(fragment);
			mFragmentManager.popBackStack();
			fragmentTrans.commit();
		}
	}

	public void showFragment(BaseFragment fragment, String tag) {
		fragmentTrans = mFragmentManager.beginTransaction();
		fragmentTrans.replace(R.id.main_relatlayout, fragment, tag);
		fragmentTrans.commit();
	}

	public ArrayList<HashMap<String, String>> json(JSONArray json) {
		ArrayList<HashMap<String, String>> arrData = null;
		try {
			if (json.length() > 0) {
				arrData = new ArrayList<HashMap<String, String>>();
				for (int i = 0; i < json.length(); i++) {
					JSONObject obj = json.getJSONObject(i);
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("regNo", obj.getString("RegNO"));
					map.put("classify", obj.getString("IntCls"));
					map.put("name", obj.getString("TMCN"));
					// dityd.setIntCls(json.getString("IntCls"));
					// dityd.setRegNo(json.getString("RegNO"));
					// dityd.setTmcn(json.getString("TMCN"));
					map.put("TMEN", obj.getString("TMEN"));
					map.put("AppDate", obj.getString("AppDate"));
					map.put("RegDate", obj.getString("RegDate"));
					map.put("TrialDate", obj.getString("TrialDate"));
					map.put("InterRegDate", obj.getString("InterRegDate"));
					map.put("ValidDate", obj.getString("ValidDate"));
					map.put("TrialNum", obj.getString("TrialNum"));
					map.put("RegNum", obj.getString("RegNum"));
					map.put("TMApplicant", obj.getString("TMApplicant"));
					map.put("TMAddress", obj.getString("TMAddress"));
					map.put("TMAgent", obj.getString("TMAgent"));
					map.put("TMDetail", obj.getString("TMDetail"));
					map.put("SimilarGroup", obj.getString("SimilarGroup"));
					map.put("TMType", obj.getString("TMType"));
					map.put("ISTotal", obj.getString("ISTotal"));
					map.put("TMAreaNum", obj.getString("TMAreaNum"));
					map.put("TMRemark", obj.getString("TMRemark"));
					map.put("TMStatus", obj.getString("TMStatus"));
					map.put("TMTY", obj.getString("TMTY"));
					map.put("TMDY", obj.getString("TMDY"));
					arrData.add(map);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrData;
	}
}
