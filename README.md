# Showing
🎞 사용자 맞춤 공연 추천 서비스

> 티켓값도 비싼데 뭘 봐야 하지? 쇼잉과 함께라면 걱정 마세요.
> <br>
> <br>
> Showing은 공연 정보를 제공하고 리뷰를 남겨 공연을 추천받는 웹서비스입니다.
> <br>
> 직접 본 공연을 리뷰하고 평가 할 수 있으며 평가 데이터를 기반으로 사용자가 선호할 만한 공연을 추천해드립니다.
> <br>
> 나에게 딱 맞는 뮤지컬, 연극을 추천받으세요!

<br />

![](https://github.com/mmmmjjj/Showing/blob/master/exec/main.gif)


## 개발 기간

### 2022.02.21 ~ 2022.04.08 (7주)

<br />

## 팀원 소개

- Backend : 김민준, 김혜지, 이명주

- Frontend : 고주희, 송예진, 최혜린

- Data : 고주희, 김민준, 송예진, 이명주

- Hadoop : 김혜지, 최혜린

<br />

## 기술 스택

### 💻 Front-end

- Node.js
- HTML5, CSS3, JS(ES6)
- Vue.js, Vuex
- Visual Studio Code

### 💻 Back-end

- Java - `openjdk v1.8.0_192`
- Spring Boot - `v2.5.7`
- Django
- MySQL - `v8.0.27`
- JPA, QueryDSL
- JWT(Json Web Token)
- Swagger - `v3`

### 💻 Data

- Hadoop HDFS
- Hadoop mapreduce
- Python
- Selenium

### 💻 Infra

- AWS EC2 Ubuntu 20.04
- Jenkins - [ jenkins/jenkins:lts ] - `v2.332.1`
- Nginx - `nginx/1.18.0 (Ubuntu)`
- Certbot - `certbot 1.25.0`
- Docker - `v20.10.13`

<br />

## 주요 기능

<img width="984" alt="image" src="https://user-images.githubusercontent.com/67090601/200235486-998d25ba-0f01-49e3-9883-c1321024a788.png">

### [🖥 상세 화면](./exec/시연시나리오.pdf)

## 실행 방법

1. **원격 저장소 복제**

```bash
$ git clone https://github.com/mmmmjjj/Showing.git
```

### 프론트엔드 실행

2. **프로젝트 폴더로 이동**

```bash
$ cd frontend
```

3. **필요한 node_modules 설치**

```bash
$ npm install
```

4. **개발 서버 실행**

```bash
$ npm run serve
```

### server 실행

5. **프로젝트 폴더로 이동**

```bash
$ cd backend
$ ./gradlew clean build
```

6. **DB 덤프파일로 로컬 MySQL에 DB 생성**

7. **jar 파일로 프로젝트 실행**

```bash
$ java -jar build/libs/backend-0.0.1-SNAPSHOT.jar
```

## 프로젝트 산출물

- ERD
![image](https://user-images.githubusercontent.com/67090601/200231702-bcb08037-4377-46a7-ad7f-19bf9af88b99.png)

- 화면 설계서
![163381405-6d09eb98-8234-4398-bd16-30a2dff067cf](https://user-images.githubusercontent.com/67090601/200232296-0df28a68-d85b-41b7-b926-98c94065b5fc.png)


- Notion
<img width="1032" alt="image" src="https://user-images.githubusercontent.com/67090601/200230767-6daf35d4-fac9-4d7a-90cb-36cc12e5a78b.png">


- [API 설계서](https://broadleaf-crabapple-56b.notion.site/API-9a5a758b6b074989ab66d53ca90184f6)
- [포팅매뉴얼](./exec/포팅_메뉴얼.pdf)
- [시연시나리오](./exec/시연시나리오.pdf)
