package com.ash.bingemaster

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform