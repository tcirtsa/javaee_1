package cn.project.jtee.annotation;

import java.lang.annotation.*;

/**
 * @Author yiren
 * @Date 2024/5/28
 * @Description Annotation that is used to log the importance in method
 *              用法(在方法上面打上该注解): @LogAnnotation("message you want to write")
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    // 写log的内容
    String value();
    // 日志级别
    String level() default "info";
    // temporarily (no sense)
    String remark() default "";
}
