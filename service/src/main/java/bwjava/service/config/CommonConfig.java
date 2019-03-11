package bwjava.service.config;

import bwjava.util.SnowFlake;
import com.bwjava.util.ExecutorServiceUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.ExecutorService;

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
        return ExecutorServiceUtil.getInstance();
    }

    /**
     * for cros
     *
     * @return
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
}
