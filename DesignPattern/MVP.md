# MVP 패턴

<center><img width="400" height="" src="https://github.com/dahui0525/world_best_CS_study/assets/80496853/115e4a9c-1f4f-496c-b8a3-90d5d02b6567"></center>

C ⇒ **P**resenter
>**Presenter**: Model과 View 사이에서 중개자 역할<br>
    - Controller와 유사하지만 **View에 직접 연결되지 않고 사용자 인터페이스를 통해 상호작용**하게 된다. (Presenter ← PresenterImpl)<br>
    - View에서 요청한 정보를 통해 Model을 가공하여 View로 전달해주는 방식을 취한다.

<br>

## 동작순서

1. 사용자의 **요청**은 **View**를 통해 받게 된다.
2. View는 **데이터를 Presenter에 요청**한다.
3. Presenter는 **Model에게** 데이터를 **요청**한다.
4. **Model은** 요청을 통해 비즈니스 **로직을 수행**하여 Presenter에서 요청받은 **데이터를 전달**한다.
5. Presenter는 **View에게** 전달받은 데이터를 **응답**한다.
6. View는 Presenter가 응답한 데이터를 이용하여 화면을 **출력**한다.

<br>

## 특징
1. Preseter와 View의 관계
    > 1:1 관계이다.
2. View와 Model은 서로를 알 필요가 전혀 없다.

<br>

## 장점
1. Presenter를 통해서만 데이터를 전달받기 때문에 MVC 패턴의 약점 중 하나인 **View와 Model의 의존성을 제거**할 수 있음

<br>

## 단점
1. **View와 Presenter가 1:1**이기 때문에 **둘 사이의 의존성**이 높아지게 된다.
2. 각각의 View마다 Presenter가 존재하게 되어서 코드량이 많아져 **유지보수**가 힘들어질 수 있다.