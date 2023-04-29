package com.example.project.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

@Configuration
class OAuth2ServerConfiguration {
    @Configuration
    @EnableResourceServer
    protected class ResourceServerConfiguration(private val jwtAccessTokenConverter: JwtAccessTokenConverter) :
        ResourceServerConfigurerAdapter() {

        override fun configure(resource: ResourceServerSecurityConfigurer) {
            resource.tokenStore(JwtTokenStore(jwtAccessTokenConverter))
        }

        @Throws(Exception::class)
        override fun configure(http: HttpSecurity) {
            http.csrf().disable().authorizeRequests().anyRequest().authenticated()
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected class AuthorizationServerConfiguration(
        private val jwtAccessTokenConverter: JwtAccessTokenConverter,
        private val passwordEncoder: BCryptPasswordEncoder,
        private val authenticationManager: AuthenticationManager,
        private val userDetailsService: UserDetailsService,
    ) : AuthorizationServerConfigurerAdapter() {

        @Throws(Exception::class)
        override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
            endpoints.tokenStore(JwtTokenStore(jwtAccessTokenConverter))
                .authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter)
                .userDetailsService(userDetailsService)
        }

        @Throws(Exception::class)
        override fun configure(clients: ClientDetailsServiceConfigurer) {
            clients.inMemory()
                .withClient("Vattana")
                .secret(passwordEncoder.encode("Seyy"))
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read", "write", "delete", "update")
                .accessTokenValiditySeconds(36000)
        }
    }
}