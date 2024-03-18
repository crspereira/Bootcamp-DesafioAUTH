package com.devsuperior.desafioAUTHEntrega.configs;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.devsuperior.desafioAUTHEntrega.components.JwtTokenEnhancer;

@Configuration
@EnableAuthorizationServer //Classe para configuração do SpringCloud Oauth 2
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	// properties externalzation
	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;
	@Value("${jwt.duration}")
	private Integer jwtDuration;

	// dependencias
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;
	@Autowired
	private JwtTokenStore tokenStore;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenEnhancer tokenEnhancer;
	@Autowired
	private UserDetailsService userDetailsService;

	// métodos
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override //configuraçao de usuário e senha da aplicação
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			   .withClient(clientId) //usuário
			   .secret(passwordEncoder.encode(clientSecret)) //senha
			   .scopes("read", "write")
			   .authorizedGrantTypes("password", "refresh_token") //metodos de autorização
			   .accessTokenValiditySeconds(jwtDuration)
			   .refreshTokenValiditySeconds(jwtDuration);
	}

	@Override //configuracao de como se dará a segurança, no caso um token JWT
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		TokenEnhancerChain chain = new TokenEnhancerChain();
		chain.setTokenEnhancers(Arrays.asList(accessTokenConverter, tokenEnhancer));

		endpoints.authenticationManager(authenticationManager)
				 .tokenStore(tokenStore)
				 .accessTokenConverter(accessTokenConverter)
				 .tokenEnhancer(chain)
				 .userDetailsService(userDetailsService);
	}
}
