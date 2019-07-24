package com.thirdi.pms.configuration;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
@ComponentScan(basePackages = "com.thirdi")
public class ApplicationConfig {

	@Bean
    public JavaMailSender getMailSender() {

		BufferedInputStream bufferedStream = new BufferedInputStream(ApplicationConfig.class.getResourceAsStream("/email_configuration.properties"));
		Properties properties = new Properties();
		try {
			properties.load(bufferedStream);
			Enumeration<Object> enuKeys = properties.keys();
			bufferedStream.close();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				String value = properties.getProperty(key);
				properties.put(key, value);
			}
	        
	        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	        mailSender.setHost(properties.getProperty("mail.smtp.host"));
	        mailSender.setPort(Integer.parseInt(properties.getProperty("mail.smtp.port")));
	        mailSender.setUsername(properties.getProperty("mail.user"));
	        mailSender.setPassword(properties.getProperty("mail.password"));
	        mailSender.setJavaMailProperties(properties);
	        return mailSender;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
 
	@Bean
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
        FreeMarkerConfigurationFactoryBean fmConfigFactoryBean = new FreeMarkerConfigurationFactoryBean();
        fmConfigFactoryBean.setTemplateLoaderPath("/template/");
        return fmConfigFactoryBean;
    }
}
