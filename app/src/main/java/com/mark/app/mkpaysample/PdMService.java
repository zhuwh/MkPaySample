package com.mark.app.mkpaysample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhuwh on 2017/9/11.
 */

public interface PdMService {

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static PdMService newService() {
            Gson gson = new GsonBuilder()
//                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
//                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PdMService.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return retrofit.create(PdMService.class);
        }
    }

    String baseUrl = "http://192.168.0.9:8080";



    /**
     * 支付信息
     *
     * @return
     */
    @POST("/api/user/pay")
    Observable<MKBaseResultEntity<String>> getPayInfo();

    /**
     * 支付信息
     *
     * @return
     */
    @POST("/api/test/pay/ipn/info")
    Observable<MKBaseResultEntity<String>> getIpnInfo(@Query("orderSn") String orderSn);

    /**
     * worldPay
     *
     * @return
     */
    @FormUrlEncoded
    @POST("/api/user/pay/wp")
    Observable<MKBaseResultEntity<String>> worldPay(@Field("token") String token);

}
