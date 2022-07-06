package io.timeline.junitTest.result;

import io.timeline.junitTest.result.TheJunit4Listener;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.runner.notification.RunNotifier;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.junit.runner.notification.RunNotifier;

@Aspect
public class TheJunit4ListenerAspect {

    private final TheJunit4Listener CustomTheJunit4Listener = new TheJunit4Listener();

    @After("execution(org.junit.runner.notification.RunNotifier.new())")
    public void addListener(final JoinPoint point) {
        final RunNotifier notifier = (RunNotifier) point.getThis();
        notifier.removeListener(CustomTheJunit4Listener);
        notifier.addListener(CustomTheJunit4Listener);
    }


}
