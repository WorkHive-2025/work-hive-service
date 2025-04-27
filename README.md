# Work&Hive 2025 Backend

## 💡 개발환경 셋팅
### 1. 환경변수 설정
.env 파일을 프로젝트 폴더 하위에 생성하고 아래와 같이 설정합니다.

```bash
# Database Configuration
DB_HOST=localhost
DB_NAME=workhive
DB_USERNAME=root
DB_PASSWORD=1234
DB_PORT=3306

# Redis Configuration
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_PASSWORD=
REDIS_DB=0

# JWT secret key
JWT_SECRET_KEY=d29ya2hpdmVqd3RzZWNyZXRrZXkg
```

### 3. application-dev.properties 추가
```bash
#.env 파일을 읽어오기 위한 설정
spring.config.import=optional:file:.env[.properties]
```

✅ 운용환경 설정은 application-prod.properties 에서 작성