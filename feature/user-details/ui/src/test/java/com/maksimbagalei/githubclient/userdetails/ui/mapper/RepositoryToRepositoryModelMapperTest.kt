package com.maksimbagalei.githubclient.userdetails.ui.mapper

import com.maksimbagalei.githubclient.common.test.BaseTest
import com.maksimbagalei.githubclient.userdetails.data.dto.Repository
import org.junit.Test
import kotlin.test.assertEquals

class RepositoryToRepositoryModelMapperTest : BaseTest() {
    private lateinit var mapper: RepositoryToRepositoryModelMapper
    override fun setUp() {
        super.setUp()
        mapper = RepositoryToRepositoryModelMapper()
    }

    @Test
    fun `all values are mapped correctly`() {
        val mock = Repository(
            id = 0,
            name = "repo",
            language = "kotlin",
            stargazers = 1,
            description = "desc",
            url = "google.com",
            isFork = true
        )
        val result = mapper.map(mock)

        assertEquals(0L, result.id)
        assertEquals("repo", result.name)
        assertEquals("kotlin", result.language)
        assertEquals("1", result.stargazers)
        assertEquals("desc", result.description)
        assertEquals("google.com", result.url)
    }
}
