package com.luwei.pay;

/**
 * @Author: chenjianrun
 * @Time: 2018/12/19
 * @Description: 支付的结果接口
 */
public interface PayCallback {
    void onPayResult(PayResult result);
}
