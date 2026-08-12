package com.example.whatsappclone.data.seed

import com.example.whatsappclone.domain.model.AvatarKey
import com.example.whatsappclone.domain.model.ConversationSummary
import com.example.whatsappclone.domain.model.DeliveryStatus
import com.example.whatsappclone.domain.model.Message
import com.example.whatsappclone.domain.model.MessageContent
import com.example.whatsappclone.domain.model.MessageDirection
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset

/**
 * Deterministic seed data matching SEED_DATA_REFERENCE.md.
 * Every user on the Chats page has a corresponding conversation with messages.
 * Each conversation summary's latestMessage/latestMessageAt is derived from its
 * actual last message to keep Chats previews synchronized.
 */
object ChatSeedData {

    private fun dateTime(year: Int, month: Int, day: Int, hour: Int, minute: Int): Instant =
        LocalDate.of(year, month, day)
            .atTime(LocalTime.of(hour, minute))
            .toInstant(ZoneOffset.UTC)

    const val ID_MARTIN = "conv_martin_randolph"
    const val ID_ELENA = "conv_elena_morales"
    const val ID_KAREN = "conv_karen_castillo"
    const val ID_DANIEL = "conv_daniel_abramov"
    const val ID_MARTHA = "conv_martha_craig"
    const val ID_TABITHA = "conv_tabitha_potter"
    const val ID_PRIYA = "conv_priya_sharma"
    const val ID_JAMES = "conv_james_thornton"

    // --- Martin Randolph messages ---
    val martinMessages: List<Message> = listOf(
        Message(
            id = "msg_martin_01",
            conversationId = ID_MARTIN,
            content = MessageContent.Text("Hey, are you free for lunch tomorrow?"),
            sentAt = dateTime(2019, 11, 18, 9, 30),
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_martin_02",
            conversationId = ID_MARTIN,
            content = MessageContent.Text("Sure, what time works for you?"),
            sentAt = dateTime(2019, 11, 18, 10, 15),
            direction = MessageDirection.RECEIVED,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_martin_03",
            conversationId = ID_MARTIN,
            content = MessageContent.Text("How about 2pm?"),
            sentAt = dateTime(2019, 11, 19, 13, 45),
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_martin_04",
            conversationId = ID_MARTIN,
            content = MessageContent.Text("Yes, 2pm is awesome"),
            sentAt = dateTime(2019, 11, 19, 14, 0),
            direction = MessageDirection.RECEIVED,
            deliveryStatus = DeliveryStatus.READ,
        ),
    )

    // --- Elena Morales messages ---
    val elenaMessages: List<Message> = listOf(
        Message(
            id = "msg_elena_01",
            conversationId = ID_ELENA,
            content = MessageContent.Text("I sent the updated slides to the team just now"),
            sentAt = dateTime(2019, 11, 15, 16, 0),
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_elena_02",
            conversationId = ID_ELENA,
            content = MessageContent.Text("Great, I'll review them tonight"),
            sentAt = dateTime(2019, 11, 15, 17, 30),
            direction = MessageDirection.RECEIVED,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_elena_03",
            conversationId = ID_ELENA,
            content = MessageContent.Text("Have you seen the quarterly report yet?"),
            sentAt = dateTime(2019, 11, 16, 9, 30),
            direction = MessageDirection.RECEIVED,
            deliveryStatus = DeliveryStatus.READ,
        ),
    )

    // --- Karen Castillo messages ---
    val karenMessages: List<Message> = listOf(
        Message(
            id = "msg_karen_01",
            conversationId = ID_KAREN,
            content = MessageContent.Text("Can you call me when you get a chance?"),
            sentAt = dateTime(2019, 11, 14, 11, 0),
            direction = MessageDirection.RECEIVED,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_karen_02",
            conversationId = ID_KAREN,
            content = MessageContent.Text("Sure, I'll call you after my meeting"),
            sentAt = dateTime(2019, 11, 14, 11, 30),
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_karen_03",
            conversationId = ID_KAREN,
            content = MessageContent.Voice("0:14"),
            sentAt = dateTime(2019, 11, 15, 16, 20),
            direction = MessageDirection.RECEIVED,
            deliveryStatus = DeliveryStatus.READ,
        ),
    )

