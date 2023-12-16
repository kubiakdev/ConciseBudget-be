package kubiakdev.com.test.route.friends

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import junit.framework.TestCase.assertEquals
import kotlinx.serialization.encodeToString
import kubiakdev.com.app.authentication.firebase.util.AuthenticationConst
import kubiakdev.com.data.database.model.friend.toEntityModel
import kubiakdev.com.domain.model.friend.Friend
import kubiakdev.com.route.friends.v1.model.FriendRouteModel
import kubiakdev.com.route.friends.v1.model.FriendsRouteModel
import kubiakdev.com.route.friends.v1.model.toRouteModel
import kubiakdev.com.route.util.RouteConst
import kubiakdev.com.util.TestConst
import kubiakdev.com.util.provider.json
import kubiakdev.com.util.testWithUserCreation
import org.junit.Test

class FriendsRouteTest {

    @Test
    fun `GIVEN logged in user THEN verify whole friends flow THEN success`() = testWithUserCreation { signUpResponse ->
        // load all friends to get newly created list with creation inside
        client.get(RouteConst.ROUTE_V1_FRIENDS) {
            contentType(ContentType.Application.Json)
            header(AuthenticationConst.AUTH_HEADER, "${AuthenticationConst.AUTH_SCHEME} ${signUpResponse.token}")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val response = json.decodeFromString<FriendsRouteModel>(bodyAsText())
            assertEquals(response.friends, emptyList<FriendRouteModel>())
        }

        val exampleFriend =  Friend(
            authId = signUpResponse.authId,
            username = TestConst.EXAMPLE_FRIEND_USERNAME,
        )

        // add friend
        client.post(RouteConst.ROUTE_V1_FRIEND) {
            contentType(ContentType.Application.Json)
            header(AuthenticationConst.AUTH_HEADER, "${AuthenticationConst.AUTH_SCHEME} ${signUpResponse.token}")
            setBody(json.encodeToString(exampleFriend.toEntityModel()))
        }.apply {
            assertEquals(HttpStatusCode.NoContent, status)
        }

        // second load for verifying added friend
        client.get(RouteConst.ROUTE_V1_FRIENDS) {
            contentType(ContentType.Application.Json)
            header(AuthenticationConst.AUTH_HEADER, "${AuthenticationConst.AUTH_SCHEME} ${signUpResponse.token}")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            val response = json.decodeFromString<FriendsRouteModel>(bodyAsText())
            assertEquals(response.friends, listOf(exampleFriend.toRouteModel()))
        }
    }
}
