import org.json.JSONObject
import java.time.Instant
import java.util.*

data class Response(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
) {
    companion object {
        fun fromJson(json: JSONObject): Response {
            return Response(
                status = json.getString("status"),
                totalResults = json.getInt("totalResults"),
                articles = json.getJSONArray("articles").let { articles ->
                    (0 until articles.length()).map { index ->
                        Article.fromJson(articles.getJSONObject(index))
                    }
                }
            )
        }
    }
}

data class Article(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: Date,
    val content: String
) {
    companion object {
        fun fromJson(json: JSONObject): Article {
            return Article(
                source = Source.fromJson(json.getJSONObject("source")),
                author = json.getStringOrEmpty("author"),
                title = json.getString("title"),
                description = json.getString("description"),
                url = json.getString("url"),
                urlToImage = json.getStringOrEmpty("urlToImage"),
                publishedAt = Date(
                    Instant.parse(json.getString("publishedAt")).toEpochMilli()),
                content = json.getStringOrEmpty("content")
            )
        }
    }
}

data class Source(
    val id: String,
    val name: String
) {
    companion object {
        fun fromJson(json: JSONObject): Source {
            return Source(
                id = json.getStringOrEmpty("id"),
                name = json.getString("name")
            )
        }
    }
}

fun JSONObject.getStringOrEmpty(key: String): String {
    return if (this.isNull(key)) "" else this.getString(key)
}