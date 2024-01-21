package com.samseptiano.fortius.utils

import android.content.Context
import android.util.Log
import com.jcraft.jsch.Channel
import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import java.io.File
import java.io.FileInputStream


/**
 * Created by samuel.septiano on 22/08/2023.
 */
object FtpUtil {
    private const val ftpServer = "202.152.27.221"
    private const val ftpPort = 1680
    private const val ftpUsername = "rodamasrdm"
    private const val ftpPassword = "SFTPRodamas2023!"


    suspend fun uploadFTP(context: Context, imagePath: String){
        val SFTPHOST2 = "202.152.27.221"
        val SFTPPORT2 = 1680
        val SFTPUSER2 = "rodamasrdm"
        val SFTPPASS2 = "SFTPRodamas2023!"
        val SFTPWORKINGDIR2 = "/2023/"

        var session2: Session? = null
        var channel2: Channel? = null
        var channelSftp2: ChannelSftp? = null

        try {
            val jsch2 = JSch()
            session2 = jsch2.getSession(SFTPUSER2, SFTPHOST2, SFTPPORT2)
            session2.setPassword(SFTPPASS2)
            session2.setConfig("StrictHostKeyChecking", "no")
            session2.timeout = 15000
            while (!session2.isConnected) session2.connect()
            channel2 = session2.openChannel("sftp")
            while (!channel2.isConnected()) channel2.connect()
            channelSftp2 = channel2 as ChannelSftp?
            channelSftp2!!.cd(SFTPWORKINGDIR2)
            channelSftp2!!.put(
                imagePath,
                "/${imagePath.split("/").last()}"
            )
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
            Log.d("Result FTP", ex.toString())
            //Toast.makeText(context, "Error Connecting : ${ex.toString()}", Toast.LENGTH_LONG).show()
        }
    }
     suspend fun uploadImageToFTP(
        imagePath: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        Thread {
            val ftpClient = FTPClient()

            try {
                ftpClient.connect(ftpServer, ftpPort)
                ftpClient.login(ftpUsername, ftpPassword)
                ftpClient.enterLocalPassiveMode()
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE)

                val file = File(imagePath)
                val inputStream = FileInputStream(file)

                val remoteFilePath = "/destination_folder/$imagePath"
                ftpClient.storeFile(remoteFilePath, inputStream)

                inputStream.close()

                ftpClient.logout()
                ftpClient.disconnect()

                onSuccess.invoke()

            } catch (e: Exception) {
                e.printStackTrace()

                onError.invoke(e)
            }
        }.start()
    }
}