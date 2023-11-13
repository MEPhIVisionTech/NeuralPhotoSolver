package ru.tanexc.photosolver.core

sealed class Screen {

    data object ImageLoad : Screen()

    data object Settings : Screen()
}