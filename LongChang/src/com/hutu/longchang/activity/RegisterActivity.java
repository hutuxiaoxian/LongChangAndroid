package com.hutu.longchang.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hutu.longchang.R;
import com.hutu.longchang.model.NetWorkCallBack;
import com.hutu.longchang.model.Request;
/**
 * 注册
 * @author hutu
 *
 */
public class RegisterActivity extends BaseActivity implements NetWorkCallBack{
	
	private EditText eAccount;
	private EditText ePassword;
	private EditText eRePassowrd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_register);
		eAccount = (EditText)findViewById(R.id.register_edit_account);
		ePassword = (EditText)findViewById(R.id.register_edit_password);
		eRePassowrd = (EditText)findViewById(R.id.register_edit_password2);
	}
	
	public void registerClick(View v) {
		String account = eAccount.getText().toString().trim();
		String password = ePassword.getText().toString().trim();
		String rePassword = eRePassowrd.getText().toString().trim();
		
		String msg = null;
		
		if (account.length() != 11) {
			msg = "账号格式不正确";
		} else if(password.length() < 4 || password.length() > 16) {
			msg = "密码长度请需要4-16位";
		} else if(!password.equals(rePassword)) {
			msg = "两次输入的密码不一致";
		}
		
		if (msg != null) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setMessage(msg);
			dialog.show();
		} else {
			// 注册请求
			Request req = new Request(this, this);
			req.setRegisterUser(account, password);
		}
		
	}
	
	@Override
	public void afterResponseFetched(int respCode, int flag, String msg) {
		if (respCode == 0 && "1".equals(msg)) {
			finish();
		}else{
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setMessage("账号已存在");
			dialog.show();
		}
	}
}
