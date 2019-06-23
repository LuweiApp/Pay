package com.luwei.luweipaydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.luwei.pay.PayCallback;
import com.luwei.pay.PayResult;
import com.luwei.pay.alipay.Alipay;
import com.luwei.pay.wechat.WechatPay;
import com.luwei.pay.wechat.WechatPayBean;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_alipay).setOnClickListener(this);
        findViewById(R.id.btn_wechat_pay).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_alipay:
                alipay();
                break;
            case R.id.btn_wechat_pay:
                wechatPay();
                break;
        }
    }

    private void alipay() {
        // payOrderInfo 改字符串数据由后台返回（敏感数据部分我是做了处理的）
        String payOrderInfo = "alipay_sdk=alipay-sdk-java-3.7.4.ALL&app_id=20190&biz_content=%7B%22goods_type%22%3A%221%22%2C%22out_trade_no%22%3A%22total42be206ea94f4%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E6%94%AF%E4%BB%98%E5%AE%9D%E6%9422%2C%22timeout_express%22%3A%2290m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Ftutor-r.com%2Fapi%2Fmodule%2Fpay%2Falipay%2Fnotify&sign=LY5n4pos9ODl8SLhPp20lvaZfzL4Urga9huoXxq%2F%2BZmr%2BIp3A0XByozr4PwXdH1pofA4xMdgosobDZpZ8CrdWHao0xKxH%2FeyL7xTvrU8VWwmn%2B2EhzZuDFYh2tKh0Qb6RmGPrtY9qHdtLEU7Di3XrF1431F7zc%2B5FIHqMC3k9%2B6wn1KiNxzS7DQy7yjgIjdxM%2FPGFIgGPJ0f4eiZvhF9GoDOCEoRqBokmUVXpAO5ibpq3AIbSHhY5tPIA4b3RxGYF2S4HCz50RQlYfZrBdtQ3STv4vzZmzs52jRcx7v5Y8WPSo%2BNKO8kBlYOZQ5DzBc%2FyPRE9%2F427lTKo9pw%3D%3D&sign_type=RSA2&timestamp=2019-06-23+16%3A11%3A41&version=1.0";//后台返回
        Alipay alipay = new Alipay(this);
        alipay.startPay(payOrderInfo, new PayCallback() {
            @Override
            public void onPayResult(PayResult result) {
                switch (result.getCode()) {
                    case PayResult.PAY_RESULT_SUCCESS:  //支付成功
                        break;
                    case PayResult.PAY_RESULT_READY:    // 准备支付
                        break;
                    case PayResult.PAY_RESULT_CANCEL:   // 支付取消
                        break;
                    case PayResult.PAY_RESULT_FAILURE:  // 支付失败
                        break;
                }
            }
        });
    }

    private void wechatPay() {
        WechatPayBean wechatPayBean = new WechatPayBean();//后台返回
        WechatPay.getInstance()
                .startPay(this, wechatPayBean.getAppid(), wechatPayBean, new PayCallback() {
                    @Override
                    public void onPayResult(PayResult result) {
                        switch (result.getCode()) {
                            case PayResult.PAY_RESULT_SUCCESS:  //支付成功
                                break;
                            case PayResult.PAY_RESULT_READY:    // 准备支付
                                break;
                            case PayResult.PAY_RESULT_CANCEL:   // 支付取消
                                break;
                            case PayResult.PAY_RESULT_FAILURE:  // 支付失败
                                break;
                        }
                    }
                });
    }
}
