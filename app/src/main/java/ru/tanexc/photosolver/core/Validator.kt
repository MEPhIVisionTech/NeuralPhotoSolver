package ru.tanexc.photosolver.core

import java.util.regex.Pattern

fun isIPValid(ip: String): Boolean = Pattern.compile(
    "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}\$"
).matcher(ip).matches()

fun isPortValid(port: String): Boolean = Pattern.compile(
    "[123456789]{4}"
).matcher(port).matches()