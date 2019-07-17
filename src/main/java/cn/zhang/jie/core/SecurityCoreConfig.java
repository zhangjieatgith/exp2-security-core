package cn.zhang.jie.core;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import cn.zhang.jie.core.properties.SercurityProperties;

@Configuration
@EnableConfigurationProperties(SercurityProperties.class)
public class SecurityCoreConfig {
	
}
