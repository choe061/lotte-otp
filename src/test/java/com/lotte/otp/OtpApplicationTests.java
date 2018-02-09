package com.lotte.otp;

import com.lotte.otp.domain.*;
import com.lotte.otp.repository.UserConnectionHistoryMapper;
import com.lotte.otp.repository.UserConnectionQueueMapper;
import com.lotte.otp.repository.UserMapper;
import com.lotte.otp.service.UserService;
import com.lotte.otp.util.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OtpApplication.class)
public class OtpApplicationTests {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserConnectionQueueMapper userConnectionQueueMapper;
	@Autowired
	private UserConnectionHistoryMapper userConnectionHistoryMapper;

	@Test
	public void contextLoads() {

	}

	@Test
	public void testTime() {
		String str = DateUtils.now();
		logger.info("Time => " + str);
		logger.info("long Time => " + DateUtils.convertStrToLongDate(str));
	}

	@Test
	public void tokenizeText() {
		String text = "choe061/123123";
		String[] keys = text.split("/");
		logger.info(String.valueOf(keys[0]) + " " + keys[1]);
	}

	@Test
	public void getAllConnectionHistoryWithId() {
		ArrayList<UserConnectionHistoryVO> history = userConnectionHistoryMapper.getAllConnectionHistoryWithId("choe061");
		logger.info(String.valueOf(history));
	}

	@Test
	public void serviceAllConnectionHistoryWithId() {
		ArrayList<UserConnectionHistoryVO> serviceHistory = userService.getAllConnectionHistoryWithId("choe061");
		ArrayList<UserConnectionHistoryVO> history = userConnectionHistoryMapper.getAllConnectionHistoryWithId("choe061");
		logger.info("Service => " + serviceHistory);
		logger.info(String.valueOf(history));
	}
}
