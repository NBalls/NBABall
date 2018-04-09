package ball.drive.com.nbaball.network

import android.util.Log
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import rx.Observable
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by aaron on 2018/4/9.
 */
class NetClient {

    private val okHttpClient: OkHttpClient by lazy {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build()
    }

    fun doGetRequest(url: String, paramsMap: Map<String, String>): Observable<JSONObject> {
        return Observable.create<JSONObject> { subscribe ->
            try {
                okHttpClient.newCall(parserGetParams(url, paramsMap))
                        .enqueue(object : Callback {
                            override fun onFailure(call: Call?, e: IOException?) {
                                subscribe.onError(e)
                            }

                            override fun onResponse(call: Call?, response: Response?) {
                                try {
                                    if (response != null) {
                                        subscribe.onNext(JSONObject(response.body().string()))
                                        subscribe.onCompleted()
                                    } else {
                                        subscribe.onError(NullPointerException())
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun doGetRequestHtml(url: String, paramsMap: Map<String, String>): Observable<String> {
        return Observable.create<String> { subscribe ->
            try {
                okHttpClient.newCall(parserGetParams(url, paramsMap))
                        .enqueue(object : Callback {
                            override fun onFailure(call: Call?, e: IOException?) {
                                subscribe.onError(e)
                            }

                            override fun onResponse(call: Call?, response: Response?) {
                                try {
                                    if (response != null) {
                                        subscribe.onNext(response.body().string())
                                        subscribe.onCompleted()
                                    } else {
                                        subscribe.onError(NullPointerException())
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun doGetRequestArray(url: String, paramsMap: Map<String, String>): Observable<JSONArray> {
        return Observable.create<JSONArray> { subscribe ->
            try {
                okHttpClient.newCall(parserGetParams(url, paramsMap))
                        .enqueue(object : Callback {
                            override fun onFailure(call: Call?, e: IOException?) {
                                subscribe.onError(e)
                            }

                            override fun onResponse(call: Call?, response: Response?) {
                                try {
                                    if (response != null) {
                                        subscribe.onNext(JSONArray(response.body().string()))
                                        subscribe.onCompleted()
                                    } else {
                                        subscribe.onError(NullPointerException())
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun parserGetParams(url: String, paramsMap: Map<String, String>): Request {
        val it = paramsMap.entries.iterator()
        val sb = StringBuffer(url)
        while(it.hasNext()) {
            val entry = it.next()
            sb.append("&").append(entry.key).append("=").append(entry.value)
        }

        Log.i("########", sb.toString())
        return Request.Builder().get().url(sb.toString()).build()
    }
}