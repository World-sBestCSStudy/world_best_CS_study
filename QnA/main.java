import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder sb = new StringBuilder();


	public static void main(String[] args) throws Exception {
		Random random = new Random();

		String[] questions = {
				"Data Structure: Array와 ArrayList의 차이점에 대해 설명해주세요.",
				"Data Structure: Array와 LinkedList의 차이점과 각각의 장/단점에 대해 설명해주세요.",
				"Data Structure: Dynamic Array를 설명하고 Linked List와 비교해주세요.",
				"Data Structure: List와 Map, Set의 차이점에 대해 설명해주세요.",
				"Data Structure: Stack과 Queue에 대해 설명하고, 각각의 예시를 설명해주세요.",
				"Data Structure: Priority Queue(우선순위 큐)에 대해 설명해주세요. Queue와는 어떻게 다른가요?",
				"Data Structure: Priority Queue(우선순위 큐)의 동작 원리와 구현 방법에 대해 설명해주세요.",
				"Data Structure: Heap 자료구조에 대해 설명해주세요.",
				"Data Structure: 트리와 그래프의 차이에 대해서 설명할 수 있나요?",
				"Data Structure: 그래프를 구현할 수 잇는 방법에 대해 설명해주세요.",
				"Data Structure: BST(Binary Search Tree)와 Binary Tree에 대해 설명해주세요.",
				"Data Structure: 이진 탐색 트리는 어떤 문제점이 있고 이를 해결하기 위한 트리 중 한 가지를 설명해주세요.",
				"Data Structure: Red Black Tree와 AVL Tree에 대해 설명해주세요.",
				"Data Structure: Hash Map과 Hash Table의 차이점에 대해 설명해주세요.",
				"Data Structure: 해시 테이블(Hash Table)과 그 시간 복잡도에 대해 설명해주세요.",
				"Data Structure: Hash Collision (해시 충돌)에 대해 설명할 수 있나요? 해결 방법은 뭐가 있을까요?",
				"Algorithm: 시간복잡도와 공간복잡도가 무엇인지 설명해주세요.",
				"Algorithm: 시간복잡도와 공간복잡도가 높은 경우 취할 수 있는 일반 전략을 각각 간단히 설명해주세요.",
				"Algorithm: 동적 계획법(DP, Dynamic Programming)에 대해 설명해주세요. 동적 계획법이 갖는 2가지 조건은 무엇인가요?",
				"Algorithm: 다양한 정렬 방법이 있는데, 알고있는 방법에 대해 간단히 설명해주세요.",
				"Algorithm: 본인이 사용하는 언어의 정렬 알고리즘에 대해 말해주세요.",
				"Algorithm: 이분탐색이 무엇이고 시간복잡도는 어떻게 되며 그 이유는 무엇인가요?",
				"Algorithm: BFS와 DFS에 대해 설명해주세요.",
				"Algorithm: 그래프의 최단경로를 구하는 방법과 구현방법에 대해 간단하게 말해주세요.",
				"Algorithm: MST(최소 신장 트리)와 구현방법에 대해 설명해주세요.",
				"Algorithm: 허프만 코딩에 대해 설명해주세요.",
				"Algorithm: 최대 공약수와 최소 공배수 알고리즘을 설명해주세요.",
				"Algorithm: 피보나치 수열을 구현하는 방식과 각 방식의 시간 복잡도에 대해 설명해주세요.",
				"Network: OSI 7계층에 대해 설명해주세요",
				"Network: TCP와 UDP에 대해서 설명해주세요",
				"Network: TCP의 연결과 끊는 과정에 대해서 설명해주세요.",
				"Network: TCP의 연결 과정과 연결 종료 과정 단계가 차이나는 이유가 뭔가요?",
				"Network: UDP는 항상 신뢰성을 보장하지 않나요?",
				"Network: 흐름제어와 혼잡제어가 무엇인가요?",
				"Network: 흐름제어와 혼잡제어를 하는 방법이 뭐가있을까요?",
				"Network: HTTP와 HTTPS에 대해서 설명해주세요",
				"Network: HTTPS의 동작 과정이 어떻게 되나요?",
				"Network: HTTP/1.1과 HTTP/2의 차이에 대해서 설명해주세요.",
				"Network: 비대칭키(공개키) 암호화와 대칭키 암호화에 대해 설명해주세요.",
				"Network: 로드 밸런싱(Load Balancing) 에 대해 말해보세요.",
				"Network: 서버 확장의 두 가지 방법(Scale-Up, Scale-Out) 에 대해 설명해보세요.",
				"Network: 로드 밸런싱 알고리즘 중 대표적인 라운드 로빈, 최소 연결 방식 에 대해 설명해보세요",
				"Network: Blocking/Non-Blocking과 sync/async의 차이에 대해 설명하세요",
				"Network: www.google.com에 접속할 때 생기는 과정을 네트워크 관점에서 설명해 주세요.",
				"Design Pattern: 디자인 패턴이란 무엇인가요?",
				"Design Pattern: 디자인 패턴은 크게 세 가지 분류가 존재합니다. 각 분류가 무엇인지 설명하시오.",
				"Design Pattern: 싱글톤 패턴이 무엇인지 설명하고, 스프링 빈에서의 싱글톤을 설명하시오.",
				"Design Pattern: 팩토리 패턴이 무엇인지 간단한 예를 들어 설명하시오.",
				"Design Pattern: 옵저버 패턴이 무엇인지 설명하시오.",
				"Design Pattern: MVC, MVP, MVVM 패턴에 대해 간단히 설명하시오.",
				"Design Pattern: 프록시 패턴을 사용하는 이유를 프록시 패턴의 개념과 함께 설명하시오.",
				"Design Pattern: 본인이 디자인 패턴을 적용하여 개발한 경험에 대해 말해주세요.",
				"Programming Paradigm: 프로그래밍 패러다임이 무엇인지 설명하고 중요성에 대해 말해보시오.",
				"Programming Paradigm: 프로그래밍 패러다임은 크게 명령형 프로그래밍과 선언형 프로그래밍으로 분류됩니다. 각 패러다임의 개념을 설명하고 대표적인 언어를 말하시오.",
				"Programming Paradigm: 자바는 대표적인 객체지향 프로그래밍 언어로 유명했습니다. 하지만 Java 8 이후부터 함수형 프로그래밍을 지원하는데 어떠한 방식으로 지원하는지 말해보시오.",
				"Programming Paradigm: 객체 지향의 네 가지 특징에 대해 말해보시오.",
				"Programming Paradigm: SOLID라고 불리는 객체지향 설계 원칙에 대해 설명하시오.",
				"Programming Paradigm: 함수형 프로그래밍에서 설명하는 순수함수에 대해 설명하시오.",
				"Programming Paradigm: 객체지향에서 캡슐화를 통해 얻을 수 있는 이점을 설명하시오.",
				"Programming Paradigm: 함수형 프로그래밍이 병렬 처리에 있어 안정적인 이유에 대해 설명하시오.",
				"Database: 데이터베이스에서 무결성이 뭔지 설명해주세요.",
				"Database: 데이터베이스 언어(DDL, DML, DCL)에 대해 설명해주세요.",
				"Database: SELECT 쿼리의 수행 순서를 알려주세요.",
				"Database: Redis의 특징을 설명하고, 언제 쓰이는지 설명해주세요. 만약 사용한 경험이 있다면 왜 사용했는지 이유를 말해주세요.",
				"Database: Index에 대해서 설명하고 장점과 단점에 대해서 설명해주세요.",
				"Database: 정규화에 대해서 알고있는데로 설명해주세요.",
				"Database: SQL Injection이 무엇인지 설명해주세요.",
				"Database: RDBMS와 NOSQL의 차이를 아시나요?.",
				"Database: 트랜잭션이 무엇인지 설명하고 특성도 알고있는데로 말해봐요.",
				"Database: inner join과 outer join의 차이점을 아시나요?.",
				"Database: 옵티마이저에 대해 아는데로 답해주세요.",
				"Database: JPA와 MyBatis를 모두 사용했다고 되어있는데 각자의 특징이 무엇이며 왜 사용했나요?.",
				"Database: 오픈소스 데이터베이스중에 PostgreSQL, MariaDB 나 MongoDB도 있는데 굳이 Mysql을 사용하신 이유가 있나요?.",
				"Database: 데이터베이스의 key에는 어떤 종류가 있는지 알고있는데로 말해봐요.",
				"Database: 프로시저가 뭔지 알고있나요?.",
				"Database: 데이터베이스의 회복기법에 대해 알고있는걸 말해봐요.",
				"JAVA: 오버라이딩과 오버로딩에 대해 설명해주세요.",
				"JAVA: JAVA는 Call by Value인지 Call by reference인지 설명해주세요.",
				"JAVA: 주언어로 Java를 사용한다고 하셨는데 다른 언어들도 많은데 왜 굳이 Java를 쓰는지 말해주세요.",
				"JAVA: Checked Exception과 Unchecked exception에 대해서 설명해주세요.",
				"JAVA: ArrayList가 내부적으로 어떻게 돌아가는지 아시나요?",
				"JAVA: 제네릭이 무엇인가요?",
				"JAVA: 자바는 왜 상속 하나만 가능할까요?",
				"JAVA: 상속과 구현의 차이에 대해서 설명해주세요.",
				"JAVA: 추상 클래스와 인터페이스의 차이에 대해서 설명해주세요.",
				"JAVA: Synchronize에 대한 설명해주세요.",
				"JAVA: 해시를 사용할 때 equals와 hashcode 왜 오버라이딩 해줘야하나요?",
				"JAVA: Java의 main문이 public static void인 이유에 대해서 설명해주세요.",
				"JAVA: 자바 바이트 코드가 무엇인가요?",
				"JAVA: Garbage Collection(GC)에 대해서 설명해주세요.",
				"JAVA: JVM의 동작과정 및 원리에 대해서 설명해주세요.",
				"JAVA: JDBC가 무엇인가요?",
				"OS: 프로세스와 스레드의 차이점을 설명해주세요.",
				"OS: 멀티 프로세스와 멀티 스레드의 차이점을 설명해주세요",
				"OS: 프로세스에서 메모리 구조가 어떻게 구성되어 있는지 설명해 주세요",
				"OS: 그럼 힙 영역과 스택영역의 차이를 설명해 보세요",
				"OS: 인터럽트가 무엇인지 설명해주세요",
				"OS: 시스템콜이 무엇인지 알고있나요?",
				"OS: PCB가 무엇인지 알고있나요?",
				"OS: Context Switching이 무엇인지 설명하고 언제 발생하는지 설명해주세요.",
				"OS: 프로세스는 독립적으로 동작한다고 하셨는데 만약 프로세스간 통신이 필요하면 어떻게 하나요?",
				"OS: 스케줄링이 무엇인지 설명하고 알고 계시는 스케줄링 알고리즘을 설명해주세요",
				"OS: 데드락이 무엇인지 설명하고 언제 발생하는지 설명해주세요.",
				"OS: 그럼 데드락이 발생할 경우 어떻게 해결할수 있나요?",
				"OS: 세마포어와 뮤텍스가 무엇인지 알고 있나요?",
				"OS: 페이징과 세그먼테이션이 무엇인지 설명하고 왜 쓰는지도 설명해주세요.",
				"OS: 페이지교체알고리즘을 왜 쓰는지 설명하고, 알고 있는 알고리즘이 있다면 설명해주세요.",
				"OS: 캐시메모리를 왜 사용하는지와 캐시메모리를 어떻게 참조하는지 설명해주세요.",
				"SE: TDD에 대해 설명해주세요.",
				"SE: 클린코드에 대해 설명해주세요.",
				"SE: 리팩토링에 대해 설명해주세요",
				"SE: 리팩토링과 클린코드의 차이점을 말해보세요.",
				"SE: 애자일 개발 방법론에 대해 설명해주세요.",
				"SE: OOP에 대해 설명해주세요.",
				"SE: 개발할 때 무엇을 고민하며 개발하는지 말해주세요.",
				"SE: MSA에 대해 설명해주세요.",
				"Spring: Spirng Framework에 대해 설명해주세요.",
				"Spring: @RequestBody, @RequestParam, @ModelAttribute의 차이를 설명해주세요.",
				"Spring: Spring Framework와 Spring Boot의 차이점을 설명해주세요.",
				"Spring: Spring MVC에 대해 설명해주세요.",
				"Spring: 제어의 역전(IoC)에 대해 아는대로 설명해주세요.",
				"Spring: 스프링에서 빈(Bean)을 등록하는 방법과 빈의 생명주기(Lifecycle)는 어떻게 관리 되는지 말해주세요.",
				"Spring: 의존성 주입(DI)에 대해 설명해주세요.",
				"Spring: OP(관점지향 프로그래밍)에 대해 설명해주세요.",
				"Spring: Spring Filter와 Interceptor에 대해 설명하고, 사용 예시를 설명해주세요.",
				"Spring: @Transactional의 동작 원리에 대해 설명해주세요.",
				"Spring: DAO, DTO, BO, VO에 대해 아는대로 설명해주세요.",
				"Spring: Spring Batch에 대해 설명해주세요.",
				"CS: CISC란 무엇인가요?",
				"CS: ARM과 RISC에 대해 설명하시오.",
				"CS: CPU의 구성요소 3가지와 동작 과정에 대해 설명하시오.",
				"CS: 인터럽트의 개념과 처리과정에 대해 설명하시오.",
				"CS: 캐시 메모리의 개념과 필요한 이유에 대해 설명하시오.",
				"CS: 지역성에 대해 설명하시오.",
				"CS: 캐시 대체 알고리즘에 대해 설명하시오.",
				"CS: 패리티비트의 개념과 장단점에 대해 설명하시오.",
				"CS: 해밍코드의 개념과 장단점에 대해 설명하시오.",
				"CS: 컴퓨터가 소수를 표현하는 두 가지 방법에 대해 설명하시오.",
				"CS: 컴퓨터가 소수를 표현할 때 오차가 생기는 이유에 대해 설명하시오.",
				"CS: 컴퓨터의 기억장치엔 세 가지가 있습니다. 각각에 대해 설명하시오."
		};
		String[] members = {"재원", "창희", "성완", "정호"};
		boolean[] v = new boolean[questions.length];
		for (int i = 0; i < 4; i++) {
			br.readLine();
			System.out.println("답변자: "+members[i]);
			int n = random.nextInt(3);
			int[] cntQ = {6,8,10};
			System.out.println("문제 개수: "+cntQ[n]);
			for (int j = 0; j < cntQ[n]; j++) {
				br.readLine();
				int randomNumber =  random.nextInt(questions.length);
				if(v[randomNumber]) {
					j--;
					continue;
				}
				v[randomNumber] = true;
				System.out.println("# "+questions[randomNumber]);
			}
		}
	}

}