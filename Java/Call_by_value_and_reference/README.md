## Call by Value & Reference
> 함수가 인수를 전달할 때 사용되는 방식

<br>

## call by value
함수가 인수로 전달받은 값을 **복사**하여 처리하는 방식. 이때 변수가 가진 값을 복사하여 전달하므로 함수 내에서 **값을 변경해도 원본 값은 변경되지 않는다.** 따라서 값의 불변성을 유지하는 데 용이하다.

**예시**
```
void func(int n) {
    n = 20;
    printf("%d", n);    // 20
}

void main() {
    int n = 10;
    func(n);
    printf("%d", n);    // 10
}
```

- **장점**: 복사하여 처리하기 때문에 안전하다. 원래의 값이 보존이 된다.
- **단점**: 복사를 하기 때문에 메모리가 사용량이 늘어난다.

<br>

### Java의 함수 호출 방식
자바의 경우 원시 자료형, 참조 자료형에 관계 없이 항상 **call by value**<br>
C/C++처럼 변수의 주소값 자체를 가져올 방법이 없으며, 넘길 방법도 없다.<br>
참조 자료형(reference type)을 넘길 시에는 해당 객체의 주소 값을 복사하여 이를 가지고 사용한다.

따라서 원본 객체의 프로퍼티까지는 접근이 가능하나, 원본객체 자체를 변경할 수는 없다. 

💫 주소값을 복사하여 전달하고, 주소값에 저장되어있는 값을 사용하기 때문에 call by reference라고 오해할 수 있다.
  - 주의: 자바에서 변수는 '할당된 값의 위치'를 '값'으로 갖는다.
  - C/C++에서는 주소값 자체를 인자로 넘겼을 때 값을 변경하면 새로운 값으로 덮어 쓰여 기존 값이 변경되고, Java에서는 주소값이 덮어 쓰여지므로 원본 값은 전혀 영향이 가지 않는 것이다. (객체의 속성값에 접근하여 변경하는 것은 직접 접근하여 변경하는 것이므로 이를 가리키는 변수들에서 변경이 일어난다.)

<br>

## call by reference
참조에 의한 호출. 함수 호출 시 인자로 전달되는 변수의 레퍼런스를 전달. 함수 안에 인자 값이 변경되면, 전달된 원본 객체의 값도 변경됨.

```
void func(int *n) {
    *n = 20;
    printf("%d", n);    // 20
}

void main() {
    int n = 10;
    func(&n);
    printf("%d", n);    // 20
}
```