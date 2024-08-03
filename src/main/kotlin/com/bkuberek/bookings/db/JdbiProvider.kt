package com.bkuberek.bookings.db

import io.agroal.api.AgroalDataSource
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin

@ApplicationScoped
class JdbiProvider @Inject constructor(private val ds: AgroalDataSource) {

    @Singleton
    @Produces
    fun provideJdbi(): Jdbi {
        val jdbi = Jdbi.create(ds.connection)
        jdbi.installPlugin(SqlObjectPlugin())
        jdbi.installPlugin(KotlinSqlObjectPlugin())
        jdbi.installPlugin(PostgresPlugin())
        return jdbi
    }
}