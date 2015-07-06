package com.hutu.longchang.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hutu.longchang.R;
import com.hutu.longchang.activity.DetailsActivity;
import com.hutu.longchang.model.Commodityd;
import com.hutu.longchang.model.NetWorkCallBack;
import com.hutu.longchang.model.Request;
import com.hutu.longchang.widget.ProgressDialogView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetaisFragment extends BaseFragment implements NetWorkCallBack {

	private TextView mTxt_leibie, mTxt_zhuce, mTxt_shenqigriqi, mTxt_zhuceriqi;
	private TextView mTxt_shangbiaoname, mTxt_shiyongshangpin, mTxt_qunzu;
	private TextView mTxt_shenqingren, mTxt_shenqingdizhi, mTxt_dailiren,
			mTxt_shangbiaozhuangtai;

	private Commodityd comm = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (mActivity instanceof DetailsActivity) {
			Intent intent = mActivity.getIntent();
			comm = (Commodityd) intent.getSerializableExtra("commond");
			ProgressDialogView.getInstance(mActivity).show();
			Request reuqest = new Request(mActivity, this);
			reuqest.stateList(comm.getRegNo());
			// request.stateList
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_details, null);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onPrevious() {
		super.onPrevious();
		mActivity.finish();
	}

	@Override
	public void init() {
		mTxt_leibie = (TextView) mView.findViewById(R.id.details_leibie_txt);

		mTxt_zhuce = (TextView) mView.findViewById(R.id.details_zhucehao_txt);

		mTxt_shenqigriqi = (TextView) mView
				.findViewById(R.id.details_shenqingriqi_txt);

		mTxt_zhuceriqi = (TextView) mView
				.findViewById(R.id.details_zhuceriqi_txt);

		mTxt_shangbiaoname = (TextView) mView
				.findViewById(R.id.details_shangpinname_txt);

		mTxt_shiyongshangpin = (TextView) mView
				.findViewById(R.id.details_shiyongshangpin_txt);

		mTxt_qunzu = (TextView) mView.findViewById(R.id.details_leisiqunzu_txt);

		mTxt_shenqingren = (TextView) mView
				.findViewById(R.id.details_shenqingren_txt);

		mTxt_shenqingdizhi = (TextView) mView
				.findViewById(R.id.details_shenqingdizhi_txt);

		mTxt_dailiren = (TextView) mView
				.findViewById(R.id.details_dailiren_txt);

		mTxt_shangbiaozhuangtai = (TextView) mView
				.findViewById(R.id.details_shangbiaozhuangtai_txt);

	}

	@Override
	public void initListener() {

	}

	@Override
	public void afterResponseFetched(int respCode, int flag, String msg) {
		System.out.println(msg);
		if (respCode == 0) {
			if (flag == Request.getReqeustFlag(Request.HostName
					+ "?method=SBStateList")) {
				ProgressDialogView.getInstance(mActivity).dismiss();
				try {
					JSONArray json = new JSONArray(msg);
					if (json.length() > 0) {
						for (int i = 0; i < json.length(); i++) {
							JSONObject obj = json.getJSONObject(i);
							mTxt_zhuce.setText(obj.getString("RegNO"));
//							map.put("regNo", obj.getString("RegNO"));
//							map.put("classify", obj.getString("IntCls"));
							mTxt_leibie.setText(obj.getString("IntCls"));
//							map.put("name", obj.getString("TMCN"));
//							map.put("TMEN", obj.getString("TMEN"));
							mTxt_shenqigriqi.setText(obj.getString("AppDate"));
//							map.put("AppDate", obj.getString("AppDate"));
							mTxt_zhuceriqi.setText(obj.getString("RegDate"));
//							map.put("RegDate", obj.getString("RegDate"));
//							map.put("TrialDate", obj.getString("TrialDate"));
//							map.put("InterRegDate",
//									obj.getString("InterRegDate"));
//							map.put("ValidDate", obj.getString("ValidDate"));
//							map.put("TrialNum", obj.getString("TrialNum"));
//							map.put("RegNum", obj.getString("RegNum"));
							mTxt_shenqingren.setText(obj.getString("TMApplicant"));
//							map.put("TMApplicant", obj.getString("TMApplicant"));
							mTxt_shenqingdizhi.setText(obj.getString("TMAddress"));
//							map.put("TMAddress", obj.getString("TMAddress"));
							mTxt_dailiren.setText(obj.getString("TMAgent"));
//							map.put("TMAgent", obj.getString("TMAgent"));
							mTxt_shiyongshangpin.setText(obj.getString("TMDetail"));
//							map.put("TMDetail", obj.getString("TMDetail"));
							mTxt_qunzu.setText(obj.getString("SimilarGroup"));
//							map.put("SimilarGroup",
//									obj.getString("SimilarGroup"));
							mTxt_shangbiaoname.setText(obj.getString("SBMC"));
							mTxt_shangbiaozhuangtai.setText(obj.getString("TMStatus"));
//							map.put("TMType", obj.getString("TMType"));
//							map.put("ISTotal", obj.getString("ISTotal"));
//							map.put("TMAreaNum", obj.getString("TMAreaNum"));
//							map.put("TMRemark", obj.getString("TMRemark"));
//							map.put("TMStatus", obj.getString("TMStatus"));
//							map.put("TMTY", obj.getString("TMTY"));
//							map.put("TMDY", obj.getString("TMDY"));
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		/*
		 * mTxt_leibie.setText(comm.getIntCls());
		 * mTxt_zhuce.setText(comm.getRegNo());
		 * 
		 * mTxt_shenqigriqi.setText(comm.getAppDate());
		 * mTxt_zhuceriqi.setText(comm.getRegDate());
		 * 
		 * mTxt_shangbiaoname.setText(comm.getTmType());
		 * mTxt_shiyongshangpin.setText(comm.getTmDetail());
		 * mTxt_qunzu.setText(comm.getSimilarGroup());
		 * mTxt_shenqingren.setText(comm.getTmApplicant());
		 * mTxt_shenqingdizhi.setText(comm.getTmAddress());
		 * mTxt_dailiren.setText(comm.getTmAgent());
		 * mTxt_shangbiaozhuangtai.setText(comm.getTmType());
		 */
	}

}
