package com.maksimbagalei.githubclient.userdetails.ui.mapper

import com.maksimbagalei.githubclient.common.ui.mapper.Mapper
import com.maksimbagalei.githubclient.userdetails.data.dto.Repository
import com.maksimbagalei.githubclient.userdetails.ui.model.RepositoryModel

internal class RepositoryToRepositoryModelMapper : Mapper<Repository, RepositoryModel> {
    override fun map(from: Repository): RepositoryModel = RepositoryModel(
        name = from.name,
        language = from.language,
        stargazers = from.stargazers.toString(),
        description = from.description,
        url = from.url
    )
}