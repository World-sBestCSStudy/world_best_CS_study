# Spring Application

## Spring Application이란?
Spring boot로 프로젝트를 생성하면 기본적으로 생성된다.
![Spring-Application경로](https://github.com/World-sBestCSStudy/world_best_CS_study/assets/77597885/a8f4301f-d48e-4d4a-9a38-6b7cd4ea72f0)
<br>
ㄴ 파일경로  

```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
```


Spring 프로젝트를 실행하게 되면 `@SpringBootApplication` 어노테이션을 통해 Bean을 읽어와 자동으로 생성한다.
해당 어노테이션이 읽는 클래스를 기준으로 설정을 읽어가므로, 반드시 프로젝트의 최상단에 존재해야 한다.

위 코드의 `SpringApplication.run()`으로 클래스를 실행시키면, 내장 WAS를 실행한다. 기존 Spring MVC에서 Tomcat을 설치해서 실행한 것과 달리 별다른 설정 없이 애플리케이션 실행이 가능하다.


## `@SpringBootApplication` 어노테이션

`@SpringBootApplication` 어느테이션 내부를 살펴보면 다음과 같이 구성되어 있다.

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
    excludeFilters = {@Filter(
    type = FilterType.CUSTOM,
    classes = {TypeExcludeFilter.class}
), @Filter(
    type = FilterType.CUSTOM,
    classes = {AutoConfigurationExcludeFilter.class}
)}
)
public @interface SpringBootApplication {
    @AliasFor(
        annotation = EnableAutoConfiguration.class
    )
    Class<?>[] exclude() default {};

    @AliasFor(
        annotation = EnableAutoConfiguration.class
    )
    String[] excludeName() default {};

    @AliasFor(
        annotation = ComponentScan.class,
        attribute = "basePackages"
    )
    String[] scanBasePackages() default {};

    @AliasFor(
        annotation = ComponentScan.class,
        attribute = "basePackageClasses"
    )
    Class<?>[] scanBasePackageClasses() default {};

    @AliasFor(
        annotation = ComponentScan.class,
        attribute = "nameGenerator"
    )
    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;

    @AliasFor(
        annotation = Configuration.class
    )
    boolean proxyBeanMethods() default true;
}
```

해당 코드에서 가장 중요하게 봐야 할 어노테이션은 세 가지이다.
- `@SpringBootConfiguration`
  - 해당 어노테이션은 애플리케이션에 하나만 존재해야 한다. 
  - 해당 어노테이션은 `@Configuration`의 하위 어노테이션이다. `@Configuration`과 완전히 동일한 역할을 수행.
  - 그럼 동일한 건데 왜 선언하느냐?
    - 해당 어노테이션을 기준으로 설정을 불러오기 위함.
    - 대표적으로 SpringBoot의 통합 테스트 어노테이션인 `@SpringBootTest`가 이를 사용합니다. (`@SpringBootconfiguration`어노테이션을 먼저 찾고, 찾은 클래스를 기준으로 하위 설정들을 찾는다.)
- `@EnableAutoConfiguration`
  - 필요할 것 같은 Bean을 자동으로 설정해주는 기능을 가진 어노테이션
  - 필요한 설정을 불러오는 내부 메소드는 디스크로부터 값을 읽어 온다. -> 처리 속도가 상당히 느림.
    - 따라서 스프링은 캐싱 방식을 사용. 
  - 해당 어노테이션은 특정 작업들을 위한 베이스 패키지가 된다.
    - 대표적으로 JPA의 `@Entity`를 탐색하는 작업은 `@EnableAutoConfiguration`이 붙은 패키지를 기준으로 진행!
    - 기본적으로 `@SpringBootApplication`에 포함되기에 신경 쓰지 않아도 되지만, 직접 붙히는 경우에 루트 패키지에 있는 클래스에 붙히는 것이 좋다.
- `@ComponentScan`
  - 빈을 등록하기 위한 어노테이션을 탐색하는 위치를 지정한다. Base패키지를 설정하는 다른 방법도 존재하지만, 만약 설정하지 않으면 해당 어노테이션을 기준으로 진행한다. 
  - 컴포넌트 스캔이 진행되면 `@Configuration`과 `@Bean`, `@Component`의 하위 어노테이션이 있는 클래스와 메소드를 찾는다. 

## `SpringApplication`의 동작 과정

### `SpringApplication` 초기화 
`SpringApplication`의 생성자
```java
public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
        this.sources = new LinkedHashSet();
        this.bannerMode = Mode.CONSOLE;
        this.logStartupInfo = true;
        this.addCommandLineProperties = true;
        this.addConversionService = true;
        this.headless = true;
        this.registerShutdownHook = true;
        this.additionalProfiles = Collections.emptySet();
        this.isCustomEnvironment = false;
        this.lazyInitialization = false;
        this.applicationContextFactory = ApplicationContextFactory.DEFAULT;
        this.applicationStartup = ApplicationStartup.DEFAULT;
        this.resourceLoader = resourceLoader;
        Assert.notNull(primarySources, "PrimarySources must not be null");
        this.primarySources = new LinkedHashSet(Arrays.asList(primarySources));
        this.webApplicationType = WebApplicationType.deduceFromClasspath();
        this.bootstrapRegistryInitializers = new ArrayList(this.getSpringFactoriesInstances(BootstrapRegistryInitializer.class));
        this.setInitializers(this.getSpringFactoriesInstances(ApplicationContextInitializer.class));
        this.setListeners(this.getSpringFactoriesInstances(ApplicationListener.class));
        this.mainApplicationClass = this.deduceMainApplicationClass();
    }
