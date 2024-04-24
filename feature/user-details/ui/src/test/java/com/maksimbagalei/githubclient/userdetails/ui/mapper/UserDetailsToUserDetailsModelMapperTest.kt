package com.maksimbagalei.githubclient.userdetails.ui.mapper

import com.maksimbagalei.githubclient.common.test.BaseTest
import com.maksimbagalei.githubclient.userdetails.data.dto.UserDetails
import org.junit.Test
import kotlin.test.assertEquals

class UserDetailsToUserDetailsModelMapperTest : BaseTest() {
    private lateinit var mapper: UserDetailsToUserDetailsModelMapper
    override fun setUp() {
        super.setUp()
        mapper = UserDetailsToUserDetailsModelMapper()
    }

    @Test
    fun `all values are mapped correctly`() {
        val mock = UserDetails(
            login = "login",
            avatarUrl = "avatar_url",
            name = "name",
            followers = 5,
            following = 3
        )
        val result = mapper.map(mock)

        assertEquals("login", result.login)
        assertEquals("avatar_url", result.avatarUrl)
        assertEquals("name", result.name)
        assertEquals("5", result.followers)
        assertEquals("3", result.following)
    }
}