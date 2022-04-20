package com.ruoyi.system.mq.consumer;

import com.ruoyi.system.mq.TestMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class LogStreamConsumer {

    @Bean
    Consumer<TestMessaging> log() {
        log.error("初始化订阅");
        return msg -> {
            log.error("通过stream消费到消息 => {}", msg.toString());
        };
    }

}
