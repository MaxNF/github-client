package com.maksimbagalei.githubclient.userlist.data

import com.maksimbagalei.githubclient.userlist.data.dto.UserBrief
import com.maksimbagalei.githubclient.userlist.data.dto.UserList

val userListMock
    get() = UserList(emptyList())
val userBriefMock
    get() = UserBrief(0, "", "")