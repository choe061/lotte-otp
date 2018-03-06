package com.lotte.otp;

import com.lotte.otp.service.UserService;
import com.lotte.otp.util.DateUtils;
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
import java.util.Calendar;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OtpApplication.class)
public abstract class OtpApplicationTests {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;

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

}
