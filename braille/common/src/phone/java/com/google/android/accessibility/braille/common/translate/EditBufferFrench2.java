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

package com.google.android.accessibility.braille.common.translate;

import static com.google.android.accessibility.braille.common.translate.BrailleTranslateUtils.DOT6;
import static com.google.android.accessibility.braille.common.translate.BrailleTranslateUtils.DOTS46;

import android.content.Context;
import com.google.android.accessibility.braille.common.TalkBackSpeaker;
import com.google.android.accessibility.braille.interfaces.BrailleCharacter;
import com.google.android.accessibility.braille.translate.BrailleTranslator;
import java.util.Map;

/** An EditBuffer for French Braille Grade 2. */
public class EditBufferFrench2 extends EditBufferContracted {

  public EditBufferFrench2(
      Context context, BrailleTranslator translator, TalkBackSpeaker talkBack) {
    super(context, translator, talkBack);
  }

  @Override
  protected void fillTranslatorMaps(
      Map<String, String> initialCharacterTranslationMap,
      Map<String, String> nonInitialCharacterTranslationMap) {
    initialCharacterTranslationMap.put("1", "a");
    initialCharacterTranslationMap.put("12", "b");
    initialCharacterTranslationMap.put("14", "c");
    initialCharacterTranslationMap.put("145", "d");
    initialCharacterTranslationMap.put("15", "e");
    initialCharacterTranslationMap.put("124", "f");
    initialCharacterTranslationMap.put("1245", "g");
    initialCharacterTranslationMap.put("125", "h");
    initialCharacterTranslationMap.put("24", "i");
    initialCharacterTranslationMap.put("245", "j");
    initialCharacterTranslationMap.put("13", "k");
    initialCharacterTranslationMap.put("123", "l");
    initialCharacterTranslationMap.put("134", "m");
    initialCharacterTranslationMap.put("1345", "n");
    initialCharacterTranslationMap.put("135", "o");
    initialCharacterTranslationMap.put("1234", "p");
    initialCharacterTranslationMap.put("12345", "q");
    initialCharacterTranslationMap.put("1235", "r");
    initialCharacterTranslationMap.put("234", "s");
    initialCharacterTranslationMap.put("2345", "t");
    initialCharacterTranslationMap.put("136", "u");
    initialCharacterTranslationMap.put("1236", "v");
    initialCharacterTranslationMap.put("2456", "w");
    initialCharacterTranslationMap.put("1346", "x");
    initialCharacterTranslationMap.put("13456", "y");
    initialCharacterTranslationMap.put("1356", "z");

    initialCharacterTranslationMap.put("12346", "ç");
    initialCharacterTranslationMap.put("123456", "é");
    initialCharacterTranslationMap.put("12356", "à");
    initialCharacterTranslationMap.put("2346", "è");
    initialCharacterTranslationMap.put("23456", "ù");
    initialCharacterTranslationMap.put("16", "â");
    initialCharacterTranslationMap.put("126", "ê");
    initialCharacterTranslationMap.put("146", "î");
    initialCharacterTranslationMap.put("1456", "ô");
    initialCharacterTranslationMap.put("156", "û");
    initialCharacterTranslationMap.put("1246", "ë");
    initialCharacterTranslationMap.put("12456", "ï");
    initialCharacterTranslationMap.put("1256", "ü");
    initialCharacterTranslationMap.put("246", "œ");
  }

  @Override
  protected boolean isLetter(char character) {
    return false;
  }

  @Override
  protected BrailleCharacter getCapitalize() {
    return DOTS46;
  }

  @Override
  protected BrailleCharacter getNumeric() {
    return DOT6;
  }

  @Override
  protected boolean forceInitialDefaultTranslation(String dotsNumber) {
    return false;
  }

  @Override
  protected boolean forceNonInitialDefaultTranslation(String dotsNumber) {
    return false;
  }
}
