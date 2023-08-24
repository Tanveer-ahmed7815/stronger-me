package com.tyss.strongameapp;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tyss.strongameapp.util.S3UploadFile;
import com.tyss.strongameapp.util.SSSUploadFile;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEncryptableProperties
@EnableSwagger2
public class StrongameappApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrongameappApplication.class, args);
	}

	@Bean
	public S3UploadFile amazonS3ObjectFile() {
		return new S3UploadFile();
	}

	@Bean
	public SSSUploadFile amazonS3Object() {
		return new SSSUploadFile();
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	@Bean
	FirebaseMessaging firebaseMessaging() throws IOException {
		GoogleCredentials googleCredentials = GoogleCredentials.fromStream(
				new ClassPathResource("strongerme-20bc3-firebase-adminsdk-3fxmh-ce7c840d42.json").getInputStream());
		FirebaseOptions firebaseOptions = FirebaseOptions.builder().setCredentials(googleCredentials).build();
		List<FirebaseApp> apps = FirebaseApp.getApps();
		for (FirebaseApp firebaseApp : apps) {
			if (firebaseApp.getName().equalsIgnoreCase("StrongerMe")) {
				return FirebaseMessaging.getInstance(firebaseApp);
			}
		}
		FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "StrongerMe");
		return FirebaseMessaging.getInstance(app);
	}
}
