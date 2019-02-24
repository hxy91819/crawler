package bwjava.config;

import bwjava.util.SnowFlake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通用配置
 *
 * @author chenjing
 * @date 2019-02-24 10:09
 */
@Configuration
public class CommonConfig {

    @Bean
    public SnowFlake initSnowFlake() {
        return new SnowFlake(0, 0);
    }
}
