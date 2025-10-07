package com.hubpedro.bookstoreapi.annotations;

import com.hubpedro.bookstoreapi.config.TestSecurityConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
@WebMvcTest(
		excludeFilters = @ComponentScan.Filter(
				type = FilterType.REGEX,
				pattern = "com\\.hubpedro\\.bookstoreapi\\.security\\..*"
		)
)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityConfig.class)
public @interface ControllerTest {
	Class<?>[] controllers() default {};
}