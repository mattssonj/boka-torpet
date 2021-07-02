package com.mattssonj.torpet.event

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.lang.reflect.Method

/**
 * This Aspect is used to intercept all calls to methods that for some reason might be interesting to know when some user
 * calls. The information from the calls are then sent down to all beans that implements UserEventHandler interface.
 * To know which methods that are interesting enough we need to annotate it with the @UserEventProducer annotation.
 *
 * Note: When working with Kotlin (all classes are final by default) which makes it a bit harder to work with AOP.
 * However if a class is annotated with any stereotype annotation it seems that classes and methods becomes from the
 * maven plugin. But if the bean is created from a @Configuration class this is not the case.
 */

private const val ErrorMessage = "Annotation contains parameter name that cannot be found: "

@Component
@Aspect
class UserEventListener(private val eventHandlers: List<UserEventHandler> = emptyList()) {

    @Around("@annotation(UserEventProducer)")
    fun produceEvent(joinPoint: ProceedingJoinPoint): Any? {
        val args = getMethodArguments(joinPoint)
        val returnValue = getMethodReturnValue(joinPoint)

        val event = UserEvent(getLoggedInUsername(), MethodExecution(joinPoint.toShortString(), args, returnValue.toString()))
        eventHandlers.forEach { it.handle(event) } // TODO start in threads

        return if (returnValue is Exception)
            throw returnValue
        else returnValue
    }

    private fun getMethodArguments(joinPoint: ProceedingJoinPoint): List<MethodArgument> {
        val anonymizeParameters = getAnnotationArguments(joinPoint)
        val parameterNames = joinPoint.toMethod().parameters.map { it.name }

        val wrongAnnotationParameterNames = anonymizeParameters.filter { it !in parameterNames }

        if (wrongAnnotationParameterNames.isNotEmpty())
            throw IllegalArgumentException("$ErrorMessage ${wrongAnnotationParameterNames.joinToString(", ")}")

        return parameterNames
            .zip(joinPoint.args)
            .map { (name, value) -> if (name in anonymizeParameters) Pair(name, "***") else Pair(name, value) }
            .map { it.toMethodArgument() }
    }

    private fun getMethodReturnValue(joinPoint: ProceedingJoinPoint): Any {
        return try {
            val result = joinPoint.proceed()
            when {
                result == null -> "null"
                joinPoint.toMethod().returnType == Void::class -> "void"
                else -> result
            }
        } catch (any: Exception) {
            any
        }
    }

    private fun getAnnotationArguments(joinPoint: ProceedingJoinPoint): List<String> {
        return try {
            return getUserEventProducerArguments(joinPoint.toMethod())
        } catch (any: Exception) {
            emptyList()
        }
    }

    private fun getUserEventProducerArguments(method: Method): List<String> {
        val annotation = method
            .annotations
            .firstOrNull { it.annotationClass == UserEventProducer::class }

        return if (annotation != null)
            (annotation as UserEventProducer).anonymizeArguments.asList()
        else emptyList()
    }

    private fun getLoggedInUsername(): String {
        return try {
            val auth = SecurityContextHolder.getContext().authentication
            (auth.principal as UserDetails).username
        } catch (exception: Exception) {
            ""
        }
    }
}

/**
 * This annotation can be placed upon methods to produce events.
 *
 * anonymizeArguments takes a list of parameter names to the annotated method. If some parameter is miss-spelled
 * or does not exists an @exception will be thrown.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class UserEventProducer(val anonymizeArguments: Array<String> = [])

private fun ProceedingJoinPoint.toMethod(): Method = (this.signature as MethodSignature).method
private fun Pair<String, Any>.toMethodArgument() = MethodArgument(this.first, this.second)