package com.chatbot.domain.auth.service

import com.chatbot.domain.auth.dto.request.SendMailRequest
import com.chatbot.domain.auth.dto.request.VerifyEmailRequest
import com.chatbot.domain.auth.exception.AuthErrorCode
import com.chatbot.domain.user.exception.UserErrorCode
import com.chatbot.domain.user.repository.UserRepository
import com.chatbot.global.dto.BaseResponse
import com.chatbot.global.exception.CustomException
import jakarta.mail.internet.MimeMessage
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.spring6.SpringTemplateEngine
import java.util.concurrent.TimeUnit

import org.thymeleaf.context.Context;

@Service
class MailService(
    private val templateEngine: SpringTemplateEngine,
    private val userRepository: UserRepository,
    private val javaMailSender: JavaMailSender,
    private val redisTemplate: StringRedisTemplate
) {
    fun generateVerificationCode(): String {
        return (0 until 1_000_000).random().toString().padStart(6, '0')
    }

    fun sendMail(request: SendMailRequest): BaseResponse<Unit> {
        val email = request.email

        if (userRepository.findByEmail(email) == null) {
            throw CustomException(UserErrorCode.EMAIL_ALREADY_EXIST)
        }

        val verificationCode = generateVerificationCode()

        redisTemplate.opsForValue().set(email, verificationCode, 5, TimeUnit.MINUTES)

        val message: MimeMessage = javaMailSender.createMimeMessage();
        val helper = MimeMessageHelper(message, true);

        helper.setTo(email);
        helper.setSubject("TurlGO 인증 코드");

        val context = Context();
        context.setVariable("verificationCode", verificationCode);

        val htmlContent = templateEngine.process("mail.html", context);

        helper.setText(htmlContent, true);

        javaMailSender.send(message);

        return BaseResponse(message = "send email successful")
    }

    fun verify(request: VerifyEmailRequest): BaseResponse<Unit> {
        val savedCode = getVerificationCode(request.email)
            ?: throw CustomException(AuthErrorCode.CODE_NOT_FOUND)

        if (savedCode != request.verifyCode) {
            throw CustomException(AuthErrorCode.CODE_NOT_MATCH)
        }

        redisTemplate.delete(request.email)

        return BaseResponse(message = "verify email successful")
    }

    fun getVerificationCode(email: String): String? {
        return redisTemplate.opsForValue().get(email)
    }
}
