package com.slow.book.springboot.web;

import com.slow.book.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
// 1-1. 테스트를 진행할 때 JUnit 에 내장된 실행자 외에 다른 실행자를 실행시킨다. 여기서는 SpringRunner 라는 스프링 실행자를 사용한다.
// @RunWith(SpringRunner.class) // 1-2. 즉, 스프링 부트 테스트와 Junit 사이에 연결자 역할을 한다.
// @WebMvcTest(controllers = HelloController.class) // 2-1. Web(SpringMVC)에 집중할 수 있는 어노테이션, @Controller, @ControllerAdvice 등을 사용할 수 있음.
public class HelloControllerTest {               // 2-2. 단, @Service, @Component, @Repository 등은 사용할 수 없음.

    @Autowired // 3. 스프링이 관리하는 빈(Bean)을 주입 받는다.
    private MockMvc mvc; // 4. 웹 API 를 테스트할 때 사용한다. 스프링 MVC 테스트의 시작점이다. 이 클래스를 통해 HTTP GET, POST 등에 대한 API 테스트를 할 수 있다.

    @WithMockUser(roles="USER")
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) // 5. MockMvc 를 통해 /hello 주소로 HTTP GET 요청을 한다. 체이닝이 지원되어 아래와 같이 여러 검증 기능을 이어서 선언 할 수 있음.
                .andExpect(status().isOk())      // 6. mvc.perform 의 결과를 검증. HTTP Header 의 Status 를 검증. 200,400,500 등의 상태를 검증. 여기선 200 = OK 상태를 검증함.
                .andExpect(content().string(hello)); // 7. mvc.perform 의 결과를 검증. 응답 본문의 내용을 검증함. Controller 에서 "hello" 를 리턴하기 때문에 이 값을 검증함.
    }

    @WithMockUser(roles="USER")
    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name",name) // 1-1. API 테스트할 때 사용될 요청 파라미터를 설정한다.
                        .param("amount",String.valueOf(amount))) // 1-2. 값은 String 만 허용됨, 숫자/날짜 등의 데이터도 등록할 때는 문자열로 변경해야만 가능함.
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) // 2. JSON 응답값을 필드별로 검증할 수 있는 메소드, $를 기준으로 필드명을 명시함
                .andExpect(jsonPath("$.amount", is(amount)));
    }

}
