package com.wasion.meterreading.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wasion.meterreading.annotation.IpAccessLimit;
import com.wasion.meterreading.entity.IotUploadRecord;
import com.wasion.meterreading.exception.MRException;
import com.wasion.meterreading.exception.MRExceptionEnum;
import com.wasion.meterreading.orm.jpa.IotUploadRecordRepository;
import com.wasion.meterreading.util.RedisUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName RedisController
 * @Description
 * @Author w24882
 * @Date 2020/1/9 9:37
 * @Version V1.0
 **/
@Slf4j
@RequestMapping("/redis")
@RestController
public class RedisController {

    @Autowired
    IotUploadRecordRepository repo;

    @Resource
    private RedisUtil redisUtil;

    @PostMapping("value")
    public boolean redisSet(String key, String value) {
        return redisUtil.set(key, value);
    }

    @GetMapping("value")
    public Object redisGet(String key) {
        return redisUtil.get(key);
    }

    @PutMapping("value")
    public boolean redisExpire(String key) {
        // redis中存储的过期时间60s
        int expireTime = 60;
        return redisUtil.expire(key, expireTime);
    }

    @IpAccessLimit
    @GetMapping("list")
    public Object redisListGet(HttpServletRequest req, String key) {
        if (!"lastUploadRecord".equals(key)) {
            throw new MRException(MRExceptionEnum.NotSupportedCacheKey);
        }
        String remoteHost = req.getRemoteHost();
        log.info("Host: {} query redis cache of key: {}.", remoteHost, key);
        final long lGetListSize = redisUtil.lGetListSize(key);
        if (lGetListSize < 40) {
            Page<IotUploadRecord> findAll = repo
                    .findAll(PageRequest.of(0, 40, new Sort(Direction.DESC, "requestDate")));
            redisUtil.del(key);
            redisUtil.lPushFront(key, findAll.getContent());
            redisUtil.trimList(key, 40);
        }
        return redisUtil.rangeList(key, 0, lGetListSize);
    }

}