package ru.skillbranch.devintensive.utils

object Utils {


    fun parseFullName(fullName: String?): Pair<String?, String?> {

        when (fullName) {
            "", " " -> return null to null
        }

        val parts: List<String>? = fullName?.split(" ")

        return parts?.getOrNull(0) to parts?.getOrNull(1)
    }


    fun toInitials(firstName: String?, lastName: String?): String? {

        return when (firstName) {
            " ", "", null -> {
                when (lastName) {
                    " ", "", null -> null
                    else -> "${lastName[0].toUpperCase()}"
                }
            }
            else -> {
                when (lastName) {
                    " ", "", null -> "${firstName[0].toUpperCase()}"
                    else -> "${firstName[0].toUpperCase()}${lastName[0].toUpperCase()}"
                }
            }
        }
    }

    fun transliteration(fullName: String?, divider: String = " "): String? {

        when (fullName) {
            "", " ", null -> return null
        }

        val alphabet = mapOf(
            'а' to "a",
            'б' to "b",
            'в' to "v",
            'г' to "g",
            'д' to "d",
            'е' to "e",
            'ё' to "e",
            'ж' to "zh",
            'з' to "z",
            'и' to "i",
            'й' to "i",
            'к' to "k",
            'л' to "l",
            'м' to "m",
            'н' to "n",
            'о' to "o",
            'п' to "p",
            'р' to "r",
            'с' to "s",
            'т' to "t",
            'у' to "u",
            'ф' to "f",
            'х' to "h",
            'ц' to "c",
            'ч' to "ch",
            'ш' to "sh",
            'щ' to "sh'",
            'ъ' to "",
            'ы' to "i",
            'ь' to "",
            'э' to "e",
            'ю' to "yu",
            'я' to "ya",
            ' ' to divider
        )

        var result = ""

        fullName?.forEach { letter ->
            if (alphabet.containsKey(letter.toLowerCase())) {
                result += if (letter.isUpperCase()) {
                    alphabet[letter.toLowerCase()]?.capitalize()
                } else {
                    alphabet[letter.toLowerCase()]
                }
            } else {
                result += letter
            }
        }
        return result
    }


    fun isRepositoryValid(repository: String): Boolean = repository == "" ||
            repository.contains(Regex("^(https://)?(www\\.)?github\\.com/[^/ ]+(|/)$")) &&
            !repository.endsWith("/security") &&
            !repository.endsWith("/customer-stories") &&
            !repository.endsWith("/nonprofit") &&
            !repository.endsWith("/marketplace") &&
            !repository.endsWith("/events") &&
            !repository.endsWith("/trending") &&
            !repository.endsWith("/collections") &&
            !repository.endsWith("/topics") &&
            !repository.endsWith("/features") &&
            !repository.endsWith("/pricing") &&
            !repository.endsWith("/enterprise") &&
            !repository.endsWith("/join") &&
            !repository.endsWith("-") &&
            !repository.contains(Regex("my.*rep"))
}