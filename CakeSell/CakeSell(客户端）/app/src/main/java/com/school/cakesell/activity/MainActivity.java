package com.school.cakesell.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.school.cakesell.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    private EditText username, pwd;
    private Button login, login_by_text, register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 1.找到登录页面的xml，注册控件
         * 2.绑定事件监听器
         * 3.实现安卓客户端与Servlet的交互
         * */

        //1.注册控件
        findViews();
        //2.绑定事件监听器，由于button太多，这里专门弄一个内部类
        setListeners();
    }

    private void setListeners() {
        MyClickListener listener = new MyClickListener();
        login.setOnClickListener(listener);
        login_by_text.setOnClickListener(listener);
        register.setOnClickListener(listener);
    }

    class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id == R.id.textLogin){
                Toast toastTip
                        = Toast.makeText(MainActivity.this,
                        "服务器维护中，该功能暂时不可用",
                        Toast.LENGTH_SHORT);
                toastTip.show();
            }else if(id == R.id.register) {
                //客户端只获取信息，具体的逻辑处理全部交给Servlet
                String account = String.valueOf(username.getText());
                String password = String.valueOf(pwd.getText());
                //将信息提交给Servlet,并判断注册是否成功
                RegisterPost(account,password);
            }else if(id == R.id.login) {
                String account = String.valueOf(username.getText());
                String password = String.valueOf(pwd.getText());
                //TODO 将信息提交给Servlet,并判断是否登录成功
                LoginPost(account,password);
            }
        }
    }

    private void LoginPost(String account, String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL("http://10.7.90.113:8080/CakeSell_war_exploded/login?account=" + account +
                            "&password=" + password);
                    URLConnection urlConnection = url.openConnection();
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    InputStream inputStream = urlConnection.getInputStream();// 字节流
                    Reader reader = new InputStreamReader(inputStream,"utf-8");//字符流
                    BufferedReader bufferedReader = new BufferedReader(reader);// 缓存流
                    String status = bufferedReader.readLine();
                    Log.i("status",status);
                    if(status.equals("success")){
                        //TODO 实现页面的跳转
                       Intent intent = new Intent();
                       intent.setClass(MainActivity.this, HomePageActivity.class);
                       intent.putExtra("account", account);
                       startActivity(intent);
                    }else if(status.equals("fail")){
                        Looper.prepare();
                        Toast toastTip
                                = Toast.makeText(MainActivity.this,
                                "登录失败，请检查用户名或者是密码是否输入正确！",
                                Toast.LENGTH_SHORT);
                        toastTip.show();
                    }
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void RegisterPost(String account, String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.7.90.113:8080/CakeSell_war_exploded/register?account=" + account +
                            "&password=" + password);
                    URLConnection urlConnection = url.openConnection();
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    InputStream inputStream = urlConnection.getInputStream();// 字节流
                    Reader reader = new InputStreamReader(inputStream,"utf-8");//字符流
                    BufferedReader bufferedReader = new BufferedReader(reader);// 缓存流
                    String status = bufferedReader.readLine();
                    Log.i("status",status);
                    if(status.equals("success")) {
                        //提醒用户注册成功
                        Looper.prepare();
                        Toast toastTip
                                = Toast.makeText(MainActivity.this,
                                "注册成功！",
                                Toast.LENGTH_SHORT);
                        toastTip.show();
                    } else if (status.equals("fail")) {
                        //提醒用户注册失败
                        Looper.prepare();
                        Toast toastTip
                                = Toast.makeText(MainActivity.this,
                                "该用户名已经被注册！请更换用户名！",
                                Toast.LENGTH_SHORT);
                        toastTip.show();
                    }
                    reader.close();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
            };



    private void findViews() {
        username = findViewById(R.id.username);
        pwd = findViewById(R.id.pwd);
        login = findViewById(R.id.login);
        login_by_text = findViewById(R.id.textLogin);
        register = findViewById(R.id.register);
    }
}