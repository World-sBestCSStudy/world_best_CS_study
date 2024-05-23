## JWT
JSON Web Token

JSON 객체에 사용자를 인증하고 식별하기 위한 정보를 담은 후 비밀키로 서명한 토큰으로, 인터넷 표준(RFC 7519) 인증방식

서버와 클라이언트가 정보를 주고 받을 때 HTTP Request Header에 JWT를 포함하여 주고 받으며 인증을 한다.

    ➕ HTTP의 특성
    Connectionless: 한 번 통신이 일어나고 나면 연결이 끊어진다.
    Statless: 이전 상태를 유지/기억하지 않는다.

    - JWT를 통해 무상태인 환경에서도 사용자 데이터를 주고 받을 수 있다.

### 장점
1. 서버의 확장성이 높으며 대량의 트래픽이 발생해도 대처할 수 있다.
2. (분리된 서버의 경우) 특정 DB/서버에 의존하지 않아도 인증할 수 있다.
3. 로컬에 저장하여 서버 용량에 영향을 끼치거나 받지 않는다.
4. 서명을 할 경우 보안성을 높일 수 있다.

### 단점
1. 세션 방식보다 비교적 많은 양의 데이터가 반복해서 전송되기 때문에 네트워크 성능 저하 가능성이 있다. 즉, 토큰 크기가 크면 트래픽에 영향을 미칠 수 있다.
2. 데이터 노출로 인한 보안 문제 존재
    > 이러한 단점의 보완을 위해 JWT로 **데이터 압축 및 서명**을 한다.
3. 토큰 만료 기간 처리를 따로 해주어야한다.
4. 클라이언트에 토큰이 저장되어 DB에서 사용자 정보를 조작하더라도 토큰에 직접 적용할 수가 없다.


### 특징
1. JSON 데이터는 Url-safe(URL에 포함할 수 있는 문자열로만 구성되어 있음)하다.
2. JWT는 HMAC 알고리즘을 사용하여 비밀키 또는 RSA를 이용한 Public Key/ Private Key 쌍으로 서명할 수 있다.


<br/>

## JWT의 구조

![image](https://github.com/seodangdogProject/seodangdog/assets/80496853/b0eeb7a5-f423-4da9-8746-9d3187884abb)
세파트로 나누어지며, 각 파트는 점(.)으로 구분한다.

### 헤더 Header
- typ: 토큰 타입("JWT")
- alg: Signature 암호화 알고리즘. (HS256, SHA256, HMAC, RSA ...)


### 정보 Payload
토큰에서 사용할 정보의 조각들인 클레임(Claim)이 담겨있다. 
클레임은 key-value 형태를 가진다.

저장되는 정보에 따라 Registered Claims(토큰 정보를 표현하기 위해 이미 정해진 종류의 데이터. iss, sub, exp ...), Public Claims(사용자 정의 클레임으로, 공개용 정보를 위해 사용, 충돌 방지를 위해 URI 포맷 사용), Private Claims(사용자 정의 클레임으로, 서버와 클라이언트 사이에 임의로 지정한 정보를 저장) 로 구분된다.


### 서명 Signature
토큰을 인코딩하거나 유효성 검증을 할 때 사용하는 고유한 암호화 코드이다. 즉, 헤더와 페이로드가 변조 되었는지를 확인하는 역할.

Signature는 헤더와 페이로드의 문자열을 합친 후에, 헤더에서 선언한 알고리즘과 key를 이용해 암호한 값이다. 

Header와 Payload는 Base64url로 인코딩되어 있어 누구나 쉽게 복호화할 수 있지만, Signature는 key가 없으면 복호화할 수 없다.
(= 서버 측에서 관리하는 비밀키가 유출되지 않는 이상 복호화할 수 없다. 이는 토큰의 위변조 여부를 확인하는데 사용된다.)

? 앞서 언급한 것처럼 header에서 선언한 알고리즘에 따라 key는 개인키가 될 수도 있고 비밀키가 될 수도 있다. 개인키로 서명했다면 공개키로 유효성 검사를 할 수 있고, 비밀키로 서명했다면 비밀키를 가지고 있는 사람만이 암호화 복호화, 유효성 검사를 할 수 있다.


---

### JWT 생성 방법
1. 헤더(Header)와 페이로드(Payload)의 값을 각각 BASE64로 인코딩
2. 인코딩한 값에 키를 더해 헤더(Header)에서 정의한 알고리즘으로 해싱
3. 이 값을 다시 BASE64로 인코딩하여 생성


<br/>


### JWT 동작과정

![image](https://github.com/seodangdogProject/seodangdog/assets/80496853/9335ed89-77da-47e0-9294-669c3e2c325f)


<br/>

### 토큰과 세션의 차이점
토큰 발급 방식: 몇 만명의 인증 방식을 저장하고 있지 않기 때문에, 가볍고 확장성이 좋은 방식. (보안성 문제: 세션 방식에서는 세션을 만료 시키면되지만, 토큰은 직접적으로 만료시키는 방식이 아니라 기간을 설정해서 만료시킴)
세션 발급 방식: 무겁지만, 그만큼 보안성이 좋은 방식.

<br/>
<br/>

## ❓ 면접질문
**Q. jwt가 무엇인가요?**
```
A. jwt는 JSON Web Token의 약자로, 웹에서 사용되는 토큰 기반의 인증 방식 중 하나입니다.
JWT는 토큰 자체가 모든 정보를 가지고 있기 때문에 서버의 세션 저장소에 정보를 저장할 필요가 없으며, 사용자 정보를 인코딩하여 토큰에 저장하므로 클라이언트와 서버 간의 통신이 더욱 간편해집니다.
```


**Q. jwt와 세션 중 왜 jwt를 사용하셨나요? (jwt의 장점)**
```
A. 세션은 서버 측에 인증 정보가 저장되어 인증/인가를 하는 과정에서 서버 자원에 접근을 하는 반면, jwt는 사용자의 정보가 토큰의 payload에 저장되어 있기 때문에 서버 자원에 접근하지 않더라도 인증이나 인가를 할 수 있습니다. 즉, 서버의 자원을 사용하는 횟수가 세션에 비해 적습니다. 그래서 다중 서버로 늘렸을 때 트래픽 처리에 용이하기 때문입니다.
```

**Q. jwt는 그럼 세션보다 무조건 좋은건가요?**
```
A. 그것은 아닙니다. 토큰을 클라이언트가 가지고 있어서 탈취를 당하게 되었을 경우 access token을 무효화 시킬 수 있는 방법이 따로 없습니다. 그렇기 때문에 토큰이 노출될 경우, 보안에 취약해 질 수 있습니다. 반면 세션은 보안상 문제가 생겼을 경우, 세션을 만료시키므로써 보안을 유지할 수 있어 상대적으로 무겁지만 보안성이 좋습니다.
```
<br/>
<br/>

참고 : [알고 쓰자, JWT](https://velog.io/@chuu1019/%EC%95%8C%EA%B3%A0-%EC%93%B0%EC%9E%90-JWTJson-Web-Token), [우리는 왜 JWT를 사용하는가?](https://puleugo.tistory.com/138), 