package com.mark.app.mkpaysample;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mark.app.mkpay.core.MkPay;
import com.mark.app.mkpay.core.MkPayCallback;
import com.mark.app.mkpay.core.MkPayResult;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    MkPay pay ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pay = MkPay.getInstance(this);
        pay.init(new int[]{MkPay.PAY_TYPE_ALIPAY,MkPay.PAY_TYPE_WXPAY});

    }

   public void alipay(View v){

       App.get().getPdmService()
                .getPayInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MKBaseResultEntity<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MKBaseResultEntity<String> baseResultEntity) {
                        final String payInfo = baseResultEntity.getData();
//                        final String payInfo =  "partner=2088021067956101&seller_id=zhiyoutech@126.com&out_trade_no=1005200958-1381&subject=测试的商品&body=该测试商品的详细描述&total_fee=0.01&service=mobile.securitypay.pay&payment_type=1&_input_charset=utf-8&it_b_pay=30m&sign=XNSKpeslPHprpWSrAkhBwWhgEvZpaaHZqL1q1pz3t2UxblDTGL2swU8M509qnfkBu3khgc3dgR9RgXDAXjWoLi4WNF8YwctKGSm%2BvVB3xGw9pxzY8peaQ%2F0jEzgncoWEitsZx2i2uOeeUJFdtiiTAeK%2FKHXDYABOJ1IyiR4NNHE%2BSKjuhy8K8APxKM4Vyt0IsQJYc%2FAPjHWCSNy9Ma4Dv8sWPYqbNuDzHM%2B3wD2jC00t%2Bbvyq5Zzr59OO%2FarcI8nhuqKz%2FraahsS6u8WZM0leuH%2Bu32%2B%2BHjm%2BwBHkQEFubJ%2BaBFaJnBIECZnLrnBe1nEUT3NmXe%2BYCb2CCtx7cloxQ%3D%3D&sign_type=RSA";
                        pay.pay(MainActivity.this, payInfo, MkPay.PAY_TYPE_ALIPAY, new MkPayCallback() {
                            @Override
                            public void onPayResult(MkPayResult result) {
                                switch (result.getResultStatus()) {
                                    case MkPayResult.PAY_STATE_SUCCESS: {
                                        Toast.makeText(MainActivity.this,"支付成功",Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                    case MkPayResult.PAY_STATE_CANCEL: {
                                        Toast.makeText(MainActivity.this,"支付取消",Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                    case MkPayResult.PAY_STATE_FAIL: {
                                        Toast.makeText(MainActivity.this,"支付失败",Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                    case MkPayResult.PAY_STATE_ERROR: {
                                        Toast.makeText(MainActivity.this,"支付错误",Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

//       startActivity(new Intent(this,Test.class));
//       if (true)return;
//        final String payInfo =  "out_trade_no=180858502680053977&partner=2088121539433230&service=create_forex_trade_wap&_input_charset=UTF-8&subject=6罐装新西兰可瑞康Karicare婴儿羊奶粉3段900g&rmb_fee=1285.73&sign=5034d80ac90c4420e398454ae215d50e&return_url=http://123.157.157.138:8999/api/mkipay/alipay/return&currency=NZD&notify_url=http://123.157.157.138:8999/api/mkipay/alipay/prepay/notify&sign_type=MD5";

    }

    public void wechatPay(View v){
//       startActivity(new Intent(this,Test.class));
//       if (true)return;
        Map payInfoMap = new ArrayMap();
        payInfoMap.put("timestamp","1506664307");
        payInfoMap.put("partnerid","1466883302");
        payInfoMap.put("package","Sign=WXPay");
        payInfoMap.put("noncestr","2c7449d6-d374-41bc-8a64-a6296aed");
        payInfoMap.put("sign","711D20CFFB023C0E12FF45DBA2A28B6C");
        payInfoMap.put("appid","wx026cb332cea46a51");
        payInfoMap.put("prepayid","wx201709291350506dc15ae5bf0978656151");
        JSONObject payInfo = new JSONObject(payInfoMap);

        pay.pay(this, payInfo.toString(), MkPay.PAY_TYPE_WXPAY, new MkPayCallback() {
            @Override
            public void onPayResult(MkPayResult result) {
                switch (result.getResultStatus()) {
                    case MkPayResult.PAY_STATE_SUCCESS: {
                        Toast.makeText(MainActivity.this,"支付成功",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case MkPayResult.PAY_STATE_CANCEL: {
                        Toast.makeText(MainActivity.this,"支付取消",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case MkPayResult.PAY_STATE_FAIL: {
                        Toast.makeText(MainActivity.this,"支付失败",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case MkPayResult.PAY_STATE_ERROR: {
                        Toast.makeText(MainActivity.this,"支付错误",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        });
    }
}
