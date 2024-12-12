import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'adaptive_card_renderer_method_channel.dart';

abstract class AdaptiveCardRendererPlatform extends PlatformInterface {
  /// Constructs a AdaptiveCardRendererPlatform.
  AdaptiveCardRendererPlatform() : super(token: _token);

  static final Object _token = Object();

  static AdaptiveCardRendererPlatform _instance =
      MethodChannelAdaptiveCardRenderer();

  /// The default instance of [AdaptiveCardRendererPlatform] to use.
  ///
  /// Defaults to [MethodChannelAdaptiveCardRenderer].
  static AdaptiveCardRendererPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [AdaptiveCardRendererPlatform] when
  /// they register themselves.
  static set instance(AdaptiveCardRendererPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
