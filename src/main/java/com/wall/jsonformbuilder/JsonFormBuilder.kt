package com.wall.jsonformbuilder

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.widget.ArrayAdapter
import com.google.gson.JsonArray
import org.jetbrains.anko.*

@SuppressLint("ResourceType")
fun verticalFormLayout(layout: JsonArray, activity: Activity, theme: Int = 0) {
    val arrayAdapater = ArrayAdapter<String>(activity, 4, listOf("yup"))

    val spinnerAdapters = mutableMapOf<Int, List<String>>()
    activity.verticalLayout(theme = theme) {
        for (i in 0 until layout.size()) {
            val obj = layout.get(i).asJsonObject

            val view: View
            when (obj["type"].asString) {
                "edit" -> {
                    view = if (obj.has("text")) editText(obj["text"].asString) else editText()
                }
                "text" -> {
                    view = if (obj.has("text")) textView(obj["text"].asString) else textView()
                }
                "button" -> {
                    view = if (obj.has("text")) button(obj["text"].asString) else button()
                }
                "spinner" -> {
                    view = spinner {
                        prompt = "prompt"
                        if (obj.has("objects")) {
                            adapter = ArrayAdapter<String>(
                                activity,
                                android.R.layout.simple_spinner_dropdown_item,
                                obj["objects"].asJsonArray.toStringList()
                            )
                        }
                    }
                }
                else -> {
                    view = view()
                }
            }

            if (obj.has("id")) view.id = obj["id"].asInt
            if (obj.has("padding")) view.padding = obj["padding"].asInt
            if (obj.has("margin")) {
                view.lparams(width = wrapContent) { margin = obj["margin"].asInt }
            }
        }
    }

//    for(adapter in spinnerAdapters) {
//        activity.findViewById<Spinner>(adapter.key).adapter = ArrayAdapter(activity, adapter.key, adapter.value)
//    }
}

fun JsonArray.toStringList(): List<String> {
    val list = mutableListOf<String>()
    this.forEach {
        list.add(it.asString)
    }
    return list
}
