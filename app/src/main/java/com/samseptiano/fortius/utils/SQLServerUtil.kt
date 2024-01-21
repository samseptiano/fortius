package com.samseptiano.fortius.utils

import android.content.Context
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException


/**
 * Created by samuel.septiano on 21/08/2023.
 */

object SQLServerUtil {

    private val host = "172.21.0.240"
    private val port = "1433"
    private val Classes = "net.sourceforge.jtds.jdbc.Driver"
    private val database = "RDMS_DEV"
    private val username = "itdev"
    private val password = "itdev!@#123"

    //private static String url = "jdbc:jtds:sqlserver://"+host+":"+port+"/"+database;
    private val url = "jdbc:jtds:sqlserver://$host/$database"

    private var connection: Connection? = null

    fun getConn(ctx: Context): Connection? {
        if (connection != null) {
            startConn(ctx)
        }
        return connection
    }

    fun executeQuery(query: String): ResultSet? {
        return if (connection != null) {
            val ps: PreparedStatement = connection!!.prepareStatement(query)
            ps.executeQuery();
        } else {
            null
        }

    }

    fun executeUpdate(query: String): Int {
            val ps: PreparedStatement = connection!!.prepareStatement(query)
            return ps.executeUpdate();
    }

    fun startConn(ctx: Context) {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            Class.forName(Classes)
            connection = DriverManager.getConnection(url, username, password)
            //Toast.makeText(ctx, "Connected", Toast.LENGTH_SHORT).show()

        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            //Toast.makeText(ctx, "Class fail: $e", Toast.LENGTH_SHORT).show()
        } catch (e: SQLException) {
            e.printStackTrace()
            //Toast.makeText(ctx, "Connected no: $e", Toast.LENGTH_SHORT).show()
        }
    }
}