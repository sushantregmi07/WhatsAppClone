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
 * 50% of names/messages match reference PNGs; 50% are original.
 */
object ChatSeedData {

    private fun dateTime(year: Int, month: Int, day: Int, hour: Int, minute: Int): Instant =
        LocalDate.of(year, month, day)
            .atTime(LocalTime.of(hour, minute))
            .toInstant(ZoneOffset.UTC)

    // --- Conversation IDs (stable, used across summaries and messages) ---
    const val ID_MARTIN = "conv_martin_randolph"
    const val ID_ELENA = "conv_elena_morales"
    const val ID_KAREN = "conv_karen_castillo"
    const val ID_DANIEL = "conv_daniel_abramov"
    const val ID_MARTHA = "conv_martha_craig"
    const val ID_TABITHA = "conv_tabitha_potter"
    const val ID_PRIYA = "conv_priya_sharma"
    const val ID_JAMES = "conv_james_thornton"

    // --- Timestamps for each conversation's latest message ---
    private val tsMartin = dateTime(2019, 11, 19, 14, 0)
    private val tsElena = dateTime(2019, 11, 16, 9, 30)
    private val tsKaren = dateTime(2019, 11, 15, 16, 20)
    private val tsDaniel = dateTime(2019, 10, 30, 11, 15)
    private val tsMartha = dateTime(2019, 10, 28, 11, 51)
    private val tsTabitha = dateTime(2019, 8, 25, 18, 45)
    private val tsPriya = dateTime(2019, 8, 20, 21, 10)
    private val tsJames = dateTime(2019, 7, 29, 15, 30)

    val conversations: List<ConversationSummary> = listOf(
        ConversationSummary(
            id = ID_MARTIN,
            displayName = "Martin Randolph",
            avatar = AvatarKey.MARTIN_RANDOLPH,
            latestMessage = MessageContent.Text("Yes, 2pm is awesome"),
            latestMessageAt = tsMartin,
            unreadCount = 0,
            isMuted = false,
            isArchived = false,
        ),
        ConversationSummary(
            id = ID_ELENA,
            displayName = "Elena Morales",
            avatar = AvatarKey.ELENA_MORALES,
            latestMessage = MessageContent.Text("Have you seen the quarterly report yet?"),
            latestMessageAt = tsElena,
            unreadCount = 0,
            isMuted = false,
            isArchived = false,
        ),
        ConversationSummary(
            id = ID_KAREN,
            displayName = "Karen Castillo",
            avatar = AvatarKey.KAREN_CASTILLO,
            latestMessage = MessageContent.Voice("0:14"),
            latestMessageAt = tsKaren,
            unreadCount = 0,
            isMuted = false,
            isArchived = false,
        ),
        ConversationSummary(
            id = ID_DANIEL,
            displayName = "Daniel Abramov",
            avatar = AvatarKey.DANIEL_ABRAMOV,
            latestMessage = MessageContent.Text("Let me check and get back to you"),
            latestMessageAt = tsDaniel,
            unreadCount = 0,
            isMuted = false,
            isArchived = false,
        ),
        ConversationSummary(
            id = ID_MARTHA,
            displayName = "Martha Craig",
            avatar = AvatarKey.MARTHA_CRAIG,
            latestMessage = MessageContent.Photo,
            latestMessageAt = tsMartha,
            unreadCount = 0,
            isMuted = false,
            isArchived = false,
        ),
        ConversationSummary(
            id = ID_TABITHA,
            displayName = "Tabitha Potter",
            avatar = AvatarKey.TABITHA_POTTER,
            latestMessage = MessageContent.Text(
                "Actually I wanted to check with you about your online business plan on our..."
            ),
            latestMessageAt = tsTabitha,
            unreadCount = 1,
            isMuted = false,
            isArchived = false,
        ),
        ConversationSummary(
            id = ID_PRIYA,
            displayName = "Priya Sharma",
            avatar = AvatarKey.PRIYA_SHARMA,
            latestMessage = MessageContent.Text(
                "Thanks for the feedback, I'll update the draft tonight"
            ),
            latestMessageAt = tsPriya,
            unreadCount = 0,
            isMuted = false,
            isArchived = false,
        ),
        ConversationSummary(
            id = ID_JAMES,
            displayName = "James Thornton",
            avatar = AvatarKey.JAMES_THORNTON,
            latestMessage = MessageContent.Text("See you at the airport!"),
            latestMessageAt = tsJames,
            unreadCount = 0,
            isMuted = false,
            isArchived = false,
        ),
    )

    /**
     * Martha Craig conversation messages in chronological order.
     * Only Martha has a full message thread; other contacts show only previews.
     */
    val marthaCraigMessages: List<Message> = listOf(
        // Jul 25 — before the date separator
        Message(
            id = "msg_martha_01",
            conversationId = ID_MARTHA,
            content = MessageContent.Text("Good bye!"),
            sentAt = dateTime(2019, 7, 25, 17, 47),
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        ),
        // Jul 26 — after "Fri, Jul 26" date separator
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
            sentAt = dateTime(2019, 7, 26, 11, 51),
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
            sentAt = dateTime(2019, 7, 26, 11, 51),
            direction = MessageDirection.SENT,
            deliveryStatus = DeliveryStatus.READ,
        ),
    )
}
