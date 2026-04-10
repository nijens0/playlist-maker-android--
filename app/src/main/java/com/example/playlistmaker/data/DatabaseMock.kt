import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException

data class Word(
    val word: String,
    var count: Int = 1
)

class DatabaseMock(private val scope: CoroutineScope) {
    private val historyList = mutableListOf<Word>()

    private val _historyUpdates = MutableSharedFlow<Unit>()
    val historyUpdates = _historyUpdates.asSharedFlow()

    fun getHistoryRequests(): List<Word> = historyList.toList()

    fun addToHistory(text: String) {
        val existingWord = historyList.find { it.word == text }
        if (existingWord != null) {
            existingWord.count += 1
        } else {
            historyList.add(Word(word = text))
        }
        notifyHistoryChanged()
    }

    private fun notifyHistoryChanged() {
        scope.launch(Dispatchers.IO) {
            _historyUpdates.emit(Unit)
        }
    }
}