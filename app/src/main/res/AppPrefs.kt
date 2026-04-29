import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import kotlin.apply
import kotlin.collections.any
import kotlin.collections.forEach
import kotlin.ranges.until
import kotlin.text.ifBlank

data class SeminarItem(
    val title: String,
    val description: String,
    val date: String
)

data class SeminarRecord(
    val title: String,
    val description: String,
    val date: String,
    val participantName: String,
    val participantEmail: String,
    val participantPhone: String,
    val gender: String
)

object AppPrefs {
    private const val PREF_NAME = "MeowDailyPrefs"

    private const val KEY_LOGGED_IN = "logged_in"
    private const val KEY_PROFILE_NAME = "profile_name"
    private const val KEY_PROFILE_EMAIL = "profile_email"
    private const val KEY_PROFILE_BIO = "profile_bio"
    private const val KEY_PROFILE_IMAGE = "profile_image"
    private const val KEY_CURRENT_SEMINAR = "current_seminar"
    private const val KEY_PREVIOUS_SEMINARS = "previous_seminars"

    private fun prefs(context: android.content.Context) =
        context.getSharedPreferences(PREF_NAME, _root_ide_package_.android.content.Context.MODE_PRIVATE)

    fun setLoggedIn(context: android.content.Context, value: Boolean) {
        prefs(context).edit().putBoolean(KEY_LOGGED_IN, value).apply()
    }

    fun isLoggedIn(context: android.content.Context): Boolean {
        return prefs(context).getBoolean(KEY_LOGGED_IN, false)
    }

    fun getDefaultSeminars(): List<SeminarItem> {
        return _root_ide_package_.kotlin.collections.listOf(
            SeminarItem(
                "The Origin of Cats",
                "Learn how cats first appeared and became part of human history.",
                "Mon, 18th May"
            ),
            SeminarItem(
                "Cats in Ancient Egypt",
                "Explore why cats were respected and protected in Ancient Egyptian culture.",
                "Fri, 6th Nov"
            ),
            SeminarItem(
                "Cats and Human Civilization",
                "Learn how cats became part of human life and culture.",
                "Sun, 11th Jan"
            ),
            SeminarItem(
                "Cat History in Modern Life",
                "Discover the role of cats in today's modern household and society.",
                "Tue, 21st May"
            )
        )
    }

    fun saveProfile(
        context: android.content.Context,
        name: String,
        email: String,
        bio: String,
        imageUri: String?
    ) {
        prefs(context).edit()
            .putString(KEY_PROFILE_NAME, name)
            .putString(KEY_PROFILE_EMAIL, email)
            .putString(KEY_PROFILE_BIO, bio)
            .putString(KEY_PROFILE_IMAGE, imageUri)
            .apply()
    }

    fun getProfileName(context: android.content.Context): String {
        val regPref = context.getSharedPreferences("DataUser", _root_ide_package_.android.content.Context.MODE_PRIVATE)
        return prefs(context).getString(KEY_PROFILE_NAME, "")?.ifBlank {
            regPref.getString("name_save", "Meowie") ?: "Meowie"
        } ?: "Meowie"
    }

    fun getProfileEmail(context: android.content.Context): String {
        val regPref = context.getSharedPreferences("DataUser", _root_ide_package_.android.content.Context.MODE_PRIVATE)
        return prefs(context).getString(KEY_PROFILE_EMAIL, "")?.ifBlank {
            regPref.getString("email_save", "meowie@gmail.com") ?: "meowie@gmail.com"
        } ?: "meowie@gmail.com"
    }

    fun getProfileBio(context: android.content.Context): String {
        return prefs(context).getString(
            KEY_PROFILE_BIO,
            "Cat history enthusiast who loves learning about feline culture and civilization."
        ) ?: ""
    }

    fun getProfileImage(context: android.content.Context): String? {
        return prefs(context).getString(KEY_PROFILE_IMAGE, null)
    }

    fun saveCurrentSeminar(context: android.content.Context, newRecord: SeminarRecord) {
        val current = getCurrentSeminar(context)
        if (current != null) {
            val sameData =
                current.title == newRecord.title &&
                        current.date == newRecord.date &&
                        current.participantEmail == newRecord.participantEmail

            if (!sameData) {
                addPreviousSeminar(context, current)
            }
        }

        prefs(context).edit()
            .putString(KEY_CURRENT_SEMINAR, seminarRecordToJson(newRecord).toString())
            .apply()
    }

    fun getCurrentSeminar(context: android.content.Context): SeminarRecord? {
        val raw = prefs(context).getString(KEY_CURRENT_SEMINAR, null) ?: return null
        return jsonToSeminarRecord(_root_ide_package_.org.json.JSONObject(raw))
    }

    fun clearCurrentSeminar(context: android.content.Context) {
        prefs(context).edit().remove(KEY_CURRENT_SEMINAR).apply()
    }

    fun getPreviousSeminars(context: android.content.Context): MutableList<SeminarRecord> {
        val result = _root_ide_package_.kotlin.collections.mutableListOf<SeminarRecord>()
        val raw = prefs(context).getString(KEY_PREVIOUS_SEMINARS, "[]") ?: "[]"
        val array = _root_ide_package_.org.json.JSONArray(raw)

        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            result.add(jsonToSeminarRecord(obj))
        }
        return result
    }

    private fun savePreviousSeminars(context: android.content.Context, list: List<SeminarRecord>) {
        val array = _root_ide_package_.org.json.JSONArray()
        list.forEach { array.put(seminarRecordToJson(it)) }

        prefs(context).edit()
            .putString(KEY_PREVIOUS_SEMINARS, array.toString())
            .apply()
    }

    fun addPreviousSeminar(context: android.content.Context, record: SeminarRecord) {
        val list = getPreviousSeminars(context)

        val alreadyExist = list.any {
            it.title == record.title &&
                    it.date == record.date &&
                    it.participantEmail == record.participantEmail
        }

        if (!alreadyExist) {
            list.add(0, record)
            savePreviousSeminars(context, list)
        }
    }

    fun logout(context: android.content.Context) {
        prefs(context).edit()
            .putBoolean(KEY_LOGGED_IN, false)
            .apply()
    }

    private fun seminarRecordToJson(record: SeminarRecord): org.json.JSONObject {
        return _root_ide_package_.org.json.JSONObject().apply {
            _root_ide_package_.org.json.JSONObject.put("title", record.title)
            _root_ide_package_.org.json.JSONObject.put("description", record.description)
            _root_ide_package_.org.json.JSONObject.put("date", record.date)
            _root_ide_package_.org.json.JSONObject.put("participantName", record.participantName)
            _root_ide_package_.org.json.JSONObject.put("participantEmail", record.participantEmail)
            _root_ide_package_.org.json.JSONObject.put("participantPhone", record.participantPhone)
            _root_ide_package_.org.json.JSONObject.put("gender", record.gender)
        }
    }

    private fun jsonToSeminarRecord(obj: org.json.JSONObject): SeminarRecord {
        return SeminarRecord(
            title = obj.optString("title"),
            description = obj.optString("description"),
            date = obj.optString("date"),
            participantName = obj.optString("participantName"),
            participantEmail = obj.optString("participantEmail"),
            participantPhone = obj.optString("participantPhone"),
            gender = obj.optString("gender")
        )
    }
}