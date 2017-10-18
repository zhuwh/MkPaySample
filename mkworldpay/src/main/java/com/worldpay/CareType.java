package com.worldpay;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@StringDef({
        CareType.VISA_CREDIT,
        CareType.VISA_DEBIT,
        CareType.VISA_CORPORATE_CREDIT,
        CareType.VISA_CORPORATE_DEBIT,
        CareType.MASTERCARD_CREDIT,
        CareType.MASTERCARD_DEBIT,
        CareType.MASTERCARD_CORPORATE_CREDIT,
        CareType.MASTERCARD_CORPORATE_DEBIT,
        CareType.MAESTRO,
        CareType.AMEX,
        CareType.CARTEBLEUE,
        CareType.JCB,
        CareType.DINERS
})
@Retention(RetentionPolicy.SOURCE)
public @interface CareType {

    String VISA_CREDIT="VISA_CREDIT";
    String VISA_DEBIT="VISA_DEBIT";
    String VISA_CORPORATE_CREDIT="VISA_CORPORATE_CREDIT";
    String VISA_CORPORATE_DEBIT="VISA_CORPORATE_DEBIT";
    String MASTERCARD_CREDIT="MASTERCARD_CREDIT";
    String MASTERCARD_DEBIT="MASTERCARD_DEBIT";
    String MASTERCARD_CORPORATE_CREDIT="MASTERCARD_CORPORATE_CREDIT";
    String MASTERCARD_CORPORATE_DEBIT="MASTERCARD_CORPORATE_DEBIT";
    String MAESTRO="MAESTRO";
    String AMEX="AMEX";
    String CARTEBLEUE="CARTEBLEUE";
    String JCB="JCB";
    String DINERS="DINERS";
}
