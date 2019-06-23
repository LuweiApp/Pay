package com.luwei.pay.wechat;

/**
 * Created by chenjianrun on 2017/4/11.
 * 描述：微信支付的 bean
 */

public class WechatPayBean {


    /**
     * appid : wx056d93262f42bd10
     * partnerid : 1491420092
     * prepayId : wx19171616308400d55d7d5fc70514513545
     * packageStr : Sign=WXPay
     * noncestr : O4agDklG4GLR6im9
     * timestamp : 1545210976
     * signType : MD5
     * sign : 341397F997AD71E457DC9EEBD75CAA97
     */

    private String appid;
    private String partnerid;
    private String prepayId;
    private String packageStr;
    private String noncestr;
    private String timestamp;
    private String signType;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getPackageStr() {
        return packageStr;
    }

    public void setPackageStr(String packageStr) {
        this.packageStr = packageStr;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
