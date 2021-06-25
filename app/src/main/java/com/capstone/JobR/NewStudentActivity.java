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
        //이메일 인증
        certificate.setOnClickListener(this);

        //아이디 입력+중복확인
        dupidcheck.setOnClickListener(view ->{
            String id = idwrite.getText().toString();
            if(id.equals("")){
                callwrongtoast("아이디를 입력해주세요");
            }else{
                registerCheckId(id);
                user.setId(id);
            }

        });
        //비밀번호 중복확인하기
        passwordcheck.setOnClickListener(view -> {
            String checkpassword = passwordwrite.getText().toString();
            String checkpassword2 = password2write.getText().toString();
            if (checkpassword.equals(checkpassword2) && !(checkpassword.equals(("")))) {
                Toast.makeText(getApplicationContext(), "비밀번호가 일치합니다", Toast.LENGTH_SHORT).show();
                user.setPassword(checkpassword);
            } else if(checkpassword.equals("") && checkpassword2.equals("")) {
                System.out.println(checkpassword);
                callwrongtoast("비밀번호를 입력해주세요");
            }else{
                callwrongtoast("비밀번호가 일치하지 않습니다");
            }
        });
        //이메일 인증
        sendmail.setOnClickListener(v -> {
            try {

                //이메일 인증 클릭 시, 인증 확인란 보여주기
                codecheck.setVisibility(View.VISIBLE);

                //여기다가 실제 송신하기 위한 내 이메일을 입력해야됨(아이디, 페스워드)
                // 이거 GMAIL 에서 보낼때 보내는 사람의 뭐 권한이나 보안같은거 승인해줘야됨
                // 안되면 그냥 내꺼 쓰자

                //email을 EditText -> String으로
                reciptemail=emailwrite.getText().toString();
                GMailSender gMailSender = new GMailSender("gksquddnr@gmail.com", "han960523!");
                //GMailSender.sendMail(제목, 본문내용, 받는사람);
                emailCode =  gMailSender.getEmailCode();
                gMailSender.sendMail("JobR 회원가입 이메일 인증번호 입니다.", emailCode, reciptemail);
                Toast.makeText(getApplicationContext(), "이메일을 성공적으로 보냈습니다.", Toast.LENGTH_SHORT).show();

            } catch (SendFailedException e) {
                //Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                callwrongtoast("이메일 형식이 잘못되었습니다");
            } catch (MessagingException e) {
                callwrongtoast("이메일을 입력해주세요");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //닉네임 중복 확인
        dupnickcheck.setOnClickListener(view ->{
            String nickname = nicknamewrite.getText().toString();
            if(nickname.equals("")){
                callwrongtoast("닉네임을 입력해주세요");

            }else{
                registerCheckNick(nickname);
            }

        });

        //회원가입
        newuserbutton.setOnClickListener(view ->{
            user.setJobSort("취준생");
            System.out.println("이메일: "+user.getEmail()+"아이디: "+user.getId()+"비밀번호:"+user.getPassword()+
                    "닉네임: "+user.getNickname());

            if(user.getId()==null||user.getPassword()==null||user.getEmail()==null||user.getNickname()==null) {
                if (user.getId()==null) {
                    callwrongtoast("아이디를 입력해주세요");
                    return;
                } else if (user.getPassword()==null) {
                    callwrongtoast("비밀번호를 입력해주세요");
                    return;
                } else if (user.getEmail()==null) {
                    callwrongtoast("이메일을 입력해주세요");
                    return;
                } else {
                    callwrongtoast("닉네임을 입력해주세요");
                    return;
                }
            }

            register();
            //다음 화면 연결
            Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            //화면 전환
            startActivity(intent);
        });
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    //입력오류 toast 호출
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

    //id 중복확인
    public void registerCheckId(String id){

        controller.API.checkId(id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                } else {
                    Log.d("연결이 성공적 : check", response.body());
                    if(response.body().equals("fail")){
                        callwrongtoast("중복입니다");
                    }else{
                        Toast.makeText(getApplicationContext(), "가입 가능한 아이디 입니다.", Toast.LENGTH_SHORT).show();
                        user.setId(id);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());

            }
        });
    }

    //닉네임 중복확인
    public void registerCheckNick(String nickname){

        controller.API.checkNick(nickname).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                } else {
                    Log.d("연결이 성공적 : check", response.body());
                    if(response.body().equals("fail")){
                        callwrongtoast("중복입니다.");
                    }else{
                        Toast.makeText(getApplicationContext(), "가입 가능한 닉네임 입니다.", Toast.LENGTH_SHORT).show();
                        user.setNickname(nickname);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });

    }
    //회원가입
    public void register() {

        //user 회원가입 하기
        controller.API.regsiter(user).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                } else {
                    Log.d("연결이 성공적 : insert", response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());

            }
        });
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.certificate : //다이얼로그 내의 인증번호 인증 버튼을 눌렀을 시
                String codewrite2 = codewrite.getText().toString();

                if(codewrite2.equals(emailCode)){
                    Toast.makeText(this, "이메일 인증 성공", Toast.LENGTH_SHORT).show();
                    user.setEmail(reciptemail);
                }else{
                    callwrongtoast("인증번호가 일치하지 않습니다");
                }

                break;



        }

    }
}
