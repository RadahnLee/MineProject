package com.school.cakesell.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.school.cakesell.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.Inflater;

public class MinePage extends Fragment {

    private View mine;
    private String account;
    private TextView personalInfo;
    private EditText changeAccount, changePwd;
    private Button button;

    public MinePage() {

    }

    public MinePage(String account) {
        this.account = account;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mine = inflater.inflate(R.layout.mine,null);
        personalInfo = mine.findViewById(R.id.current_account);
        changeAccount = mine.findViewById(R.id.change_account);
        changePwd = mine.findViewById(R.id.change_pwd);
        button = mine.findViewById(R.id.change_personal_info);
        personalInfo.setText(account);

        setListeners(account);
        return mine;
    }

    private void setListeners(String account) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeData(account);
            }
        });
    }

    private void changeData(String account) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String newAccount = changeAccount.getText().toString();
                String newPwd = changePwd.getText().toString();
                try {
                    URL url = new URL("http://10.7.90.113:8080/CakeSell_war_exploded/user?account=" + account
                            + "&newAccount=" + newAccount  + "&newPwd=" + newPwd);
                    HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                    conn.getResponseCode();
                    conn.disconnect();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
