package com.lotte.otp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Rollback
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class OtpApplicationTests {



	@Test
	public void contextLoads() {
	}

}
