package my.work.countrybyphone.app;

import my.work.countrybyphone.business.config.BusinessConfig;
import my.work.countrybyphone.restapi.config.RestApiConfig;
import my.work.countrybyphone.webui.config.WebUiConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({BusinessConfig.class,
	RestApiConfig.class,
	WebUiConfig.class
})
public class CountryByPhoneApp {
	public static void main(String[] args) {
		SpringApplication.run(CountryByPhoneApp.class, args);
	}
}
