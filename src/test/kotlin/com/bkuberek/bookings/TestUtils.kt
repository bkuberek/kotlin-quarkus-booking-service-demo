package com.bkuberek.bookings

import org.apache.commons.text.StringEscapeUtils
import org.json.JSONObject
import java.util.stream.Collectors

fun String.asResource(work: (String) -> Unit) {
    val content = this.javaClass::class.java.getResource(this)?.readText()
    return if (content != null) {
        work(content)
    } else {
        work("RESOURCE_NOT_FOUND")
    }
}

fun loadGraphqlQueryAsJson(resource: String): String {
    return loadGraphqlQueryAsJson(resource, "")
}

fun loadGraphqlQueryAsJson(resource: String, variables: Map<String, Any>): String {
    return loadGraphqlQueryAsJson(resource, JSONObject(variables).toString())
}

fun loadGraphqlQueryAsJson(resource: String, variables: String?): String {
    val query = object {}.javaClass.getResourceAsStream(resource)
        ?.bufferedReader()?.readLines()?.stream()
        ?.collect(Collectors.joining("\n"))
    val quotedQuery = StringEscapeUtils.escapeJson(query)
    val quotedVariables = if (variables.isNullOrEmpty()) {
        ""
    } else {
        """, "variables": %s""".format(variables)
    }
    val json = """{ "query": "$quotedQuery"$quotedVariables}"""
    return json
}