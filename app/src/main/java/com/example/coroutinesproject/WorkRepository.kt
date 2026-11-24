package com.example.coroutinesproject

import kotlinx.coroutines.delay

class WorkRepository {
    suspend fun haveHeavyWork() {
        delay(5000)
    }
}