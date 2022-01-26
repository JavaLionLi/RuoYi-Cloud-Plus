package com.ruoyi.system.mq.producer;

import com.ruoyi.system.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class DelayProducer {

	@Autowired
	private StreamBridge streamBridge;

	public void sendMsg(Long delay) {
		SysUser user = new SysUser();
        user.setUserName("测试");
        user.setNickName("000000");
		Message<SysUser> message = MessageBuilder.withPayload(user)
			.setHeader("x-delay", delay).build();
        streamBridge.send("delay-out-0", message);
	}
}
