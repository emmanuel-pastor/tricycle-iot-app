package com.emmanuel.pastor.simplesmartapps.tricycle

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val donkeDispatcher: DonkeDispatcher)

enum class DonkeDispatcher {
    IO
}