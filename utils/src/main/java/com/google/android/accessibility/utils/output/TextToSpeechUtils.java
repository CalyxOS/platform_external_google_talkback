/*
 * Copyright (C) 2015 The Android Open Source Project
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

package com.google.android.accessibility.utils.output;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.provider.Settings.Secure;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import com.google.android.accessibility.utils.compat.provider.SettingsCompatUtils;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

class TextToSpeechUtils {

  /**
   * Reloads the list of installed TTS engines.
   *
   * @param pm The package manager.
   * @param results The list to populate with installed TTS engines.
   * @return The package for the system default TTS.
   */
  @SuppressLint("WrongConstant") // Allow queryIntentServices() with GET_SERVICES
  public static @Nullable String reloadInstalledTtsEngines(
      PackageManager pm, List<String> results) {
    final Intent intent = new Intent(TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE);
    // The following call to queryIntentServices() with GET_SERVICES is possible due to
    // QUERY_ALL_PACKAGES being present in the AndroidManifest.xml file.
    final List<ResolveInfo> resolveInfos =
        pm.queryIntentServices(intent, PackageManager.GET_SERVICES);

    String systemTtsEngine = null;

    for (ResolveInfo resolveInfo : resolveInfos) {
      final ServiceInfo serviceInfo = resolveInfo.serviceInfo;
      final ApplicationInfo appInfo = serviceInfo.applicationInfo;
      final String packageName = serviceInfo.packageName;
      final boolean isSystemApp = ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);

      results.add(serviceInfo.packageName);

      if (isSystemApp) {
        systemTtsEngine = packageName;
      }
    }

    return systemTtsEngine;
  }

  /**
   * Attempts to shutdown the specified TTS engine, ignoring any errors.
   *
   * @param tts The TTS engine to shutdown.
   */
  static void attemptTtsShutdown(TextToSpeech tts) {
    try {
      tts.shutdown();
    } catch (Exception e) {
      // Don't care, we're shutting down.
    }
  }

  /**
   * Returns the localized name of the TTS engine with the specified package name.
   *
   * @param context The parent context.
   * @param enginePackage The package name of the TTS engine.
   * @return The localized name of the TTS engine.
   */
  static @Nullable CharSequence getLabelForEngine(Context context, String enginePackage) {
    if (enginePackage == null) {
      return null;
    }

    final PackageManager pm = context.getPackageManager();
    final Intent intent = new Intent(TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE);
    intent.setPackage(enginePackage);

    final List<ResolveInfo> resolveInfos =
        pm.queryIntentServices(intent, PackageManager.MATCH_DEFAULT_ONLY);

    if ((resolveInfos == null) || resolveInfos.isEmpty()) {
      return null;
    }

    final ResolveInfo resolveInfo = resolveInfos.get(0);
    final ServiceInfo serviceInfo = resolveInfo.serviceInfo;

    if (serviceInfo == null) {
      return null;
    }

    return serviceInfo.loadLabel(pm);
  }

  static @Nullable String getDefaultLocaleForEngine(ContentResolver cr, String engineName) {
    final String defaultLocales =
        Secure.getString(cr, SettingsCompatUtils.SecureCompatUtils.TTS_DEFAULT_LOCALE);
    return parseEnginePrefFromList(defaultLocales, engineName);
  }

  /**
   * Parses a comma-separated list of engine-specific locale preferences.
   *
   * <p>The list format is: `engine_name_1:locale_1,engine_name_2:locale_2,...`.
   *
   * <p>This method searches the list for an entry matching the given `engineName`. If found, it
   * returns the associated locale string after replacing underscores with hyphens (to match IETF
   * language tag format). Otherwise, it returns null.
   *
   * @param prefValue The comma-separated list of preferences.
   * @param engineName The engine name to search for.
   * @return The preferred locale for the given engine (in IETF format), or null if not found, the
   *     list is empty, or malformed.
   */
  private static @Nullable String parseEnginePrefFromList(String prefValue, String engineName) {
    if (TextUtils.isEmpty(prefValue) || TextUtils.isEmpty(engineName)) {
      return null;
    }

    String[] prefValues = prefValue.split(",");

    for (String value : prefValues) {
      final int delimiter = value.indexOf(':');
      if (delimiter > 0) {
        if (engineName.equals(value.substring(0, delimiter))) {
          String locale = value.substring(delimiter + 1);
          return TextUtils.isEmpty(locale) ? null : locale.replace('_', '-');
        }
      }
    }

    return null;
  }
}
