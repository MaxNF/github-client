package com.maksimbagalei.githubclient.userlist.ui.mapper

import com.maksimbagalei.githubclient.common.test.BaseTest
import com.maksimbagalei.githubclient.userlist.data.dto.UserBrief
import org.junit.Test

class UserBriefToUserBriefModelMapperTest : BaseTest() {
    private lateinit var mapper: UserBriefToUserBriefModelMapper
    override fun setUp() {
        super.setUp()
        mapper = UserBriefToUserBriefModelMapper()
    }

    @Test
    fun `all values are mapped correctly`() {
        val mock = UserBrief(
            id = 0,
            login = "login",
            avatarUrl = "avatar_url"
        )
        val result = mapper.map(mock)

        kotlin.test.assertEquals(0, result.id)
        kotlin.test.assertEquals("login", result.login)
        kotlin.test.assertEquals("avatar_url", result.avatarUrl)
    }
}