package com.leonyr.mvvm.net.cookie

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.leonyr.lib.utils.LogUtil
import okhttp3.Cookie
import okhttp3.HttpUrl
import java.io.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.HashMap
import kotlin.experimental.and

class CookieStore {

    companion object {
        val LOG_TAG: String = "CookieStore"
        val COOKIE_PREFS = "Cookies_Prefs"
        private val cookies: HashMap<String, ConcurrentHashMap<String, Cookie>> = hashMapOf()
        private lateinit var cookiePrefs: SharedPreferences
    }

    constructor(ctx: Context) {
        cookiePrefs = ctx.getSharedPreferences(COOKIE_PREFS, 0)
        val prefsMap = cookiePrefs.all
        prefsMap.forEach { item ->
            val cookieNames = TextUtils.split(item.value as String, ",")
            cookieNames.forEach {
                val encodedCookie = cookiePrefs.getString(it, null)
                if (encodedCookie != null) {
                    val decodedCookie = decodeCookie(encodedCookie)
                    if (null != decodedCookie) {
                        if (!cookies.containsKey(item.key)) {
                            cookies[item.key] = ConcurrentHashMap<String, Cookie>()
                        }
                        cookies[item.key]!![it] = decodedCookie
                    }
                }
            }
        }

    }

    protected fun getCookieToken(cookie: Cookie): String {
        return "${cookie.name}@${cookie.domain}"
    }

    fun add(url: HttpUrl, cookie: Cookie) {
        val name = getCookieToken(cookie)

        if (!cookie.persistent) {
            if (!cookies.containsKey(url.host)) {
                cookies.put(url.host, ConcurrentHashMap())
            }
            cookies.get(url.host)!!.put(name, cookie)
        } else {
            if (!cookies.containsKey(url.host)) {
                cookies[url.host]!!.remove(name)
            }
        }
        //讲cookies持久化到本地
        val prefsWriter = cookiePrefs.edit()
        if (cookies[url.host] != null && !cookies[url.host]!!.isEmpty())
            prefsWriter.putString(url.host, TextUtils.join(",", cookies[url.host]!!.keys))
        prefsWriter.putString(name, encodeCookie(OkHttpCookies(cookie)))
        prefsWriter.apply()

    }

    fun get(url: HttpUrl): List<Cookie> {
        var ret = arrayListOf<Cookie>()
        if (cookies.containsKey(url.host)) {
            ret.addAll(cookies[url.host]!!.values)
        }
        return ret
    }

    fun removeAll(): Boolean {
        val prefsWriter = cookiePrefs.edit()
        prefsWriter.clear()
        prefsWriter.apply()
        cookies.clear()
        return true
    }

    fun remove(url: HttpUrl, cookie: Cookie): Boolean {
        val name = getCookieToken(cookie)

        if (cookies.containsKey(url.host) && cookies[url.host]!!.containsKey(name)) {
            cookies[url.host]!!.remove(name)

            val prefsWriter = cookiePrefs.edit()
            if (cookiePrefs.contains(name)) {
                prefsWriter.remove(name)
            }
            prefsWriter.putString(url.host, TextUtils.join(",", cookies[url.host]!!.keys))
            prefsWriter.apply()
            return true
        }
        return false
    }

    fun getCookies(): List<Cookie> {
        val ret = arrayListOf<Cookie>()
        for (key in cookies.keys)
            ret.addAll(cookies[key]!!.values)

        return ret
    }

    /**
     * cookies 序列化成 string
     *
     * @param cookie 要序列化的cookie
     * @return 序列化之后的string
     */
    protected fun encodeCookie(cookie: OkHttpCookies?): String? {
        if (cookie == null)
            return null
        val os = ByteArrayOutputStream()
        try {
            val outputStream = ObjectOutputStream(os)
            outputStream.writeObject(cookie)
        } catch (e: IOException) {
            LogUtil.d(LOG_TAG, "IOException in encodeCookie" + e.message)
            return null
        }

        return byteArrayToHexString(os.toByteArray())
    }

    /**
     * 将字符串反序列化成cookies
     *
     * @param cookieString cookies string
     * @return cookie object
     */
    protected fun decodeCookie(cookieString: String): Cookie? {
        val bytes = hexStringToByteArray(cookieString)
        val byteArrayInputStream = ByteArrayInputStream(bytes)
        var cookie: Cookie? = null
        try {
            val objectInputStream = ObjectInputStream(byteArrayInputStream)
            cookie = (objectInputStream.readObject() as OkHttpCookies).getCookies()
        } catch (e: IOException) {
            LogUtil.d(LOG_TAG, "IOException in decodeCookie" + e.message)
        } catch (e: ClassNotFoundException) {
            LogUtil.d(LOG_TAG, "ClassNotFoundException in decodeCookie" + e.message)
        }

        return cookie
    }


    /**
     * 二进制数组转十六进制字符串
     *
     * @param bytes byte array to be converted
     * @return string containing hex values
     */
    protected fun byteArrayToHexString(bytes: ByteArray): String {
        val sb = StringBuilder(bytes.size * 2)
        for (element in bytes) {
            val v = element and 0xff as Byte
            if (v < 16) {
                sb.append('0')
            }
            sb.append(Integer.toHexString(v as Int))
        }
        return sb.toString().toUpperCase(Locale.US)
    }

    /**
     * 十六进制字符串转二进制数组
     *
     * @param hexString string of hex-encoded values
     * @return decoded byte array
     */
    protected fun hexStringToByteArray(hexString: String): ByteArray {
        val len = hexString.length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(hexString[i], 16) shl 4) + Character.digit(
                hexString[i + 1],
                16
            )).toByte()
            i += 2
        }
        return data
    }
}