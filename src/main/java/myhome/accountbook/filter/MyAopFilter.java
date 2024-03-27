package myhome.accountbook.filter;

import lombok.extern.slf4j.Slf4j;
import myhome.accountbook.util.AuthUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class MyAopFilter {

    @Before("execution(public * myhome.accountbook.presentation.*Controller.*(..)) && !@annotation(myhome.accountbook.filter.AuthFree)")
    public void checkAuth(JoinPoint joinPoint) {
        boolean isAuth = AuthUtils.checkAuth();

        if (!isAuth) {
            throw new RuntimeException("인증이 없습니다.");
        }
    }
}
