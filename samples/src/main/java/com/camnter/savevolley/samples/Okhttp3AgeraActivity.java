package com.camnter.savevolley.samples;

import android.support.annotation.NonNull;
import com.camnter.savevolley.okhttp3.agera.SaveVolley;
import com.camnter.savevolley.okhttp3.agera.SaveVolleys;
import com.camnter.savevolley.okhttp3.volley.Request;
import com.camnter.savevolley.okhttp3.volley.VolleyError;
import com.camnter.savevolley.samples.bean.GankData;
import com.camnter.savevolley.samples.bean.GankResultData;
import com.google.android.agera.Function;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Updatable;
import java.util.concurrent.Executor;

import static com.camnter.savevolley.okhttp3.agera.SaveVolleyCompilerStates.GSON;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

/**
 * Description：Okhttp3AgeraActivity
 * Created by：CaMnter
 * Time：2016-06-22 17:46
 */

public class Okhttp3AgeraActivity extends Okhttp3Activity {
    private static final GankResultData INITIAL_VALUE = new GankResultData();
    final Executor executor = newSingleThreadExecutor();


    @Override protected void initData() {
        SaveVolley saveVolley = SaveVolleys
            .request(TEST_URL)
            .method(Request.Method.GET)
            .parseStyle(GSON)
            .jsonBean(GankData.class)
            .create()
            .execute(this);
        final Repository<GankResultData> repository = Repositories.repositoryWithInitialValue(
            INITIAL_VALUE)
            .observe(saveVolley.getReservoir())
            .onUpdatesPerLoop()
            .goTo(executor)
            .attemptGetFrom(saveVolley.getReservoir())
            .orSkip()
            .thenAttemptTransform(new Function<Object, Result<GankResultData>>() {
                /**
                 * Returns the result of applying this function to {@code input}.
                 */
                @NonNull @Override public Result<GankResultData> apply(@NonNull Object input) {
                    if (input instanceof GankData) {
                        return Result.success(((GankData) input).results.get(0));
                    } else if (input instanceof VolleyError) {
                        return Result.failure((VolleyError) input);
                    }
                    return Result.failure();
                }
            })
            .orSkip()
            .compile();
        repository.addUpdatable(new Updatable() {
            @Override public void update() {
                getContentText.setText(repository.get().toString());
            }
        });
    }
}
