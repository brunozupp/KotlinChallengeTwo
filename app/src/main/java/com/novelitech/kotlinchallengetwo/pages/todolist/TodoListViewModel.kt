package com.novelitech.kotlinchallengetwo.pages.todolist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novelitech.kotlinchallengetwo.models.ActionsTodoList
import com.novelitech.kotlinchallengetwo.models.TodoItemModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID

class TodoListViewModel : ViewModel() {

    private val _items = MutableStateFlow<MutableList<TodoItemModel>>(mutableListOf())
    val items = _items.asStateFlow()

    private val _actions = MutableSharedFlow<ActionsTodoList>()
    val actions = _actions.asSharedFlow()

    init {
        viewModelScope.launch {
            _items.collect {
                if(_items.value.size > 3) {
                    _actions.emit(ActionsTodoList.SHOW_MORE_THAN_THREE)
                    Log.d("TodoListViewModel", "More than 3 items in list")
                } else {
                    _actions.emit(ActionsTodoList.SHOW_LESS_EQUAL_TO_THREE)
                    Log.d("TodoListViewModel", "Less or equal to 3 items in list")
                }
            }
        }

        viewModelScope.launch {
            _items.collect {
                Log.d("TodoListViewModel", "Count: ${_items.value.size}")
            }

            // This line of code will never be reached
            println("Hellooooo")
        }
    }

    fun addItem(
        text: String,
    ) {
        val todoItem = TodoItemModel(
            text = text,
            done = false,
            id = UUID.randomUUID().toString(),
            createdOn = LocalDateTime.now(),
            updatedOn = null,
        )

        _items.update { old ->
            val aux = old.toMutableList()
            aux.add(todoItem)
            aux
        }
    }

    fun toggleItem(
        id: String,
    ) {
        val index = _items.value.indexOfFirst { it.id == id }

        if(index != -1) {

            val itemUpdated = _items.value[index].copy(
                done = !_items.value[index].done,
                updatedOn = LocalDateTime.now()
            )

            _items.update { old ->
                val aux = old.toMutableList()
                aux[index] = itemUpdated
                aux
            }
        }
    }

    fun removeItem(
        id: String,
    ) {
        val index = _items.value.indexOfFirst { it.id == id }

        if(index != -1) {

            _items.update { old ->
                val aux = old.toMutableList()
                aux.removeAt(index)
                aux
            }
        }
    }
}