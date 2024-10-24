package org.example

import dev.inmo.tgbotapi.extensions.api.*
import dev.inmo.tgbotapi.extensions.api.edit.text.editMessageText
import dev.inmo.tgbotapi.extensions.api.send.*
import dev.inmo.tgbotapi.extensions.behaviour_builder.*
import dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling.*
import dev.inmo.tgbotapi.extensions.utils.*
import dev.inmo.tgbotapi.extensions.utils.types.buttons.dataButton
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageId
import dev.inmo.tgbotapi.types.RawChatId
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row

suspend fun main() {
    val token: String = System.getenv("BOT_TOKEN")
    val adminChatIdLong = System.getenv("ADMIN_CHAT_ID").toLong()
    val adminChatId = ChatId(RawChatId(adminChatIdLong))
    val bot = telegramBot(token)

    bot.buildBehaviourWithLongPolling {
        onChatJoinRequest {
            val member = it.user
            val messageId = bot.sendMessage(
                chatId = adminChatId,
                text = "User ${member.firstName} with username ${member.username} and premium(${member.possiblyPremiumChatOrNull()?.isPremium}) want to join to ${it.chat.usernameChatOrNull()?.username?.username}"
            ).messageId
            bot.editMessageText(
                adminChatId,
                messageId,
                text = "User ${member.firstName} with username ${member.username} and premium(${member.possiblyPremiumChatOrNull()?.isPremium}) want to join to ${it.chat.usernameChatOrNull()?.username?.username}",
                replyMarkup = InlineKeyboardMarkup(
                    keyboard = matrix {
                        row {
                            dataButton("Просмотрел, удалить уведомление", "deleteNotifivation+${messageId.long}")
                        }
                    }
                )
            )
        }

        onDataCallbackQuery {
            val data = it.data
            if (data.startsWith("deleteNotifivation+")) {
                val messageId = data.split("+")[1].toLong()
                bot.deleteMessage(adminChatId, MessageId(messageId))
            }
        }
    }.join()
}