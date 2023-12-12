# 안드로이드에서의 Dependency Injection

## Dependency Injection(의존성 주입) 개념

의존성 주입은 객체가 필요로 하는 의존성(다른 객체나 설정)을 외부에서 제공하는 기법으로 객체가 직접 의존성을 생성하는 대신, 외부 소스(ex: 프레임워크)로부터 필요한 의존성을 받는다.

## 의존성 주입의 중요성

- **코드의 재사용성과 유지보수성 향상**: 의존성 주입을 사용하면 모듈 간의 결합도를 낮출 수 있어 코드의 재사용성과 유지보수가 용이해진다.
- **테스트 용이성**: 테스트할 때 의존성을 모의 객체(mock objects)로 대체하기 쉬워진다.

## 의존성 주입의 구현 방법

1. **생성자 주입(Constructor Injection)**: 객체 생성 시 필요한 의존성을 생성자를 통해 주입
2. **필드 주입(Field Injection)**: 클래스의 필드에 직접 의존성을 주입
3. **메소드 주입(Method Injection)**: 설정자 메소드(setter method)를 통해 의존성을 주입

## 의존성 주입을 위한 도구

- **Dagger**
- **Hilt**
- **Koin**

위 세 개의 프레임워크와 라이브러리들은 의존성 관리를 더욱 용이하게 해주며, 효율적인 개발을 도와준다.

## Dagger를 사용한 의존성 주입 예제

### 1. 의존성 정의

```kotlin
@Module
class NetworkModule {

    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.example.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
```

### 2. 컴포넌트 생성
Dagger 컴포넌트를 생성하여, 의존성 주입을 할 클래스에 필요한 의존성을 제공
```kotlin
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}
```

### 3. 의존성 주입
- AppComponent 인터페이스를 정의하여 Dagger 컴포넌트를 생성한다. 
- 이 컴포넌트는 MainActivity에 필요한 의존성을 주입하는 역할을 한다. 
-  MainActivity 클래스 내에서는 @Inject 어노테이션을 사용하여 Retrofit 객체를 주입받고, DaggerAppComponent를 통해 실제 의존성 주입 과정을 수행한다
```kotlin
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Dagger 컴포넌트를 사용하여 의존성 주입
        DaggerAppComponent.create().inject(this)

        // retrofit 객체를 사용 가능해짐
    }
}
```

NetworkModule은 필요한 네트워크 관련 의존성을 제공하고, AppComponent는 이러한 의존성을 MainActivity에 주입