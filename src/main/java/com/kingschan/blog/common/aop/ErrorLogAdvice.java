package com.kingschan.blog.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;

/**
 * 
*  <pre>    
* 类名称：ErrorLogAdvice 
* 类描述：  
* 创建人：陈国祥   (kingschan)
* 创建时间：2015-4-8 下午7:47:01   
* 修改人：Administrator   
* 修改时间：2015-4-8 下午7:47:01   
* 修改备注：   
* @version V1.0
* </pre>
 */
//@Component("ErrorLogAdvice")
//@Aspect
public class ErrorLogAdvice {
    
   
//    @Before("execution(* com.kingschan.blog..*.*(..))")  
    public void before(){  
        System.out.println("@before");  
    }
    /**
     * 目标方法正常完成后，会被调用
     * @param args 目录方法的返回值 
     */
//    @AfterReturning(returning="args",pointcut="execution(*  com.kingschan.blog..*.*(..))")  
    public void AfterReturning(Object args){  
        System.out.println("@AfterReturning 获取目录方法返回值："+args); 
        System.out.println("");
    }  
      
//    @AfterThrowing(throwing="ex",pointcut="execution(*  com.kingschan.blog..*.*(..))")  
    public void AfterThrowing(JoinPoint joinPoint,Throwable ex){  
    	
        System.out.println("目标方法抛出的异常@AfterThrowing："+ex);  
    }  
    /**
     * 与@AfterReturning的区别
     * 不管方法是否正常结束它都会调用
     */
//    @After("execution(*  com.kingschan.blog..*.*(..))")
    public void after(){
        System.out.println("@after");
    }
    /**
     * 可以决定方法在何时执行，甚至可以完全阻止目标方法执行
     * @throws Throwable 
     */
//    @Around("execution(*  com.kingschan.blog..*.*(..))")
    public Object around(ProceedingJoinPoint jp) throws Throwable{
        System.out.println(String.format("@Around:参数：%s | 目标%s  | getthis:%s", jp.getArgs().toString(),jp.getTarget().getClass().getName(),jp.getThis()));
        Object o =jp.proceed();
        //jp.proceed(xxx); 还可以改变参数
        return o;
    }
}
