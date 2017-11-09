package com.worldpay.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mark.app.mkpay.R;

public class InputDialog extends Dialog implements View.OnClickListener {
    private EditText contentTxt;
    private TextView submitTxt;
    private TextView cancelTxt;

    private Context mContext;
    private OnCloseListener listener;

    public InputDialog(Context context,OnCloseListener listener) {
        super(context,R.style.dialog);
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView(){
        contentTxt = (EditText)findViewById(R.id.content);
        submitTxt = (TextView)findViewById(R.id.submit);
        submitTxt.setOnClickListener(this);
        cancelTxt = (TextView)findViewById(R.id.cancel);
        cancelTxt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.cancel){
            if(listener != null){
                listener.onClick(this, false,contentTxt.getText().toString());
            }
            this.dismiss();
        }else if(id==R.id.submit){
            if(listener != null){
                listener.onClick(this, true,contentTxt.getText().toString());
            }
        }

    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm,String data);
    }
}