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
import android.widget.TextView;

import com.hutu.longchang.R;
import com.hutu.longchang.model.NetWorkCallBack;
import com.hutu.longchang.model.Request;

/**
 * 近似查询
 * @author hutu
 *
 */
public class SearchSameActivity extends BaseActivity implements NetWorkCallBack{
	
	EditText eClassify;
	EditText eContent;
	Spinner spinner;
	
	ArrayList<HashMap<String ,String>>classifyData = new ArrayList<HashMap<String ,String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_search_same);
		
		Request req = new Request(this, this);
		req.fenLeiListAll();
		
		eClassify = (EditText)findViewById(R.id.search_same_edit_classify);
		eContent = (EditText)findViewById(R.id.search_same_edit_content);
		spinner = (Spinner)findViewById(R.id.search_same_spinner);
		
		String []items = new String[]{"汉字", "英语", "数字", "字头"};
		ArrayAdapter<String> ada = new ArrayAdapter<String>(this, R.layout.spinner_item ,items);
		spinner.setAdapter(ada);
		
		String []arrData = new String[]{"模糊", "精确"};
		Spinner sp = (Spinner)findViewById(R.id.search_comple_spinner);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item ,arrData);
		
		sp.setAdapter(adapter);
		
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
		String classify = eClassify.getText().toString().trim();
		String context = eContent.getText().toString();
		int item = (int) spinner.getSelectedItemId();
		switch (item) {
		case 0:
			item += 1;
			break;

		default:
			item += 3;
			break;
		}
//		req.jinsichaxun(classify, context, ""+item);
	}
	
	@Override
	public void afterResponseFetched(int respCode, int flag, String msg) {
		// TODO Auto-generated method stub
		if(respCode == 0){
			if(flag == Request.getReqeustFlag(Request.HostName + "?method=SBJinSiChaXun")){
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
