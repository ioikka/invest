package io.ikka.invest.startup;

import io.ikka.invest.dto.Analytics;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.beans.BeanInfo;
import java.beans.Introspector;

@Component
public class MyCLR2 implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(Analytics.class);
        beanInfo = null;
    }
}
