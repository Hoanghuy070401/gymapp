package com.gym.feature.home.presentation.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gym.feature.home.BuildConfig
import com.gym.feature.home.data.exercisedb.ExerciseFirebaseCache
import com.gym.feature.home.data.exercisedb.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Filter tab cho danh sách body parts */
enum class BodyPartFilter { ALL, NOT_IMPORTED, IMPORTED }

/** Trạng thái pagination cho từng body part */
data class BodyPartImportInfo(
    val name: String,
    val isImported: Boolean = false,
    val exerciseCount: Int = 0,          // số bài đã lưu trong Firebase
    val loadedOffset: Int = 0,           // offset đã tải (0 = chưa import lần nào)
    val isLoadingMore: Boolean = false,  // đang load trang tiếp theo
    val hasMoreData: Boolean = true      // false = đã hết data từ API (offset >= total)
)

data class AdminImportState(
    val isLoading: Boolean = false,
    val bodyPartInfos: List<BodyPartImportInfo> = emptyList(),
    val log: List<String> = emptyList(),
    val error: String? = null,
    val confirmClearAll: Boolean = false,
    val activeFilter: BodyPartFilter = BodyPartFilter.ALL
) {
    val importedParts: Set<String> get() = bodyPartInfos.filter { it.isImported }.map { it.name }.toSet()
    val pendingParts: List<String>  get() = bodyPartInfos.filter { !it.isImported }.map { it.name }

    val filteredList: List<BodyPartImportInfo> get() = when (activeFilter) {
        BodyPartFilter.ALL          -> bodyPartInfos
        BodyPartFilter.NOT_IMPORTED -> bodyPartInfos.filter { !it.isImported }
        BodyPartFilter.IMPORTED     -> bodyPartInfos.filter { it.isImported }
    }
}

