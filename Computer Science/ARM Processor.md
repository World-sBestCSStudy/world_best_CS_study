# ARM Processor
> ARM이란 `Advanced RISC Machine`의 약자로, 임베디드 기기에서 주로 사용되는 32비트 프로세서. 저전력을 사용하도록 설계하였기 때문에 모바일 기기에 많이 쓰인다. 

# CISC & RISC
둘 모두 서로 다른 ISA 설계 철학을 나타 냄. 

## ISA?
> `Instruction Set Architecture`로, 고수준 언어인 애플리케이션이 시스템과 컴파일러를 거쳐 로우 레벨의 언어로 번역되어 하드웨어와 마주하게 되는 영역의 인터페이스를 의미 -> 하드웨어와 소프트웨어가 **서로 어떻게 소통할 지 정해놓은 규약!**
> 
![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdZk6Gk%2FbtqI6Op5Pbo%2FG1mDB65uzZb0dpQ9AHeWqK%2Fimg.png)


## CISC (Complex Instruction Set Computer)
> CPU에게 명령을 내리는데 필요한 모든 명령어를 가지고 있는 구조. 복잡하고 기능이 많은 명령어로 구성되어 있다.

- 기능이 많기 때문에 하위 호환성이 좋다. 따라서 호환성이 필요한 PC 환경에서는 CISC를 주로 사용!
- 하지만 전력 소모가 크고, 속도가 느리며 가격이 비싸다.

## RISC (Reduced Instruction Set Computer)
> 적은 수의 명령어만을 수행하도록 설계된 마이크로 프로세서. 복잡한 명령어는 제거하고 사용빈도가 높은 명령어 위주로 처리 속도를 향상한 구조. 

- 적은 수의 명령어를 빠르게 처리.
- 발열과 전력 소모가 적기 때문에 임베디드기기에서는 RISC 구조를 사용한다.





# 질문
## Q1. ARM 이란 무엇인가요?
```
    Advanced RISC Machine의 약자로, 임베디드 기기에서 주로 사용되는 32비트 프로세서를 의미합니다. 기존의 CISC 구조보다 간단한 RISC 체계의 한 형식으로, 저전력과 단순화된 CPU 명령체계라는 특징이 있습니다. 
```

## Q2. 프로세서란 무엇인가요?
```
    메모리에 저장된 명령어를 실행하는 유한 상태 오토마톤을 의미합니다. 시스템의 상태는 프로세서에 있는 레지스터의 값들과 메모리에 저장된 값들에 의해 결정됩니다. 
```

## Q3. ARM 프로세서가 주로 쓰이는 분야는 어느 분야인가요?
```
    낮은 전력 소비와 높은 성능 덕분에 거의 모든 스마트폰과 태블릿에 사용됩니다. 또한 임베디드, IoT, 네트워킹 장비에 사용됩니다.
```

## Q4. ARM 프로세서와 x86 프로세서의 차이점은 무엇인가요?
```
    ARM은 RISC 아키텍처를 사용하고 x86은 CISC아키텍처를 사용한다는 아키텍처 측면의 차이가 가장 큽니다. 또한 ARM 프로세서는 저전력 소모를 중심으로 설계되어 저전력 장치에 주로 사용됩니다.
```

# 참고
[[컴퓨터구조]ISA](https://levenshtein.tistory.com/166)<br>
[[CS]CISC와 RISC의 차이](https://velog.io/@yarogono/CS-CISC%EC%99%80-RISC%EC%9D%98-%EC%B0%A8%EC%9D%B4%EC%99%80-%EA%B0%9C%EB%85%90)<br>
[RISC와 CISC 개념, 분석, 비교](https://yaneodoo2.tistory.com/entry/RISC%EC%99%80-CISC%EC%9D%98-%EB%AA%A8%EB%93%A0-%EA%B2%83-%EA%B0%9C%EB%85%90-%EB%B6%84%EC%84%9D-%EB%B9%84%EA%B5%90)
<br>
[[컴퓨터구조]ARM프로세서](https://daily-progress.tistory.com/71)<br>
[ARM아키텍처의 이해](https://m.blog.naver.com/suresofttech/221249244004)