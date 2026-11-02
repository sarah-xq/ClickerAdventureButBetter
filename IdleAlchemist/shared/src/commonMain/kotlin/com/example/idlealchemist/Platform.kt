package com.example.idlealchemist

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform