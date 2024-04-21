package com.maksimbagalei.githubclient.userlist.ui.mapper

import com.maksimbagalei.githubclient.common.ui.mapper.Mapper
import com.maksimbagalei.githubclient.userlist.data.dto.UserBrief
import com.maksimbagalei.githubclient.userlist.ui.model.UserBriefModel
import javax.inject.Inject

class UserBriefToUserBriefModelMapper @Inject constructor() : Mapper<UserBrief, UserBriefModel> {
    override fun map(from: UserBrief): UserBriefModel =
        UserBriefModel(from.id, from.login, from.avatarUrl)
}