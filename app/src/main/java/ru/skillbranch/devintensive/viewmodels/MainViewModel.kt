package ru.skillbranch.devintensive.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.extensions.shortFormat
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.models.data.ChatType
import ru.skillbranch.devintensive.repositories.ChatRepository
import java.util.*

class MainViewModel : ViewModel() {

    private val chatRepository = ChatRepository
    private val query = mutableLiveData("")
    private val chats = Transformations.map(chatRepository.loadChats()) { chats ->

        val chatItems = chats
            .filter { !it.isArchived }
            .map { it.toChatItem() }
            .sortedBy { it.id.toInt() }.toMutableList()

        val archivedChats = chats.filter { it.isArchived }

        if (archivedChats.isNotEmpty()) {
            chatItems.add(0, makeArchiveItem(archivedChats))
        }

        return@map chatItems
    }

    fun getChatData(): LiveData<List<ChatItem>> {

        val result = MediatorLiveData<List<ChatItem>>()

        val filterF = {
            val queryStr = query.value!!
            val chats = chats.value!!

            result.value = if (queryStr.isEmpty()) chats
            else chats.filter { it.title.contains(queryStr, true) }
        }

        result.addSource(chats) { filterF.invoke() }
        result.addSource(query) { filterF.invoke() }

        return result
    }


    fun addToArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = true))
    }

    fun restoreFromArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = false))
    }

    fun handleSearchQuery(text: String) {
        query.value = text
    }

    @SuppressLint("VisibleForTests")
    fun makeArchiveItem(archivedChats: List<Chat>): ChatItem {

        val lastChat = archivedChats.maxBy {
            it.lastMessageDate() ?: Date(0L)
        }

        val unreadableMessageCount = archivedChats.sumBy {
            it.unreadableMessageCount()
        }

        return ChatItem(
            "",
            null,
            "",
            "Архив чатов",
            lastChat!!.lastMessageShort().first,
            unreadableMessageCount,
            lastChat.lastMessageDate()?.shortFormat(),
            false,
            ChatType.ARCHIVE,
            lastChat.lastMessageShort().second
        )
    }

}