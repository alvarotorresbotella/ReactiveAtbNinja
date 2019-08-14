package com.atb.ninja;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@SpringBootTest(classes = ReactorAtbApplication.class)
public class ReactorAtbApplicationTests {

	@Test
	@PrepareForTest(SpringApplication.class)
	 public void main() {
        mockStatic(SpringApplication.class);
        ReactorAtbApplication.main(new String[0]);
        verifyStatic(SpringApplication.class);
        SpringApplication.run(ReactorAtbApplication.class);
    }

}
