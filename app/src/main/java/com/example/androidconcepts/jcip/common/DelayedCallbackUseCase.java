package com.example.androidconcepts.jcip.common;

import com.example.androidconcepts.common.BgThreadPoster;
import com.example.androidconcepts.common.UiThreadPoster;

public class DelayedCallbackUseCase {

    public interface Listener {
        void onResult();
    }

    private BgThreadPoster bgThreadPoster;
    private UiThreadPoster uiThreadPoster;
    private ThreadContextSwitchTrigger trigger = new ThreadContextSwitchTrigger(100L);

    public DelayedCallbackUseCase(BgThreadPoster bgThreadPoster, UiThreadPoster uiThreadPoster) {
        this.bgThreadPoster = bgThreadPoster;
        this.uiThreadPoster = uiThreadPoster;
    }

    public void fetchAsync(Long delayTime, Listener listener) {
        bgThreadPoster.post(() -> {
            trigger.trigger(delayTime);
            uiThreadPoster.post(listener::onResult);
        });
    }
}
