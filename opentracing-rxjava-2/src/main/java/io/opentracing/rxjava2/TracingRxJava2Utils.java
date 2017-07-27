package io.opentracing.rxjava2;


import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;


public class TracingRxJava2Utils {

  public static void enableTracing() {
    enableTracing(GlobalTracer.get());
  }

  public static void enableTracing(final Tracer tracer) {

    RxJavaPlugins.setScheduleHandler(new Function<Runnable, Runnable>() {
      @Override
      public Runnable apply(Runnable runnable) throws Exception {
        return new TracingRunnable(runnable);
      }
    });

    RxJavaPlugins.setOnObservableSubscribe(new BiFunction<Observable, Observer, Observer>() {
      @Override
      public Observer apply(Observable observable, Observer observer) throws Exception {
        return new TracingObserver(observable, observer, tracer);
      }
    });
  }

}
