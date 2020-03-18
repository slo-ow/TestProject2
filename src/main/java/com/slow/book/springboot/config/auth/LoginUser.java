package com.slow.book.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 1. 이 어노테이션이 생성될 수 있는 위치를 지정함, PARAMETER 로 지정했으니 메소드의 파라미터로 선언된 객체에서만 사용할 수 있음
@Retention(RetentionPolicy.RUNTIME) // 2. 이 파일을 어노테이션 클래스로 지정함, LoginUser 라는 이름을 가진 어노테이션이 생성되었다고 보면 됨.
    public @interface LoginUser {

}