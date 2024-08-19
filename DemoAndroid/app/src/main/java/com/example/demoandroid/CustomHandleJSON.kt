package com.example.demoandroid

class CustomHandleJSON {

    fun jsonToMapCus(json: String): Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        val jsonString = json.trim().removeSurrounding("{", "}")
        val pairs = splitJsonElements(jsonString)

        for (pair in pairs) {
            val (key, value) = pair.split(":", limit = 2).map { it.trim().removeSurrounding("\"") }
            map[key] = when {
                value.startsWith("{") -> jsonToMapCus(value)
                value.startsWith("[") -> jsonToListCus(value)
                value == "true" -> true
                value == "false" -> false
                value.toIntOrNull() != null -> value.toInt()
                value.toDoubleOrNull() != null -> value.toDouble()
                else -> value
            }
        }
        return map
    }

    fun jsonToListCus(json: String): List<Any> {
        val list = mutableListOf<Any>()
        val jsonArray = json.trim().removeSurrounding("[", "]")
        val elements = splitJsonElements(jsonArray)

        for (element in elements) {
            list.add(
                when {
                    element.startsWith("{") -> jsonToMapCus(element)
                    element.startsWith("[") -> jsonToListCus(element)
                    element == "true" -> true
                    element == "false" -> false
                    element.toIntOrNull() != null -> element.toInt()
                    element.toDoubleOrNull() != null -> element.toDouble()
                    else -> element.removeSurrounding("\"")
                }
            )
        }
        return list
    }

    fun mapToJsonCus(map: Any): String {
        return when (map) {
            is Map<*, *> -> {
                val jsonElements = map.entries.joinToString(",") { (key, value) ->
                    "\"$key\":${mapToJsonCus(value!!)}"
                }
                "{$jsonElements}"
            }
            is List<*> -> {
                val jsonElements = map.joinToString(",") { element ->
                    mapToJsonCus(element!!)
                }
                "[$jsonElements]"
            }
            is String -> "\"$map\""
            else -> map.toString()
        }
    }



    private fun splitJsonElements(json: String): List<String> {
        val elements = mutableListOf<String>()
        var depth = 0
        var start = 0

        for (i in json.indices) {
            when (json[i]) {
                '{', '[' -> depth++
                '}', ']' -> depth--
                ',' -> if (depth == 0) {
                    elements.add(json.substring(start, i).trim())
                    start = i + 1
                }
            }
        }
        elements.add(json.substring(start).trim())
        return elements
    }


}