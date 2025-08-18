package com.lambao.data.repository

/**
 * Interface for managing user preferences and settings.
 * 
 * This interface provides methods for storing and retrieving various types of data
 * in the device's shared preferences, with support for different data types.
 */
interface PreferenceRepository {
    // Integer preferences
    suspend fun setInt(key: String, value: Int)
    fun getInt(key: String, defaultValue: Int = 0): Int
    
    // Long preferences
    suspend fun setLong(key: String, value: Long)
    fun getLong(key: String, defaultValue: Long = 0L): Long
    
    // Float preferences
    suspend fun setFloat(key: String, value: Float)
    fun getFloat(key: String, defaultValue: Float = 0f): Float
    
    // Boolean preferences
    suspend fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
    
    // String preferences
    suspend fun setString(key: String, value: String)
    fun getString(key: String, defaultValue: String = ""): String?
    
    // Bulk operations
    suspend fun setStringSet(key: String, values: Set<String>)
    fun getStringSet(key: String, defaultValue: Set<String> = emptySet()): Set<String>?
    
    // Utility methods
    suspend fun removeKey(key: String)
    fun contains(key: String): Boolean
    fun clear()
    
    // Migration and backup
    suspend fun migratePreferences(oldKey: String, newKey: String)
    fun getAllKeys(): Set<String>
}