    // --- Daniel Abramov messages ---
    val danielMessages: List<Message> = listOf(
        Message(
            id = "msg_daniel_01",
            conversationId = ID_DANIEL,
            content = MessageContent.Text("Did you get the package I sent last week?"),
            sentAt = dateTime(2019, 10, 29, 14, 0),
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_daniel_02",
            conversationId = ID_DANIEL,
            content = MessageContent.Text("Not yet, I think it might be delayed because of the holiday weekend"),
            sentAt = dateTime(2019, 10, 29, 15, 20),
            direction = MessageDirection.RECEIVED,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_daniel_03",
            conversationId = ID_DANIEL,
            content = MessageContent.Text("Let me check and get back to you"),
            sentAt = dateTime(2019, 10, 30, 11, 15),
            direction = MessageDirection.RECEIVED,
            deliveryStatus = DeliveryStatus.READ,
        ),
    )

    // --- Martha Craig messages (full thread from SEED_DATA_REFERENCE) ---
    val marthaMessages: List<Message> = listOf(
        Message(
            id = "msg_martha_01",
            conversationId = ID_MARTHA,
            content = MessageContent.Text("Good bye!"),
            sentAt = dateTime(2019, 7, 25, 17, 47),
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_martha_02",
            conversationId = ID_MARTHA,
            content = MessageContent.Text("Good morning!"),
            sentAt = dateTime(2019, 7, 26, 10, 10),
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_martha_03",
            conversationId = ID_MARTHA,
            content = MessageContent.Text("The view from the hotel is incredible!"),
            sentAt = dateTime(2019, 7, 26, 10, 10),
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_martha_04",
            conversationId = ID_MARTHA,
            content = MessageContent.Document(
                fileName = "IMG_0475",
                sizeLabel = "2.4 MB",
                extension = "png",
            ),
            sentAt = dateTime(2019, 7, 26, 10, 15),
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_martha_05",
            conversationId = ID_MARTHA,
            content = MessageContent.Document(
                fileName = "IMG_0312",
                sizeLabel = "3.1 MB",
                extension = "png",
            ),
            sentAt = dateTime(2019, 7, 26, 10, 15),
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_martha_06",
            conversationId = ID_MARTHA,
            content = MessageContent.Text("Do you know what time is it?"),
            sentAt = dateTime(2019, 7, 26, 11, 40),
            direction = MessageDirection.RECEIVED,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_martha_07",
            conversationId = ID_MARTHA,
            content = MessageContent.Text("It's morning in Tokyo \uD83D\uDE0E"),
            sentAt = dateTime(2019, 7, 26, 11, 43),
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_martha_08",
            conversationId = ID_MARTHA,
            content = MessageContent.Text("Have you tried the ramen there?"),
            sentAt = dateTime(2019, 7, 26, 11, 45),
            direction = MessageDirection.RECEIVED,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_martha_09",
            conversationId = ID_MARTHA,
            content = MessageContent.Text("Do you like it?"),
            sentAt = dateTime(2019, 7, 26, 11, 45),
            direction = MessageDirection.RECEIVED,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_martha_10",
            conversationId = ID_MARTHA,
            content = MessageContent.Text("Absolutely, the best I've ever had!"),
            sentAt = dateTime(2019, 7, 26, 11, 50),
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_martha_11",
            conversationId = ID_MARTHA,
            content = MessageContent.Document(
                fileName = "IMG_0483",
                sizeLabel = "2.8 MB",
                extension = "png",
            ),
            sentAt = dateTime(2019, 10, 28, 11, 51),
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_martha_12",
            conversationId = ID_MARTHA,
            content = MessageContent.Document(
                fileName = "IMG_0517",
                sizeLabel = "2.2 MB",
                extension = "png",
            ),
            sentAt = dateTime(2019, 10, 28, 11, 51),
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        ),
    )

    // --- Tabitha Potter messages ---
    val tabithaMessages: List<Message> = listOf(
        Message(
            id = "msg_tabitha_01",
            conversationId = ID_TABITHA,
            content = MessageContent.Text("Hi! Long time no see"),
            sentAt = dateTime(2019, 8, 24, 10, 0),
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_tabitha_02",
            conversationId = ID_TABITHA,
            content = MessageContent.Text("Hey! Yes it's been a while, how are you doing?"),
            sentAt = dateTime(2019, 8, 24, 12, 30),
            direction = MessageDirection.RECEIVED,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_tabitha_03",
            conversationId = ID_TABITHA,
            content = MessageContent.Text(
                "Actually I wanted to check with you about your online business plan on our..."
            ),
            sentAt = dateTime(2019, 8, 25, 18, 45),
            direction = MessageDirection.RECEIVED,
            deliveryStatus = DeliveryStatus.READ,
        ),
    )

