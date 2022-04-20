package com.ruoyi.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.mq.producer.DelayProducer;
import com.ruoyi.system.mq.producer.LogStreamProducer;
import com.ruoyi.system.mq.producer.TestStreamProducer;
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
    private final TestStreamProducer testStreamProducer;
    private final LogStreamProducer logStreamProducer;

	@GetMapping("/sendRabbitmq")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "发送消息Rabbitmq", notes = "发送消息")
	public R<Void> sendRabbitmq(Long delay) {
		delayProducer.sendMsg(delay);
		return R.ok();
	}

    @GetMapping("/sendRocketmq")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "发送消息Rocketmq", notes = "发送消息")
    public R<Void> sendRocketmq(String msg) {
        testStreamProducer.streamTestMsg(msg);
        return R.ok();
    }

    @GetMapping("/sendKafka")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "发送消息Kafka", notes = "发送消息")
    public R<Void> sendKafka(String msg) {
        logStreamProducer.streamLogMsg(msg);
        return R.ok();
    }

}
