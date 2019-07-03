package ru.skillbranch.devintensive.utils

object Utils {


    fun parseFullName (fullName : String?) : Pair<String?, String?> {

        when(fullName) {
            "", " " -> return null to null
        }

        val parts : List<String>? = fullName?.split(" ")

        return parts?.getOrNull(0) to parts?.getOrNull(1)
    }



    fun toInitials(firstName: String?, lastName: String?): String? {

        when(firstName) {
            " ", "", null -> return null
        }

        val firstNameLetter = firstName?.get(0)?.toUpperCase()

        when(lastName) {
            " ", "", null -> return "$firstNameLetter"
        }

        val lastNameLetter = lastName?.get(0)?.toUpperCase()

        return "$firstNameLetter$lastNameLetter"
}

    fun transliteration(fullName: String, divider: String = " "): String {

        val pair = parseFullName(fullName)

        fun changeLetters(str: String?): String?{
            var result = ""
            str?.toLowerCase()?.forEach { letter ->
                when(letter){
                    'а' -> result += "a"
                    'б' -> result += "b"
                    'в' -> result += "v"
                    'г' -> result += "g"
                    'д' -> result += "d"
                    'е' -> result += "e"
                    'ё' -> result += "e"
                    'ж' -> result += "zh"
                    'з' -> result += "z"
                    'и' -> result += "i"
                    'й' -> result += "i"
                    'к' -> result += "k"
                    'л' -> result += "l"
                    'м' -> result += "m"
                    'н' -> result += "n"
                    'о' -> result += "o"
                    'п' -> result += "p"
                    'р' -> result += "r"
                    'с' -> result += "s"
                    'т' -> result += "t"
                    'у' -> result += "u"
                    'ф' -> result += "f"
                    'х' -> result += "h"
                    'ц' -> result += "c"
                    'ч' -> result += "ch"
                    'ш' -> result += "sh"
                    'щ' -> result += "sh'"
                    'ъ' -> result += ""
                    'ы' -> result += "i"
                    'ь' -> result += ""
                    'э' -> result += "e"
                    'ю' -> result += "yu"
                    'я' -> result += "ya"
                }
            }
            return result.replaceFirst(result[0], result[0].toUpperCase())
        }

        return if (pair.second != null)
            "${changeLetters(pair.first)}$divider${changeLetters(pair.second)}"

        else "${changeLetters(pair.first)}"
    }
}