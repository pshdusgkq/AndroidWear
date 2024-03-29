package com.antonioleiva.wearcook;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.text.ParseException;

import com.facebook.AppEventsLogger;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class signUp extends ActionBarActivity {

    String userNameTxt;
    String passwordTxt;
    EditText userName;
    EditText password;
    ImageView signUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //회원가입할 아이디,비밀번호를 받는 변수 초기화
        userName=(EditText)findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.password);
        signUpBtn=(ImageView)findViewById(R.id.signUpBtn);

        //Pasre앱에 대한 부분 초기화
        Parse.initialize(this, "USjhdBZW0Jsm8jvedZIoc4zm0OdZRvI0lMWNoRUt", "eUkreRV5NNa6iruqmLnbpTqVG6F5Z3MZDT0bWJxo");//parse와 페이스북 연동작업 초기화
        ParseFacebookUtils.initialize("461714007312357");

        //회원가입시 즉각적으로 메인 액티비티로 넘겨준다.
        final Intent intent=new Intent(this,MenuChoice.class);

        //Parse 사용자에서 Facebook을 사용하는 방법은 기본적으로 (1) Facebook 사용자로 로그인하고 ParseUser 만들기 또는
        //                                            (2) Facebook을 기존 ParseUser에 연결하는 두 가지
        //Parse에 사용자를 가입시키는 버튼
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameTxt= userName.getText().toString();
                passwordTxt= password.getText().toString();

                //아이디와 비밀번호를 제대로 입력하지 않았을 경우
                if (userNameTxt.isEmpty() || passwordTxt.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 입력해주세요",
                            Toast.LENGTH_LONG).show();
                }else{
                    //PasreUser클래스에 새로운 사용자에 대한 id,pwd를 추가한다.
                    ParseUser user = new ParseUser();
                    user.setUsername(userNameTxt);
                    user.setPassword(passwordTxt);
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(com.parse.ParseException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(),
                                        "회원가입이 완료되었습니다",Toast.LENGTH_SHORT)
                                        .show();
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "이미 등록된 이름입니다",Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
