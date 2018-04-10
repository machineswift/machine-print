package com.machine.print.config;

import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 启动时读取配置文件
 *
 * @author machine
 * @date 2017年12月19日 下午1:03:19
*/
@Configuration
public class ReadFileOnStartUp {

    @PostConstruct
    public void afterPropertiesSet() throws Exception {

        
    	StringBuilder sbReturn = new StringBuilder("-----------------------------------------------------------------");
//        BufferedReader br = new BufferedReader(
//				new InputStreamReader(new ClassPathResource("ValidationMessages.properties").getInputStream()));
//
//		String strLine = null;
//		while ((strLine = br.readLine()) != null) {
//			sbReturn.append(strLine).append(PrintCommonParam.LINE_SEPRATER);
//		}
//		br.close();
		System.out.println(sbReturn);
    }
}