/*
 * Copyright (C) 2023 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.android.accessibility.talkback.logging;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import com.google.android.accessibility.utils.LatencyTracker;
import com.google.android.accessibility.utils.Performance.EventData;
import com.google.android.accessibility.utils.output.FailoverTextToSpeech.FailoverTtsListener;
import com.google.android.accessibility.utils.output.FailoverTextToSpeech.UtteranceInfoCombo;
import com.google.android.libraries.accessibility.utils.concurrent.HandlerExecutor;
import java.util.concurrent.Executor;

/** Logs the event-based latency */
public class EventLatencyLogger implements LatencyTracker, FailoverTtsListener {

  private final Executor executor;

  public EventLatencyLogger(Context context, SharedPreferences prefs) {
    executor = new HandlerExecutor(new Handler(context.getMainLooper()));
  }

  public void destroy() {}

  @Override
  public void onFeedbackOutput(EventData eventData) {}

  @Override
  public Executor getExecutor() {
    return executor;
  }

  @Override
  public void onTtsInitialized(boolean wasSwitchingEngines, String enginePackageName) {}

  @Override
  public void onBeforeUtteranceRequested(
      String utteranceId, UtteranceInfoCombo utteranceInfoCombo) {}

  @Override
  public void onUtteranceStarted(String utteranceId) {}

  @Override
  public void onUtteranceRangeStarted(String utteranceId, int start, int end) {}

  @Override
  public void onUtteranceCompleted(String utteranceId, boolean success) {}
}
