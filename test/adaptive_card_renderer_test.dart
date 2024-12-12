import 'package:flutter_test/flutter_test.dart';
import 'package:adaptive_card_renderer/adaptive_card_renderer.dart';
import 'package:adaptive_card_renderer/adaptive_card_renderer_platform_interface.dart';
import 'package:adaptive_card_renderer/adaptive_card_renderer_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockAdaptiveCardRendererPlatform
    with MockPlatformInterfaceMixin
    implements AdaptiveCardRendererPlatform {
  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final AdaptiveCardRendererPlatform initialPlatform =
      AdaptiveCardRendererPlatform.instance;

  test('$MethodChannelAdaptiveCardRenderer is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelAdaptiveCardRenderer>());
  });
}
