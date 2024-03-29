import dev.inmo.micro_utils.coroutines.subscribeSafelyWithoutExceptions
import dev.inmo.tgbotapi.extensions.api.chat.get.getChat
import dev.inmo.tgbotapi.extensions.api.send.*
import dev.inmo.tgbotapi.extensions.behaviour_builder.telegramBotWithBehaviourAndLongPolling
import dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling.onContentMessage
import dev.inmo.tgbotapi.extensions.utils.extensions.raw.sender_chat
import dev.inmo.tgbotapi.extensions.utils.extensions.raw.text
import dev.inmo.tgbotapi.extensions.utils.formatting.linkMarkdownV2
import dev.inmo.tgbotapi.extensions.utils.formatting.textMentionMarkdownV2
import dev.inmo.tgbotapi.extensions.utils.ifChannelChat
import dev.inmo.tgbotapi.extensions.utils.ifFromChannelGroupContentMessage
import dev.inmo.tgbotapi.types.chat.*
import dev.inmo.tgbotapi.types.chat.GroupChat
import dev.inmo.tgbotapi.types.chat.PrivateChat
import dev.inmo.tgbotapi.types.chat.SupergroupChat
import dev.inmo.tgbotapi.types.message.MarkdownV2
import dev.inmo.tgbotapi.utils.PreviewFeature
import dev.inmo.tgbotapi.utils.extensions.escapeMarkdownV2Common
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import java.time.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * The main purpose of this bot is just to answer "Oh, hi, " and add user mention here
 */
@OptIn(PreviewFeature::class)
suspend fun main() {
    //val botToken = args.first()
    telegramBotWithBehaviourAndLongPolling(System.getenv("TOKEN"), CoroutineScope(Dispatchers.IO)) {
        onContentMessage { message ->
            val chat = message.chat
            val messageText = message.text ?: ""
            
            val current = LocalDate.now()

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            
            val concert = LocalDate.parse("2023-04-06", formatter)

            val period = Period.between(current, concert)
            val months = period.months
            val days = period.days
            
            val countdown = "time to vacaciones is: $months months and $days days"



            val sentence = when {
                messageText.contains("Arnold", ignoreCase = true) -> "I am making things up again"
                messageText.contains("hello", ignoreCase = true) -> "Hello\\, my name is Elder Cunningham"
                messageText.contains("semana santa", ignoreCase = true) -> countdown
                messageText.contains("vacaciones", ignoreCase = true) -> countdown

                else -> ""
            }

            if (sentence.isEmpty() || sentence.isBlank()) return@onContentMessage

            send(chat, sentence, MarkdownV2)
        }
        allUpdatesFlow.subscribeSafelyWithoutExceptions(this) { println(it) }
    }.second.join()
}
