package com.example.adaptive_card_renderer

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodChannel

/**
 * A Flutter plugin that provides Adaptive Card rendering capabilities.
 */
class AdaptiveCardRendererPlugin : FlutterPlugin, ActivityAware {

    companion object {
         var activity: FragmentActivity? = null

        /**
         * Gets the FragmentManager for the current activity, if available.
         */
        fun getFragmentManager(): FragmentManager? {
            return activity?.supportFragmentManager
        }
    }

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        val factory = AdaptiveCardViewFactory(binding.applicationContext)
        binding.platformViewRegistry.registerViewFactory("adaptive_card_renderer", factory)

        val channel = MethodChannel(binding.binaryMessenger, "adaptive_card_renderer")
        channel.setMethodCallHandler { call, result ->
            if (call.method == "updateView") {
                val id = call.argument<Int>("id") ?: return@setMethodCallHandler
                val jsonText = call.argument<String>("json")
                factory.updateView(id, jsonText)
                result.success(null)
            } else {
                result.notImplemented()
            }
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        // Perform any necessary cleanup when the plugin is detached
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity as? FragmentActivity
    }

    override fun onDetachedFromActivity() {
        activity = null
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        activity = binding.activity as? FragmentActivity
    }

    override fun onDetachedFromActivityForConfigChanges() {
        activity = null
    }
}
