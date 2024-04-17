# MVC 패턴

<center><img width="400" height="" src="https://github.com/dahui0525/world_best_CS_study/assets/80496853/d289dce6-446e-4b21-a144-20c0cbd268ee"></center>

**M**odel - **V**iew - **C**ontroller 패턴

비즈니스 로직과 화면을 구분하는 데 중점을 둔 디자인 패턴 <br>
⇒ **관심사의 분리**(**SoC**, seperation of concerns)

> **Model**: 데이터와 비즈니스 로직을 관리<br>
    - 앱이 포함해야할 데이터가 무엇인지 정의<br>
    - 데이터의 상태가 변경되면, 모델을 뷰나 컨트롤러에게 알린다.<br><br>
> **View**: 레이아웃과 화면을 처리<br>
    - 모델의 데이터를 시각적으로 보여주는 방식을 정의<br>
    - 사용자의 입력을 받아 컨트롤러로 전달<br><br>
> **Controller**: 모델과 뷰로 명령을 전달<br>
    - 사용자로부터의 입력에 대한 응답으로 모델 또는 뷰를 업데이트하는 로직을 포함<br><br>
    예를 들어, 웹 애플리케이션에서 컨트롤러는 라우팅과 사용자 요청을 처리하고, 모델은 데이터베이스와 상호작용하며, 뷰는 HTML 페이지를 생성합니다.

<br>

## 동작순서
1. 사용자의 **요청**은 **Controller**를 통해 받게 된다.
2. **Controller**가 사용자로부터 받은 Input을 Model에 전달해 **Model 업데이트**
3. **Model**은 Controller로부터 받은 요청에 **응답**
4. **Controller**는 **Model로부터 받은 Data**를 **View에 전달**
5. Controller로부터 받은 Data를 UI에 넣어 사용자에게 **출력**

<br>

> 📌 ***mvc에서 뷰가 업데이트 되는 방법***<br>1. View가 Model을 이용한 직접 업데이트<br>2. Model에서 View에게 Notify 하여 업데이트<br>3. View가 Polling으로 주기적으로 Model의 변경을 감지하여 업데이트 

<br>

## 특징
1. 컨트롤러와 뷰의 관계
    > Controller는 View를 선택할 수 있으므로 *1:N*의 관계가 된다.
2. 뷰는 컨트롤러를 알지 못함
3. 뷰는 모델의 변화를 직접적으로 알지 못함
4. 모델은 뷰를 직접적으로 업데이트할 수 없고, 컨트롤러를 통해서만 업데이트할 수 있다.

<br>

## 장점
1. 컴포넌트의 명확한 역할 분리로 인해 서로간의 **결합도를 낮출 수 있다.**
2. 코드의 **재사용성 및 확장성**을 높일 수 있다.
3. 서비스를 **유지보수**하고 **테스트**하는데 용이해진다.(**유연성** 향상)
4. 개발자 간의 **커뮤니케이션 효율성**을 높일 수 있다.

<br>

## 한계 및 단점
1. Model과 View의 의존성을 완전히 분리시킬 수 없다.
    > *복잡한 구조의 애플리케이션일수록 하나의 Controller에 다수의 View와 Model이 복잡하게 연결되어 서로간의 의존성이 커지는 상황이 발생*
2. 컨트롤러의 비중이 높아져 부담이 커진다면 `Massive-View-Controller` 현상을 피할 수 없다.
    > 📌 *Massive-View-Controller*<br>
    MVC 패턴에서 *컨트롤러의 역할이 과도하게 커지고 복잡해지는 상황*을 지칭한다. 이는 주로 대규모 애플리케이션에서 발생할 수 있으며, *코드의 비대화, 재사용성 및 확장성 저하, 유지보수성 하락 및 테스트 용이성 저하* 등의 문제를 야기할 수 있다.

<br>

### 예시
> 📌 **Spring MVC**<br>
![image](https://github.com/dahui0525/world_best_CS_study/assets/80496853/4d6a83a3-cabb-4e27-aee4-f7e8c6471f4d)