```
<br>

초기화 과정<br>

1. 메인 클래스 (여기서는 `primarySources`)가 null인지 확인
2. 클래스 패스로부터 어플리케이션 타입 추론<br>
   타입 추론 코드
   ```java
   static WebApplicationType deduceFromClasspath() {
        if (ClassUtils.isPresent("org.springframework.web.reactive.DispatcherHandler", (ClassLoader)null) && !ClassUtils.isPresent("org.springframework.web.servlet.DispatcherServlet", (ClassLoader)null) && !ClassUtils.isPresent("org.glassfish.jersey.servlet.ServletContainer", (ClassLoader)null)) {
            return REACTIVE;
        } else {
            String[] var0 = SERVLET_INDICATOR_CLASSES;
            int var1 = var0.length;

            for(int var2 = 0; var2 < var1; ++var2) {
                String className = var0[var2];
                if (!ClassUtils.isPresent(className, (ClassLoader)null)) {
                    return NONE;
                }
            }

            return SERVLET;
        }
    }
    ```
    리액티브, 웹이 아닌 애플리케이션, 서블릿 기반의 애플리케이션 총 세가지로 구분하는 것을 알 수 있음.
3. `BootstrapRegistryInitializer` 로드 및 세팅<br>
   - `BootstrapRegistryInitializer`란?
     - 실제 구동될 어플리케이션 컨텍스트 준비 및 환경 변수를 관리하는 Environt 객체가 후처리되는 동안 이용되는 **임시 컨텍스트 객체**
4. `ApplicationContextInitializer`를 찾아서 세팅<br>
   - ApplicationContext를 위한 Initializer들을 로딩한다.
   - Bootstrap단계에서 이미 파일을 스캔했으므로 캐싱된 값으로부터 찾음.
5. `ApplicationListener`를 찾아서 세팅
   - 옵저버패턴을 기반으로 애플리케이션을 구독하는 리스너
6. 메인 클래스 추론.
    ```java

    private Class<?> deduceMainApplicationClass() {
        return (Class)((Optional)StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE).walk(this::findMainClass)).orElse((Object)null);
    }
    ```
    - Java 9에서 도입된 StackWalker 를 통해 호출 스택을 탐색
    - Main클래스를 찾는다. 없으면 null을 반환

## 참고
[망나니 개발자](https://mangkyu.tistory.com/category/Spring%20)<br>
[Teck Interview](https://gyoogle.dev/blog/web-knowledge/spring-knowledge/%5BSpring%20Boot%5D%20SpringApplication.html)

