/*
 * Copyright (C) 2019 Google Inc.
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

package com.google.android.accessibility.talkback.compositor;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import java.util.List;

/** This is an interface to get information from node's menu items. */
public interface NodeMenuProvider {

  /**
   * Returns action types of menu items supported from the node itself and filter ones may come from
   * its ancestors.
   *
   * @param node The target node to find supported menu action types
   */
  List<String> getSelfNodeMenuActionTypes(AccessibilityNodeInfoCompat node);

  /** Returns the title of the action item shown in menu. */
  String getActionMenuName();
}
