package com.mark.app.mkpaysample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mark.app.mkpay.core.MkPay;
import com.mark.app.mkpay.core.MkPayCallback;
import com.mark.app.mkpay.core.MkPayResult;

public class MainActivity extends AppCompatActivity {

    MkPay pay = new MkPay();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pay.init(new int[]{MkPay.PAY_TYPE_ALIPAY});

    }

   public void alipay(View v){
//       startActivity(new Intent(this,Test.class));
//       if (true)return;
        final String payInfo =  "out_trade_no=180858502680053977&partner=2088121539433230&service=create_forex_trade_wap&_input_charset=UTF-8&subject=6罐装新西兰可瑞康Karicare婴儿羊奶粉3段900g&rmb_fee=1285.73&sign=5034d80ac90c4420e398454ae215d50e&return_url=http://123.157.157.138:8999/api/mkipay/alipay/return&currency=NZD&notify_url=http://123.157.157.138:8999/api/mkipay/alipay/prepay/notify&sign_type=MD5";
        pay.pay(this, payInfo, MkPay.PAY_TYPE_ALIPAY, new MkPayCallback() {
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
