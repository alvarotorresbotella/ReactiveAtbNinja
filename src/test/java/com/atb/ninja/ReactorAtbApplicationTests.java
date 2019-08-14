package com.atb.ninja;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReactorAtbApplication.class )
public class ReactorAtbApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void test()
	{
		ReactorAtbApplication.main(new String[] {"test.property"});
	}

}
