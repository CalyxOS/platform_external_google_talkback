/*
 * Copyright (C) 2022 Google Inc.
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

package com.google.android.accessibility.talkback;

import android.content.Context;

/** Reader class for flags of experimental feature. */
public final class FeatureFlagReader {
  public static boolean useTalkbackGestureDetection(Context context) {
    return true;
  }

  public static boolean handleStateChangeInMainThread(Context context) {
    return false;
  }

  public static boolean useMultipleGestureSet(Context context) {
    return false;
  }

  public static boolean enableImageDescription(Context context) {
    return false;
  }

  public static double getImageDescriptionHighQualityThreshold(Context context) {
    return 0;
  }

  public static double getImageDescriptionLowQualityThreshold(Context context) {
    return 0;
  }

  public static boolean enableAutomaticCaptioningForAllImages(Context context) {
    return false;
  }

  public static boolean enableMdd(Context context) {
    return false;
  }

  public static Object getImageCaptioningLibrariesManifestConfig(Context context) {
    return null;
  }

  public static Object getIconDetectionLibraryManifestConfig(Context context) {
    return null;
  }

  public static Object getGarconLibraryManifestConfig(Context context) {
    return null;
  }

  public static boolean allowFocusResting(Context context) {
    return false;
  }

  public static boolean allowAutomaticTurnOff(Context context) {
    return true;
  }

  public static boolean supportShowExitBanner(Context context) {
    return true;
  }

  public static boolean useGestureBrailleDisplayOnOff(Context context) {
    return false;
  }

  public static boolean logEventBasedLatency(Context context) {
    return false;
  }

  public static boolean usePeriodAsSeparator(Context context) {
    return false;
  }

  public static boolean removeUnnecessarySpans(Context context) {
    return false;
  }

  public static boolean enableAggressiveChunking(Context context) {
    return false;
  }

  public static boolean enableMediaControlHintForCall(Context context) {
    return false;
  }

  public static boolean enableQuickNavigationToHunGesture(Context context) {
    return false;
  }

  public static boolean supportRsbScrolling(Context context) {
    return false;
  }

  public static boolean supportActiveSpellCheck(Context context) {
    return true;
  }

  public static boolean enableShortAndLongDurationsForSpecificApps(Context context) {
    return false;
  }

  public static boolean enableCachedTtsLocale(Context context) {
    return false;
  }

  private FeatureFlagReader() {}
}
