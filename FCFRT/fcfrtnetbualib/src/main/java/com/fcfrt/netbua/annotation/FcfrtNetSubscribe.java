package com.fcfrt.netbua.annotation;


import com.fcfrt.netbua.type.Mode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 项目名称：
 * 类名称：FcfrtNetSubscribe
 * 类描述：
 * 作者：FCFRT
 * 创建时间： 2019/7/1-14:31
 * 邮箱：FCFRT_ADMIN@163.COM
 * 修改简介：
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FcfrtNetSubscribe {
    Mode mode() default Mode.AUTO;
}
