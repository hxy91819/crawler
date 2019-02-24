package bwjava.service.config;

import bwjava.util.SnowFlake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chenjing
 * @date 2019-02-24 14:31
 */
@Configuration
public class CommonConfig {

    /**
     * snow flake id 生成器
     *
     * @return
     */
    @Bean
    public SnowFlake snowFlake() {
        return new SnowFlake(0, 0);
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(10);
    }
}
