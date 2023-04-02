package com.example.kotlindemo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class LoginRepository {
    // Function that makes the network request, blocking the current thread
    suspend fun makeLoginRequest(): Result<String> {
        return withContext(Dispatchers.IO) {
            val url = URL(loginUrl)
            (url.openConnection() as? HttpURLConnection)?.run {
                requestMethod = "GET"
                setRequestProperty("Content-Type", "application/json; utf-8")
                setRequestProperty("Accept", "application/json")
                doOutput = true
//            outputStream.write(jsonBody.toByteArray())
                return@withContext Result.Success(parse(inputStream))
            }
            Result.Error(Exception("Cannot open HttpURLConnection"))
        }
    }

    private fun parse(inputStream: InputStream): String {
        val br = BufferedReader(InputStreamReader(inputStream))
        var line = br.readLine()
        var sb = StringBuffer()
        while (line != null) {
            sb.append(line)
            line = br.readLine()
        }
        br.close()
        println(
            """
            result->
            $sb
        """.trimIndent()
        )
        return sb.toString()
    }

    companion object {
        private const val loginUrl = "http://www.baidu.com"
    }
}