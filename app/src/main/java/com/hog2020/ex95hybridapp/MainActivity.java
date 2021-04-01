package com.hog2020.ex95hybridapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    WebView wv;
    EditText et;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wv=findViewById(R.id.wv);
        et=findViewById(R.id.et);
        tv=findViewById(R.id.tv);

        wv.getSettings().setJavaScriptEnabled(true);
        //웹 세팅객체에게 클라이언트에서 ajax 기술을 사용할 수 있도록 허용
        wv.getSettings().setAllowFileAccess(true);

        wv.setWebViewClient(new WebViewClient());
        wv.setWebChromeClient(new WebChromeClient());

        //웹뷰에서 보여주는 웹문서의 JS 와 연결할 객체 설정
        wv.addJavascriptInterface(new WebViewConnecter(),"Droid");


        wv.loadUrl("file:///android_asset/index.html");
    }

    public void clicksend(View view) {
        //웹뷰에 보낼 메세지 얻어오기
        String msg = et.getText().toString();
        //웹뷰가 보여주고 있는 웹문서의 JS 함수를 직접호출
        wv.loadUrl("javascript:setMessage('"+msg+"')");
        et.setText("");
    }


    //웹뷰의 Javascript 와 통신을 담당할 연결자 클래스 정의
    class WebViewConnecter{

        //Javascript 에서 호출할수 있는 메소드는
        //반드시 @javascriptInterface 라는 어노테이션 지정해야함
        @JavascriptInterface
        public void setTextView(String msg){
            tv.setText("웹뷰로 부터 받은 메세지:"+msg);
        }

        //디바이스의 고유기능인 갤러리 앱을 열어주는 기능
        @JavascriptInterface
        public void openGalleryApp(){
            Intent intent =new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivity(intent);
        }

    }


}