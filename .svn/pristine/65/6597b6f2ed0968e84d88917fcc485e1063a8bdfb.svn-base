package com.wasion.meterreading.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wasion.meterreading.entity.IotUploadRecord;
import com.wasion.meterreading.orm.jpa.IotUploadRecordRepository;
import com.wasion.meterreading.util.RedisUtil;

/**
 * 日志记录线程
 * 
 * @author w24882
 * @date 2019年11月15日
 */
@Component
@ConditionalOnProperty(name = "task.iot.upload.recorder.enable", havingValue = "true")
public class IotUploadRecorder {

	private static ConcurrentLinkedQueue<IotUploadRecord> queue = new ConcurrentLinkedQueue<>();

	private IotUploadRecordRepository repository;
	private RedisUtil redisUtil;

	public IotUploadRecorder(RedisUtil redisUtil, IotUploadRecordRepository repository) {
		this.redisUtil = redisUtil;
		this.repository = repository;
	}
	
	public static void push(HttpServletRequest req, String requestBody) {
		
	}

	public static void push(IotUploadRecord value) {
		queue.add(value);
	}

	@Scheduled(cron = "0/2 * * * * *")
	public void scheduled() {
		if (queue.isEmpty()) {
			return;
		}

		List<IotUploadRecord> records = new ArrayList<>();
		records.addAll(queue);

		String key = "lastUploadRecord";
		redisUtil.lPushFront(key, records);
		redisUtil.trimList(key, 40);

		repository.saveAll(records);
		queue.removeAll(records);
	}
}
