# atm-web错误日志

## 25.11.10-18.52

1. 使用数据库里的数据登录失败，提示账号或密码错误，疑似问题二的问题

2. 数据库里数据储存有问题（须清理数据库现有数据），card储存的是用户名，name储存的是卡号

3. 开户时使用数据库里已经存在的用户名开户，会出现后端连接失败提示（要求能使用同一用户名开户）

   ```
   //报错日志（后端）
   2025-11-10T18:59:20.101+08:00 ERROR 24492 --- [atm] [nio-8090-exec-1] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: org.springframework.dao.DuplicateKeyException: 
   ### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '段子康' for key 'account.PRIMARY'
   ### The error may exist in file [C:\Users\vaedu\Desktop\ATM-Web\atm\build\resources\main\mapper\AccountMapper.xml]
   ### The error may involve com.example.atm.mapper.AccountMapper.insert-Inline
   ### The error occurred while setting parameters
   ### SQL: INSERT INTO account(card, name, password, balance, `limit`, sex)         VALUES (?, ?, ?, ?, ?, ?);
   ### Cause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '段子康' for key 'account.PRIMARY'
   ; Duplicate entry '段子康' for key 'account.PRIMARY'] with root cause
   //前端
   Failed to load resource: the server responded with a status of 500 ()
   ```

   

## 25.11.13-23.38

1. 前后端均无报错，但就是跳转失败

## 25.11.14 - 20.37

1. 少一个更改密码页面的样式

2. 前端页面加载失败，报错

   ```vue
   [plugin:vite:import-analysis] Failed to resolve import "@/components/TransactionList.vue" from "src/views/Home.vue". Does the file exist?
   C:/Users/vaedu/Desktop/ATM-Web/atm-front/src/views/Home.vue:32:29
   1  |  
   2  |  import NavBar from '@/components/NavBar.vue';
   3  |  import TransactionList from '@/components/TransactionList.vue';
      |                                 ^
   4  |  import axios from 'axios';
   5  |  const _sfc_main = {
       at TransformPluginContext._formatLog (file:///C:/Users/vaedu/Desktop/ATM-Web/atm-front/node_modules/vite/dist/node/chunks/config.js:29608:43)
       at TransformPluginContext.error (file:///C:/Users/vaedu/Desktop/ATM-Web/atm-front/node_modules/vite/dist/node/chunks/config.js:29605:14)
       at normalizeUrl (file:///C:/Users/vaedu/Desktop/ATM-Web/atm-front/node_modules/vite/dist/node/chunks/config.js:27728:18)
       at async file:///C:/Users/vaedu/Desktop/ATM-Web/atm-front/node_modules/vite/dist/node/chunks/config.js:27786:32
       at async Promise.all (index 1)
       at async TransformPluginContext.transform (file:///C:/Users/vaedu/Desktop/ATM-Web/atm-front/node_modules/vite/dist/node/chunks/config.js:27754:4)
       at async EnvironmentPluginContainer.transform (file:///C:/Users/vaedu/Desktop/ATM-Web/atm-front/node_modules/vite/dist/node/chunks/config.js:29406:14)
       at async loadAndTransform (file:///C:/Users/vaedu/Desktop/ATM-Web/atm-front/node_modules/vite/dist/node/chunks/config.js:23277:26)
   Click outside, press Esc key, or fix the code to dismiss.
   You can also disable this overlay by setting server.hmr.overlay to false in vite.config.js.
   ```
## 25.11.15 - 19.24

1. 限额显示NAN元
2. 最近交易显示的是类名，如存款显示DEPOSIT，取款显示WITHDRAW，转账显示TRANSFER_OUT
3. 账户信息显示不出来性别
4. 存款、取款成功后没有任何提示信息，取款密码输入错误也没任何提示但前端控制台有报错500
    ```
    //前端
    Withdraw.vue:34 
     POST http://localhost:8090/api/atm/withdraw 500 (Internal Server Error)
    ﻿
    //后端
        2025-11-15T19:35:00.213+08:00 ERROR 45800 --- [atm] [nio-8090-exec-5] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: java.lang.RuntimeException: 密码错误] with root cause
    
    java.lang.RuntimeException: 密码错误
    	at com.example.atm.service.impl.AccountServiceImpl.withdraw(AccountServiceImpl.java:66) ~[main/:na]
    	at com.example.atm.controller.AccountController.withdraw(AccountController.java:39) ~[main/:na]
    	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
    	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77) ~[na:na]
    	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
    	at java.base/java.lang.reflect.Method.invoke(Method.java:569) ~[na:na]
    	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:258) ~[spring-web-6.2.12.jar:6.2.12]
    	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:191) ~[spring-web-6.2.12.jar:6.2.12]
    ```
