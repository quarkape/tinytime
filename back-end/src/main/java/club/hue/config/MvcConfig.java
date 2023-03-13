package club.hue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

// 设置拦截器后的放行配置文件
// 必须添加@Configuration注解
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

    // 必须要自定义资源文件路径
    // 配置虚拟路径
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 加分图片，上传的学生名单、上传的成绩表格，上传的加分文件，导出的评价结果等静态资源目录
        // registry.addResourceHandler("/files/**").addResourceLocations("/www/apps/cqes4cs/files/");
        registry.addResourceHandler("/srcs/**").addResourceLocations("file:G:/Projects/tinytime/resources/");
        super.addResourceHandlers(registry);
    }
}
