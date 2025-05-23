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

package com.google.android.accessibility.braille.brailledisplay.platform.lib;

import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;

/** A BroadcastReceiver for listening to battery change. */
public class BatteryChangeReceiver
    extends ActionReceiver<BatteryChangeReceiver, BatteryChangeReceiver.Callback> {
  private static final int PERCENTAGE = 100;

  public BatteryChangeReceiver(Context context, Callback callback) {
    super(context, callback);
  }

  @Override
  protected void onReceive(Callback callback, String action, Bundle extras) {
    int level = extras.getInt(BatteryManager.EXTRA_LEVEL, /* defaultValue= */ -1);
    int scale = extras.getInt(BatteryManager.EXTRA_SCALE, /* defaultValue= */ -1);
    callback.onBatteryVolumePercentageChanged((int) (((double) level / scale) * PERCENTAGE));
  }

  @Override
  protected String[] getActionsList() {
    return new String[] {Intent.ACTION_BATTERY_CHANGED};
  }

  /** The callback associated with the actions of this receiver. */
  public interface Callback {
    void onBatteryVolumePercentageChanged(int percentage);
  }
}
