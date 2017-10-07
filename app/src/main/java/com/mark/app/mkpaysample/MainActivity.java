package com.mark.app.mkpaysample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mark.app.mkpay.core.MkPay;
import com.mark.app.mkpay.core.MkPayCallback;
import com.mark.app.mkpay.core.MkPayResult;
import com.worldpay.Card;
import com.worldpay.ResponseCard;
import com.worldpay.ResponseError;
import com.worldpay.SaveCardActivity;
import com.worldpay.WorldPay;
import com.worldpay.WorldPayError;
import com.worldpay.WorldPayResponseReusableToken;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.worldpay.SaveCardActivity.EXTRA_CUSTOMIZE_THEME;
import static com.worldpay.SaveCardActivity.EXTRA_RESPONSE_CARD;
import static com.worldpay.SaveCardActivity.EXTRA_RESPONSE_ERROR;
import static com.worldpay.SaveCardActivity.EXTRA_RESPONSE_WORLDPAY_ERROR;
import static com.worldpay.SaveCardActivity.RESULT_RESPONSE_CARD;
import static com.worldpay.SaveCardActivity.RESULT_RESPONSE_ERROR;
import static com.worldpay.SaveCardActivity.RESULT_WORLDPAY_ERROR;

public class MainActivity extends AppCompatActivity {

    MkPay pay ;
    WorldPay worldPay;

    private static final String clientKey = "T_C_91c8096c-b279-46b3-8076-988fa9c3ea9e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pay = MkPay.getInstance(this);
        pay.init(new int[]{MkPay.PAY_TYPE_ALIPAY,MkPay.PAY_TYPE_WXPAY});

        worldPay = WorldPay.getInstance();
        worldPay.setClientKey(clientKey);
        worldPay.setReusable(true);
        Card.setValidationType(Card.VALIDATION_TYPE_BASIC);
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

    private static final int SAVE_CARD_REQUEST_CODE = 1500;

    public void addCard(View v){

        final Intent intent = new Intent(this, SaveCardActivity.class);
        intent.putExtra(EXTRA_CUSTOMIZE_THEME, Color.parseColor("#ff0000"));
        startActivityForResult(intent, SAVE_CARD_REQUEST_CODE);

    }

    public void worldPay(View v){

        final ResponseCard selectedCard = card;
        SaveCardActivity
                .requestCVC(
                        this,
                        getLayoutInflater(),
                        selectedCard.getToken(),
                        clientKey,
                        new WorldPayResponseReusableToken() {
                            @Override
                            public void onSuccess() {
                                //请求服务器支付
                                App.get().getPdmService()
                                        .worldPay(selectedCard.getToken())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<MKBaseResultEntity<String>>() {
                                            @Override
                                            public void onSubscribe(@NonNull Disposable d) {

                                            }

                                            @Override
                                            public void onNext(@NonNull MKBaseResultEntity<String> baseResultEntity) {
                                               if (baseResultEntity.getResultCode()==1000){
                                                   Toast.makeText(MainActivity.this,"支付成功",Toast.LENGTH_SHORT).show();
                                               }else{
                                                   Toast.makeText(MainActivity.this,"支付失败",Toast.LENGTH_SHORT).show();
                                               }

                                            }

                                            @Override
                                            public void onError(@NonNull Throwable e) {
                                                Toast.makeText(MainActivity.this,"支付失败",Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });
//                                if (threeDsEnabled) {
//                                    final OrderDetails orderDetails = new OrderDetails(
//                                            deliveryAddress.getText().toString(),
//                                            cityEditText.getText().toString(),
//                                            postcodeEditText.getText().toString(),
//                                            getPriceFromForm());
//                                    final Intent orderIntent = new Intent(OrderDetailsActivity.this, ThreeDsOrderActivity.class);
//                                    orderIntent.putExtra(EXTRA_ORDER_DETAIL, orderDetails);
//                                    orderIntent.putExtra(ThreeDsOrderActivity.EXTRA_CARD_TOKEN, selectedCard.getToken());
//                                    startActivityForResult(orderIntent, THREE_DS_REQUEST_CODE);
//                                } else {
//                                    // At this point you can create an Order with the
//                                    // new token via the WorldPay Orders API.
//                                    new AlertDialog.Builder(OrderDetailsActivity.this)
//                                            .setTitle(R.string.confirm_purchase)
//                                            .setMessage(R.string.confirm_purchase_message)
//                                            .setPositiveButton(R.string.ok,
//                                                    new DialogInterface.OnClickListener() {
//                                                        @Override
//                                                        public void onClick(final DialogInterface dialog, final int which) {
//                                                            startActivity(new Intent(getApplicationContext(), OrderConfirmationActivity.class));
//                                                        }
//                                                    }).show();
//                                }


                            }

                            @Override
                            public void onResponseError(final ResponseError responseError) {
                                showDialog("Response error", responseError.getMessage());
                            }

                            @Override
                            public void onError(final WorldPayError worldPayError) {
                                showDialog("world pay", worldPayError.getMessage());
                            }
                        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode,
                                    final Intent data) {
        if (requestCode == SAVE_CARD_REQUEST_CODE) {
            handleCardTokenizationResult(resultCode, data);
        }
    }

    ResponseCard card ;

    private void handleCardTokenizationResult(final int resultCode, final Intent data) {
        switch (resultCode) {
            case RESULT_RESPONSE_CARD:
                final ResponseCard responseCard =
                        (ResponseCard) data.getSerializableExtra(EXTRA_RESPONSE_CARD);
                if (responseCard != null) {
                    //增加卡成功
                    Timber.d(responseCard.toString());
                    card = responseCard;
//                    savedCards.add(responseCard);
//                    cardRepository.save(savedCards, OrderDetailsActivity.this);
//                    refreshStoredCardsPanel();
                }
                break;
            case RESULT_RESPONSE_ERROR:
                final ResponseError rError = (ResponseError) data.getSerializableExtra(EXTRA_RESPONSE_ERROR);
                if (rError != null) {
                    showDialog("Response error", rError.getMessage());
                }
                break;
            case RESULT_WORLDPAY_ERROR:
                final WorldPayError wError = (WorldPayError) data
                        .getSerializableExtra(EXTRA_RESPONSE_WORLDPAY_ERROR);
                if (wError != null) {
                    showDialog("WorldPay error", wError.getMessage());
                }
                break;
        }
    }

    private void showDialog(final String title, final String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
}
