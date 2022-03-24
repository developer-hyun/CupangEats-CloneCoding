# Cupang Eats Clone Coding

 + **배달 서비스 전문 어플리케이션 "쿠팡 이츠"를 클론 코딩!**
 + **최대한 원본 어플리케이션과 똑같이 구현하는것이 목표**
 + **오픈 API를 사용해보기**
 + **AOS분과 협업을 통해 클론 코딩 진행(developer-hyun은 백엔드 부분 진행)**
 + **Client(AOS)와 RestFulAPi 및 Api 명세서로 원할하게 소통하기**
<br/>

### 🥇 POINT
 + **앱 UI는 클라이언트 분이 따로 만들었으며 백엔드 개발이 맡은 역활!!!!**



### 🥕 USE TOOL

+ **SpringBoot**
+ **MYSQL**
+ **AWS**
+ **GIT**

# Clone Coding 서버 구조

![image](https://user-images.githubusercontent.com/84491771/159438398-af753700-a483-421b-b2c2-7c50d22fcd92.png)


<br/>

# ERD 설계

![image](https://user-images.githubusercontent.com/84491771/159482016-08c95f8a-1d25-4fa8-bc40-a6e4a61a967a.png)


<br/>

# 주요 화면

<img src="https://user-images.githubusercontent.com/84491771/159491906-7fa370dc-bc9a-49fb-ab8a-149b3bd0344f.png" width="250" height="300"/><img src="https://user-images.githubusercontent.com/84491771/159492243-56a20f58-4bf9-4a25-8bbe-9916df4e7f22.png" width="250" height="300"/>

**로그인/로그아웃/회원가입**
* **1) 회원가입 시 정해진 형식(숫자,특수문자포함 등)에 맞지 않으면 반환하는 Validation 적용**
* **2) 로그인,회원가입 시 JWT인증 방식을 통해 TOKEN을 발급하여 해당 토큰의 유효성 여부 검증**
* **3) KAKAO SOCIAL LOGIN 구현**

<img src="https://user-images.githubusercontent.com/84491771/159494598-a303a5ec-19d0-4afd-aa3b-19e4cc3d7da9.png" width="250" height="300"/><img src="https://user-images.githubusercontent.com/84491771/159493922-a2d34195-2ba9-42a3-8414-1a814c819671.png" width="250" height="300"/>

**메인화면**
* **1) 할인 중인 맛집, 인기 가맹점 등으로 쿠팡 잇츠와 똑같이 제공하도록 노력함**

<img src="https://user-images.githubusercontent.com/84491771/159494833-2f02f6e6-27e6-4a54-9fb7-3bb7e8399ac1.png" width="250" height="300"/><img src="https://user-images.githubusercontent.com/84491771/159494983-521017a7-5d7e-4c5a-8f85-09a33ee41887.png" width="250" height="300"/>

**배달지설정**
* **1) Naver Open API를 이용하여 User가 주소지를 입력하면 Client로부터 위도 경도를 받아 주소를 반환하는 로직으로 구현**
* **2) 배달지설정/ 배달지변경/ 배달지삭제/ 배달지즐겨찾기 구현**

<img src="https://user-images.githubusercontent.com/84491771/159496018-f29ab9cd-0a13-40ba-a0fe-e162f4d955bc.png" width="250" height="300"/><img src="https://user-images.githubusercontent.com/84491771/159496237-b3259f56-7b46-4b9b-824c-96cff967bd67.png" width="250" height="300"/>

**스토어화면**
* **1) 스토어의 상품 정보 구현**
* **2) 상품 주문 시 서브 상품 주문이 가능하게 구현**


<img src="https://user-images.githubusercontent.com/84491771/159496769-c67317d5-09a6-4f15-97cb-a95eb1d1a12b.png" width="250" height="300"/><img src="https://user-images.githubusercontent.com/84491771/159496925-f3ea50fa-ac68-4ab7-ae82-d330afecff51.png" width="250" height="300"/>
<img src="https://user-images.githubusercontent.com/84491771/159497143-6476430d-0160-4ac8-9458-9fe049fdb2d3.png" width="250" height="300"/>

**카트/주문내역**
* **1) 선택한 상품 카트에 담기 및 주문(할인쿠폰) 구현**
* **2) 과거 주문 내역 조회 구현**

<img src="https://user-images.githubusercontent.com/84491771/159497699-a857dd2b-ff17-436b-b370-1d3f989f5b59.png" width="250" height="300"/><img src="https://user-images.githubusercontent.com/84491771/159498181-c7197c72-02df-496e-a2e2-c3064fa02e7e.png" width="250" height="300"/>


**리뷰/즐겨찾기**
* **1) 리뷰 및 리뷰 좋아요/싫어요 구현**
* **2) 스토어에 대한 즐겨찾기 추가/삭제 구현**

<br><br>

# 주요기능(정리)

#### 1. 회원가입/로그인/로그아웃(소셜로그인)
#### 2. 배달지 주소 설정/변경/삭제
#### 3. 결제 관리
#### 4. 문자 인증
#### 5. 스토어 리뷰
#### 6. 홈 화면
#### 7. 주문
#### 8. 검색









