package com.example.androidapp.models.factories

import com.example.androidapp.models.ContainerElement
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

fun interface CardFactory {
    fun create(card: JSONObject): ContainerElement.Card?
}

class CardFactoryImpl @Inject constructor(
    private val actionFactory: ActionFactory,
    private val buttonFactory: ButtonFactory,
    private val imageFactory: ImageFactory
): CardFactory {
    override fun create(card: JSONObject): ContainerElement.Card {
        val hasAction = !card.isNull("action")
        val hasLinks = !card.isNull("links")
        val hasMedia = !card.isNull("media")
        return ContainerElement.Card(
            primary = card.getString("primary"),
            secondaries = getSecondaries(card.getJSONArray("secondaries")),
            action = if (hasAction) actionFactory.create(card.getJSONObject("action")) else null,
            links = if (hasLinks) {
                val links = card.getJSONArray("links")
                val output: MutableList<ContainerElement.Button> = emptyList<ContainerElement.Button>().toMutableList()
                var index = 0
                while (index < links.length()) {
                    output.add(buttonFactory.create(links.getJSONObject(index)))
                    index++
                }
                output
            } else {
                null
            },
            media = if (hasMedia) imageFactory.create(card.getJSONObject("media")) else null
        )
    }

    private fun getSecondaries(secondaries: JSONArray): List<String> {
        val result: MutableList<String> = mutableListOf()
        var index = 0
        while (index < secondaries.length()) {
            result.add(secondaries.getString(index))
            index++
        }
        return result
    }
}