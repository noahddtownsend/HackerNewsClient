package com.noahtownsend.hackernewsclient

import android.app.Application
import com.noahtownsend.hackernewsclient.db.HNDatabase

class HNApplication: Application() {
    val database: HNDatabase by lazy { HNDatabase.getDatabase(this) }
}