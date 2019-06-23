package com.luwei.pay.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.luwei.pay.PayResult;
import com.luwei.pay.PayCallback;

import java.lang.ref.WeakReference;
import java.util.Map;


/**
 * Created by chenjianrun on 2017/11/13.
 * 描述：支付宝支付类
 */

public class Alipay{

    private static final int SDK_PAY_FLAG = 1;
    private WeakReference<Activity> mActivityWeakReference;
    private MyHandler mHandler = new MyHandler();
    private static PayCallback mCallback;


    public Alipay(Activity activity) {
        mActivityWeakReference = new WeakReference<>(activity);
    }


    private static class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SDK_PAY_FLAG){
                AliPayResult aliPayResult = new AliPayResult((Map<String, String>) msg.obj);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = aliPayResult.getResult();// 同步返回需要验证的信息
                String resultStatus = aliPayResult.getResultStatus();
                PayResult payResult;

                switch (resultStatus) {
                    case "9000":
                        payResult = new PayResult(PayResult.PAY_RESULT_SUCCESS ,"支付成功" ,PayResult.PayType.ali_pay);
                        break;
                    case "8000":
                        payResult = new PayResult(PayResult.PAY_RESULT_FAILURE,"正在处理中",PayResult.PayType.ali_pay);
                        break;
                    case "4000":
                        payResult = new PayResult(PayResult.PAY_RESULT_FAILURE,"支付失败",PayResult.PayType.ali_pay);
                        break;
                    case "5000":
                        payResult = new PayResult(PayResult.PAY_RESULT_FAILURE,"重复请求",PayResult.PayType.ali_pay);
                        break;
                    case "6001":
                        payResult = new PayResult(PayResult.PAY_RESULT_CANCEL,"已取消支付",PayResult.PayType.ali_pay);
                        break;
                    case "6002":
                        payResult = new PayResult(PayResult.PAY_RESULT_FAILURE,"网络连接出错",PayResult.PayType.ali_pay);
                        break;
                    case "6004":
                        payResult = new PayResult(PayResult.PAY_RESULT_FAILURE,"正在处理中",PayResult.PayType.ali_pay);
                        break;
                    default:
                        payResult = new PayResult(PayResult.PAY_RESULT_FAILURE,"支付失败",PayResult.PayType.ali_pay);
                        break;
                }

                if (mCallback != null) {
                    mCallback.onPayResult(payResult);
                }
            }
        }
    }



    /**
     * 支付宝支付业务
     */
    public void startPay(final String payOrderInfo,PayCallback callback) {
        this.mCallback = callback;
        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(mActivityWeakReference.get());
            Map<String, String> result = alipay.payV2(payOrderInfo, true);
            Log.i("msp", result.toString());

            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();

//        AsyncTask.execute(payRunnable);
        // 准备好了
        PayResult payResult = new PayResult(PayResult.PAY_RESULT_READY,"准备好了",PayResult.PayType.ali_pay);
        if (mCallback != null) {
            mCallback.onPayResult(payResult);
        }
    }

}
