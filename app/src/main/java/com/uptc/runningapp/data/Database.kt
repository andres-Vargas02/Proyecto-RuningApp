package com.uptc.runningapp.data

import java.sql.Connection
import java.sql.DriverManager

object DatabaseConfig {
    private const val JDBC_URL = "jdbc:sqlserver://RunningAppDB.mssql.somee.com:1433;databaseName=RunningAppDB;TrustServerCertificate=True"
    private const val DB_USER = "julies18_SQLLogin_1"
    private const val DB_PASSWORD = "x7jxz7xwci"

    fun getConnection(): Connection {
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)
    }
}
