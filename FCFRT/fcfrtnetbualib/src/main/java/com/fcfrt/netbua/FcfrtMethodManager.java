package com.fcfrt.netbua;
import com.fcfrt.netbua.type.Mode;

import java.lang.reflect.Method;


/**
 * 项目名称：
 * 类名称：FcfrtMethodManager
 * 类描述：保存符合要求的网络监听注解方法
 * 作者：FCFRT
 * 创建时间： 2019/7/1-14:32
 * 邮箱：FCFRT_ADMIN@163.COM
 * 修改简介：
 */
public class FcfrtMethodManager {

    //参数类型
    private Class<?> parameterClazz;

    //订阅类型
    private Mode mode;

    //需要执行的方法
    private Method method;

    public FcfrtMethodManager(Class<?> clazz, Mode mode, Method method) {
        this.parameterClazz = clazz;
        this.mode = mode;
        this.method = method;
    }

    public Class<?> getParameterClazz() {
        return parameterClazz;
    }

    public void setParameterClazz(Class<?> parameterClazz) {
        this.parameterClazz = parameterClazz;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
