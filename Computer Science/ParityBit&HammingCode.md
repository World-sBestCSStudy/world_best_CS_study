# 패리티 비트 (Parity Bit)

## 개념
> 데이터를 전송하는 과정에서 오류가 생겼는지 검사하기 위해 추가하는 비트. 
> 전송하고자 하는 데이터의 문자에 1비트를 추가하여 전송한다.

## 짝수 패리티와 홀수 패리티
패리티배트의 종류에는 짝수(Even), 홀수(Odd) 두 가지가 있다.
전체 데이터의 비트들을 다 더했을 때 짝수로 만들면 짝수, 홀수로 만들면 홀수이다.<br>
송신자와 수신자 간 약속을 통해 짝수로 할지 홀수로 할지 정해서 전송한다.

<img width="623" alt="image" src="https://github.com/user-attachments/assets/56f7128a-d3d4-43b3-8647-f8641a2d859c">

## 문제점
- 하지만 이러한 패리티 비트를 통해서는 홀수 개의 오류만 검출할 수 있으며, 짝수개의 오류는 검출하지 못한다!<br>
  - 위의 예시에서 짝수 패리티를 붙혀 전송한 경우<br>
0001 1011 이 맞는 데이터인데<br>
0000 0011 으로 전송되는 경우 짝수는 맞기 때문에 오류인 것을 확인하지 못함.<br>
- 또한 패리티비트를 사용할 경우 오류 발생 여부만 알 수 있고 수정할 수는 없다! 따라서 오류를 수정하는 것이 아니라 재전송 요청만을 보냄
    > 이를 자가수정하기 위해 해밍코드 사용

