package com.ruoyi.system.mq.consumer;


import com.ruoyi.system.api.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class DelayConsumer {

	@Bean
    Consumer<SysUser> delay() {
        log.error("初始化订阅");
        return obj -> {
            log.error("消息接收成功：" + obj);
        };
	}
}
