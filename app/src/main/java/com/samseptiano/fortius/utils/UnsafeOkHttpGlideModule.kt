package com.samseptiano.fortius.utils

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import java.io.InputStream
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


/**
 * Created by samuel.septiano on 29/08/2023.
 */
@GlideModule
class UnsafeOkHttpGlideModule : AppGlideModule() {
 override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
  registry.replace(
   GlideUrl::class.java,
   InputStream::class.java,
   OkHttpUrlLoader.Factory(UnsafeOkHttpClient.unsafeOkHttpClient)
  )
 }
}

object UnsafeOkHttpClient {
 // Create a trust manager that does not validate certificate chains
 val unsafeOkHttpClient:

         OkHttpClient
  get() = try {
   // Create a trust manager that does not validate certificate chains
   val trustAllCerts: Array<TrustManager> = arrayOf<TrustManager>(
    object : X509TrustManager {
     @Throws(CertificateException::class)
     override fun checkClientTrusted(
      chain: Array<X509Certificate?>?,
      authType: String?
     ) {
     }

     @Throws(CertificateException::class)
     override fun checkServerTrusted(
      chain: Array<X509Certificate?>?,
      authType: String?
     ) {
     }

     override fun getAcceptedIssuers(): Array<X509Certificate?>? {
      return arrayOf()
     }
    }
   )
   val sslContext: SSLContext = SSLContext.getInstance("SSL")
   sslContext.init(null, trustAllCerts, SecureRandom())
   // Create an ssl socket factory with our all-trusting manager
   val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
   val builder: OkHttpClient.Builder = OkHttpClient.Builder()
   builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
   builder.hostnameVerifier(HostnameVerifier { hostname, session -> true })
   builder.build()
  } catch (e: Exception) {
   throw RuntimeException(e)
  }
}