package com.fcfrt.netbua;


import com.fcfrt.netbua.annotation.FcfrtNetSubscribe;
import com.fcfrt.netbua.type.NetType;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 项目名称：
 * 类名称：FcfrtNetStatusReceiver
 * 类描述：
 * 作者：FCFRT
 * 创建时间： 2019/7/1-14:33
 * 邮箱：FCFRT_ADMIN@163.COM
 * 修改简介：
 */
public class FcfrtNetStatusReceiver {

    private NetType mNetType;//网络类型

    private Map<Object, List<FcfrtMethodManager>> networkList;


    protected FcfrtNetStatusReceiver() {
        mNetType = NetType.NONE;
        networkList = new HashMap<>();
    }

    /**
     * 分发
     */
    protected void post(NetType netType) {
        //所有的注册类
        Set<Object> subscribeClazzSet = networkList.keySet();
        this.mNetType = netType;
        for (Object subscribeClazz : subscribeClazzSet) {
            List<FcfrtMethodManager> methodManagerList = networkList.get(subscribeClazz);
            executeInvoke(subscribeClazz, methodManagerList);
        }
    }

    private void executeInvoke(Object subscribeClazz, List<FcfrtMethodManager> methodManagerList) {
        if (methodManagerList != null) {
            for (FcfrtMethodManager subscribeMethod : methodManagerList) {

                switch (subscribeMethod.getMode()) {
                    case AUTO:
                        invoke(subscribeMethod, subscribeClazz, mNetType);
                        break;

                    case WIFI:
                        if (mNetType == NetType.WIFI || mNetType == NetType.NONE)
                            invoke(subscribeMethod, subscribeClazz, mNetType);
                        break;

                    case WIFI_CONNECT:
                        if (mNetType == NetType.WIFI)
                            invoke(subscribeMethod, subscribeClazz, mNetType);
                        break;

                    case MOBILE:
                        if (mNetType == NetType.MOBILE || mNetType == NetType.NONE)
                            invoke(subscribeMethod, subscribeClazz, mNetType);
                        break;

                    case MOBILE_CONNECT:
                        if (mNetType == NetType.MOBILE) {
                            invoke(subscribeMethod, subscribeClazz, mNetType);
                        }
                        break;

                    case NONE:
                        if (mNetType == NetType.NONE)
                            invoke(subscribeMethod, subscribeClazz, mNetType);

                    default:
                }
            }
        }
    }

    private void invoke(FcfrtMethodManager subscribeMethod, Object subscribeClazz, NetType netType) {

        Method execute = subscribeMethod.getMethod();
        try {
            //有参数时
            if (subscribeMethod.getParameterClazz() != null) {
                if (subscribeMethod.getParameterClazz().isAssignableFrom(mNetType.getClass())) {
                    execute.invoke(subscribeClazz, netType);
                }
            } else {
                execute.invoke(subscribeClazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void registerObserver(Object mContext) {
        List<FcfrtMethodManager> methodList = networkList.get(mContext);
        if (methodList == null) {
//        开始添加
            methodList = findAnnotationMethod(mContext);
            networkList.put(mContext, methodList);
        }
        executeInvoke(mContext, networkList.get(mContext));
    }

    private List<FcfrtMethodManager> findAnnotationMethod(Object mContext) {
        List<FcfrtMethodManager> methodManagerList = new ArrayList<>();
//        获取到activity fragment
        Class<?> clazz = mContext.getClass();
        Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                FcfrtNetSubscribe netSubscribe = method.getAnnotation(FcfrtNetSubscribe.class);
                if (netSubscribe == null) {
                    continue;
                }
                //注解方法校验返回值
                Type genericReturnType = method.getGenericReturnType();
                if (!"void".equalsIgnoreCase(genericReturnType.toString())) {
                    throw new IllegalArgumentException("you " + method.getName() + "method return value must be void");
                }

                //判断参数
                Class<?>[] parameterTypes = method.getParameterTypes();
                FcfrtMethodManager methodManager;
                if (parameterTypes.length == 0) {
                    methodManager = new FcfrtMethodManager(null, netSubscribe.mode(), method);
                } else if (parameterTypes.length == 1) {
                    methodManager = new FcfrtMethodManager(parameterTypes[0], netSubscribe.mode(), method);
                } else {
                    throw new IllegalArgumentException("Your method " + method.getName() + " can have at most one parameter of type NetType ");
                }

                methodManagerList.add(methodManager);
            }

        return methodManagerList;
    }

    public void unRegisterObserver(Object mContext) {
        if (!networkList.isEmpty()) {
            networkList.remove(mContext);
        }
    }

    public void unRegisterAllObserver() {
        if (!networkList.isEmpty()) {
            networkList.clear();
            networkList = null;
        }
    }
}
