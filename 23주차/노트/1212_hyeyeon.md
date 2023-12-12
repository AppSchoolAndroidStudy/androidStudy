## Intro
서버 통신 중 400 에러가 났을 때 백엔드 로그를 안드로이드에서 확인할 수 없다. 
이로 인해 프로젝트를 진행하며 요청 형식이 백엔드 요구사항에 부합하는지 즉시 파악하기 어려운 상황이 계속 발생했다.
매번 백엔드분께 log를 여쭤 보기도 힘들어 방법을 찾아보다가 OKHttp Logging Interceptor를 사용하여 HTTP 통신 로그를 확인하는 방법을 찾았다.
<br>

* * *

## 🧐 400 Bad Request란?
서버가 클라이언트 오류(예: 잘못된 요청 구문, 유효하지 않은 요청 메시지 프레이밍, 또는 변조된 요청 라우팅) 를 감지해 요청을 처리할 수 없거나, 하지 않는다는 것이다.
<br>

* * *

## OkHttp interceptor란?
HTTP 요청 및 응답 데이터를 기록하는 OkHttp 인터셉터이다.
<br>

* * *


## 🕹️ 사용해보기
### build.gradle(Module :app)
먼저 logging-interceptor에 관한 의존성을 설정해준다.

```kotlin
dependencies {
    // OkHttp3
    implementation 'com.squareup.okhttp3:logging-interceptor:4.9.2'
}
```

### WeightClient.kt

의존성을 설정한 후 HTTP 요청을 처리하는 부분에서 OKHttp 클라이언트를 통해 로깅 기능을 추가하면 된다.

HttpLoggingInterceptor를 level = HttpLoggingInterceptor.Level.BODY로 설정해 요청과 응답의 본문 내용까지 로그에 포함되도록 했다.
OKHttp 클라이언트를 구성해 로깅 인터셉터를 추가하면 네트워크 요청 시 로그가 생성된다.
로깅 기능이 포함된 OKHttp 클라이언트를 Retrofit을 구성할 때 연결해주면 된다.

코드는 다음과 같다.

``` kotlin
object WeightClient {
    private const val BASE_URL = "https://dev.meongcare.com/"

    private val logging = HttpLoggingInterceptor().apply {
        // 요청과 응답의 본문 내용까지 로그에 포함
        level = HttpLoggingInterceptor.Level.BODY
    }

    // OKHttp 클라이언트를 구성
    // 이 클라이언트는 로깅 인터셉터를 추가하여 네트워크 요청 시 로그가 생성되도록 함
    // cURL을 확인 하기 위해 사용
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)  // 로깅 기능이 포함된 OKHttp 클라이언트를 Retrofit에 연결
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weightService: WeightService = getRetrofit().create(WeightService::class.java)
}

```
<br>

* * *

## 결과
![](https://velog.velcdn.com/images/hxeyexn/post/8e641bb1-a185-4e6f-bcf6-c70e21c2672e/image.png)

이를 통해 cURL 및 요청과 응답의 세부 사항을 쉽게 확인할 수 있어 400 에러에 대한 조치를 좀 더 빠르게 할 수 있게 되었다.

![](https://velog.velcdn.com/images/hxeyexn/post/5323a3c3-3d8f-4332-aa91-e73697b129f8/image.png)

200 일때 응답도 확인할 수 있다.

대신 500 에러 문제점은 알 수 없어 백엔드분께 log를 요청해야한다..
그래도 400 Bad Request 해결하는 시간이 훨씬 짧아져서 좋다 🙂