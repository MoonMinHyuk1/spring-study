package spring.aop.member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) //클래스에 적용
@Retention(RetentionPolicy.RUNTIME) //실제 실행까지 살아있음
public @interface ClassAop {
}
