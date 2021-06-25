package com.capstone.JobR;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Dimension;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.capstone.JobR.DBA.spec.SpecController;
import com.capstone.JobR.DBA.spec.SpecVO;
import com.capstone.JobR.DBA.user.UserController;
import com.capstone.JobR.DBA.user.UserVO;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewEmployActivity extends AppCompatActivity implements View.OnClickListener{
    //idwrite: ID입력, passwordWrite: PASS 입력, password2write: 중복확인 PASS입력, emailwrite: 이메일 입력
    //codewrite: 이메일 인증코드 입력, nicknamewrite: 닉네임 입력, schoolwrite: 출신 대학명, scorewrite: 학점
    private EditText idwrite, passwordwrite, password2write, emailwrite, codewrite, nicknamewrite, schoolwrite, scorewrite;
    //companynamewrite = 회사이름 입력, 이거는 쉽지?
    private EditText companynamewrite, toeicwrite, toeflwrite, tepswrite, opicwrite, toswrite;
    //dupcheck: 아이디 중복확인, dupcheck2: 닉네임 중복확인, registerbutton: 회원가입, passwordcheck: 비밀번호 체크
    private Button dupcheck, sendmail, certificate, dupchecknick, registerbutton ,passwordcheck;
    //영어스펙
    private int toeic, toefl, teps, opic, tos;
    //인턴십 체크
    private RadioButton internbutton1, internbutton2;
    //인증을 위한 난수를 담을 string
    private String emailCode, reciptemail, company;
    //spinner
    private Spinner spinner;
    //user, spec 통신
    private UserController controller = new UserController();
    private SpecController controller2 = new SpecController();
    //회원가입하기 위한 UserVO, SpecVO
    private UserVO user = new UserVO();
    private SpecVO spec = new SpecVO();

    private LinearLayout codecheck;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register_gotjob);

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

        companynamewrite =  findViewById(R.id.companynamewrite);
        toeicwrite = (EditText) findViewById(R.id.toeicwrite);
        toeflwrite = (EditText) findViewById(R.id.toeflwrite);
        tepswrite = (EditText) findViewById(R.id.tepswrite);
        opicwrite = (EditText) findViewById(R.id.opicwrite);
        toswrite = (EditText) findViewById(R.id.toswrite);

        dupcheck =(Button) findViewById(R.id.dupcheck);
        sendmail =(Button) findViewById(R.id.sendmail);
        certificate =(Button) findViewById(R.id.certificate);
        dupchecknick =(Button) findViewById(R.id.dupcheck2);
        passwordcheck = findViewById(R.id.passwordcheck);

        //인턴십
        internbutton1 = findViewById(R.id.internbutton1);
        internbutton2 =  findViewById(R.id.internbutton2);
        //학교, 학점
        schoolwrite = findViewById(R.id.schoolwrite);
        scorewrite = findViewById(R.id.scorewrite);
        //회원가입 버튼
        registerbutton = findViewById(R.id.registerbutton);

        //이메일 인증 버튼
        certificate.setOnClickListener(this);

        //이메일 인증 확인 레이아웃
        codecheck = findViewById(R.id.codecheck);

        //아이디 입력+중복확인
        dupcheck.setOnClickListener(view ->{
            String id = idwrite.getText().toString();
            if(id.equals("")){
                callwrongtoast("아이디를 입력해주세요");
                //Toast.makeText(getApplicationContext(), "아이디를 입력해주세요!", Toast.LENGTH_SHORT).show();

            }else{
                registerCheckId(id);
            }

        });
        //비밀번호 중복확인하기
        passwordcheck.setOnClickListener(view -> {
                    String checkpassword = passwordwrite.getText().toString();
                    String checkpassword2 = password2write.getText().toString();
                    if (checkpassword.equals(checkpassword2) && !(checkpassword.equals(("")))) {
                        user.setPassword(checkpassword);
                        Toast.makeText(getApplicationContext(), "비밀번호가 일치합니다.", Toast.LENGTH_SHORT).show();
                    }else if(checkpassword.equals("") && checkpassword2.equals("")) {
                System.out.println(checkpassword);
                callwrongtoast("비밀번호를 입력해주세요");
            }else{
                callwrongtoast("비밀번호가 일치하지 않습니다");
            }
                });

        //이메일 인증
        sendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    callwrongtoast("이메일 형식이 잘못되었습니다");
                    //Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                } catch (MessagingException e) {
                    callwrongtoast("이메일을 입력해주세요");
                    //Toast.makeText(getApplicationContext(), "이메일ㅇ", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //닉네임 중복 확인
        dupchecknick.setOnClickListener(view ->{
            String nickname = nicknamewrite.getText().toString();
            if(nickname.equals("")){
                callwrongtoast("닉네임을 입력해주세요");
                //Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요!", Toast.LENGTH_SHORT).show();
            }else{
                registerCheckNick(nickname);
            }

        });

        //회사선택
        spinner = (Spinner) findViewById(R.id.spinner);

        //스피너 회사 선택시 보여주기?
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!parent.getSelectedItem().toString().equals("직접입력")) {
                    companynamewrite.setText(parent.getItemAtPosition(position).toString());
                    companynamewrite.setEnabled(false);
                }
                else{
                    companynamewrite.setText("");
                    companynamewrite.setEnabled(true);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //인턴십 버튼
        internbutton1.setOnClickListener(view ->{
            String selectinternbutton1 = internbutton1.getText().toString();
            System.out.println(selectinternbutton1);
            spec.setInternship(true);

        });
        internbutton2.setOnClickListener(view ->{
            String selectinternbutton2 = internbutton2.getText().toString();
            System.out.println(selectinternbutton2);
            spec.setInternship(false);
        });

        //회원가입
        registerbutton.setOnClickListener(view ->{
            String insertCompany = companynamewrite.getText().toString();
            String schoolname = schoolwrite.getText().toString();
            String score = scorewrite.getText().toString();

            if(user.getId()==null||user.getPassword()==null||user.getEmail()==null||user.getNickname()==null||insertCompany.equals("직접입력")
                    ||schoolname.equals("")) {
                if (user.getId() == null) {
                    callwrongtoast("아이디를 입력해주세요");
                    return;
                } else if (user.getPassword() == null) {
                    callwrongtoast("비밀번호를 입력해주세요");
                    return;
                } else if (user.getEmail() == null) {
                    callwrongtoast("이메일을 입력해주세요");
                    return;
                } else if (user.getNickname() == null) {
                    callwrongtoast("닉네임을 입력해주세요");
                    return;
                } else if (schoolname.equals("")) {
                    callwrongtoast("최종학력을 입력해주세요");
                    return;
                }else if(insertCompany.equals("직접입력")){
                    callwrongtoast("회사를 선택해주세요");
                    return;
                }

            }else{
                spec.setDegree(schoolname);
                spec.setCompanyName(insertCompany);
                Float abc = Float.parseFloat(score);
                spec.setScore(abc);
            }
            englishInsert();

            register();

            //다음 화면 연결
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            //화면 전환
            startActivity(intent);
        });

    }

