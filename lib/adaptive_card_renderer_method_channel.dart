import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'adaptive_card_renderer_platform_interface.dart';

/// An implementation of [AdaptiveCardRendererPlatform] that uses method channels.
class MethodChannelAdaptiveCardRenderer extends AdaptiveCardRendererPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('adaptive_card_renderer');

  @override
  Future<String?> getPlatformVersion() async {
    final version =
        await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
