package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.models.data.Chat
import java.util.*

class ImageMessage (
    id:String,
    from: User,
    chat: Chat,
    isIncoming : Boolean = false,
    date: Date = Date(),
    isReaded:Boolean = false,
    var image:String
) : BaseMessage(id, from, chat, isIncoming, date,isReaded) {


    override fun formatMessage(): String = "${from?.firstName} " +
            (if (isIncoming) "получил" else "отправил") +
            " изображение \"$image\" $date"
}