///////////////////////////////////////////////////////////////////////////////////////////////////
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
                        //Toast.makeText(getApplicationContext(), "중복입니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "가입 가능한 아이디 입니다.", Toast.LENGTH_SHORT).show();
                        user.setId(id);
                        spec.setId(id);
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
                        callwrongtoast("중복입니다");
                        //Toast.makeText(getApplicationContext(), "중복입니다.", Toast.LENGTH_SHORT).show();
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
    //영어스펙 담기
    public void englishInsert(){
        String a,b,c,d,e;
        a = toeicwrite.getText().toString();
        b = toeflwrite.getText().toString();
        c = tepswrite.getText().toString();
        d = opicwrite.getText().toString();
        e = toswrite.getText().toString();
        if(a.getBytes().length<=0) { toeic = 0;}else{toeic = Integer.parseInt(a);}
        if(b.getBytes().length<=0) { toefl = 0;}else{toefl = Integer.parseInt(b);}
        if(c.getBytes().length<=0) { teps = 0;}else{teps = Integer.parseInt(c);}
        if(d.getBytes().length<=0) { opic = 0;}else{opic = Integer.parseInt(d);}
        if(e.getBytes().length<=0) { tos = 0;}else{tos = Integer.parseInt(e);}

        spec.setOpic(opic);
        spec.setTeps(teps);
        spec.setToefl(toefl);
        spec.setToeic(toeic);
        spec.setTos(tos);
        System.out.println("어학점수 확인하기 \n"+" 토익: "+spec.getToeic()+" 토플: "+spec.getToefl()
                +" 텝스: "+spec.getTeps()+" 오픽: "+spec.getOpic());
    }

    //회원가입
    public void register() {
        //user 회원가입 하기
        user.setJobSort("회사원");
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

        //Spec 입력
        controller2.API.write(spec).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                else{
                    Log.d("연결이 성공적 : ", response.body());
                    Toast.makeText(getApplicationContext(), "등록되었습니다.", Toast.LENGTH_SHORT).show();
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

            case R.id.certificate:
                String codewrite2 = codewrite.getText().toString();

                if (codewrite2.equals(emailCode)) {
                    Toast.makeText(this, "이메일 인증 성공", Toast.LENGTH_SHORT).show();
                    user.setEmail(reciptemail);
                } else {
                    Toast.makeText(this, "이메일 인증 실패", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }
}