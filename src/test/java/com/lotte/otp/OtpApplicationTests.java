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
	public void testTime() {
		String str = DateUtils.now();
		logger.info("Time => " + str);
		logger.info("long Time => " + DateUtils.convertStringDateToLongDate(str));
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

	@Test
	public void history() {
		UserConnectionHistoryVO history = userConnectionHistoryMapper.getConnectionHistory("dYq1wL6LmnnR");
		logger.info(DateUtils.splitTime(history.getAccessed_date()));
		String text = "[최근 로그인 내역]";
		text += "\n일시 : " + history.getAccessed_date();
		text += "\n접속 환경 : " + history.getOs() + " " + history.getBrowser();
		text += "\nIP : " + history.getIp();
		logger.info(text);
	}

	@Test
	public void otp() {
		long time = DateUtils.convertStringDateToLongDate(DateUtils.now());
		String otp1 = OTP.create(time, "abcdefg123456");
		String otp2 = OTP.create(time, "abcdefg123456");
		String otp3 = OTP.create(time, "123456abcdefg");
		logger.info("OTP1 => " + otp1);
		logger.info("OTP2 => " + otp2);
		logger.info("OTP3 => " + otp3);
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
