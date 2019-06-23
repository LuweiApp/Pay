package com.luwei.pay.wechat;

import android.app.Activity;
import android.database.Observable;
import android.text.TextUtils;

import com.luwei.pay.PayResult;
import com.luwei.pay.PayCallback;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by chenjianrun on 2017/4/11.
 */

public class WechatPay {

    private IWXAPI iwxapi;
    private static WechatPay wechatPay;
    private WechatPayBean wechatPayBean;
    private PayCallback wxPayCallback;


    private WechatPay(){

    }

    public static WechatPay getInstance(){
        if (wechatPay == null) {
            synchronized (WechatPay.class){
                if (wechatPay == null) {
                    wechatPay = new WechatPay();
                }
            }
        }
        return wechatPay;
    }

    public IWXAPI getIwxapi(){
        //该方法被调用时，说明已经出现支付的窗口
        PayResult payResult = new PayResult(PayResult.PAY_RESULT_READY,"准备好了",PayResult.PayType.wechat_pay);
        if (wxPayCallback != null) {
            wxPayCallback.onPayResult(payResult);
        }
        return iwxapi;
    }


    public void startPay(Activity activity, String wxAppId, WechatPayBean payBean, PayCallback payCallback) {
        initWechat(activity,wxAppId);
        this.wechatPayBean = payBean;
        wxPayCallback =payCallback;
        PayResult payResult;

        //检查是否支持微信支付
        if (!isSupportWXPay()) {
            payResult = new PayResult(PayResult.PAY_RESULT_FAILURE,"没有安装微信",PayResult.PayType.wechat_pay);

            if (wxPayCallback != null) {
                wxPayCallback.onPayResult(payResult);
            }
            return;
        }

        //检查某个字段是否为空
        if(isHasNull(wechatPayBean)) {
            payResult = new PayResult(PayResult.PAY_RESULT_FAILURE, "WechatPayBean 存在字段为空", PayResult.PayType.wechat_pay);

            if (wxPayCallback != null) {
                wxPayCallback.onPayResult(payResult);
            }
            return;
        }


        PayReq req = new PayReq();
        req.appId = wechatPayBean.getAppid();
        req.partnerId = wechatPayBean.getPartnerid();
        req.prepayId = wechatPayBean.getPrepayId();
        req.packageValue = wechatPayBean.getPackageStr();
        req.nonceStr = wechatPayBean.getNoncestr();
        req.timeStamp = String.valueOf(wechatPayBean.getTimestamp());
        req.sign = wechatPayBean.getSign();
        //调起微信支付
        iwxapi.sendReq(req);
    }

    /**
     * 微信初始化
     * @param activity
     * @param wxAppId
     */
    private void initWechat(Activity activity, String wxAppId){
        // 微信初始化
        iwxapi = WXAPIFactory.createWXAPI(activity, null);
        iwxapi.registerApp(wxAppId);
    }

    private boolean isHasNull(WechatPayBean payBean){
        //检查某个字段是否为空
        if(payBean == null || TextUtils.isEmpty(payBean.getAppid())
                || TextUtils.isEmpty(payBean.getPartnerid())
                || TextUtils.isEmpty(payBean.getPrepayId())
                || TextUtils.isEmpty(payBean.getPackageStr())
                || TextUtils.isEmpty(payBean.getNoncestr())
                || TextUtils.isEmpty(payBean.getTimestamp())
                || TextUtils.isEmpty(payBean.getSign())) {

            return true;
        }
        return false;
    }


    /**
     * 微信支付结果回调
     * @param err_code
     */
    public void onResp(int err_code,String err_str){
        PayResult payResult;
        if(err_code == 0) {             // 成功
            payResult = new PayResult(PayResult.PAY_RESULT_SUCCESS,"微信支付成功",PayResult.PayType.wechat_pay);
        } else if(err_code == -2) {     // 取消
            payResult = new PayResult(PayResult.PAY_RESULT_CANCEL,"微信支付取消",PayResult.PayType.wechat_pay);
        } else {                        // 失败
            payResult = new PayResult(PayResult.PAY_RESULT_FAILURE,TextUtils.isEmpty(err_str)?"微信支付失败":err_str,PayResult.PayType.wechat_pay);
        }
        if (wxPayCallback != null) {
            wxPayCallback.onPayResult(payResult);
        }

        wxPayCallback = null;
    }

    /**
     * 检测是否支持微信支付
     * @return
     */
    private boolean isSupportWXPay() {
        return iwxapi.isWXAppInstalled() && iwxapi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }

}
