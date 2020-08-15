package com.example.algamoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    // configurando acesso do(s) cliente(s) ao servidor de autenticação
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("angular") // nome do cliente
                .secret("$2a$10$n3SMkZmWmEBgbSn1O88hh.nTslaeO/HvkK23FvKIbqfUYOlC1Jv.m") // senha de acesso cliente
                .scopes("read", "write") // escopo das permissões do clientes
                .authorizedGrantTypes("password", "refresh_token") // tipo de grand type do auth2 que será usado
                .accessTokenValiditySeconds(20)
                .refreshTokenValiditySeconds(3600 * 24);

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(this.tokenStore()) // onde será armazenado o token retornado ao cliente
                .accessTokenConverter(this.accessTokenConverter())
                .reuseRefreshTokens(false)
                .userDetailsService(this.userDetailsService)
                .authenticationManager(this.authenticationManager);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("algaworks");
        return accessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(this.accessTokenConverter());
    }

}
