package com.bkuberek.bookings.resources

import io.quarkus.test.junit.QuarkusTest
import jakarta.enterprise.inject.Stereotype
import jakarta.transaction.Transactional

@QuarkusTest
@Stereotype
@Transactional
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class TransactionalQuarkusTest {
}