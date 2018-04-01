package com.anrong.user.controller;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RunWith(value = SpringRunner.class)
//@SpringBootTest
public class MyTest {
	@Test
	public void genMybatisHelperClass() {
		List<String> warnings = new ArrayList<String>();
		File configFile = new File("/Users/liudh/project/user/src/main/resources/mbg_cfg.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);

		try {
			Configuration config = cp.parseConfiguration(configFile);
			DefaultShellCallback callback = new DefaultShellCallback(true);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);

		} catch (Exception error) {
			error.printStackTrace();
		}

		for (String string : warnings) {
			System.out.println(string);
		}

		System.out.println("over.");
	}

	@Test
	public void test2() {
		Map<String, String> out = new HashMap<String, String>();
		out.put("touser", "oFT0JwlV4WcKKVgYI-E5xrm92LK0");
		out.put("template_id", "G4L_Ey1Fqn2P5aasybYhFpz2AttI2Jhp2sgj6bpR8Gs");
		out.put("url", "http://www.circ.gov.cn/web/site0/");
		out.put("topcolor", "#ff000");
		out.put("first", "1");
		out.put("keyword1", "2");
		out.put("keyword2", "3");
		out.put("keyword3", "4");
		out.put("keyword4", "5");
		out.put("keyword5", "6");
		out.put("remark", "7");

		System.out.println(JSON.toJSONString(out));
	}

}
