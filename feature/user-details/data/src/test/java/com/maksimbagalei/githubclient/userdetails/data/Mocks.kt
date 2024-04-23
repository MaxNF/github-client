package com.maksimbagalei.githubclient.userdetails.data

import com.maksimbagalei.githubclient.userdetails.data.dto.Repository
import com.maksimbagalei.githubclient.userdetails.data.dto.UserDetails

val repositoryMock
    get() = Repository(0, "", null, 0, null, "", false)

val userDetailsMock
    get() = UserDetails("", "", null, 0, 0)