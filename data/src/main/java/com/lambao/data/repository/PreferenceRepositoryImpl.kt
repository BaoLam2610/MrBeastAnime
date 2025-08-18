package com.lambao.data.repository

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Implementation of PreferenceRepository using SharedPreferences.
 * 
 * This class provides a concrete implementation for managing user preferences
 * with proper error handling and coroutine support.
 *
 * @param pref SharedPreferences instance for storing data
 * @param editor SharedPreferences.Editor for writing data
 */
class PreferenceRepositoryImpl(
    private val pref: SharedPreferences,
    private val editor: SharedPreferences.Editor
) : PreferenceRepository {
    
    override suspend fun setInt(key: String, value: Int) {
        withContext(Dispatchers.IO) {
            try {
                editor.putInt(key, value).apply()
            } catch (e: Exception) {
                throw RuntimeException("Failed to set int preference: $key", e)
            }
        }
    }
    
    override fun getInt(key: String, defaultValue: Int): Int {
        return try {
            pref.getInt(key, defaultValue)
        } catch (e: Exception) {
            defaultValue
        }
    }
    
    override suspend fun setLong(key: String, value: Long) {
        withContext(Dispatchers.IO) {
            try {
                editor.putLong(key, value).apply()
            } catch (e: Exception) {
                throw RuntimeException("Failed to set long preference: $key", e)
            }
        }
    }
    
    override fun getLong(key: String, defaultValue: Long): Long {
        return try {
            pref.getLong(key, defaultValue)
        } catch (e: Exception) {
            defaultValue
        }
    }
    
    override suspend fun setFloat(key: String, value: Float) {
        withContext(Dispatchers.IO) {
            try {
                editor.putFloat(key, value).apply()
            } catch (e: Exception) {
                throw RuntimeException("Failed to set float preference: $key", e)
            }
        }
    }
    
    override fun getFloat(key: String, defaultValue: Float): Float {
        return try {
            pref.getFloat(key, defaultValue)
        } catch (e: Exception) {
            defaultValue
        }
    }
    
    override suspend fun setBoolean(key: String, value: Boolean) {
        withContext(Dispatchers.IO) {
            try {
                editor.putBoolean(key, value).apply()
            } catch (e: Exception) {
                throw RuntimeException("Failed to set boolean preference: $key", e)
            }
        }
    }
    
    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return try {
            pref.getBoolean(key, defaultValue)
        } catch (e: Exception) {
            defaultValue
        }
    }
    
    override suspend fun setString(key: String, value: String) {
        withContext(Dispatchers.IO) {
            try {
                editor.putString(key, value).apply()
            } catch (e: Exception) {
                throw RuntimeException("Failed to set string preference: $key", e)
            }
        }
    }
    
    override fun getString(key: String, defaultValue: String): String? {
        return try {
            pref.getString(key, defaultValue)
        } catch (e: Exception) {
            defaultValue
        }
    }
    
    override suspend fun setStringSet(key: String, values: Set<String>) {
        withContext(Dispatchers.IO) {
            try {
                editor.putStringSet(key, values).apply()
            } catch (e: Exception) {
                throw RuntimeException("Failed to set string set preference: $key", e)
            }
        }
    }
    
    override fun getStringSet(key: String, defaultValue: Set<String>): Set<String>? {
        return try {
            pref.getStringSet(key, defaultValue)
        } catch (e: Exception) {
            defaultValue
        }
    }
    
    override suspend fun removeKey(key: String) {
        withContext(Dispatchers.IO) {
            try {
                editor.remove(key).apply()
            } catch (e: Exception) {
                throw RuntimeException("Failed to remove preference: $key", e)
            }
        }
    }
    
    override fun contains(key: String): Boolean {
        return try {
            pref.contains(key)
        } catch (e: Exception) {
            false
        }
    }
    
    override fun clear() {
        try {
            editor.clear().apply()
        } catch (e: Exception) {
            throw RuntimeException("Failed to clear preferences", e)
        }
    }
    
    override suspend fun migratePreferences(oldKey: String, newKey: String) {
        withContext(Dispatchers.IO) {
            try {
                if (pref.contains(oldKey)) {
                    when (val value = pref.all[oldKey]) {
                        is Int -> setInt(newKey, value)
                        is Long -> setLong(newKey, value)
                        is Float -> setFloat(newKey, value)
                        is Boolean -> setBoolean(newKey, value)
                        is String -> setString(newKey, value)
                        is Set<*> -> setStringSet(newKey, value.filterIsInstance<String>().toSet())
                    }
                    removeKey(oldKey)
                }
            } catch (e: Exception) {
                throw RuntimeException("Failed to migrate preferences from $oldKey to $newKey", e)
            }
        }
    }
    
    override fun getAllKeys(): Set<String> {
        return try {
            pref.all.keys
        } catch (e: Exception) {
            emptySet()
        }
    }
}
