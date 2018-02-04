package com.lotte.otp;

import com.lotte.otp.domain.ChatBotSession;
import com.lotte.otp.domain.ChatBotStep;
import com.lotte.otp.domain.UserConnectionQueueVO;
import com.lotte.otp.domain.UserConnectionStatus;
import com.lotte.otp.repository.UserConnectionQueueMapper;
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
import java.util.Date;
import java.util.Locale;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OtpApplication.class)
public class OtpApplicationTests {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserConnectionQueueMapper userConnectionQueueMapper;
	@Autowired
	private ChatBotSession chatBotSession;

	@Test
	public void contextLoads() {
		logger.info(String.valueOf(chatBotSession.getHttpSession("user")));
		chatBotSession.setHttpSession("user", ChatBotStep.NO_BASE);
		logger.info(String.valueOf(chatBotSession.getHttpSession("user")));
	}

	@Test
	public void printDateTime() {
		logger.info("TIME => " + DateUtils.now());
		UserConnectionQueueVO userConnection = userConnectionQueueMapper.getUserConnection("choe061");
		logger.info("UserConnection => " + userConnection);
		logger.info("UserConnection Time => " + userConnection.getPublished_at());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		Date date = null;
		Date now = null;
		try {
			date = dateFormat.parse(userConnection.getPublished_at());
			now = dateFormat.parse(dateFormat.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long utc = System.currentTimeMillis();
		long publishTime = date.getTime();
		long requestTime = now.getTime();
		logger.info("현재 시간 => " + now + ", 발급 시간 => " + date);
		logger.info("키 시간 차이 => " + (utc - publishTime));
	}

	@Test
	public void testTime() {
		String str = DateUtils.now();
		logger.info("Time => " + str);
		logger.info("long Time => " + DateUtils.convertStrToLongDate(str));
	}
}
