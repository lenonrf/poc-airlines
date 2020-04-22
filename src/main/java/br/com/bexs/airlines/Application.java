package br.com.bexs.airlines;

import br.com.bexs.airlines.batch.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class Application{

	public static void main(String[] args) throws Exception{

		ConfigurableApplicationContext context =
				SpringApplication.run(Application.class, args);

		context.getBean(Main.class).init(args);
	}
}
