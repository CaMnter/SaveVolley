package com.camnter.savevolley.samples;

import android.support.annotation.NonNull;
import com.camnter.savevolley.okhttp3.volley.VolleyError;
import com.camnter.savevolley.samples.bean.GankData;
import com.camnter.savevolley.samples.bean.GankResultData;
import com.camnter.savevolley.samples.request.OkHttp3GsonRequest;
import com.google.android.agera.Function;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Reservoir;
import com.google.android.agera.Reservoirs;
import com.google.android.agera.Result;
import com.google.android.agera.Updatable;
import java.util.concurrent.Executor;

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
        final Reservoir<Object> reservoir = Reservoirs.reservoir();
        Volleys.getRequestQueue(this)
            .add(new OkHttp3GsonRequest<GankData>(TEST_URL, GankData.class) {
                @Override public void onResponse(GankData response) {
                    reservoir.accept(response);
                }


                @Override
                public void onErrorResponse(VolleyError error) {
                    reservoir.accept(error);
                }
            });
        final Repository<GankResultData> repository = Repositories.repositoryWithInitialValue(
            INITIAL_VALUE)
            .observe(reservoir)
            .onUpdatesPerLoop()
            .goTo(executor)
            .attemptGetFrom(reservoir)
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
                mGetContentText.setText(repository.get().toString());
            }
        });
    }
}
