package com.luwei.pay;

/**
 * @Author: chenjianrun
 * @Time: 2018/12/19
 * @Description: 支付結果的 bean
 */
public class PayResult {
    // 支付类型
    public enum PayType{
        ali_pay,wechat_pay
    }
    // 支付成功
    public static final int PAY_RESULT_SUCCESS = 200;
    // 支付取消
    public static final int PAY_RESULT_CANCEL = 1;
    // 支付失败
    public static final int PAY_RESULT_FAILURE = -1;
    // 支付调用准备好了
    public static final int PAY_RESULT_READY = 2;

    private int code;
    private String msg;
    private PayType payType;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public PayResult(int code, String msg, PayType payType) {
        this.code = code;
        this.msg = msg;
        this.payType = payType;
    }
}
