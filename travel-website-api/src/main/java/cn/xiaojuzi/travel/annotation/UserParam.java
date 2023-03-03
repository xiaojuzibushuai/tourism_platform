package cn.xiaojuzi.travel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 *
 * 区分解析器参数注解
 * 1>如果接口方法声明的UserInfo类型参数，并且贴了这个注解， 表示使用自定义参数解析器进行参数解析
 * author:xiaojuzi
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserParam {
}