5. 每个二级页面（除了账户信息）都没有返回首页的功能，需要有
6. 导航条没有样式
7. 自助开户的输入框前面没有文字提示
## 25.11.15 - 23.22
1. 余额不会更新
## 25.11.16 - 12.06
1. 登录进去后首页 性别和限额都不显示，且控制台报错，纯前端报错
   
   ```console
   Access to XMLHttpRequest at 'http://localhost:8090/api/atm/transactions?card=12345678' from origin 'http://localhost:5173' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
   Login.vue:45 [Vue warn]: Unhandled error during execution of created hook 
     at <Home onVnodeUnmounted=fn<onVnodeUnmounted> ref=Ref< Proxy(Object) {…} > > 
     at <RouterView> 
     at <App>
   warn$1 @ chunk-3BUNKTSD.js?v=eb6cf2ff:2149
   logError @ chunk-3BUNKTSD.js?v=eb6cf2ff:2360
   handleError @ chunk-3BUNKTSD.js?v=eb6cf2ff:2352
   （匿名） @ chunk-3BUNKTSD.js?v=eb6cf2ff:2306
   Promise.catch
   callWithAsyncErrorHandling @ chunk-3BUNKTSD.js?v=eb6cf2ff:2305
   callHook @ chunk-3BUNKTSD.js?v=eb6cf2ff:5813
   applyOptions @ chunk-3BUNKTSD.js?v=eb6cf2ff:5730
   finishComponentSetup @ chunk-3BUNKTSD.js?v=eb6cf2ff:10244
   setupStatefulComponent @ chunk-3BUNKTSD.js?v=eb6cf2ff:10167
   setupComponent @ chunk-3BUNKTSD.js?v=eb6cf2ff:10092
   mountComponent @ chunk-3BUNKTSD.js?v=eb6cf2ff:7420
   processComponent @ chunk-3BUNKTSD.js?v=eb6cf2ff:7386
   patch @ chunk-3BUNKTSD.js?v=eb6cf2ff:6890
   componentUpdateFn @ chunk-3BUNKTSD.js?v=eb6cf2ff:7612
   run @ chunk-3BUNKTSD.js?v=eb6cf2ff:505
   runIfDirty @ chunk-3BUNKTSD.js?v=eb6cf2ff:543
   callWithErrorHandling @ chunk-3BUNKTSD.js?v=eb6cf2ff:2296
   flushJobs @ chunk-3BUNKTSD.js?v=eb6cf2ff:2504
   Promise.then
   queueFlush @ chunk-3BUNKTSD.js?v=eb6cf2ff:2418
   queuePostFlushCb @ chunk-3BUNKTSD.js?v=eb6cf2ff:2432
   queueEffectWithSuspense @ chunk-3BUNKTSD.js?v=eb6cf2ff:9546
   baseWatchOptions.scheduler @ chunk-3BUNKTSD.js?v=eb6cf2ff:8417
   effect2.scheduler @ chunk-3BUNKTSD.js?v=eb6cf2ff:2042
   trigger @ chunk-3BUNKTSD.js?v=eb6cf2ff:533
   endBatch @ chunk-3BUNKTSD.js?v=eb6cf2ff:591
   notify @ chunk-3BUNKTSD.js?v=eb6cf2ff:853
   trigger @ chunk-3BUNKTSD.js?v=eb6cf2ff:827
   set value @ chunk-3BUNKTSD.js?v=eb6cf2ff:1706
   finalizeNavigation @ vue-router.js?v=eb6cf2ff:2213
   （匿名） @ vue-router.js?v=eb6cf2ff:2151
   Promise.then
   pushWithRedirect @ vue-router.js?v=eb6cf2ff:2138
   push @ vue-router.js?v=eb6cf2ff:2089
   onLogin @ Login.vue:45
   await in onLogin
   _createElementVNode.onClick._cache.<computed>._cache.<computed> @ Login.vue:13
   callWithErrorHandling @ chunk-3BUNKTSD.js?v=eb6cf2ff:2296
   callWithAsyncErrorHandling @ chunk-3BUNKTSD.js?v=eb6cf2ff:2303
   invoker @ chunk-3BUNKTSD.js?v=eb6cf2ff:11358
   Home.vue:61  GET http://localhost:8090/api/atm/transactions?card=12345678 net::ERR_FAILED 404 (Not Found)
   dispatchXhrRequest @ axios.js?v=eb6cf2ff:1696
   xhr @ axios.js?v=eb6cf2ff:1573
   dispatchRequest @ axios.js?v=eb6cf2ff:2107
   _request @ axios.js?v=eb6cf2ff:2327
   request @ axios.js?v=eb6cf2ff:2219
   Axios.<computed> @ axios.js?v=eb6cf2ff:2346
   wrap @ axios.js?v=eb6cf2ff:8
   created @ Home.vue:61
   await in created
   callWithErrorHandling @ chunk-3BUNKTSD.js?v=eb6cf2ff:2296
   callWithAsyncErrorHandling @ chunk-3BUNKTSD.js?v=eb6cf2ff:2303
   callHook @ chunk-3BUNKTSD.js?v=eb6cf2ff:5813
   applyOptions @ chunk-3BUNKTSD.js?v=eb6cf2ff:5730
   finishComponentSetup @ chunk-3BUNKTSD.js?v=eb6cf2ff:10244
   setupStatefulComponent @ chunk-3BUNKTSD.js?v=eb6cf2ff:10167
   setupComponent @ chunk-3BUNKTSD.js?v=eb6cf2ff:10092
   mountComponent @ chunk-3BUNKTSD.js?v=eb6cf2ff:7420
   processComponent @ chunk-3BUNKTSD.js?v=eb6cf2ff:7386
   patch @ chunk-3BUNKTSD.js?v=eb6cf2ff:6890
   componentUpdateFn @ chunk-3BUNKTSD.js?v=eb6cf2ff:7612
   run @ chunk-3BUNKTSD.js?v=eb6cf2ff:505
   runIfDirty @ chunk-3BUNKTSD.js?v=eb6cf2ff:543
   callWithErrorHandling @ chunk-3BUNKTSD.js?v=eb6cf2ff:2296
   flushJobs @ chunk-3BUNKTSD.js?v=eb6cf2ff:2504
   Promise.then
   queueFlush @ chunk-3BUNKTSD.js?v=eb6cf2ff:2418
   queuePostFlushCb @ chunk-3BUNKTSD.js?v=eb6cf2ff:2432
   queueEffectWithSuspense @ chunk-3BUNKTSD.js?v=eb6cf2ff:9546
   baseWatchOptions.scheduler @ chunk-3BUNKTSD.js?v=eb6cf2ff:8417
   effect2.scheduler @ chunk-3BUNKTSD.js?v=eb6cf2ff:2042
   trigger @ chunk-3BUNKTSD.js?v=eb6cf2ff:533
   endBatch @ chunk-3BUNKTSD.js?v=eb6cf2ff:591
   notify @ chunk-3BUNKTSD.js?v=eb6cf2ff:853
   trigger @ chunk-3BUNKTSD.js?v=eb6cf2ff:827
   set value @ chunk-3BUNKTSD.js?v=eb6cf2ff:1706
   finalizeNavigation @ vue-router.js?v=eb6cf2ff:2213
   （匿名） @ vue-router.js?v=eb6cf2ff:2151
   Promise.then
   pushWithRedirect @ vue-router.js?v=eb6cf2ff:2138
   push @ vue-router.js?v=eb6cf2ff:2089
   onLogin @ Login.vue:45
   await in onLogin
   _createElementVNode.onClick._cache.<computed>._cache.<computed> @ Login.vue:13
   callWithErrorHandling @ chunk-3BUNKTSD.js?v=eb6cf2ff:2296
   callWithAsyncErrorHandling @ chunk-3BUNKTSD.js?v=eb6cf2ff:2303
   invoker @ chunk-3BUNKTSD.js?v=eb6cf2ff:11358
   axios.js?v=eb6cf2ff:1637 Uncaught (in promise) AxiosError {message: 'Network Error', name: 'AxiosError', code: 'ERR_NETWORK', config: {…}, request: XMLHttpRequest, …}
   ```
