package com.uidt.module_base_one.aspect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.uidt.common_base.utils.PreferenceUtil;
import com.uidt.module_base_one.LoginActivity;
import com.uidt.module_base_one.MainModuleOneActivity;
import com.uidt.module_base_one.annotation.ClickBehavior;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author yijixin on 2019-12-13
 */
@Aspect //顶一个一个切面类
public class LoginCheckAspect {

    private static final String TAG = "JiaGou ==>";

    //1、应用中用到了哪些注解，找到需要处理的切入点
    //execution,以方法执行时作为切点 触发Aspect类
    //* *(..)) 可以处理ClickBehavior这个类所有的方法
    //如果设置为com.uidt.module_base_one.MainModuleOneActivity 标识MainModuleOneActivity下的所有方法
    @Pointcut("execution(@com.uidt.module_base_one.annotation.LoginCheck * *(..))")
    public void methodPointCut() {
    }

    ;

    //2、对Pointcut methodPointCut()切入点如何处理
    //有around before等
    //必须遵循1、Object返回值 2、ProceedingJoinPoint参数 3、Throwable抛出异常
    @Around("methodPointCut()")
    public Object joinPoint(ProceedingJoinPoint joinPoint) throws Throwable {

        Context context = (Context) joinPoint.getThis();

        boolean isLogin = PreferenceUtil.getBoolean(context,"login_result",false);
        if (isLogin) {
            Log.e(TAG,"检测已登录！");
            //可以进行切点方法的执行
            return joinPoint.proceed();
        } else {
            Log.e(TAG,"检测未登录！");
            Toast.makeText(context, "请先登录!", Toast.LENGTH_SHORT).show();
            LoginActivity.startAction((Activity) context);
            //不再执行切点所在的方法
            return null;
        }

    }
}
