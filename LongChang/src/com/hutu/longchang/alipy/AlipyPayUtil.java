package com.hutu.longchang.alipy;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


public class AlipyPayUtil {

	// 商户PID
	public static final String PARTNER = "2088812105749241";
	// 商户收款账号
	public static final String SELLER = "635657188@qq.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANpL53VWOFUrfZm8lu3ylTAW8PXP17mKYdTTM4NQqmO8LBHMZh6arGq8QqrftBtLu2Vs9MeIDWQmIr/O4ik2dN52+m1bNM0LtrIlcsSPrDr6dMH7FRaIN4KrNkaxPtonCtAj1sm2hW4+nVdkxgFp15SfasL5fJKJkOXyG0L31szVAgMBAAECgYBlI53Ng3D+JPRAclwLSsVMTpS9jtqIIFFLZb8MLCeFpf1VEbqOm2Me4LKSsKqlquTcDSsr9yEdMX4QGC44of5q6GETgO2xj41+IJ3elUKGmmrPuMzaQsAHoCT5BJw0bS1ku5E75EUUqTbM+NJbd112lBiAxqZXlURF7WTTik6CAQJBAP3T+cxOeTWoMvau7N6o47orSACE9EpMeehL+2gAGhsiV/Z+mmj2YA4Nj/bQ3BQ/QgJXirkRBezaVMTv9RCwPPUCQQDcKhg3YB487FqS/kJvi43GbgPykMhNi/rDr5IPS/YYPFvofzVLDhYhFjgBACMx+Xcw7fJ7yDRTXUW4sktQQGRhAkAmvfsLrxKbGQAmXM60sYyItuB3i9OJn6CfzzEhT5qsd5J7ghlpWemRW4qUvo5I3NrjZp863hlMbIqxwHpkQLIdAkEAlOiLtHh4OzCJGj6KZLN40qr6VIeEUp7Inq4TFfGEo2O/rgLL4tXGNd63RkX3iAd4jEmD5iDE81V0oLVGpyLGQQJACj9i+RbmGxVCW905nSMmnZIotqPkoDUQlsTKrFJfg5w8ISikGqcwCHLiEk7ngFLyMowp3CbDmaNfL6K5WkaYag==";

	public static String getOrderInfo(String subject, String body, String price) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
				+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}
	
	
	public static String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}
	
	public static String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}
	
	public static String getSignType() {
		return "sign_type=\"RSA\"";
	}
	
	public static String formatMoney(String money){
		double o = Double.valueOf(money);
		DecimalFormat format = new DecimalFormat("0.00");
		return format.format(o);
	} 
}
