package com.example.adaptive_card_renderer

import android.content.Context
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory
import io.flutter.plugin.common.StandardMessageCodec

/**
 * Factory class for creating Adaptive Card views.
 */
class AdaptiveCardViewFactory(private val context: Context) :
    PlatformViewFactory(StandardMessageCodec.INSTANCE) {

    private val views = mutableMapOf<Int, AdaptiveCardView>()

    override fun create(context: Context, id: Int, args: Any?): PlatformView {
        val jsonText = (args as? Map<String, Any>)?.get("json") as? String
        val view = AdaptiveCardView(context, jsonText)
        views[id] = view
        return view
    }

    fun updateView(id: Int, jsonText: String?) {
        views[id]?.updateText(jsonText)
    }
}
