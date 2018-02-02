package com.lotte.otp;

import com.lotte.otp.domain.ChatBotSession;
import com.lotte.otp.domain.ChatBotStep;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OtpApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OtpApplicationTests {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ChatBotSession chatBotSession;

	@Test
	public void contextLoads() {
		logger.info(String.valueOf(chatBotSession.getHttpSession("user")));
		chatBotSession.setHttpSession("user", ChatBotStep.NO_BASE);
		logger.info(String.valueOf(chatBotSession.getHttpSession("user")));
	}

}
