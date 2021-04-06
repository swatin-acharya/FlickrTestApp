package com.example.flickrtestapp.utils

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/*Used under the hood by Hilt, just need to declare*/
@HiltAndroidApp
class BaseApplication: Application() {
}