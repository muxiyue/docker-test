//package docker.test;
//
//import org.springframework.boot.web.servlet.MultipartConfigFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.MultipartConfigElement;
//
//@Configuration
//public class UploadConfiguration {
//
//
//	/**
//	 * 文件上传配置
//	 * @return
//	 */
//	@Bean
//	public MultipartConfigElement multipartConfigElement() {
//		MultipartConfigFactory factory = new MultipartConfigFactory();
//		//文件最大
//		factory.setMaxFileSize("10240KB"); //KB,MB
//		/// 设置总上传数据总大小
//		factory.setMaxRequestSize("102400KB");
//		return factory.createMultipartConfig();
//	}
//
//
//}
