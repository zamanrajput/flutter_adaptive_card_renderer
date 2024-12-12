import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class AdaptiveCardView extends StatefulWidget {
  final String jsonText;

  const AdaptiveCardView({required this.jsonText, super.key});

  @override
  State<AdaptiveCardView> createState() => _AdaptiveCardViewState();
}

class _AdaptiveCardViewState extends State<AdaptiveCardView> {
  static const MethodChannel _channel = MethodChannel('adaptive_card_renderer');
  int? _viewId;

  @override
  Widget build(BuildContext context) {
    return AndroidView(
      viewType: 'adaptive_card_renderer',
      creationParams: {'json': widget.jsonText},
      creationParamsCodec: const StandardMessageCodec(),
      onPlatformViewCreated: (int id) {
        _viewId = id;
        _updateNativeView();
      },
    );
  }

  @override
  void didUpdateWidget(covariant AdaptiveCardView oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (widget.jsonText != oldWidget.jsonText) {
      _updateNativeView();
    }
  }

  Future<void> _updateNativeView() async {
    if (_viewId != null) {
      await _channel
          .invokeMethod('updateView', {'id': _viewId, 'json': widget.jsonText});
    }
  }
}
