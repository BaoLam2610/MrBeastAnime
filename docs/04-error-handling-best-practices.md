# Error Handling Best Practices in Clean Architecture

## Overview
This document explains how to properly handle errors following Clean Architecture principles, where the data layer handles errors but the presentation layer handles user messages.

## 🏗️ Clean Architecture Error Flow

```
Data Layer → Domain Layer → Presentation Layer → UI
    ↓              ↓              ↓           ↓
  Error        Exception      User Message   Toast/Snackbar
  Type         Object         (string.xml)   Display
```

## ✅ What Data Layer Should Do

### **1. Handle Error Types**
```kotlin
// data/src/main/java/com/lambao/data/network/NetworkException.kt
data class NetworkException(
    val type: NetworkErrorType,  // ✅ Error type (enum)
    val code: Int? = null,       // ✅ HTTP status code
    override val message: String? = null,  // ✅ Technical message (optional)
    override val cause: Throwable? = null  // ✅ Original exception
) : Throwable(message, cause)
```

### **2. Provide Error Context**
```kotlin
// data/src/main/java/com/lambao/data/network/NetworkErrorType.kt
enum class NetworkErrorType(val code: Int? = null) {
    NO_NETWORK,           // ✅ Network connectivity issues
    UNAUTHORIZED(401),    // ✅ Authentication errors
    NOT_FOUND(404),       // ✅ Resource not found
    SERVER_ERROR(500),    // ✅ Server-side errors
    UNKNOWN               // ✅ Unknown errors
}
```

### **3. Return Resource with Error**
```kotlin
// data/src/main/java/com/lambao/data/network/BaseRemoteDataSource.kt
protected open fun <T> safeCall(apiCall: suspend () -> Response<T>): Flow<Resource<T>> = flow {
    // ... API call logic
    if (response.isSuccessful) {
        emit(Resource.Success(data = response.body()))
    } else {
        // ✅ Return error with type, NOT user message
        emit(Resource.Error(throwable = errorParser.parseErrorResponse(response)))
    }
}
```

## 🎯 What Presentation Layer Should Do

### **1. Map Error Types to User Messages**
```kotlin
// presentation/src/main/java/com/lambao/presentation/ui/viewmodel/BaseViewModel.kt
abstract class BaseViewModel : ViewModel() {
    
    protected fun getErrorMessage(error: Throwable): String {
        return when (error) {
            is NetworkException -> mapNetworkErrorToUserMessage(error)
            is LocalException -> mapLocalErrorToUserMessage(error)
            else -> getString(R.string.error_unknown)
        }
    }
    
    private fun mapNetworkErrorToUserMessage(error: NetworkException): String {
        return when (error.type) {
            NetworkErrorType.NO_NETWORK -> getString(R.string.error_no_network)
            NetworkErrorType.UNAUTHORIZED -> getString(R.string.error_unauthorized)
            NetworkErrorType.NOT_FOUND -> getString(R.string.error_not_found)
            NetworkErrorType.SERVER_ERROR -> getString(R.string.error_server)
            NetworkErrorType.UNKNOWN -> getString(R.string.error_unknown)
        }
    }
    
    private fun mapLocalErrorToUserMessage(error: LocalException): String {
        return when (error.type) {
            LocalErrorType.PERMISSION_DENIED -> getString(R.string.error_permission_denied)
            LocalErrorType.STORAGE_UNAVAILABLE -> getString(R.string.error_storage_unavailable)
            LocalErrorType.QUERY_FAILED -> getString(R.string.error_query_failed)
            LocalErrorType.DATA_CORRUPTED -> getString(R.string.error_data_corrupted)
            LocalErrorType.STORAGE_FULL -> getString(R.string.error_storage_full)
            LocalErrorType.UNKNOWN -> getString(R.string.error_unknown)
        }
    }
}
```

### **2. Use String Resources**
```xml
<!-- res/values/strings.xml -->
<resources>
    <!-- Network Errors -->
    <string name="error_no_network">No network connection</string>
    <string name="error_unauthorized">Please log in again</string>
    <string name="error_not_found">Content not found</string>
    <string name="error_server">Server error, please try again later</string>
    <string name="error_unknown">An unknown error occurred</string>
    
    <!-- Local Errors -->
    <string name="error_permission_denied">Permission denied</string>
    <string name="error_storage_unavailable">Storage unavailable</string>
    <string name="error_query_failed">Failed to load data</string>
    <string name="error_data_corrupted">Data appears to be corrupted</string>
    <string name="error_storage_full">Storage is full</string>
</resources>
```

