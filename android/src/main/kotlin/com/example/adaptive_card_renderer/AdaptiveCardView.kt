package com.example.adaptive_card_renderer

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import io.flutter.plugin.platform.PlatformView
import org.json.JSONObject

/**
 * Class representing an adaptive card view for rendering dynamic content.
 */
class AdaptiveCardView(context: Context, private var jsonText: String?) : PlatformView {
    private val rootView: LinearLayout = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    init {
        renderViews()
    }

    private fun renderViews() {
        rootView.removeAllViews() // Clear previous views

        // If the JSON text is empty or null, show an error message
        if (jsonText.isNullOrBlank()) {
            rootView.addView(createTextView("Invalid or Empty JSON"))
            return
        }

        try {
            // Create a Render object and call the render method
            val render = Renderer(rootView.context)
            val renderedView = render.render(jsonText)

            // Add the rendered views to the root layout
            rootView.addView(renderedView)
        } catch (e: Exception) {
            rootView.addView(createTextView("Error parsing JSON: ${e.message}"))
        }
    }

    private fun renderViewsOld() {
        rootView.removeAllViews() // Clear previous views
        if (jsonText.isNullOrBlank()) {
            rootView.addView(createTextView("Invalid or Empty JSON"))
            return
        }

        try {
            val jsonObject = JSONObject(jsonText)
            for (key in jsonObject.keys()) {
                // Add views dynamically based on value type
                when (val value = jsonObject.get(key)) {
                    is String -> rootView.addView(createTextView("$key: $value"))
                    is Int, is Double, is Float -> rootView.addView(createTextView("$key: $value"))
                    is Boolean -> rootView.addView(createCheckbox(key, value))
                    else -> rootView.addView(createTextView("$key: Unsupported type"))
                }
            }
        } catch (e: Exception) {
            rootView.addView(createTextView("Error parsing JSON: ${e.message}"))
        }
    }

    private fun createTextView(text: String): TextView {
        return TextView(rootView.context).apply {
            this.text = text
            textSize = 16f
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun createCheckbox(key: String, isChecked: Boolean): View {
        return CheckBox(rootView.context).apply {
            text = key
            this.isChecked = isChecked
        }
    }

    fun updateText(newJson: String?) {
        jsonText = newJson
        renderViews()
    }

    override fun getView(): View {
        return rootView
    }

    override fun dispose() {}
}
