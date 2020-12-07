# Helper
고령의 노인분들의 수가 증가함에 따라 독거노인의 고독사, 노인분들의 낙상사고, 치매노인의 실종사고의 발생 또한 증가하는데 
이런 사고들이 발생할 위험을 피보호자의 스마트폰을 통해 감지해서 보호자 또는 지역센터에 알림을 보내거나 보호자 또는 지역센터가 피보호자를 모니터링할 수 있게 해주는 REST API형 서버입니다. 

# 사용 기술 및 환경
Spring boot, Gradle, MySQL, Naver Cloud Platform, Jenkins, Nginx, Java11,
# 실행방법
test and build
```
./gradlew test
./gradlew build
```

start api-server
```
cd ./build/libs 
java -jar helper-0.0.1-SNAPSHOT.jar --server.port=9000
```
