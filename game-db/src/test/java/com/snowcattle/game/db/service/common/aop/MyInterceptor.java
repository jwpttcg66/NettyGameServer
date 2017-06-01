package com.snowcattle.game.db.service.common.aop;
/**
 * Created by jwp on 2017/3/22.
 */
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * @author whp
 * @Email whp@ehoo.cn
 * @Jan 4, 2011
 *
 */
@Aspect//声明切面
@Controller//把类交给spring管理
public class MyInterceptor {
    @Pointcut("execution(* com.snowcattle.game..db.service.aop.PersonServiceBean.*(..))")// 切入点表达式
    private void anyMethod() {
    }// 声明一个切入点
    @Before("anyMethod()&& args(userName)")//定义前置通知 执行业务方法前执行 args(userName) 表示要执行的方式必须为一个参数并为Person类型.这样就给他再加了限制条件
    public void doAccessCheck(Person userName) {
        System.out.println(userName);//得到输入的参数
        System.out.println("执行前置通知");
    }
    @AfterReturning(pointcut="anyMethod()")//定义后置通知 执行完业务方法后执行 如果例外通知执行，那么它不会执行
    public void doReturnCheck() {
        System.out.println("执行后置通知");
    }

    @After("anyMethod()")//定义最终通知 finally里执行的通知。
    public void doReleaseAction() {
        System.out.println("执行最终通知");
    }

    @AfterThrowing(pointcut="anyMethod()", throwing="ex")//定义例外通知
    public void doExceptionAction(Exception ex) {
        System.out.println("执行例外通知");
    }

    @Around("anyMethod()")//环绕通知 doBasicProfiling    pjp可以修改  用于权限
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("执行环绕通知");
        Object retule =pjp.proceed();
        System.out.println("退出环绕通知");
        return retule;
    }

}
