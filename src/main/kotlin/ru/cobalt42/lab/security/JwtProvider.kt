//package ru.cobalt42.lab.security
//
//import io.jsonwebtoken.JwtException
//import io.jsonwebtoken.Jwts
//import org.apache.tomcat.jni.Time
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.http.HttpStatus
//import org.springframework.stereotype.Component
//import ru.cobalt42.lab.exception.LabConclusionErrorResponse
//import java.util.*
//import javax.annotation.PostConstruct
//import javax.servlet.http.HttpServletRequest
//
//@Component
//class JwtProvider {
//    @Value("\${security.jwt.token.secret-key}")
//    private lateinit var secretKey: String
//
//    @PostConstruct
//    protected fun init() {
//        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
//    }
//
//    fun resolveToken(req: HttpServletRequest): String? {
//        val bearerToken = req.getHeader("Authorization")
//        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            bearerToken.substring(7)
//        } else null
//    }
//
//    fun validateToken(token: String?): Boolean {
//        return try {
//            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
//            true
//        } catch (e: JwtException) {
//            throw LabConclusionErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Expired or invalid JWT token", Time.now())
//        } catch (e: IllegalArgumentException) {
//            throw LabConclusionErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Expired or invalid JWT token", Time.now())
//        }
//    }
//}