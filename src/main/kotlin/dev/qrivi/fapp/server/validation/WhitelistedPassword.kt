package dev.qrivi.fapp.server.validation

import dev.qrivi.fapp.server.constants.SecurityConstants
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

@Target(AnnotationTarget.PROPERTY_GETTER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [WhitelistedPasswordValidator::class])
annotation class WhitelistedPassword(
        val message: String = "{fapp.validation.WhitelistedPassword.message}",
        val groups: Array<KClass<out Any>> = [],
        val payload: Array<KClass<out Any>> = []
)

class WhitelistedPasswordValidator : ConstraintValidator<WhitelistedPassword, String> {

    override fun initialize(constraintAnnotation: WhitelistedPassword?) {}

    override fun isValid(value: String, context: ConstraintValidatorContext?): Boolean {
        return !SecurityConstants.PASSWORD_BLACKLIST.contains(value.toLowerCase())
    }
}
