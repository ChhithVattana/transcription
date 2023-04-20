package com.example.project.security

import com.example.project.model.Account
import com.example.project.model.Privilege
import com.example.project.model.Role
import com.example.project.repository.AccountRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service
import javax.transaction.Transactional
import java.util.stream.Collectors.toList

@Service
@Transactional
class CustomUserDetailsService(private val accountRepository: AccountRepository) : UserDetailsService {

    private val passwordEncoder = BCryptPasswordEncoder()

    override fun loadUserByUsername(username: String): UserDetails {
        val list: List<Account> = accountRepository.findAll()
        var foundUsername = username
        for (userAccount in list) {
            if (passwordEncoder.matches(username, userAccount.username)) {
                foundUsername = userAccount.username.toString()
            }
        }
        val accountbyusername = accountRepository.findByUsername(foundUsername)
            .orElseThrow { UsernameNotFoundException("User $username not found.") }
        val account = accountbyusername
        if (account.roles == null || account.roles!!.isEmpty()) {
            throw UsernameNotFoundException("User not Authorize.")
        }
        return User(
            account.username, account.password, account.isEnabled!!, true, true, true,
            getAuthorities(account.roles!!)
        )
    }

    private fun getAuthorities(roles: Collection<Role>): Collection<GrantedAuthority> {
        return getGrantedAuthorities(getPrivileges(roles))
    }

    private fun getPrivileges(roles: Collection<Role>): List<String> {
        val privileges: List<Privilege> = roles.stream()
            .map(Role::privileges)
            .flatMap(Set<Privilege>::stream)
            .collect(toList())
        return privileges.stream()
            .map(Privilege::name)
            .collect(toList())
    }

    private fun getGrantedAuthorities(privileges: List<String>): List<GrantedAuthority> {
        return privileges.stream()
            .map(::SimpleGrantedAuthority)
            .collect(toList())
    }
}