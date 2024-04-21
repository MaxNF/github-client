package com.maksimbagalei.githubclient.userdetails.ui.mapper

import com.maksimbagalei.githubclient.common.ui.mapper.Mapper
import com.maksimbagalei.githubclient.userdetails.data.dto.UserDetails
import com.maksimbagalei.githubclient.userdetails.ui.model.UserDetailsModel
import javax.inject.Inject

internal class UserDetailsToUserDetailsModelMapper @Inject constructor() :
    Mapper<UserDetails, UserDetailsModel> {
    override fun map(from: UserDetails): UserDetailsModel = UserDetailsModel(
        login = from.login,
        avatarUrl = from.avatarUrl,
        name = from.name,
        followers = from.followers.toString(),
        following = from.following.toString(),
    )
}