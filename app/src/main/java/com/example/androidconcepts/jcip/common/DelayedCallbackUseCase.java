package com.example.androidconcepts.jcip.common;

import com.example.androidconcepts.common.BaseObservable;
import com.example.androidconcepts.common.BgThreadPoster;
import com.example.androidconcepts.common.UiThreadPoster;

public class DelayedCallbackUseCase extends BaseObservable<DelayedCallbackUseCase.Listener> {

    public interface Listener {
        void onResult(int count);
    }

    private BgThreadPoster bgThreadPoster;
    private UiThreadPoster uiThreadPoster;
    private ThreadContextSwitchTrigger trigger = new ThreadContextSwitchTrigger(100L);

    private int counter = 0;

    public DelayedCallbackUseCase(BgThreadPoster bgThreadPoster, UiThreadPoster uiThreadPoster) {
        this.bgThreadPoster = bgThreadPoster;
        this.uiThreadPoster = uiThreadPoster;
    }

    public void fetchAsync(Long delayTime) {
        bgThreadPoster.post(() -> {
            trigger.trigger(delayTime);

            uiThreadPoster.post(() -> {
                for (Listener listener : getAllListeners()) {
                    listener.onResult(++counter);
                }
            });
        });
    }
}
