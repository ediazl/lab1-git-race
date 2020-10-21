package es.unizar.webeng.hello

import org.springframework.web.client.RestTemplate
import java.util.*


/*
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.ProtocolException;*/

/*
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.ProtocolException;*/
/**
 * This class is used to zip all about HTTP protocol and GET request
 */
class GetRequest {
    private var res: JokeResponse? = null

    /**
     * Getter  simplified
     * @return res.toString();
     */
    fun AllJoke(): String? {
        return if (res == null) null else res.toString()
    }

    /**
     * Returns if this joke contains question+response
     * @return boolean
     */
    val isSuccess: Boolean
        get() = if (res == null) false else res!!.setup != null && res!!.punchline != null

    /**
     * Getter  simplified
     * @return res.getSetup();
     */
    fun plus(): String? {
        return if (res == null) null else res!!.setup
    }

    /**
     * Getter  simplified
     * @return res.getPunchline();
     */
    fun minus(): String? {
        return if (res == null) null else res!!.punchline
    }

    init {
        res = try {
            val rt = RestTemplate()
            val url = "https://official-joke-api.appspot.com/jokes/programming/random"
            val entity = rt.getForEntity(url, Array<JokeResponse>::class.java)
            if (entity.body != null) Arrays.asList(*entity.body)[0] else null
        } catch (e: Exception) {
            //System.out.println("ERROR CATCH: " + e.toString());
            null
        }
    }
}