@HiltViewModel
class AdminExerciseImportViewModel @Inject constructor(
    private val repository: ExerciseRepository,
    private val cache: ExerciseFirebaseCache
) : ViewModel() {

    private val _state = MutableStateFlow(AdminImportState())
    val state: StateFlow<AdminImportState> = _state.asStateFlow()

    private val apiKey get() = BuildConfig.EXERCISEDB_API_KEY

    init { loadBodyParts() }

    fun setFilter(filter: BodyPartFilter) {
        _state.update { it.copy(activeFilter = filter) }
    }

    private fun loadBodyParts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            appendLog("🔄 Đang tải danh sách body parts từ API...")

            repository.fetchBodyPartListFromApi(apiKey)
                .onSuccess { parts ->
                    val infos = parts.map { part ->
                        val imported = cache.hasBodyPart(part)
                        val count = if (imported) cache.getExerciseCount(part) else 0
                        BodyPartImportInfo(
                            name          = part,
                            isImported    = imported,
                            exerciseCount = count,
                            loadedOffset  = if (imported) count else 0
                        )
                    }.sortedWith(compareBy({ it.isImported }, { it.name })) // Chưa import lên trước
                    _state.update { it.copy(isLoading = false, bodyPartInfos = infos) }
                    appendLog("✅ Tải xong: ${parts.size} body parts (${parts.count { cache.hasBodyPart(it) }} đã import)")
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                    appendLog("❌ Lỗi tải danh sách: ${e.message}")
                }
        }
    }

    /** Import 1 body part (100 bài đầu tiên) */
    fun importBodyPart(bodyPart: String, limit: Int = 100) {
        if (_state.value.isLoading) return
        viewModelScope.launch {
            setPartLoading(bodyPart, true)
            appendLog("⏳ Đang fetch '$bodyPart' (offset=0, limit=$limit)...")

            repository.fetchRawExercisesForImport(apiKey, bodyPart = bodyPart, limit = limit, offset = 0)
                .onSuccess { exercises ->
                    appendLog("📥 Fetch xong: ${exercises.size} bài tập")
                    cache.pushExercisesAndUpdateMeta(bodyPart, exercises)
                        .onSuccess { count ->
                            updatePartInfo(bodyPart, isImported = true, exerciseCount = count, loadedOffset = exercises.size)
                            appendLog("✅ '$bodyPart': $count bài → Firebase")
                        }
                        .onFailure { e -> appendLog("❌ Lỗi Firebase: ${e.message}") }
                }
                .onFailure { e -> appendLog("❌ Lỗi API: ${e.message}") }

            setPartLoading(bodyPart, false)
        }
    }

    /**
     * Load thêm bài tập cho body part đã import (phân trang).
     * Gọi khi user nhấn "Load thêm 100 bài".
     */
    fun loadMoreForBodyPart(bodyPart: String, limit: Int = 100) {
        val info = _state.value.bodyPartInfos.find { it.name == bodyPart } ?: return
        if (info.isLoadingMore || !info.hasMoreData) return

        val offset = info.loadedOffset
        viewModelScope.launch {
            setPartLoadingMore(bodyPart, true)
            appendLog("⏳ Load thêm '$bodyPart' (offset=$offset, limit=$limit)...")

            repository.fetchRawExercisesForImport(apiKey, bodyPart = bodyPart, limit = limit, offset = offset)
                .onSuccess { exercises ->
                    when {
                        exercises.isEmpty() -> {
                            // Đã hết data từ API
                            updatePartHasMore(bodyPart, hasMore = false)
                            appendLog("ℹ️ '$bodyPart': Đã tải hết (${ info.exerciseCount} bài tổng cộng)")
                        }
                        exercises.size < limit -> {
                            // Trả về ít hơn limit = trang cuối
                            cache.pushExercisesAndUpdateMeta(bodyPart, exercises)
                                .onSuccess { _ ->
                                    val newCount = info.exerciseCount + exercises.size
                                    updatePartInfo(bodyPart, isImported = true, exerciseCount = newCount, loadedOffset = offset + exercises.size)
                                    updatePartHasMore(bodyPart, hasMore = false)  // đầy dữ liệu
                                    appendLog("✅ '$bodyPart': +${exercises.size} bài (Tổng: $newCount) — Đã hết!")
                                }
                        }
                        else -> {
                            // Đủ limit → có thể còn trang tiếp
                            cache.pushExercisesAndUpdateMeta(bodyPart, exercises)
                                .onSuccess { _ ->
                                    val newCount = info.exerciseCount + exercises.size
                                    val newOffset = offset + exercises.size
                                    updatePartInfo(bodyPart, isImported = true, exerciseCount = newCount, loadedOffset = newOffset)
                                    appendLog("✅ '$bodyPart': +${exercises.size} bài (Tổng: $newCount, offset tiếp: $newOffset)")
                                }
                        }
                    }
                }
                .onFailure { e -> appendLog("❌ Load thêm thất bại: ${e.message}") }

            setPartLoadingMore(bodyPart, false)
        }
    }

    /** Import TOÀN BỘ các body parts chưa import (theo filter Chưa import) */
    fun importPending(limit: Int = 100) {
        val pending = _state.value.pendingParts
        if (pending.isEmpty()) { appendLog("ℹ️ Tất cả đã được import!"); return }
        if (_state.value.isLoading) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            appendLog("🚀 Import ${pending.size} body parts chưa import...")

            pending.forEachIndexed { i, part ->
                appendLog("[${i + 1}/${pending.size}] '$part'...")
                repository.fetchRawExercisesForImport(apiKey, bodyPart = part, limit = limit)
                    .onSuccess { exercises ->
                        cache.pushExercisesAndUpdateMeta(part, exercises)
                            .onSuccess { count ->
                                updatePartInfo(part, isImported = true, exerciseCount = count, loadedOffset = exercises.size)
                                appendLog("  ✅ '$part': $count bài")
                            }
                            .onFailure { appendLog("  ❌ '$part': Lỗi Firebase") }
                    }
                    .onFailure { appendLog("  ❌ '$part': Lỗi API") }
            }

            _state.update { it.copy(isLoading = false) }
            appendLog("🎉 Hoàn thành! ${_state.value.importedParts.size}/${_state.value.bodyPartInfos.size} body parts")
        }
    }

    /** Import TẤT CẢ (kể cả đã import → ghi đè) */
    fun importAll(limit: Int = 100) {
        val parts = _state.value.bodyPartInfos.map { it.name }
        if (parts.isEmpty()) return
        if (_state.value.isLoading) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            appendLog("🚀 Import TẤT CẢ ${parts.size} body parts (ghi đè)...")

            parts.forEachIndexed { i, part ->
                appendLog("[${i + 1}/${parts.size}] '$part'...")
                repository.fetchRawExercisesForImport(apiKey, bodyPart = part, limit = limit)
                    .onSuccess { exercises ->
                        cache.pushExercisesAndUpdateMeta(part, exercises)
                            .onSuccess { count ->
                                updatePartInfo(part, isImported = true, exerciseCount = count, loadedOffset = exercises.size)
                                appendLog("  ✅ $count bài")
                            }
                            .onFailure { appendLog("  ❌ Lỗi Firebase") }
                    }
                    .onFailure { appendLog("  ❌ Lỗi API") }
            }

            _state.update { it.copy(isLoading = false) }
            appendLog("🎉 Hoàn thành!")
        }
    }

    fun deleteBodyPart(bodyPart: String) {
        if (_state.value.isLoading) return
        viewModelScope.launch {
            appendLog("🗑️ Xoá '$bodyPart'...")
            cache.deleteBodyPart(bodyPart)
                .onSuccess {
                    updatePartInfo(bodyPart, isImported = false, exerciseCount = 0, loadedOffset = 0)
                    appendLog("✅ Đã xoá '$bodyPart'")
                }
                .onFailure { e -> appendLog("❌ Lỗi: ${e.message}") }
        }
    }

    fun requestClearAll() { _state.update { it.copy(confirmClearAll = true) } }
    fun cancelClearAll()  { _state.update { it.copy(confirmClearAll = false) } }

    fun confirmClearAll() {
        _state.update { it.copy(confirmClearAll = false) }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            appendLog("🗑️ Xoá TOÀN BỘ...")
            cache.clearAllExercises()
                .onSuccess {
                    _state.update { s ->
                        s.copy(
                            isLoading = false,
                            bodyPartInfos = s.bodyPartInfos.map {
                                it.copy(isImported = false, exerciseCount = 0, loadedOffset = 0)
                            }
                        )
                    }
                    appendLog("✅ Đã xóa sạch toàn bộ!")
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                    appendLog("❌ Lỗi: ${e.message}")
                }
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private fun updatePartInfo(name: String, isImported: Boolean, exerciseCount: Int, loadedOffset: Int) {
        _state.update { s ->
            s.copy(bodyPartInfos = s.bodyPartInfos.map {
                if (it.name == name) it.copy(isImported = isImported, exerciseCount = exerciseCount, loadedOffset = loadedOffset)
                else it
            })
        }
    }

    private fun updatePartHasMore(name: String, hasMore: Boolean) {
        _state.update { s ->
            s.copy(bodyPartInfos = s.bodyPartInfos.map {
                if (it.name == name) it.copy(hasMoreData = hasMore) else it
            })
        }
    }

    private fun setPartLoading(name: String, loading: Boolean) {
        _state.update { s ->
            s.copy(
                isLoading = loading,
                bodyPartInfos = s.bodyPartInfos.map {
                    if (it.name == name) it.copy(isLoadingMore = loading) else it
                }
            )
        }
    }

    private fun setPartLoadingMore(name: String, loading: Boolean) {
        _state.update { s ->
            s.copy(bodyPartInfos = s.bodyPartInfos.map {
                if (it.name == name) it.copy(isLoadingMore = loading) else it
            })
        }
    }

    private fun appendLog(message: String) {
        _state.update { it.copy(log = listOf(message) + it.log.take(49)) }
    }
}
