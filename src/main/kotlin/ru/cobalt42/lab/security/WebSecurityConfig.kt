//package ru.cobalt42.lab.security
//
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
//import org.springframework.security.config.http.SessionCreationPolicy
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//class WebSecurityConfig : WebSecurityConfigurerAdapter() {
//
//    @Autowired
//    lateinit var jwtProvider: JwtProvider
//
//    override fun configure(http: HttpSecurity) {
//
//        // Disable CSRF (cross site request forgery)
//        http.csrf().disable()
//
//        // No session will be created or used by spring security
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//        // Entry points
//        http.authorizeRequests()
//            .antMatchers("api/lab/**").hasRole("VerifiedToken")
//            .anyRequest().authenticated()
//
//        // Apply JWT
//        http.apply(JwtFilterConfigurer(jwtProvider))
//        http.cors()
//        http.httpBasic().disable().formLogin().disable()
//    }
//
//}