    // --- Priya Sharma messages ---
    val priyaMessages: List<Message> = listOf(
        Message(
            id = "msg_priya_01",
            conversationId = ID_PRIYA,
            content = MessageContent.Text("Could you review the design mockups I uploaded?"),
            sentAt = dateTime(2019, 8, 19, 15, 0),
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_priya_02",
            conversationId = ID_PRIYA,
            content = MessageContent.Text("They look really good overall, just a few minor notes on the color palette"),
            sentAt = dateTime(2019, 8, 20, 14, 30),
            direction = MessageDirection.RECEIVED,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_priya_03",
            conversationId = ID_PRIYA,
            content = MessageContent.Text(
                "Thanks for the feedback, I'll update the draft tonight"
            ),
            sentAt = dateTime(2019, 8, 20, 21, 10),
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        ),
    )

    // --- James Thornton messages ---
    val jamesMessages: List<Message> = listOf(
        Message(
            id = "msg_james_01",
            conversationId = ID_JAMES,
            content = MessageContent.Text("Flight confirmed for next Friday. Terminal 2, gate B7"),
            sentAt = dateTime(2019, 7, 28, 20, 0),
            direction = MessageDirection.RECEIVED,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_james_02",
            conversationId = ID_JAMES,
            content = MessageContent.Text("Perfect, I'll pick you up. What time does it land?"),
            sentAt = dateTime(2019, 7, 29, 8, 15),
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        ),
        Message(
            id = "msg_james_03",
            conversationId = ID_JAMES,
            content = MessageContent.Text("See you at the airport!"),
            sentAt = dateTime(2019, 7, 29, 15, 30),
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        ),
    )

    /**
     * All messages keyed by conversation ID.
     * Used by InMemoryChatRepository to provide per-user conversations.
     */
    val allMessages: Map<String, List<Message>> = mapOf(
        ID_MARTIN to martinMessages,
        ID_ELENA to elenaMessages,
        ID_KAREN to karenMessages,
        ID_DANIEL to danielMessages,
        ID_MARTHA to marthaMessages,
        ID_TABITHA to tabithaMessages,
        ID_PRIYA to priyaMessages,
        ID_JAMES to jamesMessages,
    )

    /**
     * Builds conversation summaries from the actual message data.
     * The latestMessage and latestMessageAt are derived from each conversation's
     * actual last message so Chats previews stay synchronized.
     */
    val conversations: List<ConversationSummary> by lazy {
        data class ConvMeta(
            val id: String,
            val displayName: String,
            val avatar: AvatarKey,
            val unreadCount: Int = 0,
        )

        val metas = listOf(
            ConvMeta(ID_MARTIN, "Martin Randolph", AvatarKey.MARTIN_RANDOLPH),
            ConvMeta(ID_ELENA, "Elena Morales", AvatarKey.ELENA_MORALES),
            ConvMeta(ID_KAREN, "Karen Castillo", AvatarKey.KAREN_CASTILLO),
            ConvMeta(ID_DANIEL, "Daniel Abramov", AvatarKey.DANIEL_ABRAMOV),
            ConvMeta(ID_MARTHA, "Martha Craig", AvatarKey.MARTHA_CRAIG),
            ConvMeta(ID_TABITHA, "Tabitha Potter", AvatarKey.TABITHA_POTTER, unreadCount = 1),
            ConvMeta(ID_PRIYA, "Priya Sharma", AvatarKey.PRIYA_SHARMA),
            ConvMeta(ID_JAMES, "James Thornton", AvatarKey.JAMES_THORNTON),
        )

        metas.map { meta ->
            val messages = allMessages[meta.id].orEmpty()
            val lastMsg = messages.lastOrNull()
            val previewContent: MessageContent? = lastMsg?.content
            ConversationSummary(
                id = meta.id,
                displayName = meta.displayName,
                avatar = meta.avatar,
                latestMessage = previewContent,
                latestMessageAt = lastMsg?.sentAt,
                unreadCount = meta.unreadCount,
                isMuted = false,
                isArchived = false,
            )
        }
    }
}
