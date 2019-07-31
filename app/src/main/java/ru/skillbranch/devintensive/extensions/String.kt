package ru.skillbranch.devintensive.extensions

fun String.truncate(value: Int = 16): String {

    return if (value < this.trim().length) {
        this.substring(0, value).trim() + "..."
    } else {
        this
    }
}

fun String.stripHtml(): String {
    return this
        .replace(Regex("<.+?>|&[^а-яА-Я\\s]+?;"), "")
        .replace(Regex(" +"), " ")
}