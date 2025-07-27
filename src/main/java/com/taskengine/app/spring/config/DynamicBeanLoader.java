package com.taskengine.app.spring.config;

import com.taskengine.app.core.service.NodeHandler;
import com.taskengine.app.parser.converter.Converter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.beans.Introspector;

@Configuration
public class DynamicBeanLoader implements BeanDefinitionRegistryPostProcessor {

    private static final String BASE_PACKAGE = "com.taskengine.app";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AssignableTypeFilter(Converter.class));
        scanner.addIncludeFilter(new AssignableTypeFilter(NodeHandler.class));

        for (BeanDefinition bd : scanner.findCandidateComponents(BASE_PACKAGE)) {
            try {
                Class<?> clazz = Class.forName(bd.getBeanClassName());
                AbstractBeanDefinition abd = BeanDefinitionBuilder.genericBeanDefinition(clazz).getBeanDefinition();
                String beanName = Introspector.decapitalize(clazz.getSimpleName());

                registry.registerBeanDefinition(beanName, abd);

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }
}