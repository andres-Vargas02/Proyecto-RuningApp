package com.uptc.runningapp.data

import java.sql.Connection
import java.sql.DriverManager

object DatabaseConfig {
    init {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
        } catch (e: ClassNotFoundException) {
            throw RuntimeException("SQL Server JDBC Driver not found. Include it in your library path", e)
        }
    }

    private const val JDBC_URL = "jdbc:jtds:sqlserver://RunningAppDB.mssql.somee.com:1433/RunningAppDB;" +
            "user=julies18_SQLLogin_1;" +
            "password=x7jxz7xwci;" +
            "encrypt=false;"

    fun getConnection(): Connection {
        return DriverManager.getConnection(JDBC_URL)
    }
}
