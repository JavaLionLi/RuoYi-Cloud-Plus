package com.ruoyi.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.mq.producer.DelayProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/test-mq")
@Api(value = "测试mq", tags = "测试mq")
public class TestMqController {

	private final DelayProducer delayProducer;

	@GetMapping("/send")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "发送消息", notes = "发送消息")
	public R<Void> send(Long delay) {
		delayProducer.sendMsg(delay);
		return R.ok();
	}

}
