package com.maksimbagalei.githubclient.common.ui.mapper

interface Mapper<FROM, TO> {
    fun map(from: FROM): TO
}