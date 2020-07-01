# SF 模仿SpringBoot实现的一个框架
#### 功能列表：
* [x] 自动装配
* [x] 自动注入
* [ ] ...

_num开头的包是LeetCode对应的题_
```java
public class App{
    public static void main(String[] args) throws Exception {
        Application.run(App.class,args);
    }
}
``` 
启动与SpringBoot一样，不过没有`@SpringBootApplication`注解,还有很多功能待补充。
