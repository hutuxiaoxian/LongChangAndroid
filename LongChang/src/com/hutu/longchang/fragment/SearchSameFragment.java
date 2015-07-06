package com.hutu.longchang.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hutu.longchang.R;
import com.hutu.longchang.activity.ListActivity;
import com.hutu.longchang.activity.MainActivity;
import com.hutu.longchang.model.NetWorkCallBack;
import com.hutu.longchang.model.Request;
import com.hutu.longchang.widget.ProgressDialogView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchSameFragment extends BaseFragment implements NetWorkCallBack{

	EditText eClassify;
	EditText eContent;
	Spinner spinner ,sp;
	private Button info_btn;
	private Button mSerach ;
	ArrayList<HashMap<String ,String>>classifyData = new ArrayList<HashMap<String ,String>>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_search_same, null);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void onPrevious() {
		super.onPrevious();
		showFragment(new MainFragment(), Constant.TAG_MAIN);
	}
	@Override
	public void init() {
		Request req = new Request(mActivity, SearchSameFragment.this);
		req.fenLeiListAll();
		mSerach = (Button) mView.findViewById(R.id.search_same_bt_search);
		eClassify = (EditText)mView.findViewById(R.id.search_same_edit_classify);
		eContent = (EditText)mView.findViewById(R.id.search_same_edit_content);
		spinner = (Spinner)mView.findViewById(R.id.search_same_spinner);
		info_btn = (Button) mView.findViewById(R.id.info_btn);
		String []items = new String[]{"汉字", "英语", "数字", "字头"};
		ArrayAdapter<String> ada = new ArrayAdapter<String>(mActivity, R.layout.spinner_item ,items);
		spinner.setAdapter(ada);
		setTitle("近似查询");
		
		String []arrData = new String[]{"精确","模糊"};
		sp = (Spinner)mView.findViewById(R.id.search_comple_spinner);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mActivity, R.layout.spinner_item ,arrData);
		
		sp.setAdapter(adapter);
		Bundle bundle = new Bundle();
		bundle = getArguments();
		if(bundle != null){
			String getClassify = bundle.getSerializable("data").toString();
			eClassify.setText(getClassify);
		}
//		String getClassify = getArguments().getString("data");
		
	}

	@Override
	public void onResume() {
		super.onResume();
//		if(null != getArguments().getSerializable("data")){
//			eClassify.setText(getArguments().getSerializable("data").toString());
//		}
	}
	@Override
	public void initListener() {
		mSerach.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {
				Request req = new Request(mActivity, SearchSameFragment.this);
//				HashMap<String ,String> map = classifyData.get((int) eClassify.getSelectedItemId());
//				String classify = map.get("classify");
				String classify = eClassify.getText().toString().trim();
				String context = eContent.getText().toString();
				if(TextUtils.isEmpty(context)){
					AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
					dialog.setTitle("提示");
					dialog.setMessage("请输入查询内容");
					dialog.setPositiveButton("知道了",null);
					dialog.show();
				}else{
					ProgressDialogView.getInstance(mActivity).show();
					int item = (int) spinner.getSelectedItemId();
					switch (item) {
					case 0:
						item = 1;
						break;
					case 1:
						item = 3;
						break;
					case 2:
						item = 4;
						break;
					case 3:
						item = 5;
						break;
					default:
						item = 2;
						break;
					}
					int jing = (int) sp.getSelectedItemId();
					req.jinsichaxun(classify, context, ""+item , 1 , 20,jing);
				}
			}
		});
		info_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
//				Intent intent = new Intent(this, ClassifyListActivity.class);
//				intent.putExtra("data", classifyData);
//				this.startActivityForResult(intent, 0);
				Bundle bundle = new Bundle();
