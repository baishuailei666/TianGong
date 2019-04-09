package com.xlauncher.ics;

import com.xlauncher.ics.util.PropertiesUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IcsApplicationTests {
	@Autowired
	PropertiesUtil propertiesUtil;
	@Test
	public void contextLoads() {
	}

}

