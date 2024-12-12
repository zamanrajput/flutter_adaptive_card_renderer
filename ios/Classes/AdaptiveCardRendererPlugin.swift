import Flutter
import UIKit

public class AdaptiveCardRendererPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let factory = AdaptiveCardViewFactory(messenger: registrar.messenger())
    registrar.register(factory, withId: "adaptive_card_renderer")
  }
}

class AdaptiveCardViewFactory: NSObject, FlutterPlatformViewFactory {
  private var messenger: FlutterBinaryMessenger

  init(messenger: FlutterBinaryMessenger) {
    self.messenger = messenger
    super.init()
  }

  func create(
    withFrame frame: CGRect,
    viewIdentifier viewId: Int64,
    arguments args: Any?
  ) -> FlutterPlatformView {
    let jsonText = (args as? [String: Any])?["json"] as? String
    return AdaptiveCardView(frame: frame, viewId: viewId, jsonText: jsonText)
  }
}

class AdaptiveCardView: NSObject, FlutterPlatformView {
  private var textView: UILabel

  init(frame: CGRect, viewId: Int64, jsonText: String?) {
    textView = UILabel(frame: frame)
    textView.text = "Renderer: \(jsonText ?? "No JSON")"
    textView.textAlignment = .center
    textView.textColor = .black
    textView.font = UIFont.systemFont(ofSize: 20)
  }

  func view() -> UIView {
    return textView
  }
}
