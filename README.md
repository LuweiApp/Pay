# Pay


对于微信支付及支付宝支付，其实并不难实现，但对于一些新手来说，他们还是会经常踩坑，那么为了避免新手重复踩坑，我对微信支付和支付宝支付进行了组件化，然后对一些常见的问题进行梳理总结。为了方便大家使用，我把 Pay 这个组件放在了 [GitHub](https://github.com/LuweiApp/Pay) 上，有兴趣的可以下载[源码](https://github.com/LuweiApp/Pay)看看。

[![](https://jitpack.io/v/LuweiApp/Pay.svg)](https://jitpack.io/#LuweiApp/Pay)

## 集成方式
- step1：在项目的根目录中的 build.grade 中添加依赖

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
- **step2**：app.build 中添加依赖
```
 implementation 'com.github.LuweiApp:Pay:v1.0.0'
```
- **step3**：app.build 中添（支付宝支付所必须依赖的 arr 文件）
```
    repositories {
         flatDir {
             dirs "libs","../pay/libs" // 依赖支付宝的 arr 路径
         }
     }
```
- **step4**：在 AndroidManifest.xml 中添加 WXPayEntryActivity 类的声明（WXPayEntryActivity 接收支付结果回调）
```
        <activity-alias
            android:name="${applicationId}.wxapi.WXPayEntryActivity"
            android:exported="true"
            android:targetActivity="com.luwei.pay.wechat.WXPayEntryActivity"/>
```


## 使用方式
### 支付宝
```
        // payOrderInfo 改字符串数据由后台返回
        String payOrderInfo = "";
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
```
### 微信
微信支付的时候，是不需要在本地中保存 appid，我们只需要从服务器返回的 bean 获取就可以了
```
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
```

### 注意点
调起微信支付时，后台返回的数据格式必须是以下的格式，这是字段都是调起微信支付所必须的。

```
    /**
     * appid : wx056d93262f42bd10
     * partnerid : 1111111111
     * prepayId : wx19171616308400d55d7d5fc70514513545
     * packageStr : Sign=WXPay
     * noncestr : O4agDklG4GLR6im9
     * timestamp : 1545210976
     * sign : 341397F997AD71E457DC9EEBD75CAA97
     */

    private String appid;
    private String partnerid;
    private String prepayId;
    private String packageStr;
    private String noncestr;
    private String timestamp;
    private String sign;
```
## 关键类

类 | 说明
---|---
Alipay | 调起支付宝支付
WechatPay | 调起微信支付
WechatPayBean| 微信支付所必需的字段
PayResult| 微信支付和支付宝支付的结果 bean
PayCallback| 微信支付和支付宝支付的结果回调

## 不用在 wxapi 目录下创建 WXPayEntryActivity 了
很多老手都知道，微信支付或者是登录，都需要在 app 的包路径下面创建一个文件夹 wxapi，然后在 wxapi 中创建 WXPayEntryActivity 或者是 WXEntryActivity 来接收结果的回调。对于这一点，很多人都表示很不爽。

其实，要解决这个问题方法还是有的，**<activity-alias/>** 这个标签就可以解决了。

在我的组件化中，WXPayEntryActivity 这个类我是放在 Pay 这个 module 中的，并没有放在 com.luwei.LuweiPayDemo.wxapi.WXPayEntryActivity 这么个路径中，省了一些多余的操作。所以我们只要在  AndroidManifest.xml 清单文件中添加一下声明就可以了。

```
        <activity-alias
            android:name="${applicationId}.wxapi.WXPayEntryActivity"
            android:exported="true"
            android:targetActivity="com.luwei.pay.wechat.WXPayEntryActivity"/>
```

## 微信支付可能会出现的问题
- **问题1**：无法调起支付，resp.errCode 的值总是为 -1
    - 调起微信支付的参数签名sign 不正确 
    - 应用的包签名不正确 
- **问题2**：报错：ignore wechat app signature validation 或者支付成功之后没有回调
    - 应用的包签名不正确
    - WXPayEntryActivity 没有设置或者路径有问题


[问题参考链接](https://blog.csdn.net/qq_28468727/article/details/54949236)