# 테스트 주도 개발(TDD)

- 먼저 테스트 코드를 작성하고, 그 테스트를 통과하기 위한 최소한의 코드를 작성하는 방식
- 기존의 개발 방식 : 설계 -> 코드 개발 -> 테스트 코드 작성
- TDD : 테스트 코드 작성 -> 코드 개발 -> 코드 수정(리팩토링)

### TDD의 3단계
1. Red: 실패하는 테스트를 작성. 기능이 아직 구현되지 않았기 때문에 테스트는 실패함.
2. Green: 테스트를 통과할 수 있을 정도로 최소한의 코드를 작성. 이 단계에서는 구현의 간단함이 중요함.
3. Refactor: 테스트가 통과하면, 코드를 리팩토링함. 이 단계에서는 코드의 중복을 제거하고, 가독성을 높이며, 유지보수성을 개선.

### TDD의 장점
1. 신뢰성 향상: 모든 기능이 테스트로 검증되므로, 코드의 신뢰성이 높아진다.
2. 버그 조기 발견: 코드 작성 초기 단계에서 버그를 발견하고 수정할 수 있음.
3. 문서화: 테스트 코드는 코드의 동작 방식을 문서화하는 역할을 함.

### TDD의 단점
1. 초기 투자 비용: 초기에는 테스트 코드를 작성하는 데 시간이 추가로 소요됨.
2. 과잉 테스트 가능성: 너무 많은 테스트를 작성하여 유지보수가 어려워질 수 있음.
3. 학습 곡선: TDD를 익히고 적용하는 데 시간이 필요함.

### TDD의 적용 방법
1. 단위 테스트 작성: 가장 작은 단위의 기능을 테스트함. 이는 함수나 메서드 수준에서 이루어짐.
```
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {
    
    @Test
    public void testAddItem() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Apple", 2);
        assertEquals(2, cart.getItemQuantity("Apple"));
    }

    @Test
    public void testRemoveItem() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Apple", 2);
        cart.removeItem("Apple");
        assertEquals(0, cart.getItemQuantity("Apple"));
    }
}
```
```
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<String, Integer> items = new HashMap<>();

    public void addItem(String itemName, int quantity) {
        items.put(itemName, items.getOrDefault(itemName, 0) + quantity);
    }

    public void removeItem(String itemName) {
        items.remove(itemName);
    }

    public int getItemQuantity(String itemName) {
        return items.getOrDefault(itemName, 0);
    }
}
```
2. 통합 테스트 작성: 여러 단위가 결합된 기능을 테스트함.
```
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartIntegrationTest {
    
    @Test
    public void testAddAndRemoveItems() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Apple", 2);
        cart.addItem("Banana", 3);
        assertEquals(5, cart.getTotalItems());
        
        cart.removeItem("Apple");
        assertEquals(3, cart.getTotalItems());
    }
}
```
3. 인수 테스트 작성: 시스템 전체의 기능을 사용자 관점에서 테스트함.
```
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartAcceptanceTest {
    
    @Test
    public void testCompleteOrder() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Apple", 2);
        cart.addItem("Banana", 3);

        OrderService orderService = new OrderService();
        orderService.completeOrder(cart);

        assertEquals(0, cart.getTotalItems());
    }
}
```

### 면접 질문
- **TDD에 대해서 설명해주세요.** <br>
TDD(Test-Driven Development)는 매우 짧은 개발 사이클의 반복에 의존하는 개발 프로세스로, 개발자는 우선 요구되는 기능에 대한 테스트케이스를 작성하고,  <br>
그에 맞는 코드를 작성하여 테스트를 통과한 후에 상황에 맞게 리팩토링하는 테스트 주도 개발 방식을 의미합니다.  <br>
개발자는 테스트를 작성하기 위해 해당 기능의 요구사항을 확실히 이해해야 하기 때문에 개발 전에 요구사항에 집중할 수 있도록 도와주고, 계속된 테스트를 통해 보다 안정적인 시스템의 개발을 돕지만  <br> 
테스트를 위한 진입 장벽과 작성해야 하는 코드의 증가는 단점으로 뽑힙니다.
