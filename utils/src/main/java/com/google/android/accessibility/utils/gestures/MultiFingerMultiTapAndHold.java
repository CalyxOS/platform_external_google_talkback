/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.accessibility.utils.gestures;

import android.content.Context;
import android.view.MotionEvent;
import com.google.android.accessibility.utils.Performance.EventId;

/**
 * This class matches gestures of the form multi-finger multi-tap and hold. The number of fingers
 * and taps for each instance is specified in the constructor.
 */
class MultiFingerMultiTapAndHold extends MultiFingerMultiTap {

  MultiFingerMultiTapAndHold(
      Context context,
      int fingers,
      int taps,
      int gestureId,
      GestureMatcher.StateChangeListener listener) {
    super(context, fingers, taps, gestureId, listener);
  }

  @Override
  protected void onPointerDown(EventId eventId, MotionEvent event) {
    super.onPointerDown(eventId, event);
    if (isTargetFingerCountReached && completedTapCount + 1 == mTargetTapCount) {
      completeAfterLongPressTimeout(eventId, event);
    }
  }

  @Override
  protected void onUp(EventId eventId, MotionEvent event) {
    if (completedTapCount + 1 == mTargetTapCount) {
      // Calling super.onUp  would complete the multi-tap version of this.
      cancelGesture(event);
    } else {
      super.onUp(eventId, event);
      cancelAfterDoubleTapTimeout(event);
    }
  }

  @Override
  public String getGestureName() {
    final StringBuilder builder = new StringBuilder();
    builder.append(targetFingerCount).append("-Finger ");
    if (mTargetTapCount == 1) {
      builder.append("Single");
    } else if (mTargetTapCount == 2) {
      builder.append("Double");
    } else if (mTargetTapCount == 3) {
      builder.append("Triple");
    } else if (mTargetTapCount > 3) {
      builder.append(mTargetTapCount);
    }
    return builder.append(" Tap and hold").toString();
  }
}
