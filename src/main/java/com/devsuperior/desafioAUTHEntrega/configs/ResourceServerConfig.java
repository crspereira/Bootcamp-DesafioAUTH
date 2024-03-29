package com.devsuperior.desafioAUTHEntrega.configs;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer //Classe para configuração do SpringCloud Oauth 2
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	// properties
	@Value("${cors.origins}")
	private String corsOrigins;

	// dependencias
	@Autowired
	private JwtTokenStore tokenStore;
	@Autowired // libera o banco H2
	private Environment env;

	// contantes de permissionamento
	private static final String[] PUBLIC = { "/oauth/token", "/h2-console/**", "/v2/api-docs", "/swagger-ui.html",
			 								 "/swagger-resources/**", "/configuration/**", "/webjars/**" };
//	private static final String[] ADMIN = { "/users/**" };

	// metodos
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {

		// Libera o Banco H2
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers(headers -> headers.frameOptions().disable());
		}

        http.authorizeRequests(requests -> requests
        		.antMatchers(PUBLIC).permitAll()
        		//utilizar quando Classe WebSecurityConfig estiver sem anotation @EnableGlobalMethodSecurity
//        		.antMatchers(ADMIN).hasAnyRole("ADMIN") 
        		.anyRequest().authenticated());

        // Libera o CORES
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
	}

	// Config Liberação do CORS
	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		String[] origins = corsOrigins.split(",");

		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOriginPatterns(Arrays.asList(origins));
		corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "PATCH"));
		corsConfig.setAllowCredentials(true);
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}

	@Bean
	FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
				new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
	
}