//				bundle.putSerializable("data", classifyData);
				bundle.putInt("type", 0);
				BaseFragment fragment = new ShopListFragment();
				fragment.setArguments(bundle);
				showFragment(fragment, Constant.TAG_SHOPLIST);
			}
		});
	}
	@Override
	public void afterResponseFetched(int respCode, int flag, String msg) {
		ProgressDialogView.getInstance(mActivity).dismiss();
		if(respCode == 0){
			if(flag == Request.getReqeustFlag(Request.HostName + "?method=SBJinSiChaXun")){
				
				try {
					JSONObject jsonObj = new JSONObject(msg);
					JSONArray json = jsonObj.getJSONArray("data");
					int count = jsonObj.getInt("total");
					if(json.length() > 0 ){
						ArrayList<HashMap<String ,String>>arrData = new ArrayList<HashMap<String ,String>>();
						for(int i= 0;i< json.length() ;i++){
							JSONObject obj = json.getJSONObject(i);
							HashMap<String ,String> map = new HashMap<String, String>();
							map.put("regNo", obj.getString("RegNO"));
							map.put("classify", obj.getString("IntCls"));
							map.put("name", obj.getString("TMCN")+obj.getString("TMEN")+obj.getString("TMZT"));
							map.put("TMEN",obj.getString("TMEN"));
							map.put("AppDate",obj.getString("AppDate"));
							map.put("RegDate",obj.getString("RegDate"));
							map.put("TrialDate",obj.getString("TrialDate"));
							map.put("InterRegDate",obj.getString("InterRegDate"));
							map.put("ValidDate",obj.getString("ValidDate"));
							map.put("TrialNum",obj.getString("TrialNum"));
							map.put("RegNum",obj.getString("RegNum"));
							map.put("TMApplicant",obj.getString("TMApplicant"));
							map.put("TMAddress",obj.getString("TMAddress"));
							map.put("TMAgent",obj.getString("TMAgent"));
							map.put("TMDetail",obj.getString("TMDetail"));
							map.put("SimilarGroup",obj.getString("SimilarGroup"));
							map.put("TMType",obj.getString("TMType"));
							map.put("ISTotal",obj.getString("ISTotal"));
							map.put("TMAreaNum",obj.getString("TMAreaNum"));
							map.put("TMRemark",obj.getString("TMRemark"));
							map.put("TMStatus",obj.getString("TMStatus"));
							map.put("TMTY",obj.getString("TMTY"));
							map.put("TMDY",obj.getString("TMDY"));
							arrData.add(map);
						}
//						BaseFragment fragment = new MyCardFragment();
//						Bundle bundle = new Bundle();
//						bundle.putSerializable("posData", posList.getPosList());
//						fragment.setArguments(bundle);
//						fragment.setOnTradeListener(new AccountTradeListener(TAG_MYCARDFRAGMENT));
//						addFragment(fragment, R.id.center_frame, TAG_MYCARDFRAGMENT);
						BaseFragment fragment = new ListFragment();
						Bundle bundle = new Bundle();
						bundle.putInt("count", count);
						bundle.putString("type", "same");
						
						HashMap<String, String> map = new HashMap<String, String>();
						String classify = eClassify.getText().toString().trim();
						String context = eContent.getText().toString();
						int item = (int) spinner.getSelectedItemId();
						switch (item) {
						case 0:
							item = 1;
							break;
						case 1:
							item = 3;
							break;
						case 2:
							item = 4;
							break;
						case 3:
							item = 5;
							break;
						default:
							item = 2;
							break;
						}
						int jing = (int) sp.getSelectedItemId();
						map.put("TabNum", classify);
						map.put("typeName", context);
						map.put("type", item+"");
						map.put("jingmo", jing+"");
						bundle.putSerializable("request", map);
						bundle.putInt("count", count);
						bundle.putSerializable("data", arrData);
						fragment.setArguments(bundle);
						showFragment(fragment, Constant.TAG_SEARCHLIST);
						
//						Intent intent = new Intent(mActivity, ListActivity.class);
//						intent.putExtra("data", arrData);
//						startActivity(intent);
					}else{
						AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
						dialog.setTitle("提示");
						dialog.setMessage("没有找到相关数据");
						dialog.setPositiveButton("确认",null);
						dialog.setNegativeButton("取消",null);
						dialog.show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}else if(flag == Request.getReqeustFlag(Request.HostName + "?method=SBFenLeiListAll")){
				try {
					JSONArray json = new JSONArray(msg);
					if(json.length() > 0 ){
						String arr[] = new String[json.length()];
						for(int i= 0;i< json.length() ;i++){
							JSONObject obj = json.getJSONObject(i);
							HashMap<String ,String> map = new HashMap<String, String>();
							map.put("classify", obj.getString("IntCls"));
							map.put("DetailName", obj.getString("DetailName"));
							arr[i] = obj.getString("DetailName");
							classifyData.add(map);
						}
						
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	
	}

}