2. 进入存款，输入金额存款，点击确认存款后提示服务器错误，控制台报
    ```
      Deposit.vue:33  POST http://localhost:8090/api/atm/deposit 400 (Bad Request)
        同时，后端日志报
            2025-11-16T12:44:34.053+08:00  WARN 28668 --- [atm] [nio-8090-exec-5] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.web.bind.MissingServletRequestParameterException: Required request parameter 'card' for method parameter type String is not present]
    ```
3. 从存款返回主页能正常返回，但同时前端控制台报错
    ```
        Access to XMLHttpRequest at 'http://localhost:8090/api/atm/transactions?card=12345678' from origin 'http://localhost:5173' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
      Deposit.vue:13 [Vue warn]: Unhandled error during execution of created hook
      at <Home onVnodeUnmounted=fn<onVnodeUnmounted> ref=Ref< Proxy(Object) {…} > >
      at <RouterView>
      at <App>
      warn$1 @ chunk-3BUNKTSD.js?v=eb6cf2ff:2149
      logError @ chunk-3BUNKTSD.js?v=eb6cf2ff:2360
      handleError @ chunk-3BUNKTSD.js?v=eb6cf2ff:2352
      （匿名） @ chunk-3BUNKTSD.js?v=eb6cf2ff:2306
      Promise.catch
      callWithAsyncErrorHandling @ chunk-3BUNKTSD.js?v=eb6cf2ff:2305
      callHook @ chunk-3BUNKTSD.js?v=eb6cf2ff:5813
      applyOptions @ chunk-3BUNKTSD.js?v=eb6cf2ff:5730
      finishComponentSetup @ chunk-3BUNKTSD.js?v=eb6cf2ff:10244
      setupStatefulComponent @ chunk-3BUNKTSD.js?v=eb6cf2ff:10167
      setupComponent @ chunk-3BUNKTSD.js?v=eb6cf2ff:10092
      mountComponent @ chunk-3BUNKTSD.js?v=eb6cf2ff:7420
      processComponent @ chunk-3BUNKTSD.js?v=eb6cf2ff:7386
      patch @ chunk-3BUNKTSD.js?v=eb6cf2ff:6890
      componentUpdateFn @ chunk-3BUNKTSD.js?v=eb6cf2ff:7612
      run @ chunk-3BUNKTSD.js?v=eb6cf2ff:505
      runIfDirty @ chunk-3BUNKTSD.js?v=eb6cf2ff:543
      callWithErrorHandling @ chunk-3BUNKTSD.js?v=eb6cf2ff:2296
      flushJobs @ chunk-3BUNKTSD.js?v=eb6cf2ff:2504
      Promise.then
      queueFlush @ chunk-3BUNKTSD.js?v=eb6cf2ff:2418
      queuePostFlushCb @ chunk-3BUNKTSD.js?v=eb6cf2ff:2432
      queueEffectWithSuspense @ chunk-3BUNKTSD.js?v=eb6cf2ff:9546
      baseWatchOptions.scheduler @ chunk-3BUNKTSD.js?v=eb6cf2ff:8417
      effect2.scheduler @ chunk-3BUNKTSD.js?v=eb6cf2ff:2042
      trigger @ chunk-3BUNKTSD.js?v=eb6cf2ff:533
      endBatch @ chunk-3BUNKTSD.js?v=eb6cf2ff:591
      notify @ chunk-3BUNKTSD.js?v=eb6cf2ff:853
      trigger @ chunk-3BUNKTSD.js?v=eb6cf2ff:827
      set value @ chunk-3BUNKTSD.js?v=eb6cf2ff:1706
      finalizeNavigation @ vue-router.js?v=eb6cf2ff:2213
      （匿名） @ vue-router.js?v=eb6cf2ff:2151
      Promise.then
      pushWithRedirect @ vue-router.js?v=eb6cf2ff:2138
      push @ vue-router.js?v=eb6cf2ff:2089
      _createElementVNode.onClick._cache.<computed>._cache.<computed> @ Deposit.vue:13
      callWithErrorHandling @ chunk-3BUNKTSD.js?v=eb6cf2ff:2296
      callWithAsyncErrorHandling @ chunk-3BUNKTSD.js?v=eb6cf2ff:2303
      invoker @ chunk-3BUNKTSD.js?v=eb6cf2ff:11358
      Home.vue:61  GET http://localhost:8090/api/atm/transactions?card=12345678 net::ERR_FAILED 404 (Not Found)
      dispatchXhrRequest @ axios.js?v=eb6cf2ff:1696
      xhr @ axios.js?v=eb6cf2ff:1573
      dispatchRequest @ axios.js?v=eb6cf2ff:2107
      _request @ axios.js?v=eb6cf2ff:2327
      request @ axios.js?v=eb6cf2ff:2219
      Axios.<computed> @ axios.js?v=eb6cf2ff:2346
      wrap @ axios.js?v=eb6cf2ff:8
      created @ Home.vue:61
      await in created
      callWithErrorHandling @ chunk-3BUNKTSD.js?v=eb6cf2ff:2296
      callWithAsyncErrorHandling @ chunk-3BUNKTSD.js?v=eb6cf2ff:2303
      callHook @ chunk-3BUNKTSD.js?v=eb6cf2ff:5813
      applyOptions @ chunk-3BUNKTSD.js?v=eb6cf2ff:5730
      finishComponentSetup @ chunk-3BUNKTSD.js?v=eb6cf2ff:10244
      setupStatefulComponent @ chunk-3BUNKTSD.js?v=eb6cf2ff:10167
      setupComponent @ chunk-3BUNKTSD.js?v=eb6cf2ff:10092
      mountComponent @ chunk-3BUNKTSD.js?v=eb6cf2ff:7420
      processComponent @ chunk-3BUNKTSD.js?v=eb6cf2ff:7386
      patch @ chunk-3BUNKTSD.js?v=eb6cf2ff:6890
      componentUpdateFn @ chunk-3BUNKTSD.js?v=eb6cf2ff:7612
      run @ chunk-3BUNKTSD.js?v=eb6cf2ff:505
      runIfDirty @ chunk-3BUNKTSD.js?v=eb6cf2ff:543
      callWithErrorHandling @ chunk-3BUNKTSD.js?v=eb6cf2ff:2296
      flushJobs @ chunk-3BUNKTSD.js?v=eb6cf2ff:2504
      Promise.then
      queueFlush @ chunk-3BUNKTSD.js?v=eb6cf2ff:2418
      queuePostFlushCb @ chunk-3BUNKTSD.js?v=eb6cf2ff:2432
      queueEffectWithSuspense @ chunk-3BUNKTSD.js?v=eb6cf2ff:9546
      baseWatchOptions.scheduler @ chunk-3BUNKTSD.js?v=eb6cf2ff:8417
      effect2.scheduler @ chunk-3BUNKTSD.js?v=eb6cf2ff:2042
      trigger @ chunk-3BUNKTSD.js?v=eb6cf2ff:533
      endBatch @ chunk-3BUNKTSD.js?v=eb6cf2ff:591
      notify @ chunk-3BUNKTSD.js?v=eb6cf2ff:853
      trigger @ chunk-3BUNKTSD.js?v=eb6cf2ff:827
      set value @ chunk-3BUNKTSD.js?v=eb6cf2ff:1706
      finalizeNavigation @ vue-router.js?v=eb6cf2ff:2213
      （匿名） @ vue-router.js?v=eb6cf2ff:2151
      Promise.then
      pushWithRedirect @ vue-router.js?v=eb6cf2ff:2138
      push @ vue-router.js?v=eb6cf2ff:2089
      _createElementVNode.onClick._cache.<computed>._cache.<computed> @ Deposit.vue:13
      callWithErrorHandling @ chunk-3BUNKTSD.js?v=eb6cf2ff:2296
      callWithAsyncErrorHandling @ chunk-3BUNKTSD.js?v=eb6cf2ff:2303
      invoker @ chunk-3BUNKTSD.js?v=eb6cf2ff:11358
      axios.js?v=eb6cf2ff:1637 Uncaught (in promise) AxiosError {message: 'Network Error', name: 'AxiosError', code: 'ERR_NETWORK', config: {…}, request: XMLHttpRequest, …}
    ```
