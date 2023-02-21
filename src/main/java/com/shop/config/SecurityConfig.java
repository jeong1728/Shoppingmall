package com.shop.config;


import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration //설정 임폴트가 안나올때 해결법
@EnableWebSecurity
//WebSecurityConfigurerAdapter 상속 받는 클래스에 @EnableWebSecurity 선언을 하면
//SpringSecurityFilterChain이 자동 포함 메소드를 오버라이딩 할 수 있습니다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    MemberService memberService;


    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.formLogin()
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/members/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/");

        http.authorizeRequests() //인증요청이오면
                        .mvcMatchers("/", "/members/**", "/item/**","/images/**").permitAll() //안에것은 모두가능
                        .mvcMatchers("/admin/**").hasRole("ADMIN") //admin만 가능
                        .anyRequest().authenticated() //이외의 것은 로그인필요
                        .and()
                        .oauth2Login()
                        .defaultSuccessUrl("/")
                        .userInfoEndpoint()
                        .userService(customOAuth2UserService);

        http.exceptionHandling() //예외처리
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()); //만들어논 커스텀~로 예외처르리를 할것이다.


}
    @Bean //원두 -> 객체 빈객체 -> SpringContainer 들어갑니다. 이 객체 하나로 돌려쓴다.(싱글톤)
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //비밀번호를 암호화 하는 해시함수 패스워드를 암오화할때 이것을 돌려쓴다.
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web)throws Exception{
        web.ignoring().antMatchers("/css/**", "/js/**","/img/**"); // 뒤에있는것을 메치하는것은 제외한다 너네마음대로해라 폴더안에있는 하위의 모든파일은 인증을 무시한다.
    }
}
