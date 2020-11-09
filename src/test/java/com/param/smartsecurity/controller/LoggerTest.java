package com.param.smartsecurity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerTest {
    private static final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    public static void main(String[] args) {
        logger.info("致命错误");
	logger.error("严重警告");
	logger.info("普通信息");
	logger.debug("调试信息");
    }
}