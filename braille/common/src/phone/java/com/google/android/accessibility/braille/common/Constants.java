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

package com.google.android.accessibility.braille.common;

/** Braille constants. */
public class Constants {
  /** Braille types. */
  public enum BrailleType {
    SIX_DOT(/* dotCount= */ 6),
    EIGHT_DOT(/* dotCount = */ 8),
    ;

    private final int dotCount;

    BrailleType(int dotCount) {
      this.dotCount = dotCount;
    }

    public int getDotCount() {
      return dotCount;
    }
  }

  private Constants() {}
}
