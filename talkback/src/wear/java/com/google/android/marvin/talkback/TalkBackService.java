package com.google.android.marvin.talkback;

import static android.view.KeyEvent.KEYCODE_VOLUME_DOWN;
import static android.view.KeyEvent.KEYCODE_VOLUME_UP;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import com.google.android.accessibility.talkback.FeatureFlagReader;
import com.google.android.accessibility.talkback.MotionEventController;
import com.google.android.accessibility.talkback.Pipeline;
import com.google.android.accessibility.utils.FeatureSupport;
import com.google.android.accessibility.utils.FormFactorUtils;
import com.google.android.libraries.accessibility.utils.log.LogUtils;
import org.checkerframework.checker.nullness.qual.Nullable;

/** Wear-specific changes to TalkBackService. */
public class TalkBackService extends com.google.android.accessibility.talkback.TalkBackService {
  private static final String TAG = "TalkBackServiceWear";
  private static final int NUM_STEM_BUTTONS_REQUIRED = 3;
  private static final boolean SUPPORT_VOLUME_CHANGE_BY_WEAR_KEY = false;
  private @Nullable Pair<Integer, Integer> volumeButtons;

  // A controller to intercept and handle motion events if needed.
  private @Nullable MotionEventController motionEventController;

  @Override
  public void onCreate() {
    super.onCreate();
    if (SUPPORT_VOLUME_CHANGE_BY_WEAR_KEY) {
      volumeButtons = getVolumeStemButtons(this);
    }
  }

  @Override
  protected void onServiceConnected() {
    checkAudioOutput();
    super.onServiceConnected();
  }

  @Override
  protected void onPipelineInitialized(Pipeline pipeline) {
    if (FeatureFlagReader.supportRsbScrolling(this)
        && FeatureSupport.supportMotionEventSources()
        && FeatureSupport.supportsRotaryEncoder()) {
      motionEventController =
          new MotionEventController(this, pipeline.getFeedbackReturner(), getFeedbackController());
      addEventListener(motionEventController);
    }
  }

  @Override
  protected void shutdownInfrastructure() {
    super.shutdownInfrastructure();
    if (motionEventController != null) {
      motionEventController.shutdown();
      motionEventController = null;
    }
  }

  /** Check whether the device has a audio output available */
  private void checkAudioOutput() {
    PackageManager packageManager = getPackageManager();
    AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    if (packageManager.hasSystemFeature(PackageManager.FEATURE_AUDIO_OUTPUT)) {
      AudioDeviceInfo[] devices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS);
      for (AudioDeviceInfo device : devices) {
        switch (device.getType()) {
          case AudioDeviceInfo.TYPE_BUILTIN_SPEAKER:
            LogUtils.v(TAG, "Wear System with built-in speaker.");
            break;
          case AudioDeviceInfo.TYPE_BLUETOOTH_A2DP:
            LogUtils.v(TAG, "Wear System with Bluetooth audio.");
            break;
          default:
            break;
        }
      }
    }
  }

  /** Transform the volume up and down buttons, if possible. */
  @Override
  protected boolean onKeyEventInternal(KeyEvent keyEvent) {
    // Do Wear specific transformations.
    if (SUPPORT_VOLUME_CHANGE_BY_WEAR_KEY) {
      int keyCode = keyEvent.getKeyCode();
      if (keyCode == KeyEvent.KEYCODE_STEM_1 || keyCode == KeyEvent.KEYCODE_STEM_2) {
        if (volumeButtons.first == keyCode) {
          keyEvent = copyKeyEventWithNewCode(keyEvent, KeyEvent.KEYCODE_VOLUME_UP);
        } else if (volumeButtons.second == keyCode) {
          keyEvent = copyKeyEventWithNewCode(keyEvent, KeyEvent.KEYCODE_VOLUME_DOWN);
        }
      }
    }
    return super.onKeyEventInternal(keyEvent);
  }

  @Override
  public void onMotionEvent(@NonNull MotionEvent event) {
    super.onMotionEvent(event);
    if (motionEventController != null) {
      motionEventController.onMotionEvent(event);
    }
  }

  /** Return volume up and down buttons */
  private Pair<Integer, Integer> getVolumeStemButtons(Context context) {
    Pair<Integer, Integer> volumeButtons = new Pair<>(KEYCODE_VOLUME_UP, KEYCODE_VOLUME_DOWN);
    // Check that this is a watch, based on BUILD rules it should be.
    if (!FormFactorUtils.getInstance().isAndroidWear()) {
      return volumeButtons;
    }
    return null;
  }

  /** Change only the Code and keep all the other values in KeyEvent. */
  private KeyEvent copyKeyEventWithNewCode(KeyEvent sourceEvent, int newCode) {
    return new KeyEvent(
        sourceEvent.getDownTime(),
        sourceEvent.getEventTime(),
        sourceEvent.getAction(),
        newCode,
        sourceEvent.getRepeatCount(),
        sourceEvent.getMetaState(),
        sourceEvent.getDeviceId(),
        sourceEvent.getScanCode(),
        sourceEvent.getFlags(),
        sourceEvent.getSource());
  }
}
