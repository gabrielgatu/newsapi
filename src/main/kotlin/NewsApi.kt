class NewsApi(val apiKey: String) {

    fun topHeadlines(configBlock: Config.TopHeadlines.() -> Unit): Response? {
        val config = Config.TopHeadlines().apply(configBlock)
        val params = mutableMapOf<String, String>().apply {
            putIfNotNull("q", config.query)
            putIfNotNull("country", config.country)
            putIfNotNull("category", config.category)
            putIfNotNull("sources", config.sources)
            put("apiKey", apiKey)
        }
        return makeRequest("top-headlines", params)
    }

    fun everything(configBlock: Config.Everything.() -> Unit): Response? {
        val config = Config.Everything().apply(configBlock)
        val params = mutableMapOf<String, String>().apply {
            putIfNotNull("q", config.query)
            putIfNotNull("from", config.from)
            putIfNotNull("language", config.language)
            putIfNotNull("sortBy", config.sortBy)
            putIfNotNull("domains", config.domains)
            put("apiKey", apiKey)
        }
        return makeRequest("everything", params)
    }

    private fun makeRequest(endpoint: String, params: MutableMap<String, String>): Response? {
        val response = khttp.get("https://newsapi.org/v2/$endpoint", params = params)

        return when (response.statusCode) {
            200 -> Response.fromJson(response.jsonObject)
            else -> null
        }
    }
}

fun <T, V> MutableMap<T, V>.putIfNotNull(key: T, value: V?) {
    value?.let {
        this[key] = it
    }
}