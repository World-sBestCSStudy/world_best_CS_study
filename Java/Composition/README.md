# 상속(Inheritance)
- 상속은 "is-a" 관계를 나타내며, 부모 클래스의 특징과 기능을 자식 클래스가 물려받는 것을 의미.
- 부모 클래스의 기능을 재사용하여 새로운 클래스를 빠르게 구현할 수 있음.
- 부모 클래스의 변경이 자식 클래스에 영향을 줄 수 있으며, 이는 클래스 간의 강한 결합을 초래하여 유연성을 감소시킬 수 있고, <br>
클래스의 계층 구조를 만들어내기 때문에, 클래스의 디자인을 더 복잡하게 만든다.

# 컴포지션(Composition)
- 컴포지션은 "has-a" 관계를 나타내며, 한 클래스가 다른 클래스를 포함하는 것을 의미.
- 클래스는 다른 클래스를 포함하기만 하면 되므로 객체 간의 결합도를 낮추어 변경이 쉽고 유연한 구조를 만들 수 있음.
- 각 클래스는 단일 책임 원칙을 준수하여 자신의 역할에만 집중하고 다른 클래스의 기능을 조합하여 사용하기 때문에
클래스 간의 결합도를 낮추어 상속보다 더 유연한 설계를 가능하게 한다.

### 상속 예시
```
// 도형을 나타내는 추상 클래스
abstract class Shape {
    protected String color;

    public Shape(String color) {
        this.color = color;
    }

    abstract double area();
}

// 원을 나타내는 클래스
class Circle extends Shape {
    private double radius;

    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }
}

// 사각형을 나타내는 클래스
class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(String color, double width, double height) {
        super(color);
        this.width = width;
        this.height = height;
    }

    @Override
    double area() {
        return width * height;
    }
}
```

### 컴포지션 예시
```
// 도서를 나타내는 클래스
class Book {
    private String title;
    private String author;
    private double price;

    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }
}

// 도서를 대여하는 클래스
class Library {
    private Book book;
    private String borrower;

    public Library(Book book, String borrower) {
        this.book = book;
        this.borrower = borrower;
    }

    public void displayLoanDetails() {
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Price: " + book.getPrice());
        System.out.println("Borrower: " + borrower);
    }
}
```

### 면접 질문
- **상속과 컴포지션(합성)의 차이에 대해서 설명해주세요.** <br>
상속은 is a 관계, 컴포지션은 개체들 간의 has a 관계입니다. <br> 
상속은 클래스를 확장해 부모 클래스에서 속성 및 동작을 상속하는 기능이구요. <br> 
컴포지션은 클래스가 구성원 데이터로 다른 클래스의 객체를 포함할 수 있는 능력입니다.