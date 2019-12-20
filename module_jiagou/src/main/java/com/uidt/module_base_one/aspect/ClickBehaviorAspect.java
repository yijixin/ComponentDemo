package com.uidt.module_base_one.aspect;

import android.util.Log;

import com.uidt.module_base_one.annotation.ClickBehavior;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author yijixin on 2019-12-13
 */
@Aspect //顶一个一个切面类
public class ClickBehaviorAspect {

    private static final String TAG = "JiaGou ==>";

    //1、应用中用到了哪些注解，找到需要处理的切入点
    //execution,以方法执行时作为切点 触发Aspect类
    //* *(..)) 可以处理ClickBehavior这个类所有的方法
    //如果设置为com.uidt.module_base_one.MainModuleOneActivity 标识MainModuleOneActivity下的所有方法
    @Pointcut("execution(@com.uidt.module_base_one.annotation.ClickBehavior * *(..))")
    public void methodPointCut() {
    }

    ;

    //2、对Pointcut methodPointCut()切入点如何处理
    //有around before等
    //必须遵循1、Object返回值 2、ProceedingJoinPoint参数 3、Throwable抛出异常
    @Around("methodPointCut()")
    public Object joinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        //获取方法所属的类名
        String className = signature.getDeclaringType().getSimpleName();

        //获取方法名
        String methName = signature.getName();

        //获取方法的注解值(用户行为统计)
        String funName = signature.getMethod().getAnnotation(ClickBehavior.class).value();

        //统计方法的执行时间
        //统计用户点击某功能的行为（存储到本地，每过多少天上传）
        long begin = System.currentTimeMillis();
        Log.e(TAG, "Clickbehavior Method start");
        Object result = joinPoint.proceed(); //MainModuleOneActivity中切面的方法
        long duration = System.currentTimeMillis() - begin;
        Log.e(TAG, "Clickbehavior Method end");
        Log.e(TAG, String.format("统计了:%s功能，在%s类的%s方法，用时%d ms", funName, className, methName, duration));

        return result;
    }
}
