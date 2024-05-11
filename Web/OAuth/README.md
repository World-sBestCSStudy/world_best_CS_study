## OAuth
Open Authorization

다양한 플랫폼의 특정한 사용자 데이터에 접근하기 위해 제 3자(우리 서비스)가 사용자의 접근 권한을 위임 받을 수 있는 표준 프로토콜 
> 쉽게 말하면 우리가 개발한 앱이 구글, 페이스북, 트위터와 같은 타사 플랫폼 정보에 접근하기 위해 **권한을 타사 플랫폼으로부터 위임받는 것**이다. 좀 더 간단하게는 타 플랫폼의 인증과 권한을 획득하는 것.

타사 플랫폼의 입장에서는 **외부 서비스에서도 인증을 가능하게 하고, 외부 서비스가 플랫폼의 API를 이용하게 해주는 것**이다.

사용자 입장에서는 비밀번호를 제공하지 않고도, 다른 웹사이트 상의 자신들의 정보에 대해 웹이나 앱에게 접근 권한을 부여할 수있는 개방형 표준 방법이다.

트위터의 주도로 OAuth가 등장하기 이전에 인증 방식들로 구글의 AuthSub, 야후 BBAuth 등이 있다. 이후 보안 문제를 해결한 OAuth 1.0a 버전이 나왔으나 모바일 상에서 안정성이 보장되지 않았다. 이를 보완하고 좀 더 단순화한 것이 OAuth 2.0이다. 또한 2.0과 1.0은 호환되지 않는다.

<br/>

### 인증과 인가
- 인증(Authentication) : 사용자가 누구인지 확인하는 것. 자격 증명을 확인한다고도 한다.
  - ex. 로그인
  - JWT나 세션을 통해 인증을 유지
- 인가, 권한(Authorization) : 특정 리소스에 대한 권한이 있는지 확인
  - 인가 이전에 인증이 되어야한다.
  - JWT나 세션을 이용해 인증을 확인하고, 서버가 인증된 사용자의 권한을 확인해 요청한 리소스에 대한 접근 권한을 결정한다.

<br/>

## OAuth 2.0
### 특징
- 1.0의 단점을 개선하여 웹이 아닌 앱 지원이 강화되었고, 암호화가 필요 없다.
- 보안 강화를 위해 Access Token에 Life-time을 지정할 수 있게 하였다.

### OAuth 2.0의 구성요소
- Resource Owner: 서드파티 어플리케이션(구글, 카카오 등)에서 리소스(개인정보. 구글 캘린더 정보, 페이스북 친구목록 등)를 소유하고 있으면서 우리 서비스를 이용하려는 사용자.
- Authorization Server: 리소스 소유자를 인증하고, 클라이언트에게 권한을 부여해주는 서버.
    - 사용자가 이 서버로 ID, PW를 넘겨 Authroization Code를 발급 받는다. > 클라이언트가 이 서버로 Authorizaiton Code를 넘겨 Access Token을 발급 받을 수 있다.
- Resource Server: 구글, 페이스북, 트위터와 같이 리소스를 가지고 있는 서버.
    - 클라이언트는 Access Token을 이 서버로 넘겨 개인 정보를 응답 받을 수 있다.
- Client: Resource Server의 자원을 이용하고자하는 서비스. 보통 우리가 개발하는 서비스.
- Access Token: 인증 후 Client가 Resource Server의 자원에 접근하기 위한 키를 포함한 값
- Refresh Token: Access Token이 만료되었을 때, 새로운 Access Token을 발급 받기 위한 Token. Authorization Server에서 Access Token을 발급할 때 Refresh Token도 함께 발급하여 전달한다.

### OAuth 2.0 동작과정
![image](https://github.com/seodangdogProject/seodangdog/assets/80496853/0e84c2e1-efed-4343-8809-2926d2c57216)

+ Authorization Code란 Client가 Access Token을 획득하기 위해 사용하는 임시 코드이다. 이 코드는 수명이 매우 짧다. (일반적으로 1~10분)  
+ OAuth 2.0은 scope를 통해 유저 리소스에 대한 클라이언트의 접근 범위를 제한할 수 있다. 예를 들어 구글 연락처에 접근하고 싶다면 클라이언트인 우리가 스코프에 연락처 스코프 문자열을 포함하여 리소스 서버에 전달하고, 사용자는 스코프에 명시된 권한을 허용하는 요청을 하는 화면을 만날 수 있다. 이 과정을 통해 발급된 액세스 토큰은 부여된 스코프에 해당하는 권한을 제한적으로 획득할 수 있다.

### 간략한 OAuth 2.0 동작과정
![image](https://github.com/seodangdogProject/seodangdog/assets/80496853/556ac126-00f0-44c6-b581-b01c2f30f94f)
1. Resource Owner가 로그인 요청
2. Client는 Authorization Server에게 Access Token 요청
3. Authorization Server가 Access Token 발급 및 Client가 해당 Access Token 저장
4. Access Token 기반으로 Resource Server와 통신
5. Access Token 만료 시 Refresh Token을 이용해 Access Token 재발급

### 간단한 OAuth 1.0 vs 2.0 비교
<img width="542" alt="스크린샷 2024-05-11 오후 5 46 56" src="https://github.com/seodangdogProject/seodangdog/assets/80496853/6bcc9127-8d86-4e31-b9ad-db6efb802e08">


<br/>
<br/>

## ❓ 면접질문
**Q. OAuth 2.0에 대해 설명해주세요.**
```
A. OAuth 2.0은 플랫폼의 사용자 리소스에 접근하기 위한 권한을 외부 서비스에 위임하는 방식의 인증을 위한 개방형 표준 프로토콜입니다. 
```
**Q. OAuth 1.0과 2.0의 차이에 대해 설명해주세요.**
```
A. 2.0이 1.0에 비해 웹이 아닌 앱 지원이 강화되었고, 암호화가 필요 없다는 장점이 있습니다. 또한 Access Token에 Life-time을 지정할 수 있어서 보안성이 더 뛰어납니다.
```
**Q. OAuth 2.0의 동작과정에 대해 설명해주세요**
```
A. 먼저 사용자(리소스 소유자)가 로그인 요청을 하면 클라이언트(저희 서비스)는 Client ID, Redirect URL, 스코프 등을 권한서버로 보냅니다. 이후 권한 서버에서 사용자에게 로그인 페이지를 제공하고 사용자는 ID, PW로 권한 서버에 로그인 요청을 합니다. 로그인 성공 시 권한 서버는 사용자에게 Authorization code를 발급합니다. 이후 사용자는 이 코드르 갖고 클라이언트의 Redirect URL로 리다이렉트 되고, 클라이언트는 이 Authorization Code, Client ID, Client Secret을 갖고 권한 서버에 Access Token을 요청하여 발급 받습니다. 클라이언트는 Access Token을 저장하고 있다가, 이후 사용자가 리소스가 필요한 요청을 할 경우, 클라이언트는 리소스 서버에 이 토큰을 갖고 접근하여 필요 리소스를 받아 사용합니다.
```

<br/>
<br/>

참고 : [OAuth 2.0 개념과 동작원리](https://hudi.blog/oauth-2.0/)
, [OAuth(Open Authorization)](https://velog.io/@octo__/OAuthOpen-Authorization)