4. 取款按钮按下后，前端提示{ "timestamp": "2025-11-16T04:49:11.965+00:00", "status": 400, "error": "Bad Request", "path": "/api/atm/withdraw" }，控制台报错
    ```
        Withdraw.vue:39  POST http://localhost:8090/api/atm/withdraw 400 (Bad Request)
        同时后端日志
        2025-11-16T12:58:27.342+08:00  WARN 28668 --- [atm] [nio-8090-exec-4] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.web.bind.MissingServletRequestParameterException: Required request parameter 'card' for method parameter type String is not present]
   ```
5. 转账信息输入玩后，发生与错误4一样的情况
6. 修改密码，确认修改后控制台报错
    ```
        axios.js?v=eb6cf2ff:1637 Uncaught (in promise) 
        AxiosError {message: 'Network Error', name: 'AxiosError', code: 'ERR_NETWORK', config: {…}, request: XMLHttpRequest,
        同时后端日志
         2025-11-16T12:51:38.854+08:00  WARN 28668 --- [atm] [nio-8090-exec-6] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.web.bind.MissingServletRequestParameterException: Required request parameter 'fromCard' for method parameter type String is not present]
   ```
7. 开户输入完信息，前端报错
    ```
        ccess to XMLHttpRequest at 'http://localhost:8090/api/atm/register' from origin 'http://localhost:5173' has been blocked by CORS policy: Response to preflight request doesn't pass access control check: No 'Access-Control-Allow-Origin' header is present on the requested resource.
        Register.vue:47  POST http://localhost:8090/api/atm/register net::ERR_FAILED   
   ```