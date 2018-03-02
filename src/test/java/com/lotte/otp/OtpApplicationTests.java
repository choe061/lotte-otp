package com.lotte.otp;

import com.lotte.otp.domain.UserConnectionHistoryVO;
import com.lotte.otp.repository.UserConnectionHistoryMapper;
import com.lotte.otp.repository.UserMapper;
import com.lotte.otp.service.UserService;
import com.lotte.otp.service.UserServiceImpl;
import com.lotte.otp.util.DateUtils;
import com.lotte.otp.util.OTP;
import com.lotte.otp.util.SecurityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
	private UserConnectionHistoryMapper userConnectionHistoryMapper;

	@Test
	public void contextLoads() {
		int tempKey = SecurityUtils.distributeTempKey();
		logger.info("tempKey => " + tempKey);
		tempKey = SecurityUtils.distributeTempKey();
		logger.info("tempKey => " + tempKey);
		tempKey = SecurityUtils.distributeTempKey();
		logger.info("tempKey => " + tempKey);
		tempKey = SecurityUtils.distributeTempKey();
		logger.info("tempKey => " + tempKey);
		tempKey = SecurityUtils.distributeTempKey();
		logger.info("tempKey => " + tempKey);
		tempKey = SecurityUtils.distributeTempKey();
		logger.info("tempKey => " + tempKey);
		tempKey = SecurityUtils.distributeTempKey();
		logger.info("tempKey => " + tempKey);
	}

	@Test
	public void dateTest() {
		Calendar calendar = Calendar.getInstance(Locale.KOREA);
		logger.info(String.valueOf(DateUtils.now()));
		long time = DateUtils.convertStringDateToLongDate(DateUtils.now());
		long l = calendar.getTimeInMillis();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		String d = dateFormat.format(new Date(l));
		logger.info("l : " + d);
		logger.info("t : " + time);
	}
}
