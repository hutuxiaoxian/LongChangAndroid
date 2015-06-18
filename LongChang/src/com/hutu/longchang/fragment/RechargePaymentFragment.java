package com.hutu.longchang.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import junit.framework.TestSuite;

import com.alipay.sdk.app.PayTask;
import com.hutu.longchang.R;
import com.hutu.longchang.alipy.AlipyPayUtil;
import com.hutu.longchang.alipy.PayResult;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RechargePaymentFragment extends BaseFragment {

	private EditText moneyEdit;
	private Button zhifuBtn;
	private boolean isExist = false;
	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(mActivity, "支付成功", Toast.LENGTH_SHORT)
							.show();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(mActivity, "支付结果确认中", Toast.LENGTH_SHORT)
								.show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(mActivity, "支付失败", Toast.LENGTH_SHORT)
								.show();
						
					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				isExist = (Boolean) msg.obj;
//				Toast.makeText(mActivity, "检查结果为：" + msg.obj,
//						Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_rechargepayment, null);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void init() {
		jianceZhifu();
		moneyEdit = (EditText) mView.findViewById(R.id.chongzhi_txt);
		zhifuBtn = (Button) mView.findViewById(R.id.chongzhi_btn);
		moneyEdit.setText("500");
		moneyEdit.setEnabled(false);
		setTitle("支付");
	}

	@Override
	public void onPrevious() {
		super.onPrevious();
		showFragment(new RechargeFragment(), Constant.TAG_USERCHONG);
	}

	@Override
	public void initListener() {
		zhifuBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(isExist){
					payZhifu();
				}
			}
		});
	}
	
	public void payZhifu(){
		String money = moneyEdit.getText().toString().trim();
		if (TextUtils.isEmpty(money)) {
			Toast.makeText(mActivity, "金钱不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		money = AlipyPayUtil.formatMoney(money);
//		String money = moneyEdit.getText().toString().trim();
//		money = AlipyPayUtil.formatMoney(money);
		// 订单
		String orderInfo = AlipyPayUtil.getOrderInfo("龙昌账号VIP付费", "VIP", money);

		// 对订单做RSA 签名
		String sign = AlipyPayUtil.sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ AlipyPayUtil.getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(mActivity);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}
	
	public void jianceZhifu(){
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask(mActivity);
				// 调用查询接口，获取查询结果
				boolean isExist = payTask.checkAccountIfExist();
				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();
	}

}
