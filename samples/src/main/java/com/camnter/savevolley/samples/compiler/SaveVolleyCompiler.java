package com.camnter.savevolley.samples.compiler;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.camnter.savevolley.okhttp3.volley.Request;
import com.camnter.savevolley.okhttp3.volley.Request.Method;
import com.camnter.savevolley.okhttp3.volley.RequestQueue;
import com.camnter.savevolley.okhttp3.volley.toolbox.Volley;
import com.google.android.agera.Reservoir;

import static com.camnter.savevolley.samples.compiler.SaveVolleyCompilerStates.GSON;
import static com.camnter.savevolley.samples.compiler.SaveVolleyCompilerStates.JSON_ARRAY;
import static com.camnter.savevolley.samples.compiler.SaveVolleyCompilerStates.JSON_OBJECT;
import static com.camnter.savevolley.samples.compiler.SaveVolleyCompilerStates.ParseStyle;
import static com.google.android.agera.Preconditions.checkNotNull;

/**
 * Description：SaveVolleyCompiler
 * Created by：CaMnter
 * Time：2016-06-23 13:52
 */

public final class SaveVolleyCompiler<RType> implements
    SaveVolleyCompilerStates.VRequestState<RType>,
    SaveVolleyCompilerStates.VRequestQueue {

    private int requestMethod;
    @NonNull
    private String requestUrl;
    private int requestParseStyle;
    private Class requestTypeClass;

    private Request<?> request;

    private Reservoir<Object> reservoir;

    private static final ThreadLocal<SaveVolleyCompiler> compilers = new ThreadLocal<>();
    private static final ThreadLocal<RequestQueue> queue = new ThreadLocal<>();


    public static RequestQueue requestQueue(Context context) {
        if (queue.get() == null) {
            queue.set(Volley.newRequestQueue(context));
        }
        return queue.get();
    }


    private static void recycle(@NonNull final SaveVolleyCompiler compiler) {
        compilers.set(compiler);
    }


    @NonNull @Override public SaveVolleyCompiler<RType> url(@NonNull String url) {
        this.requestUrl = url;
        return this;
    }


    @NonNull
    static <RType> SaveVolleyCompiler<RType> request(
        @NonNull final String url) {
        checkNotNull(Looper.myLooper());
        Preconditions.checkNotNull(url,
            "The url was null, url == null");
        SaveVolleyCompiler compiler = compilers.get();
        if (compiler == null) {
            compiler = new SaveVolleyCompiler();
        } else {
            compilers.set(null);
        }
        return compiler.url(url);
    }


    @NonNull @Override public SaveVolleyCompiler<RType> method(@Nullable Integer method) {
        this.requestMethod = method != null ? method : Method.GET;
        return this;
    }


    @NonNull @Override
    public SaveVolleyCompiler<RType> parseStyle(
        @Nullable @ParseStyle Integer parseStyle) {
        this.requestParseStyle = parseStyle != null ? parseStyle : GSON;
        return this;
    }


    @NonNull @Override public SaveVolleyCompiler<RType> type(@Nullable Class clazz) {
        this.requestTypeClass = clazz;
        return this;
    }


    @NonNull @Override public SaveVolleyCompiler<RType> create() {
        switch (this.requestParseStyle) {
            case GSON:
                Preconditions.checkNotNull(this.requestTypeClass,
                    "The parse style of response was null, requestTypeClass == null");
                this.request = new GsonReservoirRequest<>(this.requestMethod, this.requestUrl,
                    this.requestTypeClass);
                break;
            case JSON_OBJECT:
                this.request = new JsonReservoirRequest(this.requestMethod, this.requestUrl);
                break;
            case JSON_ARRAY:
                this.request = new JsonArrayReservoirRequest(this.requestMethod, this.requestUrl);
                break;
        }
        return this;
    }


    @Override public SaveVolley execute(@NonNull Context context) {
        Preconditions.checkNotNull(this.request,
            "The request was null, request == null");
        requestQueue(context).add(this.request);
        if (this.request instanceof GsonReservoirRequest) {
            this.reservoir = ((GsonReservoirRequest) this.request).getReservoir();
        } else if (this.request instanceof JsonReservoirRequest) {
            this.reservoir = ((JsonReservoirRequest) this.request).getReservoir();
        } else if (this.request instanceof JsonArrayReservoirRequest) {
            this.reservoir = ((JsonArrayReservoirRequest) this.request).getReservoir();
        }
        SaveVolley saveVolley = new SaveVolley(this.requestMethod, this.requestUrl,
            this.requestParseStyle, this.requestTypeClass, this.request, this.reservoir);
        recycle(this);
        return saveVolley;
    }

}
