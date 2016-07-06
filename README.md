# SaveVolley

**Save volley from anything, By Agera to save. Thus, derived the AgeraVolley** . **(｡>﹏<｡)**   


![Language](https://img.shields.io/badge/language-Java-EE0000.svg) [![License](https://img.shields.io/badge/license-Apache%202.0-orange.svg)](https://github.com/CaMnter/SaveVolley/blob/master/LICENSE)
![Version](https://img.shields.io/badge/version-1.6.6-8470FF.svg)
[ ![Download](https://api.bintray.com/packages/camnter/maven/SaveVolley/images/download.svg) ](https://bintray.com/camnter/maven/SaveVolley/_latestVersion) [![Build Status](https://travis-ci.org/CaMnter/SaveVolley.svg?branch=master)](https://travis-ci.org/CaMnter/SaveVolley)   


**gson:** 2.7   

**fastjson:** 1.1.52.android   

**agera:** 1.1.0   

**okhttp3:** 3.3.1   

<br/>

# savevolley-okhttp3-agera-gson || savevolley-okhttp3-agera-fastjson

## savevolley-okhttp3-agera-gson gradle

```gradle
dependencies {
    compile 'com.google.code.gson:gson:2.7'
    compile 'com.squareup.okhttp3:okhttp:3.3.1'
    compile 'com.google.android.agera:agera:1.1.0'

    // for okhttp3
    compile 'com.camnter.savevolley:okhttp3:1.6.6'
    // for gson
    compile 'com.camnter.savevolley:savevolley-okhttp3-agera-gson:1.6.6'
}
```
    
可以使用 `SaveVolley` **Flow** 和 `agera` **Flow** 。          
    
```java
    SaveVolley saveVolley = SaveVolleys
        .<GankData>request(TEST_URL)
        .method(Method.GET)
        .parseStyle(GSON)
        .classOf(GankData.class)
        .createRequest()
        .context(this)
        .compile();

    final Repository<GankResultData> repository = Repositories.repositoryWithInitialValue(
        INITIAL_VALUE)
        .observe(saveVolley.getReservoir())
        .onUpdatesPerLoop()
        .goTo(executor)
        .attemptGetFrom(saveVolley.getReservoir())
        .orSkip()
        .thenAttemptTransform(new Function<Object, Result<GankResultData>>() {
            /**
             * Returns the result of applying this function to {@code input}.
             */
            @NonNull @Override public Result<GankResultData> apply(@NonNull Object input) {
                if (input instanceof GankData) {
                    return Result.success(((GankData) input).results.get(0));
                } else if (input instanceof VolleyError) {
                    return Result.failure((VolleyError) input);
                }
                return Result.failure();
            }
        })
        .orSkip()
        .compile();

    repository.addUpdatable(new Updatable() {
        @Override public void update() {
            getContentText.setText(repository.get().toString());
        }
    });
```
   
## savevolley-okhttp3-agera-fastjson gradle
   
```gradle
dependencies {
    compile 'com.alibaba:fastjson:1.1.52.android'
    compile 'com.squareup.okhttp3:okhttp:3.3.1'
    compile 'com.google.android.agera:agera:1.1.0'

    // for okhttp3
    compile 'com.camnter.savevolley:okhttp3:1.6.6'
    // for fastjson
    compile 'com.camnter.savevolley:savevolley-okhttp3-agera-fastjson:1.6.6'
}
```
   
```java
    SaveVolley saveVolley = SaveVolleys
        .<GankData>request(TEST_URL)
        .method(Method.GET)
        .parseStyle(FASTJSON)
        .classOf(GankData.class)
        .createRequest()
        .context(this)
        .compile();

    final Repository<GankResultData> repository = Repositories
        .repositoryWithInitialValue(INITIAL_VALUE)
        .observe(saveVolley.getReservoir())
        .onUpdatesPerLoop()
        .goTo(executor)
        .attemptGetFrom(saveVolley.getReservoir())
        .orSkip()
        .thenAttemptTransform(new Function<Object, Result<GankResultData>>() {
            /**
             * Returns the result of applying this function to {@code input}.
             */
            @NonNull @Override public Result<GankResultData> apply(@NonNull Object input) {
                if (input instanceof GankData) {
                    return Result.success(((GankData) input).results.get(0));
                } else if (input instanceof VolleyError) {
                    return Result.failure((VolleyError) input);
                }
                return Result.failure();
            }
        })
        .orSkip()
        .compile();

    repository.addUpdatable(new Updatable() {
        @Override public void update() {
            getContentText.setText(repository.get().toString());
        }
    });
```
   
<br/>
    
# savevolley-hurl-agera-gson || savevolley-hurl-agera-fastjson
   
可以使用 `SaveVolley` **Flow** 和 `agera` **Flow** 。   
   
## savevolley-hurl-agera-gson gradle
   
```gradle
dependencies {
    compile 'com.google.code.gson:gson:2.7'
    compile 'com.google.android.agera:agera:1.1.0'

    // for hurl
    compile 'com.camnter.savevolley:hurl:1.6.6'
    // for gson
    compile 'com.camnter.savevolley:savevolley-hurl-agera-gson:1.6.6'
}
```


```java
    SaveVolley saveVolley = SaveVolleys
        .<GankData>request(TEST_URL)
        .method(Method.GET)
        .parseStyle(GSON)
        .classOf(GankData.class)
        .createRequest()
        .context(this)
        .compile();

    final Repository<GankResultData> repository = Repositories.repositoryWithInitialValue(
        INITIAL_VALUE)
        .observe(saveVolley.getReservoir())
        .onUpdatesPerLoop()
        .goTo(executor)
        .attemptGetFrom(saveVolley.getReservoir())
        .orSkip()
        .thenAttemptTransform(new Function<Object, Result<GankResultData>>() {
            /**
             * Returns the result of applying this function to {@code input}.
             */
            @NonNull @Override public Result<GankResultData> apply(@NonNull Object input) {
                if (input instanceof GankData) {
                    return Result.success(((GankData) input).results.get(0));
                } else if (input instanceof VolleyError) {
                    return Result.failure((VolleyError) input);
                }
                return Result.failure();
            }
        })
        .orSkip()
        .compile();

    repository.addUpdatable(new Updatable() {
        @Override public void update() {
            getContentText.setText(repository.get().toString());
        }
    });
```

## savevolley-hurl-agera-fastjson gradle

```gradle
dependencies {
    compile 'com.alibaba:fastjson:1.1.52.android'
    compile 'com.google.android.agera:agera:1.1.0'

    // for hurl
    compile 'com.camnter.savevolley:hurl:1.6.6'
    // for fastjson
    compile 'com.camnter.savevolley:savevolley-hurl-agera-fastjson:1.6.6'
}
```
   
```java
    SaveVolley saveVolley = SaveVolleys
        .<GankData>request(TEST_URL)
        .method(Method.GET)
        .parseStyle(FASTJSON)
        .classOf(GankData.class)
        .createRequest()
        .context(this)
        .compile();

    final Repository<GankResultData> repository = Repositories
        .repositoryWithInitialValue(INITIAL_VALUE)
        .observe(saveVolley.getReservoir())
        .onUpdatesPerLoop()
        .goTo(executor)
        .attemptGetFrom(saveVolley.getReservoir())
        .orSkip()
        .thenAttemptTransform(new Function<Object, Result<GankResultData>>() {
            /**
             * Returns the result of applying this function to {@code input}.
             */
            @NonNull @Override public Result<GankResultData> apply(@NonNull Object input) {
                if (input instanceof GankData) {
                    return Result.success(((GankData) input).results.get(0));
                } else if (input instanceof VolleyError) {
                    return Result.failure((VolleyError) input);
                }
                return Result.failure();
            }
        })
        .orSkip()
        .compile();

    repository.addUpdatable(new Updatable() {
        @Override public void update() {
            getContentText.setText(repository.get().toString());
        }
    });
```
   
   
<br/>
   
# savevolley-okhttp3-gson || savevolley-okhttp3-fastjson

## savevolley-okhttp3-gson  gradle

```gradle
dependencies {
    compile 'com.google.code.gson:gson:2.7'
    compile 'com.squareup.okhttp3:okhttp:3.3.1'

    // for okhttp3
    compile 'com.camnter.savevolley:okhttp3:1.6.6'
    // for gson
    compile 'com.camnter.savevolley:savevolley-okhttp3-gson:1.6.6'
}
```

可以使用 `OkHttp3GsonRequest` 。         

```java
    RequestQueue queue = Volley.newRequestQueue(this);
    queue.add(new OkHttp3GsonRequest<GankData>(TEST_URL,
        GankData.class) {
        /**
         * Called when a response is received.
         */
        @Override public void onResponse(GankData response) {
            getContentText.setText(response.toString());
        }


        /**
         * Callback method that an error has been occurred with the
         * provided error code and optional user-readable message.
         */
        @Override public void onErrorResponse(VolleyError error) {
            Toast.makeText(Okhttp3GsonActivity.this,
                error != null && error.getMessage() != null
                ? error.getMessage()
                : "No error message", Toast.LENGTH_LONG)
                .show();
            Log.d("GsonRequest", error != null && error.getMessage() != null
                                 ? error.getMessage()
                                 : "No error message");
        }
    });
```

## savevolley-okhttp3-fastjson  gradle

```gradle
dependencies {
    compile 'com.alibaba:fastjson:1.1.52.android'

    // for okhttp3
    compile 'com.camnter.savevolley:okhttp3:1.6.6'
    // for fastjson
    compile 'com.camnter.savevolley:savevolley-okhttp3-fastjson:1.6.6'
}
```
   
可以使用 `Okhttp3FastjsonRequest` 。

```java
    RequestQueue queue = Volley.newRequestQueue(this);
    queue.add(new OkHttp3FastjsonRequest<GankData>(TEST_URL, GankData.class) {
        /**
         * Called when a response is received.
         */
        @Override public void onResponse(GankData response) {
            getContentText.setText(response.toString());
        }


        /**
         * Callback method that an error has been occurred with the
         * provided error code and optional user-readable message.
         */
        @Override public void onErrorResponse(VolleyError error) {
            Toast.makeText(Okhttp3FastjsonActivity.this,
                error != null && error.getMessage() != null
                ? error.getMessage()
                : "No error message", Toast.LENGTH_LONG)
                .show();
            Log.d("GsonRequest", error != null && error.getMessage() != null
                                 ? error.getMessage()
                                 : "No error message");
        }
    });
```

<br/>

# savevolley-hurl-gson || savevolley-hurl-fastjson

## savevolley-hurl-gson  gradle

```gradle
dependencies {
    compile 'com.google.code.gson:gson:2.7'

    // for hurl
    compile 'com.camnter.savevolley:hurl:1.6.6'
    // for gson
    compile 'com.camnter.savevolley:savevolley-hurl-gson:1.6.6'
}
```

可以使用 `HurlGsonRequest` 。       


```java
    RequestQueue queue = Volley.newRequestQueue(this);
    queue.add(new HurlGsonRequest<GankData>(TEST_URL,
        GankData.class) {
        /**
         * Called when a response is received.
         */
        @Override public void onResponse(GankData response) {
            getContentText.setText(response.toString());
        }


        /**
         * Callback method that an error has been occurred with the
         * provided error code and optional user-readable message.
         */
        @Override public void onErrorResponse(VolleyError error) {
            Toast.makeText(HurlGsonActivity.this,
                error != null && error.getMessage() != null
                ? error.getMessage()
                : "No error message", Toast.LENGTH_LONG)
                .show();
            Log.d("GsonRequest", error != null && error.getMessage() != null
                                 ? error.getMessage()
                                 : "No error message");
        }
    });
```

## savevolley-hurl-fastjson  gradle
   
```gradle
dependencies {
    compile 'com.alibaba:fastjson:1.1.52.android'

    // for hurl
    compile 'com.camnter.savevolley:hurl:1.6.6'
    // for fastjson
    compile 'com.camnter.savevolley:savevolley-hurl-fastjson:1.6.6'
}
```
   
可以使用 `HurlFastjsonRequest` 。
   
```java
    RequestQueue queue = Volley.newRequestQueue(this);
    queue.add(new HurlFastjsonRequest<GankData>(TEST_URL, GankData.class) {
        /**
         * Called when a response is received.
         */
        @Override public void onResponse(GankData response) {
            getContentText.setText(response.toString());
        }


        /**
         * Callback method that an error has been occurred with the
         * provided error code and optional user-readable message.
         */
        @Override public void onErrorResponse(VolleyError error) {
            Toast.makeText(HurlFastjsonActivity.this,
                error != null && error.getMessage() != null
                ? error.getMessage()
                : "No error message", Toast.LENGTH_LONG)
                .show();
            Log.d("GsonRequest", error != null && error.getMessage() != null
                                 ? error.getMessage()
                                 : "No error message");
        }
    });
```
   
<br/>
   
# savevolley-okhttp3

## gradle

```gradle
dependencies {
    // for okhttp3
    compile 'com.camnter.savevolley:okhttp3:1.6.6'
}
```

[savevolley-okhttp3](https://github.com/CaMnter/SaveVolley/tree/master/savevolley-okhttp3/src/main/java/com/camnter/savevolley/okhttp3/volley)         

将 原版的 **google/volley** 中 网络实现层 的 **Apache HttpClient** 和 **原生的 HttpUrlConnection** 都移除。
换成 **square/okhttp3** 作为实现网络请求。

<br/>

# savevolley-hurl

## gradle

```gradle
dependencies {
    // for hurl
    compile 'com.camnter.savevolley:hurl:1.6.6'
}
```


**API >= 9**     

[savevolley-hurl](https://github.com/CaMnter/SaveVolley/tree/master/savevolley-hurl/src/main/java/com/camnter/savevolley/hurl)         

移除了 **原版** **google/volley** 中的，所有相关与 HttpClient 的逻辑，全版本的网络通信接入到 **HttpUrlConnection** 内。   

<br/>

# savevolley-network-core

[savevolley-network-core](https://github.com/CaMnter/SaveVolley/tree/master/savevolley-network-core/src/main/java/com/camnter/savevolley/network/core)   

由于，网络底层实现（ 网络请求实现 ）可以通过不同框架（ okhttp、HttpUrlConnection、Apache HttpClient ... ）去实现。     
所以，只是拿到 **不同框架的响应结果**。     
但是 Volley 的缓存层是 **通用的**，所以需要定义一套 通用的 **HTTP Response API**，然后将 **不同框架的响应结果** 转换为     
通用的 **HTTP Response API**。  

这个模块还需要与 [savevolley-network-adapter](https://github.com/CaMnter/SaveVolley/tree/master/savevolley-network-adapter/src/main/java/com/camnter/savevolley/network/adapter/core) 的子模块（ [savevolley-network-okhttp3-adapter](https://github.com/CaMnter/SaveVolley/tree/master/savevolley-network-okhttp3-adapter/src/main/java/com/camnter/savevolley/network/okhttp3/adapter), [savevolley-network-hurl-adapter](https://github.com/CaMnter/SaveVolley/tree/master/savevolley-network-hurl-adapter/src/main/java/com/camnter/savevolley/network/hurl/adapter) ）一起协作。

<br/>

# savevolley-network-adapter

[savevolley-network-adapter](https://github.com/CaMnter/SaveVolley/tree/master/savevolley-network-adapter/src/main/java/com/camnter/savevolley/network/adapter/core)      

目的是为了定义一些接口： **不同框架的响应结果** >> **通用的 HTTP Response API**。   
子模块有： [savevolley-network-okhttp3-adapter](https://github.com/CaMnter/SaveVolley/tree/master/savevolley-network-okhttp3-adapter/src/main/java/com/camnter/savevolley/network/okhttp3/adapter), [savevolley-network-hurl-adapter](https://github.com/CaMnter/SaveVolley/tree/master/savevolley-network-hurl-adapter/src/main/java/com/camnter/savevolley/network/hurl/adapter) 。   

<br/>

# savevolley-network-okhttp3-adapter

[savevolley-network-okhttp3-adapter](https://github.com/CaMnter/SaveVolley/tree/master/savevolley-network-okhttp3-adapter/src/main/java/com/camnter/savevolley/network/okhttp3/adapter)   

**savevolley-network-adapter** 的子模块，**square/okhttp Response** >> **通用的 HTTP Response API**。

<br/>

# savevolley-network-hurl-adapter

[savevolley-network-hurl-adapter](https://github.com/CaMnter/SaveVolley/tree/master/savevolley-network-hurl-adapter/src/main/java/com/camnter/savevolley/network/hurl/adapter)   

**savevolley-network-adapter** 的子模块，**HttpConnection** >> **通用的 HTTP Response API**。
   
<br/>
   
# extensions / savevolley-hurl-agera-core
   
[savevolley-hurl-agera-core](https://github.com/CaMnter/SaveVolley/tree/master/extensions/savevolley-hurl-agera-core/src/main/java/com/camnter/savevolley/hurl/agera/core)   
   
**作用**: 提供一些 **hurl** 与 **agera** 协作需要的基础类。   
   
<br/>
   
# extensions / savevolley-okhttp3-agera-core
   
[savevolley-okhttp3-agera-core](https://github.com/CaMnter/SaveVolley/tree/master/extensions/savevolley-okhttp3-agera-core/src/main/java/com/camnter/savevolley/okhttp3/agera/core)   
   
**作用**: 提供一些 **okhttp3** 与 **agera** 协作需要的基础类。   
   
<br/>
   
# extensions / agera-gson / savevolley-okhttp3-agera-gson

[savevolley-okhttp3-agera-gson](https://github.com/CaMnter/SaveVolley/tree/master/extensions/agera-gson/savevolley-okhttp3-agera-gson/src/main/java/com/camnter/savevolley/okhttp3/agera/gson)      

**作用**: **agera** **>>**  **savevolley-okhttp3** **<<** **gson** , 为 **savevolley-okhttp3** 桥接了 **agera** 和 **gson** 。  

<br/>

# extensions / agera-gson / savevolley-hurl-agera-gson

[savevolley-hurl-agera-gson](https://github.com/CaMnter/SaveVolley/tree/master/extensions/agera-gson/savevolley-hurl-agera-gson/src/main/java/com/camnter/savevolley/hurl/agera/gson)      

**作用**: **agera** **>>**  **savevolley-hurl** **<<** **gson** , 为 **savevolley-hurl** 桥接了 **agera** 和 **gson** 。   

<br/>
   
# extensions / agera-fastjson / savevolley-okhttp3-agera-fastjson
   
[savevolley-okhttp3-agera-fastjson](https://github.com/CaMnter/SaveVolley/tree/master/extensions/agera-fastjson/savevolley-okhttp3-agera-fastjson/src/main/java/com/camnter/savevolley/okhttp3/agera/fastjson)   
    
**作用**: **agera** **>>**  **savevolley-okhttp3** **<<** **fastjson** , 为 **savevolley-okhttp3** 桥接了 **agera** 和 **fastjson** 。  
   
<br/>
   
# extensions / agera-fastjson / savevolley-hurl-agera-fastjson
   
[savevolley-hurl-agera-fastjson](https://github.com/CaMnter/SaveVolley/tree/master/extensions/agera-fastjson/savevolley-hurl-agera-fastjson/src/main/java/com/camnter/savevolley/hurl/agera/fastjson)   
   
**作用**: **agera** **>>**  **savevolley-hurl** **<<** **fastjson** , 为 **savevolley-hurl** 桥接了 **agera** 和 **fastjson** 。
   
<br/>
   
# extensions / fastjson / savevolley-hurl-fastjson
   
[savevolley-hurl-fastjson](https://github.com/CaMnter/SaveVolley/tree/master/extensions/fastjson/savevolley-hurl-fastjson/src/main/java/com/camnter/savevolley/hurl/fastjson/request)   
   
**作用**: **fastjson** **>>**  **savevolley-hurl**  。   
   
<br/>
   
# extensions / fastjson / savevolley-okhttp3-fastjson
   
[savevolley-okhttp3-fastjson](https://github.com/CaMnter/SaveVolley/tree/master/extensions/fastjson/savevolley-okhttp3-fastjson/src/main/java/com/camnter/savevolley/okhttp3/fastjson/request)   
   
**作用**: **fastjson** **>>**  **savevolley-okhttp3**  。      
   
<br/>
   
# extensions / gson / savevolley-hurl-gson
   
[savevolley-hurl-gson](https://github.com/CaMnter/SaveVolley/tree/master/extensions/gson/savevolley-hurl-gson/src/main/java/com/camnter/savevolley/hurl/gson/request)      
   
**作用**: **gson** **>>**  **savevolley-hurl** 。      
   
<br/>
   
# extensions / gson / savevolley-okhttp3-gson

[savevolley-okhttp3-gson](https://github.com/CaMnter/SaveVolley/tree/master/extensions/gson/savevolley-okhttp3-gson/src/main/java/com/camnter/savevolley/okhttp3/gson/request)      

**作用**: **gson** **>>**  **savevolley-okhttp3**  。      
   
   
<br/>

# square-okhttp3

[square/okhttp](https://github.com/square/okhttp)  Version: **3.3.1**   

<br/>

# google-agera

[google/agera](https://github.com/google/agera)  Version: **1.1.0-beta2**   


<br/>
<br/>


# volley-comments

[volley-comments](https://github.com/CaMnter/SaveVolley/tree/master/volley-comments/src/main/java/com/android/volley)      

原版的 **google/volley**，全部代码加上了注释。

## 入口

**Volley**：Volley 框架使用的入口，用于创建一个 RequestQueue。

- **1.** `RequestQueue queue = Volley.newRequestQueue(this)`

- **2.** `queue.add(request)`

**RequestQueue**：`RequestQueue` 被定义为 **请求队列**。用于操作 缓存请求执行线程（ `CacheDispatcher` ）和 网络请求执行线程（ `NetworkDispatcher` ）。默认的情况下：

- **1.** 启动 **1 个** 缓存请求执行线程（ `CacheDispatcher` ）。

- **2.** 根据 `DEFAULT_NETWORK_THREAD_POOL_SIZE` 的值，启动 **4 个** 网络请求执行线程（ `NetworkDispatcher` ）。

- **3.**  这些线程共享 缓存请求队列 （ `mCacheQueue` ）和 网络请求队列（ `mNetworkQueue` ），当 `RequestQueue` **进行 请求的 添加、结束时，都会进入同步操作，保证线程安全**。所以，`RequestQueue` **进行 请求的 添加、结束。是直接影响 CacheDispatcher 和 NetworkDispatcher 的执行**。


## 核心 异步

**NetworkDispatcher**：是用于处理 Volley 中的网络请求 的 **网络线程**，会将 网络 `Request` 队列的 `Request` 逐个抽出，然后进行网络请求：

- **1.** **成功**，拿到数据进行解析，然后将 `Response` 进行硬盘缓存，缓存成 `Cache.Entry` 的形式，最后 传递 `Request` 和 `Response` 数据。

- **2.** **失败**，失败的话，一般会抛出异常，然后进行 **记录请求时长** 和 **传递错误（ VolleyError ）**。

**CacheDispatcher**：是用于处理 Volley 中的缓存数据 的 **缓存线程**。

- **1.1.** **检查缓存**。从 缓存 `Request` 队列中取出 **缓存 Request**，然后 根据 **缓存 Request CacheKey** 去硬盘缓存（ `DiskBasedCache` ）映射过来的内存缓存中寻找 是否存在 `Entry` （ `Response` ）。

- **1.2.** **存在的话**，通过 `DefaultRetryPolicy` 回传相关数据。

- **1.3.** **存在但缓存需要刷新的话**，放入 **网络 Request 队列** 内，会在 `NetworkDispatcher` 的循环体中被用来 **重新请求**。

- **1.4.** **不存在的话**，将该 Request，放入 **网络 Request 队列** 内，会在 `NetworkDispatcher` 的循环体中被用来 **重新请求**。

- **2.** `CacheDispatcher` **只做缓存相关的操作，不做网络的操作**。所以 `CacheDispatcher`，**只涉及到 缓存 Response 的增删改查 以及 查询成功后的数据分发（ 传递 ）**。

## 网络请求 使用层

**Network**：在 Volley 中，是处理 网络请求 的表层使用接口。只有一个方法：`performRequest(...)` 执行请求，

**BasicNetwork**：`BasicNetwork` 是 目前 Volley 内，`Network` 接口的唯一实现类，在 Volley 内，处理了 网络请求 的表层使用接口。实现了 `performRequest(...)` 方法的具体功能，`performRequest(...)` **要做的事情是**：

- **1.** 调用  `HttpStack` 接口的实现类 （ `HurlStack`, `HttpClientStack` ） 去执行网络请求实现，会拿到一个 `Apache HttpResponse`。

- **2.** 然后，将 **Apache HttpResponse** -> **Volley NetworkResponse** 进行转化，并返回。


## 网络请求 实现层

**HttpStack**：`HttpStack` 是 Volley 内 执行 网络请求 的 **底层实现接口**。实现类有：

- **1.** `HttpClientStack`：基于 `org.apache.http` 的网络请求实现。负责 **系统版本 2.3 以下** 的 网络请求实现。

- **2.** `HurlStack`：基于 `HttpURLConnection` 的网络请求实现。负责  **系统版本 2.3 以上** 的 网络请求实现。

**HttpClientStack**：`HttpClientStack` 实现了 `HttpStack` 接口。封装了基于 `org.apache.http.HttpClient` 提供的网络实现（ `performRequest(...)` ），处理了 **2.3 版本以下的 各种网络请求（ GET、POST、DEL ... ）的实现**。

**HurlStack**：`HurlStack` 实现了 `HttpStack` 接口。封装了基于 `javax.net.ssl.HttpsURLConnection` 提供的网络实现（ `performRequest(...)` ），处理了 **2.3 版本以上的 各种网络请求（ GET、POST、DEL ... ）的实现**。


## 请求（ Request ）    

**Request**：Volley 内所有抽象请求的 **基类**。   

**StringRequest**：继承扩展了 `Request`，指定了泛型为 `<String>`。会将请求结果解析成 `String` 类型数据，并且 需要你 传入一个 `Response.Listener<String>` 进行解析结果数据进行回调。

**JsonRequest**：`JsonRequest<T>` 抽象继承了 `Request<T>` 类。

- **1.** 进行了` Request<T>` -> `JsonRequest<T>` 的转换。

- **2.** 将请求结果数据 根据 **"charset=utf-8"** 转换为 **byte[]**。

- **3.** 通过覆写 `getBodyContentType()` 方法，将 **Content-Type** 设置为 **application/json; charset=utf-8**。

**JsonObjectRequest**：`JsonObjectRequest` 继承自 `JsonRequest<JSONObject>`，将 `JsonRequest<T>` 的泛型 **T**，设置为 `JSONObject`。`JsonRequest<T>` 没有解析 **body** 内的数据为 真正的 **Json** 数据。所以，`JsonObjectRequest` 要单独将 **body** 内的数据解析为 `JSONObject `类型。

**JsonArrayRequest**：`JsonArrayRequest` 继承自 `JsonRequest<JSONArray>`，将 `JsonRequest<T>` 的泛型 T，设置为 `JSONArray`。`JsonRequest<T>` 没有解析 body 内的数据为 真正的 Json 数据。所以，`JsonArrayRequest` 要单独将 body 内的数据解析为 `JSONArray` 类型。

**ImageRequest**：继承扩展了 `Request`，指定了泛型为 `<Bitmap>`，会将请求结果解析成 **Bitmap** 类型数据。并且需要你传入：

- **1.** `Response.Listener<Bitmap>`：将解析结果数据 进行回调。

- **2.** `maxWidth`：最大宽度。

- **3.** `maxHeight`：最大高度。

- **4.** `ScaleType`：`ImageView` 的 `ScaleType`（ CENTER_CROP, FIT_XY ... ）。

- **5.**  `Config`：**Bitmap** 的 `Config` （ ALPHA_8, RGB_565, ARGB_4444, ARGB_8888 ）。

- **6.** `Response.ErrorListener`：请求结果 **Response** 发生错误的回调接口。

**ClearCacheRequest**：`ClearCacheRequest` 继承自 `Request<Object>`，用于清空 **HTTP** 缓存的请求。如果该请求被添加到 **请求队列（ RequestQueue ）**中，由于覆写了 `getPriority()` 方法，将优先级设置为 **Priority.IMMEDIATE （ 立即执行 ）**。       


## 响应（ Response ）

**Response**：请求结果（响应）类。

- **1.** 存放 **请求结果数据** 和 **缓存 key**；或者 **错误信息（ VolleyError ）**。

- **2.** 提供一个 `Listener` 接口。当接受到 **请求结果（响应）**时，将 **解析结果** 返回。

- **3.** 再提供一个 `ErrorListener` 接口。发生错误时，将调用该方法，将返回 `VolleyError`。

- **4.** 提供一个静态方法 `success(...)`，实例一个有 **请求结果数据** 的 **Response 对象**。

- **5.** 再提供一个静态方法 `error(...)`，实例一个只有 **错误信息（ VolleyError ）** 的 `Response` 对象


## 缓存

**Cache**：Volley 缓存的接口。提供一些接口方法，需要子类去实现，全 Volley 中主要实现类，只有一个 `DiskBasedCache`。

**DiskBasedCache**：`DiskBasedCache` 是 `Cache` 的实现类。用于将保存缓存文件在硬盘上的指定目录中，默认的缓存大小是 **5MB**，缓存大小是可以手动配置的。

- **1.** 实例化了一个 `LinkedHashMap<String, CacheHeader>`，`accessOrder` 设置为 **true**。即，按照访问顺序对数据进行排序，就是 **LRU** 的简单时间。容量 `initialCapacity` 为默认值 **16**，虽然手动写了 **16**。负载因子 `loadFactor` 默认值 **0.75f**，虽然手动写了 **0.75**。负载因子是 当容量超过这个百分比就会扩容，**0.75f** 的话，就是容量超过**75%**就会扩容。    

- **2.** 在 `initialize()` 的时候：**<1>.**：判断缓存目录是否存在，不存在则创建一系列文件夹，然后返回。**<2>.**：存在缓存文件，开始读取缓存文件内容。每一个缓存文件内容对应一个 `CacheHeader`。会给上述的 `LinkedHashMap<String, CacheHeader>`， `CacheHeader` 缓存的内容进行添加，把文件缓存的内容映射过来。

**NoCache**：继承了 `Cache`，不做任何操作的缓存实现类。可以作为 `RequestQueue` 构造方法的参数，实现一个不带缓存的请求队列。   


## 数据传递（分发）

**ResponseDelivery**：**ResponseDelivery 接口的作用** -> 从 内存缓存 或者 服务器 取得请求结果数据，由 `ResponseDelivery` 去做结果分发以及回调处理。       

**ExecutorDelivery**：`ExecutorDelivery` 实现了 `ResponseDelivery` 接口。
**主要功能就是：**

- **1.** 传递 请求、请求结果 or 相应错误 以及 可能有自定义的 `Runnable` 执行回调操作。

- **2.** 定义了一个 `Executor`，因为可能会有自定义的 `Runnable` 执行回调操作，所以需要它的存在。

- **3.** 因为有 `Executor` 和 `Runnable` 的存在，但是都在子线程内。所以，还需要传入一个 **UI** 线程的 `Handler`（大多情况回调都是跟 **UI** 线程通信），可以在 `RequestQueue` 类中 搜搜 **new Handler(Looper.getMainLooper())** 关键句。

**构造方法也是有趣设计：**

- **1.** `Handler` 作为参数的构造方法。全 Volley 只有 `RequestQueue` 内使用，并且传入一个 **UI** 线程的 `Handler`。可以简单得出：这个构造方法可以指定（ 不只是 **UI** 线程 ）线程的 `Handler` 去处理这个 `Runnable`。看过 `Handler` 源码的都知道：你给我一个 `Runnable`，我会 拿到这个 `Handler` 实例化时的 `Looper`。再拿到 `MessageQueue` 去添加一条 `Message` ，`Runnable` 则作为 这个 `Message.callback` 保存在这，然后再 **Handler.dispatchMessage(...)** 的时候，会执行 **Message.callback.run()**。所以，这里（不只是 **UI** 线程）线程的 `Handler`，说明了可以给其他线程传递（ 通信 ）。

- **2.** `Executor` 作为参数的构造方法。这里由于 `Executor` 作为外部提供的参数，那么 `Handler` 也在外面提供了，更具有可定制性。`Handler`作为参数的构造方法 就是 这个 `Executor` 作为参数的构造方法 的升级版。因为，`ExecutorDelivery` 需要 `Executor` 和 `Handler`，`Handler` 作为参数的构造方法：实例化了 `Executor`，`Handler` 由外部提供，这里 `Executor` 是默认执行 **handler.post(Runnable command)**。`Executor` 作为参数的构造方法：都需要外部提供。


## 工具

**HttpHeaderParser**：**Http header** 的解析工具类。   

- **1.** 解析 `Header`，判断返回结果是否需要缓存。需要缓存的话，从网络请求回来的请求结果 `NetworkResponse` 的 `Header` 中提取出一个用于缓存的 `Cache.Entry`。

- **2.** 解析编码集，在 **Content-Type** 中获取**编码集（ charset ）**，可以根据 编码 解析 **Response.data** 的 **byte[]** 数据。

**RequestFuture**：`RequestFuture<T>` 实现了 `Future<T>` 接口，用于对 `Request` 解析数据的结果 进行 **取消**、**查询是否完成**、**获取结果**。


## byte[] 流和缓存池

**ByteArrayPool**：一个 **byte[]** 缓存池，用于 **byte[]** 的回收再利用，减少内存分配和回收。

- **1.** 内置一个 `LinkedList`， 采用 LRU 的机制，最少使用的放在 `index=0`，最近使用的 放在 `index=size()-1`。

- **2.** 再内置一个 `LinkedList`，缓存 **byte[]** 缓存 List，采用根据 **byte[].length** 由小到大的方式排序。排序使用 `Collections.binarySearch(...)`，对应 `Comparator<byte[]> BUF_COMPARATOR`。

**PoolingByteArrayOutputStream**：`PoolingByteArrayOutputStream` 继承了 原生的 `ByteArrayOutputStream`，使用了 `ByteArrayPool` 回收利用一些 **byte[]**，防止了 **byte[]** 的重复内存分配和回收。


## 重试策略

**RetryPolicy**：重试策略接口。具体实现可以自定义，也有默认的 `DefaultRetryPolicy`。

**DefaultRetryPolicy**：`DefaultRetryPolicy` 继承自 `RetryPolicy`。**注意的地方就是** ->  `mBackoffMultiplier` 和 `DEFAULT_BACKOFF_MULT` 用于设置 **退避乘数**，跟 **"指数退避"** 有关。  


## 日志     

**VolleyLog**：`VolleyLog` 是 Volley 的一个工具类。根据 `Log.isLoggable(String tag,int level)` 设置 Log 的开关。
**level >= INFO** 时 `isLoggable` 返回 true，反之则返回 false，所以，开关 默认 关闭。

- `public static final int VERBOSE = 2`

- `public static final int DEBUG = 3`

- `public static final int INFO = 4`

- `public static final int WARN = 5`

- `public static final int ERROR = 6`

- `public static final int ASSERT = 7`

**MarkerLog**：VolleyLog 提供的一个简单的静态内部 Log 类。可以通过 `add(...)` 添加一个 `MarkerLog` 的 Log 单位（ `Marker` ），然后调用 `finish(...)` 打印已经添加的所有 Log。


## 认证          

**Authenticator**：一个身份认证接口，用于基本认证或者摘要认证。在 Volley 内，是用于和身份认证，比如 OAuth。

**AndroidAuthenticator**：实现了 `Authenticator` 接口，基于 Android 上的 `AccountManager`，实现了认证交互。


## 错误类型

**VolleyError**：所有 Volley 错误的基类。继承自 `Exception`，用于描述 Volley 中所有的错误异常，可以设置 `NetworkResponse` 和 请求消耗时间。

**AuthFailureError**：`AuthFailureError` 继承了 `VolleyError`。表示：**请求认证失败错误，如 RespondCode 的 401 和 403**。       

**TimeoutError**：`TimeoutError` 继承了 `VolleyError`。表示：**请求超时错误**。但是什么都没有实现，作为一个标识存在。

**ServerError**：`ServerError` 继承了 `VolleyError`。表示：**服务端错误**。       

**ClientError**：`ClientError` 扩展了 `ServerError` 的概念。表示：**客户端错误，即 4xx 错误**。        

**ParseError**：`ParseError` 继承了 `VolleyError`。表示：**内容解析错误**。      

**NoConnectionError**：`NoConnectionError` 继承了 `NetworkError`。表示：**网络连接错误**。      

**NetworkError**：`NetworkError` 继承了 `VolleyError`，表示：**网络错误**。         

<br/>
<br/>

# License

      Copyright (C) 2016 CaMnter yuanyu.camnter@gmail.com
      Copyright (C) 2015 Google Inc. All Rights Reserved.
      Copyright (C) 2012 Square, Inc.
      Copyright (C) 2011 The Android Open Source Project
      Copyright (C) 2008 The Apache Software Foundation (ASF)
      Copyright (C) 2007 The Guava Authors

      Licensed under the Apache License, Version 2.0 (the "License");
      you may not use this file except in compliance with the License.
      You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

      Unless required by applicable law or agreed to in writing, software
      distributed under the License is distributed on an "AS IS" BASIS,
      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
      See the License for the specific language governing permissions and
      limitations under the License.
