package com.example.project.security

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AccountAuthenticationProvider(
    private val userDetailsService: UserDetailsService,
    private val passwordEncoder: PasswordEncoder,
) : AbstractUserDetailsAuthenticationProvider() {

    @Throws(AuthenticationException::class)
    override fun additionalAuthenticationChecks(userDetails: UserDetails, token: UsernamePasswordAuthenticationToken) {
        if (token.credentials == null || userDetails.password == null) {
            throw BadCredentialsException("Credentials may not be null.")
        }
        if (!passwordEncoder.matches(token.credentials as String, userDetails.password)) {
            throw BadCredentialsException("Invalid credentials.")
        }
    }

    @Throws(AuthenticationException::class)
    override fun retrieveUser(username: String, authentication: UsernamePasswordAuthenticationToken): UserDetails {
        return userDetailsService.loadUserByUsername(username)
            ?: throw UsernameNotFoundException("User $username not found.")
    }
}