```xml
<!-- res/values-vi/strings.xml (Vietnamese) -->
<resources>
    <!-- Network Errors -->
    <string name="error_no_network">Không có kết nối mạng</string>
    <string name="error_unauthorized">Vui lòng đăng nhập lại</string>
    <string name="error_not_found">Không tìm thấy nội dung</string>
    <string name="error_server">Lỗi máy chủ, vui lòng thử lại sau</string>
    <string name="error_unknown">Đã xảy ra lỗi không xác định</string>
    
    <!-- Local Errors -->
    <string name="error_permission_denied">Quyền truy cập bị từ chối</string>
    <string name="error_storage_unavailable">Bộ nhớ không khả dụng</string>
    <string name="error_query_failed">Không thể tải dữ liệu</string>
    <string name="error_data_corrupted">Dữ liệu có vẻ bị hỏng</string>
    <string name="error_storage_full">Bộ nhớ đã đầy</string>
</resources>
```

## 🚀 Usage Example

### **ViewModel Implementation:**
```kotlin
class AnimeListViewModel(
    private val getAnimeUseCase: GetAnimeUseCase
) : BaseViewModel() {
    
    private val _uiState = MutableStateFlow<AnimeListUiState>(AnimeListUiState.Loading)
    val uiState: StateFlow<AnimeListUiState> = _uiState.asStateFlow()
    
    fun loadAnime() {
        viewModelScope.launch {
            _uiState.value = AnimeListUiState.Loading
            
            getAnimeUseCase.execute().collect { resource ->
                _uiState.value = when (resource) {
                    is Resource.Success -> {
                        AnimeListUiState.Success(resource.data ?: emptyList())
                    }
                    is Resource.Error -> {
                        // ✅ Map error to user message here
                        val userMessage = getErrorMessage(resource.throwable)
                        AnimeListUiState.Error(userMessage)
                    }
                    is Resource.Loading -> {
                        AnimeListUiState.Loading
                    }
                    is Resource.Init -> {
                        AnimeListUiState.Loading
                    }
                }
            }
        }
    }
}
```

### **UI State:**
```kotlin
sealed class AnimeListUiState {
    object Loading : AnimeListUiState()
    data class Success(val animeList: List<Anime>) : AnimeListUiState()
    data class Error(val message: String) : AnimeListUiState()  // ✅ User-friendly message
}
```

### **Fragment/Activity Usage:**
```kotlin
class AnimeListFragment : Fragment() {
    
    private val viewModel: AnimeListViewModel by viewModels()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is AnimeListUiState.Loading -> showLoading()
                    is AnimeListUiState.Success -> showAnimeList(state.animeList)
                    is AnimeListUiState.Error -> {
                        // ✅ Display user-friendly message
                        showError(state.message)
                    }
                }
            }
        }
    }
    
    private fun showError(message: String) {
        // Use Snackbar, Toast, or Dialog with the localized message
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}
```

## 🎯 Benefits of This Approach

### **✅ Clean Architecture Compliance:**
- **Data Layer**: Only handles data and error types
- **Domain Layer**: Business logic and error handling
- **Presentation Layer**: User messages and UI logic

### **✅ Localization Support:**
- Error messages in `string.xml` files
- Easy to add new languages
- Consistent with Android best practices

### **✅ Testability:**
- Data layer can be tested without UI concerns
- Presentation layer can be tested with mocked strings
- Clear separation of responsibilities

### **✅ Maintainability:**
- Error messages centralized in resources
- Easy to update messages without code changes
- Consistent error handling across the app

## 🚫 What NOT to Do

### **❌ Don't put user messages in data layer:**
```kotlin
// ❌ WRONG - Data layer shouldn't know about user messages
class NetworkException(
    val userMessage: String  // ❌ This belongs in presentation layer
)
```

### **❌ Don't hardcode error messages:**
```kotlin
// ❌ WRONG - Hardcoded messages
private const val ERROR_MESSAGE = "No network connection"
```

### **❌ Don't mix technical and user messages:**
```kotlin
// ❌ WRONG - Mixed concerns
emit(Resource.Error("User-friendly message", technicalException))
```

## 🎉 Summary

**Data Layer**: Handles errors, provides error types and context
**Presentation Layer**: Maps error types to user-friendly messages using `string.xml`
**UI**: Displays localized, user-friendly error messages

This approach follows Clean Architecture principles and makes your app maintainable, testable, and localization-ready! 🚀
