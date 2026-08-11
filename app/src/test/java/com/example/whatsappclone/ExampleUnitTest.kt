package com.example.whatsappclone

import com.example.whatsappclone.navigation.ConversationRoute
import com.example.whatsappclone.navigation.PhoneAuthorizationRoute
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun phoneAuthorizationRoute_exists() {
        assertNotNull(PhoneAuthorizationRoute)
    }

    @Test
    fun conversationRoute_preservesContactId() {
        val route = ConversationRoute(contactId = "martha_craig")
        assertEquals("martha_craig", route.contactId)
    }
}