> 오류의 위치를 찾을 수 있는 2차원 패리티검사(병렬 패리티)도 존재한다. 기존의 수평 구조에 대한 패리티비트 뿐 아니라 블럭 단위로 수직 단위의 패리티비트를 추가한 방식. 하지만 해당 방식도 수평 수직으로 짝수개씩 똑같은 오류가 발생하면 검출이 불가능.<br>
> ![image](https://github.com/user-attachments/assets/0efee97f-b89f-4fd6-a91b-c2c1c674730d)

# 해밍코드

## 개념
기존의 패리티코드는 오류를 검출할 수만 있지 수정할 수는 없다는 문제가 있었다.<br>
이러한 문제를 해결하고자 나온 것이 해밍코드!
> 에러 비트의 위치를 알 수 있고 정정할 수도 있는 추가적인 체크비트

해밍코드는 패리티비트 (1bit)에 비해 많은 수의 체크비트가 추가된다. 따라서 비효율적이라고 느낄 수 있지만 실시간으로 데이터를 정정할 수 있다는 장점은 굉장히 크기 때문에 실제로 많이 사용되고 있다.

## 작동 방식
우선 필요한 패리티 개수는 다음과 같다.
![](https://mblogthumb-phinf.pstatic.net/MjAxNzEwMDlfMTY1/MDAxNTA3NTI0NDc5MTM2.uayg26tFpxObVNgxxjfgGThnDbhmFB6zfujKCZfUrMgg.YIZMBLhXV-0L1-1R8cOEbY9AY_gEQGKsGuESNYCIS5sg.JPEG.ggggamang/%EA%B3%B5%EC%8B%9D.JPG?type=w800)<br>
위 공식을 만족하는 최소 p값을 사용한다.<br>
식으로 보면 복잡해 보일 수 있으니 실제 값이 삽입되는 예시를 보자. 

### 예시
1바이트의 데이터를 전송한다고 했을 때, 비트 수는 8bit이므로  체크비트 p값은 4이다.<br>
데이터 비트가 8비트, 체크 비트가 4비트이므로 총 12비트의 데이터가 만들어지게 된다.<br>
데이터: **0010 0011<br>**
데이터가 들어갈 모양을 보자면 다음과 같다.
<img width="485" alt="image" src="https://github.com/user-attachments/assets/448eb182-e50c-4dce-b214-a1d8d2355802"><br>
체크비트가 삽입되는 위치는 오른쪽부터 2^n의 자리이다. 여기서는 1,2,4,8번째 자리에 체크비트가 삽입되게 된다. 그렇다면 해당 자리를 비워놓고 데이터 비트를 채우면 된다.
<img width="485" alt="image" src="https://github.com/user-attachments/assets/a4a3eabf-b98d-4beb-a126-72139ac9acb7"><br>
여기서 삽입될 체크비트들이 각자 담당하는 영역이 있다. 담당하는 영역들에 따라 패리티 비트로써 동작하기 때문! 영역은 다음과 같다.

1. 1번째 위치에 삽입될 체크비트
    <img width="485" alt="image" src="https://github.com/user-attachments/assets/1f2a17e4-e470-49e8-9198-559abd5bbe01">
2. 2번째 위치에 삽입될 체크비트
   <img width="485" alt="image" src="https://github.com/user-attachments/assets/462f86ba-2101-4f11-9874-4ac175618613">
3. 4번재 위치에 삽입될 체크비트
   <img width="485" alt="image" src="https://github.com/user-attachments/assets/fc9490b8-598d-4cd2-9d2d-db6635e1ca81">
4. 8번째 위치에 삽입될 체크비트
   <img width="485" alt="image" src="https://github.com/user-attachments/assets/90eb088b-cce1-40f6-9d62-4325428beb3b"><br>

각자의 자리수가 n이라고 했을 때, n개씩 띄워진 n개를 읽는다. 색칠된 영역의 비트를 짝수 혹은 홀수로 맞추는 패리티 비트가 각 영역에 채워진다고 보면 된다.<br>
예시에서 패리티 비트를 채운다면<br>
<img width="485" alt="image" src="https://github.com/user-attachments/assets/802c7cdf-000b-4c26-a876-947ce5b373a8"><br>
패리티비트를 1100으로 만들 수 있다.
여기서 만약 11번째 비트가 잘못 전송되었다고 해보자.
<img width="485" alt="image" src="https://github.com/user-attachments/assets/61dd8519-02cc-4364-a97e-a953e2d3c26a"> <br>
패리티 비트는 0111이 될 것이다.
기존의 1100과 0111을 비교해 보았을 때 XOR연산을 통해 1011이라는 오류 비트를 얻을 수 있다.<br>
해당 오류 비트를 10진수로 변환해 보면 11이 나온다. 11번째 자리에서 오류가 발생했다는 뜻! 따라서 해당 비트를 반대로 바꿔주기만 하면 오류를 정정할 수 있다.

# 오류 검출
## 개념
> OSI 7계층인 Data Link Layer에서 진행되는 작업으로, 비트로 이루어진 데이터의 오류를 검출하고 수정하는 것.

데이터 통신 과정에서 발생하는 오류를 검출하는 것은 추가적인 몇 가지 방법이 더 있다.
## CRC(Cyclic Redundancy Check: 중복 순환 검사)
> 특수한 계산 식을 사용해서 데이터를 중복검사하는 방법이다.
### 계산식(Polynomial)
CRC에서 계산 시 사용되는 식이다. 주로 지수형태로 표현된다. 일반적으로 다음과 같은 다항식이 사용된다.<br>
![image](https://github.com/user-attachments/assets/1294b5fb-cb23-46ca-9ee9-6b58395993c1)
### 작동 방식 및 예시
CRC는 중복 검사 방식으로, 계산식을 데이터에 계속 나눔으로써 CRC비트를 만들어 낸다. 우선 계산식을 비트로 변환해야 한다.여기서는 CRC-8을 사용.<br>
<img width="187" alt="image" src="https://github.com/user-attachments/assets/1abd2a31-6776-4c02-aa2d-e23b4f386729"><br>
-> 100000111 (다항식의 계수만 떼서 생성)<br>
**1바이트 데이터: 1101 0011**<br>
1. 데이터의 끝에 `계산식의 길이 -1`만큼 0을 추가한다. 여기서 계산식은 9자리이므로 8개의 0 추가
   ```
   1101 0011 0000 0000
   ```
2. 데이터의 맨 앞부터 xor 연산을 진행한다.
   ```
   1101001100000000
   100000111
   ----------------
   0101000010000000
   ```
3. 이 후 계산식을 오른쪽으로 한 비트씩 이동해가며 계속 XOR 연산을 진행(맨 앞 비트가 0이라면 바로 다음자리로 넘어감). 원래의 데이터가 모두 0이 될 때까지 진행
   ```
   101000010000000
   100000111
   ----------------
   001000101000000
     100000111
     --------------
     0000100110000
         100000111
        -----------
         000110111
   ```
   기존의 데이터를 모두 0으로 바꾸고 남은 값이 CRC 값.
4. 원래의 데이터에 CRC를 붙혀 데이터를 전송한다.
   ```
   1101 0011 0011 0111
   ```
5. 수신 측은 동일한 CRC 계산 절차를 수행. 송신 오류가 없다면 마지막 8개 비트가 0000 0000으로 끝남.
   ```       
   1101001100110111
   100000111
   ----------------
   0101000010110111
    100000111
    ---------------
    001000101110111
      100000111
      -------------
      0000100000111
          100000111
          ---------
          000000000
   ```
   
## 체크섬(checksum)
> 중복검사의 한 형태로 무결성을 보호하는 단순한 방법

데이터를 모두 더하고, 더한 수의 캐리니블(최상위 4비트)를 버린 값의 2의 보수를 체크섬으로 사용
### 예시
`ABCD`라는 값을 데이터로 사용한다고 했을 때 각 데이터를 아스키코드로 나타내면 다음과 같다.
```
'A': 65
'B': 66
'C': 67
'D': 68
```
각 데이터의 값을 모두 더한다.
```
65+66+67+68 = 266
```
이를 16진수로 변환하면 `0x10A`
비트로 나타내면 `0001 0000 1010`, 캐리니블인 `0001`을 버리면 `0000 1010`. <br>
해당 값에 2의 보수를 취한다. (2의 보수 -> 1의 보수를 취한 후 +1)
```
1의 보수 -> 1111 0101
+1 -> 1111 0110 (체크섬 값)
```
원래의 수에 체크섬 값을 더하면 266+246 => 512. 2진수로 나타낼 시 `0010 0000 0000`이므로 캐리니블을 버렸을 겨우 `0000 0000`이 되므로 이상 없다는 것을 알 수 있다.

# 질문
## 해밍코드 방식에는 어떤 문제점이 있나요?
```
체크 비트의 수가 많이 추가된다는 점과 오류가 두개 이상일 경우 정정하지 못한다는 문제점이 있습니다.
```

# 참고 
[패리티비트&해밍코드](https://gyoogle.dev/blog/computer-science/computer-architecture/%ED%8C%A8%EB%A6%AC%ED%8B%B0%20%EB%B9%84%ED%8A%B8%20&%20%ED%95%B4%EB%B0%8D%20%EC%BD%94%EB%93%9C.html)<br>
[패리티비트(Parity Bit](https://velog.io/@octo__/%ED%8C%A8%EB%A6%AC%ED%8B%B0-%EB%B9%84%ED%8A%B8-Parity-Bit)<br>
[체크섬](https://hojak99.tistory.com/246)<br>
[패리티 비트](https://kih0902.tistory.com/46)<br>
[오류 검출 방식](https://junboom.tistory.com/32)<br>
[CRC 뜻과 계산 방법](https://depotceffio.tistory.com/entry/CRC%EC%9D%98-%EB%9C%BB%EA%B3%BC-%EA%B3%84%EC%82%B0-%EB%B0%A9%EB%B2%95)<br>
[Hamming Code](https://m.blog.naver.com/ggggamang/221113176831)<br>
[체크섬 생성 및 검증](https://velog.io/@narcoker/%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC-checksum-%EC%83%9D%EC%84%B1-%EB%B0%8F-%EA%B2%80%EC%A6%9D)
