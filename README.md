# 🤔 취업이 알고 싶을 땐 - JobR 

<img width="300" alt="스크린샷 2020-06-14 오후 11 02 32" src="https://user-images.githubusercontent.com/55496667/123357549-abe20000-d5a4-11eb-8667-98b45620febb.png">

## 📝 주요 기능
* **이메일 인증을 통한 회원가입 기능 제공**
  * 무분별한 회원가입 방지

* **평균 스펙 차트 제공**
  * 회사원들이 제공한 스펙을 바탕으로 회사별 스펙 제공
  * 스펙별 평균점수 제공
  * 스펙별 분포도 제공

* **기업 정보 제공**
  * 기업별 기본 정보 제공
  * 네이버 지도 API를 통한 위치 정보 제공

* **커뮤니티 기능 제공**
  * 회사원들과 취준생들간의 소통 장소 제공
  
* **게시글 좋아요💕 기능 제공**
  * '좋아요'한 게시글 스크랩
  * '좋아요' 갯수에 따른 TOP10 게시글 조회 가능

<br/>

## About Project

> 명지대학교 컴퓨터공학과 2021-1 캡스톤디자인
<br/>

## ️🙋🏻‍♂️팀 구성
  * **안재엽: Project Manager & Server Manager**

    * ` AWS EC2 `, `AWS RDS`, ` Spring Boot `, ` REST API `, `RETROFIT2`, `Chart`, `Open Source`

  * **이요한: Design Manager & Database Manager**

    * ` UI Manager`, `MySQL Manager`, `Naver Map API`, `Conference Recorder`

  * **한병욱: Server Manager & Database Manager**

    * `SMTP`, ` Spring Boot`, ` REST API`, `RETROFIT2`, `MySQL Manager`, `Schedule Manager`

<br/>

## 💻 개발 환경
* Android Studio (JAVA)

* AWS: EC2, RDS

* Eclipe: Spring Boot(REST API)

* MySQL Workbench


<br/>

## ⚒   Project Architecture

> System Architecture
<img width="500" height="400" alt="스크린샷 2020-06-14 오후 10 49 21" src="https://user-images.githubusercontent.com/55496667/123371111-0c306c00-d5bc-11eb-90ff-432b81095c50.png">
<img width="500" height="400" alt="스크린샷 2020-06-14 오후 10 49 21" src="https://user-images.githubusercontent.com/55496667/123371120-10f52000-d5bc-11eb-86c6-19cf0f7511c8.png">

> DB Architecture

![Database Shcema-4 (1)](https://user-images.githubusercontent.com/55496667/123366554-5d882d80-d5b3-11eb-91ea-0a34aed9f034.png)

* **user**
  * 사용자의 아이디와 비밀번호, 해당 사용자가 가지고 있는 하위 데이터
* **board**
  * 게시글 데이터
* **good**
  * 게시글 좋아요 데이터
* **comment**
  * 댓글 데이터
* **company**
  * 회사 데이터
* **spec**
  * 사용자가 입력한 스펙 데이터

<br/>

 ## 🗞  Reference Lists
  - **개발 환경**
    - [안드로이드 다양한 UI 및 UX](https://github.com/wasabeef/awesome-android-ui)
    - [회원가입 이메일 인증 구현](https://csy7792.tistory.com/m/209)
    - [AWS 구축](https://aws.amazon.com/ko/getting-started/hands-on/build-android-app-amplify/)
    - [Spring Boot Rest API 서버 구축(환경구축)](https://binit.tistory.com/13?category=925287)
    - [MySql 생성 및 연결](https://aws.amazon.com/ko/getting-started/hands-on/create-mysql-db/)

   
  - **오픈소스**
    - [네이버 지도 API](https://navermaps.github.io/android-map-sdk/guide-ko/0.html)
    - [Android Chart](https://github.com/lecho/hellocharts-android)
    - [안드로이드 Rest API 호출](https://velog.io/@jini0318/Android-Retrofit2%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-API-%EC%84%9C%EB%B2%84%ED%86%B5%EC%8B%A0)


<br/>  

## 📑  회의록
- [2021년 3월 15일 월요일 아이디어 회의](./회의내용/캡디_아이디어_회의.txt)
- [2021년 4월 13일 화요일 회의록](./회의내용/캡디1_회의내용_4.13.txt)
- [2021년 4월 20일 화요일 회의록](./회의내용/캡디1_회의내용_4.20.txt)
- [2021년 4월 27일 화요일 회의록](./회의내용/캡디1_회의내용_4.27.txt)
- [2021년 5월 3일 월요일 회의록](./회의내용/캡디1_회의내용_5.03.txt)
- [2021년 5월 6일 목요일 회의록](./회의내용/캡디1_회의내용_5.06.txt)
- [2021년 5월 12일 수요일 회의록](./회의내용/캡디1_회의내용_5.12.txt)
- [2021년 5월 25일 화요일 회의록](./회의내용/캡디1_회의내용_5.25.txt)
- [2021년 6월 2일 수요일 회의록](./회의내용/캡디1_회의내용_6.02.txt)

<br/>  

## 🎬 프로젝트 시연 영상
[![Video Label](http://img.youtube.com/vi/_eP65T2OfAA/0.jpg)](https://youtu.be/_eP65T2OfAA)
