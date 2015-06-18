package com.hutu.longchang.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.hutu.longchang.R;
import com.hutu.longchang.model.NetWorkCallBack;
import com.hutu.longchang.model.Request;

/**
 * 综合查询
 * @author hutu
 *
 */
public class SearchComplexActivity extends BaseActivity implements NetWorkCallBack{

//	Spinner eClassify;
	EditText eClassify;
	EditText eRegister;
	EditText eName;
	EditText ePeople;
	Spinner spinner;
	
	String []arrData = new String[]{"模糊", "精确"};
	ArrayList<HashMap<String ,String>>classifyData = new ArrayList<HashMap<String ,String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_search_complex);

		Request req = new Request(this, this);
		req.fenLeiListAll();

		eClassify = (EditText)findViewById(R.id.search_comple_edit_classify);
//		eClassify = (Spinner)findViewById(R.id.search_comple_edit_classify);
		eRegister = (EditText)findViewById(R.id.search_comple_edit_register);
		eName = (EditText)findViewById(R.id.search_comple_edit_name);
		ePeople = (EditText)findViewById(R.id.search_comple_edit_people);
		spinner = (Spinner)findViewById(R.id.search_comple_spinner);
		
//		arrData = getResources().getStringArray(R.array.search_comple_spinner);
		
		ArrayAdapter<String> ada = new ArrayAdapter<String>(this, R.layout.spinner_item ,arrData);
		
		spinner.setAdapter(ada);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 0xF1){
			String str = data.getStringExtra("data");
			eClassify.setText(str);
		}
	}
	
	public void infoClick(View v){
		Intent intent = new Intent(this, ClassifyListActivity.class);
		intent.putExtra("data", classifyData);
		this.startActivityForResult(intent, 0);
	}
	
	public void searchClick(View v){
		Request req = new Request(this, this);
//		HashMap<String ,String> map = classifyData.get((int) eClassify.getSelectedItemId());
//		String classify = map.get("classify");
		String classify = eClassify.getText().toString();
		
		String reg = eRegister.getText().toString();
		reg = reg.trim();
		String name = eName.getText().toString();
		name = name.trim();
		String people = ePeople.getText().toString();
		people = people.trim();
		
		if(reg.length() == 0 && name.length() == 0 && people.length() == 0){
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setMessage("请输入一个查询关键字");
			dialog.show();
		}else{
			req.zongHeInfo(classify, reg, name, people, null);
		}
		
	}
	
	@Override
	public void afterResponseFetched(int respCode, int flag, String msg) {
		if(respCode == 0){
			if(flag == Request.getReqeustFlag(Request.HostName + "?method=SBZongHeInfo")){
				try {
					JSONArray json = new JSONArray(msg);
					if(json.length() > 0 ){
						ArrayList<HashMap<String ,String>>arrData = new ArrayList<HashMap<String ,String>>();
						
						for(int i= 0;i< json.length() ;i++){
							JSONObject obj = json.getJSONObject(i);
							HashMap<String ,String> map = new HashMap<String, String>();
							map.put("regNo", obj.getString("RegNO"));
							map.put("classify", obj.getString("IntCls"));
							map.put("name", obj.getString("TMCN"));
							arrData.add(map);
						}
						
						Intent intent = new Intent(this, ListActivity.class);
						intent.putExtra("data", arrData);
						startActivity(intent);
					}else{
						AlertDialog.Builder dialog = new AlertDialog.Builder(this);
						dialog.setMessage("没有找到相关数据");
						dialog.setTitle("提示");
						dialog.setPositiveButton("确认",null);
						dialog.setNegativeButton("取消",null);
						dialog.show();
					}
					return;
				} catch (JSONException e) {
					e.printStackTrace();
				}

				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				dialog.setMessage("参数不正确");
				dialog.setTitle("提示");
				dialog.setPositiveButton("确认",null);
				dialog.setNegativeButton("取消",null);
				dialog.show();
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
//						ArrayAdapter<String> ada = new ArrayAdapter<String>(this, R.layout.spinner_item ,arr);
//						eClassify.setAdapter(ada);
						return;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				dialog.setMessage("参数不正确");
				dialog.setTitle("提示");
				dialog.setPositiveButton("确认",null);
				dialog.setNegativeButton("取消",null);
				dialog.show();
			}
		}
	}
}
