# calendar-api-server

## Project setup
```
mvn install
```

### Compiles and hot-reloads for development
```
./mvnw spring-boot:run
```
### Run your unit tests
```
mvn test
```

### 프로젝트 구성
Spring boot rest service를 이용해서 만들었으며 프로젝트 구성은 스프링 사이트에 등록된 문서(https://spring.io/guides/gs/rest-servic)를 이용해서 구현해습니다.

그 외에 추가적으로 h2database, spring-boot-starter-data-jpa, commons-collections4을 설치했습니다.

h2database - 캘린더 일정을 관리하기 위한 메모리DB
commons-collections4 - 조회 기능시 반환되는 Iterator -> list로 변환하기 위해서

사용되는 포트는 8080번으로 http://localhost:8080 으로 실행할 수 있습니다
http://localhost:8080/schedule/health-check 를 이용해서 API서버가 제대로 구동중인지 확인할 수 있습니다.

CURD를 수행하기 위해서 4개의 API가 제공된다.
- Get

/schedule/{year}/{month}

````
curl -X GET \
  http://localhost:8080/schedule/2019/8 \
  -H 'Accept: */*' \
  -H 'Accept-Encoding: gzip, deflate' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Host: localhost:8080' \
  -H 'Postman-Token: 0452bcbe-c07d-4ecb-b25c-55c481234da9,fb1fa440-ceb4-4e2c-8034-d14ce3a83dae' \
  -H 'User-Agent: PostmanRuntime/7.15.2' \
  -H 'cache-control: no-cache'
````

- Post

/post-schedule

````
curl -X POST \
  http://localhost:8080/post-schedule \
  -H 'Accept: */*' \
  -H 'Accept-Encoding: gzip, deflate' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Content-Length: 69' \
  -H 'Content-Type: application/json' \
  -H 'Host: localhost:8080' \
  -H 'Postman-Token: 8ef944f0-4838-49fe-a33b-de267bb1b806,676b41a0-6041-4339-a9b1-2ecc347365bb' \
  -H 'User-Agent: PostmanRuntime/7.15.2' \
  -H 'cache-control: no-cache' \
  -d '{"start":1565334000000,"end":1565341200000,"title":"테스트111222"}'
````

- Put

/put-schedule

````
curl -X PUT \
  http://localhost:8080/put-schedule \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: eb6867da-efa0-4140-8f84-cdbd20a8bfda' \
  -H 'cache-control: no-cache' \
  -d '{"id":10,"start":1565334000000,"end":1565341200000,"title":"update-테스트111222"}'
````
- Delete
/schedule/1

````
curl -X DELETE \
  http://localhost:8080/schedule/3 \
  -H 'Postman-Token: 78310392-9002-49b8-85f1-9e69f2ee9957' \
  -H 'cache-control: no-cache'
````

