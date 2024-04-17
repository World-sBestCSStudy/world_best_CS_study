## 선언형과 함수형 프로그래밍

- **선언형 프로그래밍**
  - ‘무엇을' 풀어내는가에 집중하는 패러다임
  - "프로그램은 함수로 이루어진 것이다." 라는 명제가 담겨 있는 패러다임
- **함수형 프로그램**

  - '순수함수'들을 블록처럼 쌓아 로직을 구현하고 '고차함수'를 통해 재사용성을 높인 프로그래밍 패러다임
  - 함수를 다른 함수의 파라미터로 넘길 수도 있고 반환 값으로 함수를 받을 수도 있는 프로그래밍 형태
  - 대표적인 예시로 **자바스크립트**</br>
**❓ 순수함수**</br>
    출력이 입력에만 의존하는 함수

  ```jsx
  function add(a, b) {
    return a + b;
  }
  console.log(add(10, 5));
  ```

  **❓ 고차함수**</br>
  함수가 함수를 값처럼 매개변수로 받아 로직을 생성할 수 있는것

  - 일급 객체

    1. 변수에 할당(assignment) 할 수 있다.
    2. 다른 함수의 인자(argument)로 전달될 수 있다.
    3. 다른 함수의 결과로서 리턴될 수 있다.

    ```jsx
    const square = function (num) {
      return num * num;
    };

    output = square(7);
    console.log(output); // --> 49
    ```
    위의 함수 표현식(function expression)은 함수 선언식(function declaration)과 다르게 호이스팅(Hoisting)이 적용되지 않는다.
    호이스팅은 선언된 위치에 관계없이 어디서든 함수를 사용할 수 있다(함수 선언부를 코드의 최상단으로 끌어올리는 것처럼 보이게 한다.) → 유지보수가 쉽지않다
    반면에 **함수 표현식**은 함수의 할당과 실행의 위치에 따라 결과가 달라진다 → 코드의 위치를 어느 정도 예측할 수 있다

  - **내가 이해한 고차함수**
    - **함수의 형태로 리턴할 수 있는 함수**
    - 다른 함수의 인자로 전달되는 함수를 **콜백 함수(callback function)**

  ```jsx
  1. 다른 함수를 인자로 받는 경우
  function double(num) {
    return num * 2;
  }

  function doubleNum(func, num) {
    return func(num);
  }
  ```

  ```jsx
  2. 함수를 리턴하는 경우
  function adder(added) {
    return function (num) {
      return num + added;
    };
  }

  // adder(5)는 함수이므로 함수 호출 연산자 '()'를 사용할 수 있습니다.
  let output = adder(5)(3); // -> 8
  console.log(output); // -> 8

  // adder가 리턴하는 함수를 변수에 저장할 수 있습니다.
  // javascript에서 함수는 일급 객체이기 때문입니다.
  const add3 = adder(3);
  output = add3(2);
  console.log(output); // -> 5
  ```

  ```jsx
  3. 함수를 인자로 받고, 함수를 리턴하는 경우
  function double(num) {
    return num * 2;
  }

  function doubleAdder(added, func) {
    const doubled = func(added);
    return function (num) {
      return num + doubled;
    };
  }

  // doubleAdder(5, double)는 함수이므로 함수 호출 기호 '()'를 사용할 수 있습니다.
  doubleAdder(5, double)(3); // -> 13

  // doubleAdder가 리턴하는 함수를 변수에 저장할 수 있습니다. (일급 객체)
  const addTwice3 = doubleAdder(3, double);
  addTwice3(2); // --> 8
  ```
