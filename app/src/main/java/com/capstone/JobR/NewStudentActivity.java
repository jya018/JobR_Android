package com.capstone.JobR;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Dimension;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.capstone.JobR.DBA.user.UserController;
import com.capstone.JobR.DBA.user.UserVO;

import org.w3c.dom.Text;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewStudentActivity  extends AppCompatActivity implements View.OnClickListener{

    private EditText idwrite, passwordwrite, password2write, emailwrite, codewrite, nicknamewrite;

    private Button dupidcheck,passwordcheck,sendmail, certificate,dupnickcheck, newuserbutton;

    private String emailCode, reciptemail;

    private LinearLayout codecheck;

    private UserController controller = new UserController();
    private UserVO user = new UserVO();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register_nojob);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        idwrite =(EditText) findViewById(R.id.idwrite);
        passwordwrite =(EditText) findViewById(R.id.passwordwrite);
        password2write =(EditText) findViewById(R.id.password2write);
        emailwrite =(EditText) findViewById(R.id.emailwrite);
        codewrite =(EditText) findViewById(R.id.codewrite);
        nicknamewrite =(EditText) findViewById(R.id.nicknamewrite);

        codecheck = findViewById(R.id.codecheck);
        dupidcheck =(Button) findViewById(R.id.dupidcheck);
        sendmail =(Button) findViewById(R.id.sendmail);
        certificate =(Button) findViewById(R.id.certificate);
        dupnickcheck =(Button) findViewById(R.id.dupnickcheck);
        passwordcheck = findViewById(R.id.passwordcheck);
        newuserbutton = findViewById(R.id.newuserbutton);
        //????????? ??????
        certificate.setOnClickListener(this);

        //????????? ??????+????????????
        dupidcheck.setOnClickListener(view ->{
            String id = idwrite.getText().toString();
            if(id.equals("")){
                callwrongtoast("???????????? ??????????????????");
            }else{
                registerCheckId(id);
                user.setId(id);
            }

        });
        //???????????? ??????????????????
        passwordcheck.setOnClickListener(view -> {
            String checkpassword = passwordwrite.getText().toString();
            String checkpassword2 = password2write.getText().toString();
            if (checkpassword.equals(checkpassword2) && !(checkpassword.equals(("")))) {
                Toast.makeText(getApplicationContext(), "??????????????? ???????????????", Toast.LENGTH_SHORT).show();
                user.setPassword(checkpassword);
            } else if(checkpassword.equals("") && checkpassword2.equals("")) {
                System.out.println(checkpassword);
                callwrongtoast("??????????????? ??????????????????");
            }else{
                callwrongtoast("??????????????? ???????????? ????????????");
            }
        });
        //????????? ??????
        sendmail.setOnClickListener(v -> {
            try {

                //????????? ?????? ?????? ???, ?????? ????????? ????????????
                codecheck.setVisibility(View.VISIBLE);

                //???????????? ?????? ???????????? ?????? ??? ???????????? ???????????????(?????????, ????????????)
                // ?????? GMAIL ?????? ????????? ????????? ????????? ??? ???????????? ??????????????? ??????????????????
                // ????????? ?????? ?????? ??????

                //email??? EditText -> String??????
                reciptemail=emailwrite.getText().toString();
                GMailSender gMailSender = new GMailSender("gksquddnr@gmail.com", "han960523!");
                //GMailSender.sendMail(??????, ????????????, ????????????);
                emailCode =  gMailSender.getEmailCode();
                gMailSender.sendMail("JobR ???????????? ????????? ???????????? ?????????.", emailCode, reciptemail);
                Toast.makeText(getApplicationContext(), "???????????? ??????????????? ???????????????.", Toast.LENGTH_SHORT).show();

            } catch (SendFailedException e) {
                //Toast.makeText(getApplicationContext(), "????????? ????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                callwrongtoast("????????? ????????? ?????????????????????");
            } catch (MessagingException e) {
                callwrongtoast("???????????? ??????????????????");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //????????? ?????? ??????
        dupnickcheck.setOnClickListener(view ->{
            String nickname = nicknamewrite.getText().toString();
            if(nickname.equals("")){
                callwrongtoast("???????????? ??????????????????");

            }else{
                registerCheckNick(nickname);
            }

        });

        //????????????
        newuserbutton.setOnClickListener(view ->{
            user.setJobSort("?????????");
            System.out.println("?????????: "+user.getEmail()+"?????????: "+user.getId()+"????????????:"+user.getPassword()+
                    "?????????: "+user.getNickname());

            if(user.getId()==null||user.getPassword()==null||user.getEmail()==null||user.getNickname()==null) {
                if (user.getId()==null) {
                    callwrongtoast("???????????? ??????????????????");
                    return;
                } else if (user.getPassword()==null) {
                    callwrongtoast("??????????????? ??????????????????");
                    return;
                } else if (user.getEmail()==null) {
                    callwrongtoast("???????????? ??????????????????");
                    return;
                } else {
                    callwrongtoast("???????????? ??????????????????");
                    return;
                }
            }

            register();
            //?????? ?????? ??????
            Toast.makeText(getApplicationContext(), "??????????????? ?????????????????????", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            //?????? ??????
            startActivity(intent);
        });
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    //???????????? toast ??????
    public void callwrongtoast(String toast){
        TextView tvToastMsg = new TextView(getApplicationContext());
        tvToastMsg.setText(toast);
        tvToastMsg.setTextColor(Color.parseColor("#FF6600"));
        tvToastMsg.setTextSize(14);
        tvToastMsg.setBackground(ContextCompat.getDrawable(this,R.drawable.roundbutton_toast));
        tvToastMsg.setPadding(50,40,50,40);
        Toast toastMsg = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        toastMsg.setView(tvToastMsg);
        toastMsg.show();
    }

    //id ????????????
    public void registerCheckId(String id){

        controller.API.checkId(id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("????????? ???????????? : ", "error code : " + response.code());
                } else {
                    Log.d("????????? ????????? : check", response.body());
                    if(response.body().equals("fail")){
                        callwrongtoast("???????????????");
                    }else{
                        Toast.makeText(getApplicationContext(), "?????? ????????? ????????? ?????????.", Toast.LENGTH_SHORT).show();
                        user.setId(id);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("????????????", t.getMessage());

            }
        });
    }

    //????????? ????????????
    public void registerCheckNick(String nickname){

        controller.API.checkNick(nickname).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("????????? ???????????? : ", "error code : " + response.code());
                } else {
                    Log.d("????????? ????????? : check", response.body());
                    if(response.body().equals("fail")){
                        callwrongtoast("???????????????.");
                    }else{
                        Toast.makeText(getApplicationContext(), "?????? ????????? ????????? ?????????.", Toast.LENGTH_SHORT).show();
                        user.setNickname(nickname);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("????????????", t.getMessage());
            }
        });

    }
    //????????????
    public void register() {

        //user ???????????? ??????
        controller.API.regsiter(user).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("????????? ???????????? : ", "error code : " + response.code());
                    return;
                } else {
                    Log.d("????????? ????????? : insert", response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("????????????", t.getMessage());

            }
        });
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.certificate : //??????????????? ?????? ???????????? ?????? ????????? ????????? ???
                String codewrite2 = codewrite.getText().toString();

                if(codewrite2.equals(emailCode)){
                    Toast.makeText(this, "????????? ?????? ??????", Toast.LENGTH_SHORT).show();
                    user.setEmail(reciptemail);
                }else{
                    callwrongtoast("??????????????? ???????????? ????????????");
                }

                break;



        }

    }
}
