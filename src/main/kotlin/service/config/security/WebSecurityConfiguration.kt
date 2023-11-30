package service.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.HttpSecurityDsl
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import service.config.security.Authorities.SCOPE_ACTUATOR
import service.config.security.Authorities.SCOPE_ADMIN

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration {

    private val passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun generalSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            securityMatcher("/**")
            applyDefaults()

            httpBasic {}
            authorizeRequests {
                authorize("/admin", hasAuthority(SCOPE_ADMIN))
                authorize("/admin/**", hasAuthority(SCOPE_ADMIN))
                authorize("/view/**", permitAll)
                authorize("/resources/**", permitAll)
                authorize("/error", permitAll)
                authorize(anyRequest, denyAll)
            }
        }
        return http.build()
    }

    private fun HttpSecurityDsl.applyDefaults() {
        cors { disable() }
        csrf { disable() }
    }

    @Bean
    fun userDetailService(): UserDetailsService =
        InMemoryUserDetailsManager(
            dummyUser("user", SCOPE_ADMIN),
            dummyUser("actuator", SCOPE_ACTUATOR)
        )

    private fun dummyUser(username: String, vararg authorities: String) =
        User.withUsername(username)
            .password(passwordEncoder.encode(username.reversed()))
            .authorities(*authorities)
            .build()
}
