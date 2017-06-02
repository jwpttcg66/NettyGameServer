# game-executor
> 采用Reactor模式，注册readycreate, readyfinish事件到更新服务UpdateService，通过处理后进行模型缓存，然后将消息转化为
dispatchThread消息分配模型需要的create, update, finish的事件进行单线程循环调度
。调度过程使用了系统预置锁模型，来进行多线程唤醒机制，将所有的update循环检测进行多
线程调度，多线程更新服务使用future-listener机制，在完成调度后，根据模型状态，如果模型存活重新将消息转化为update
事件注册到dispatchThread消息分配模型进行循环处理。如果模型死亡将消息转化为readyfinish事件注册到更新服务UpdateServic进行销毁
。这个系统实现了模型自动缓存，多线程异步循环调度模型更新，自动处理模型死亡事件进行销毁。
支持将多个updater绑定到同一个更新线程上，减少线程调度。
增加异步event-bus

## 异步update使用例子

> 可参考test目录下的AsyncUpdateBusTest.

1. 生成eventbus，注册react事件模型。
3. 生成异步线程服务UpdateExecutorService。
4. 生成异步分配线程LockSupportDisptachThread。
5. 生成更新服务UpdateService
6. 生成eventbus监听器. 注册监听器
8. 生成事件，放入UpdateService更新服务
9. 进行循环处理

## 异步update绑定更新线程使用例子

> 可参考test目录下的AsyncNotifyUpdateTest.

1. 生成eventbus，注册react事件模型。
3. 生成异步线程服务UpdateEventExcutorService。
4. 生成异步分配线程LockSupportEventDisptachThread。
5. 生成更新服务UpdateService
6. 生成eventbus监听器. 注册监听器
8. 生成事件，放入UpdateService更新服务
9. 进行循环处理

## 同步event-bus使用例子

> 可参考test下的SynsEventBusTest.

1. 生成eventbus，注册react事件模型。
2. 生成dispatchThread，生成分配线程.
3. 生成eventbus监听器. 注册监听器
4. 生成事件，放入eventbus
5. 进行循环处理

## 异步event-bus使用例子

> 可参考test下的AsyncEventServiceTest.

1. 生成eventbus，注册react事件模型。
2. 生成AsyncEventService，生成异步event-bus服务.
3. 生成eventbus监听器. 注册监听器
4. 生成事件，放入eventbus
5. 进行循环处理

### 代码最后通过maven部署

- 作者qq 330258845
- QQ群310158485
