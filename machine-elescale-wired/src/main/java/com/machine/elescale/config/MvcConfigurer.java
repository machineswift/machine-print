package com.machine.elescale.config;

import org.springframework.core.Ordered;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description: MvcConfigurer
 *
 * @author machine
 * @date 2017年12月20日 下午1:09:38
*/
@Configuration
public class MvcConfigurer extends WebMvcConfigurerAdapter{
    /**
     * @Description: 设置服务的首页
     *
     * @author machine
     * @date 2017年12月20日 下午1:10:59
    */
    @Override
    public void addViewControllers( ViewControllerRegistry registry ) {
        registry.addViewController( "/" ).setViewName( "forward:/index.html" );
        registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
        super.addViewControllers( registry );
    } 
}