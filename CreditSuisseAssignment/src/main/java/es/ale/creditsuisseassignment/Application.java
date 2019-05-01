package es.ale.creditsuisseassignment;

import java.io.File;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import es.ale.creditsuisseassignment.controller.JSONProcessor;

@SpringBootApplication
@EnableAsync
public class Application {
	
	
	/** Logger of the app **/
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	/** Entry point of the app 
	 ** It should receive a JSON file path **/
	public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
	}
	
	
	@Bean
	public CommandLineRunner demo(JSONProcessor jsonProcessor) {
		return (args) -> {
			log.info("Starting process");
			
			// Validating argument (JSON file)
			if (args[0] != null && new File(args[0]).exists()) {
				log.info("JSON contained in "+args[0]+" will be processed");
				jsonProcessor.process(args[0]); 
			}
			else {
				log.error("You should provide an existing JSON file path as app argument");
			}
		};
	}
	
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("CreditSuisseAssignment -");
        executor.initialize();
        return executor;
    }



}
