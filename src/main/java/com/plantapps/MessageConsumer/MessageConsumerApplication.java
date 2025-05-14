package com.plantapps.MessageConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessageConsumerApplication {

//	@Autowired
//	private CustomListner custistener;
	
	public static void main(String[] args) {
		SpringApplication.run(MessageConsumerApplication.class, args);
	}
	
//	@PostConstruct
//    public void init() {
//        List<String> tenantIds = List.of("1", "2"); // or load from DB
//        tenantIds.forEach(custistener::registerTenantListener);
//    }

}
