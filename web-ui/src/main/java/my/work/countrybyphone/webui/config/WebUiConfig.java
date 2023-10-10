package my.work.countrybyphone.webui.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "my.work.countrybyphone.webui",
	excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class WebUiConfig {
}
