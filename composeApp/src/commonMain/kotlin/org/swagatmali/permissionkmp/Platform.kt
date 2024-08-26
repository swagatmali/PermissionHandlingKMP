package org.swagatmali.permissionkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform