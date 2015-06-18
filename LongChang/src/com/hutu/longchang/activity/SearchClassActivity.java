package com.hutu.longchang.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hutu.longchang.R;
import com.hutu.longchang.model.JSONArrayExt;
import com.hutu.longchang.model.NetWorkCallBack;
import com.hutu.longchang.model.Request;

/**
 * 分类查询
 * @author hutu
 *
 */
@SuppressLint("ResourceAsColor") 
public class SearchClassActivity extends BaseActivity implements NetWorkCallBack{

	public Button btName,btClass;
	public EditText txt;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_search_class);
		txt = (EditText)findViewById(R.id.searc_class_edit_name);
	}
	
	
	public void searchClick(View v){
		String context = txt.getText().toString();
		context = context.trim();
		if(context.length() == 0){
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setMessage("请输入查询关键字");
			dialog.show();
		}else{
			Request req = new Request(this,this);
			req.stateList(context);
		}
	}
	

	@Override
	public void afterResponseFetched(int respCode, int flag, String msg) {
		if(respCode == 0){
			if(flag == Request.getReqeustFlag(Request.HostName + "?method=SBStateList")){
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
					return;
				} catch (JSONException e) {
					e.printStackTrace();
				}

				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				dialog.setTitle("提示");
				dialog.setMessage("参数不正确");
				dialog.setPositiveButton("确认",null);
				dialog.setNegativeButton("取消",null);
				dialog.show();
			}
		}
	}
	
	
}
