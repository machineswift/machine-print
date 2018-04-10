package com.machine.print.template;

import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;

import com.machine.print.config.PrintConfig;
import com.machine.print.envm.PrintExceptions;
import com.machine.print.exception.impl.PrintGeneralException;

/**
 * @Description: Beetl模板引擎工具类
 *
 * @author machine
 * @date 2017年12月20日 下午1:18:22
*/
public class BeetlTemplateUtils {

	
	public static String getBindedResult(String templeteName,String modelName, Object messageModel) {
		Template template = getTemplate(templeteName);
		template.binding(modelName, messageModel);
		return template.render();
	}
	
	private static Template getTemplate(String templateName) {
		Configuration cfg = null;
		String root = PrintConfig.PTINT_PARA+"template";
		FileResourceLoader resourceLoader = new FileResourceLoader(root,"UTF-8");
		try {
			cfg = Configuration.defaultConfiguration();
		} catch (IOException e) {
			throw new PrintGeneralException(PrintExceptions.getValueByKey("PRINT_3004") + e.getMessage());
		}
		return new GroupTemplate(resourceLoader, cfg).getTemplate(templateName);
	}

}
