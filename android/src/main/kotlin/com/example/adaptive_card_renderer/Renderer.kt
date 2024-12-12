package com.example.adaptive_card_renderer
import android.widget.TextView
import android.content.Context
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity

import io.adaptivecards.objectmodel.AdaptiveCard
import io.adaptivecards.objectmodel.BaseActionElement
import io.adaptivecards.objectmodel.BaseCardElement
import io.adaptivecards.renderer.AdaptiveCardRenderer
import io.adaptivecards.renderer.RenderedAdaptiveCard
import io.adaptivecards.renderer.actionhandler.ICardActionHandler
import org.json.JSONObject
import org.json.JSONArray

class Renderer(private val context: Context) {
    fun getJsonObjectString(): String {
        val factSet = JSONArray().apply {
            put(JSONObject().put("title", "Board:").put("value", "Adaptive Cards"))
            put(JSONObject().put("title", "List:").put("value", "Backlog"))
            put(JSONObject().put("title", "Assigned to:").put("value", "Matt Hidinger"))
            put(JSONObject().put("title", "Due date:").put("value", "Not set"))
        }

        val columnSet = JSONArray().apply {
            put(JSONObject().apply {
                put("type", "Column")
                put("items", JSONArray().apply {
                    put(JSONObject().apply {
                        put("type", "Image")
                        put("style", "Person")
                        put(
                            "url",
                            "https://pbs.twimg.com/profile_images/3647943215/d7f12830b3c17a5a9e4afcc370e3a37e_400x400.jpeg"
                        )
                        put("altText", "Matt Hidinger")
                        put("size", "Small")
                    })
                })
                put("width", "auto")
            })
            put(JSONObject().apply {
                put("type", "Column")
                put("items", JSONArray().apply {
                    put(
                        JSONObject().put("type", "TextBlock").put("weight", "Bolder")
                            .put("text", "Matt Hidinger").put("wrap", true)
                    )
                    put(
                        JSONObject().put("type", "TextBlock").put("spacing", "None")
                            .put("text", "Created {{DATE(2017-02-14T06:08:39Z,SHORT)}}")
                            .put("isSubtle", true).put("wrap", true)
                    )
                })
                put("width", "stretch")
            })
        }

        val actions = JSONArray().apply {
            put(JSONObject().apply {
                put("type", "Action.ShowCard")
                put("title", "Set due date")
                put("card", JSONObject().apply {
                    put("type", "AdaptiveCard")
                    put("body", JSONArray().apply {
                        put(JSONObject().put("type", "Input.Date").put("id", "dueDate"))
                        put(
                            JSONObject().put("type", "Input.Text").put("id", "comment")
                                .put("placeholder", "Add a comment").put("isMultiline", true)
                        )
                    })
                    put("actions", JSONArray().apply {
                        put(JSONObject().put("type", "Action.Submit").put("title", "OK"))
                    })
                    put("\$schema", "http://adaptivecards.io/schemas/adaptive-card.json")
                })
            })
            put(JSONObject().apply {
                put("type", "Action.OpenUrl")
                put("title", "View")
                put("url", "https://adaptivecards.io")
            })
        }

        val jsonObject = JSONObject().apply {
            put("type", "AdaptiveCard")
            put("\$schema", "http://adaptivecards.io/schemas/adaptive-card.json")
            put("version", "1.6")
            put("body", JSONArray().apply {
                put(
                    JSONObject().put("type", "TextBlock").put("size", "Medium")
                        .put("weight", "Bolder").put("text", "Publish Adaptive Card Schema")
                )
                put(JSONObject().put("type", "ColumnSet").put("columns", columnSet))
                put(
                    JSONObject().put("type", "TextBlock").put(
                        "text",
                        "Now that we have defined the main rules and features of the format, we need to produce a schema and publish it to GitHub. The schema will be the starting point of our reference documentation."
                    ).put("wrap", true)
                )
                put(JSONObject().put("type", "FactSet").put("facts", factSet))
            })
            put("actions", actions)
        }

        return jsonObject.toString(4) // Pretty print with 4-space indentation
    }

    fun render(jsonText: String?): View {
        if (jsonText.isNullOrBlank()) {
            return createErrorView("Invalid or Empty JSON")
        }

        return try {
            createViewFromJson(jsonText)
                ?: createErrorView("Context is not a FragmentActivity or unsupported JSON")
        } catch (e: Exception) {
            createErrorView("Error parsing JSON: ${e.message}")
        }
    }

    private fun createViewFromJson(jsonString: String): View? {


            val parseResult = AdaptiveCard.DeserializeFromString(
                jsonString, AdaptiveCardRenderer.VERSION
            )

            return AdaptiveCardRenderer.getInstance().render(
                AdaptiveCardRendererPlugin.activity,
               AdaptiveCardRendererPlugin.getFragmentManager() ,
                parseResult.GetAdaptiveCard(),
                object : ICardActionHandler {
                    override fun onAction(action: BaseActionElement?, card: RenderedAdaptiveCard?) {
                        // Handle action
                    }

                    override fun onMediaPlay(cardElement: BaseCardElement?, card: RenderedAdaptiveCard?) {
                        // Handle media play
                    }

                    override  fun onMediaStop(cardElement: BaseCardElement?, card: RenderedAdaptiveCard?) {
                        // Handle media stop
                    }
                }
            ).view

    }

    private fun createErrorView(message: String): View {
        val rootView = LinearLayout(context)
        rootView.orientation = LinearLayout.VERTICAL
        rootView.addView(TextView(context).apply {
            text = message
            textSize = 16f
        })
        return rootView
    }
}
