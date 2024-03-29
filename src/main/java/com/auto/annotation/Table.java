package com.auto.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 自定义表注解
 * @author chenfuqiang
 *
 */
//表示注解加在接口、类、枚举等
@Target(ElementType.TYPE)
//VM将在运行期也保留注释，因此可以通过反射机制读取注解的信息
@Retention(RetentionPolicy.RUNTIME)
//将此注解包含在javadoc中
@Documented
//允许子类继承父类中的注解
@Inherited
public @interface Table {
	/**
	 * 默认值当前类名
	 * 也可以指定个性化表名，提供参数如下：
	 * @year : 传递当前年份
	 * @month: 传递当前月份
	 * @return 表名
	 */
	public abstract String name() default "";
	/**
	 * 默认 false
	 * 是否包含父类只支持一次继承
	 * @return
	 */
	public abstract boolean includeSupperClass() default false;
	
	/**
	 * 默认值 “”
	 * 设定js panel title
	 * @return
	 */
	public abstract String jsname() default "";
}
