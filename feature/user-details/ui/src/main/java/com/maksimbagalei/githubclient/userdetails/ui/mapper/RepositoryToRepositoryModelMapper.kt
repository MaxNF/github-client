package com.maksimbagalei.githubclient.userdetails.ui.mapper

import com.maksimbagalei.githubclient.common.ui.mapper.Mapper
import com.maksimbagalei.githubclient.userdetails.data.dto.Repository
import com.maksimbagalei.githubclient.userdetails.ui.model.RepositoryModel
import javax.inject.Inject

internal class RepositoryToRepositoryModelMapper @Inject constructor() : Mapper<Repository, RepositoryModel> {
    override fun map(from: Repository): RepositoryModel = RepositoryModel(
        id = from.id,
        name = from.name,
        language = from.language,
        stargazers = from.stargazers.toString(),
        description = from.description,
        url = from.url
    )
}