package com.lambao.presentation.handler.media

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia

/**
 * Custom implementation of PickMultipleVisualMedia for handling multiple media selection.
 * 
 * This class extends the default PickMultipleVisualMedia contract to provide
 * better control over the maximum number of selectable items.
 */
class CustomPickMultipleVisualMedia : ActivityResultContracts.PickMultipleVisualMedia() {
    
    private var maxItems: Int =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) MediaStore.getPickImagesMaxLimit()
        else 100

    /**
     * Update the maximum number of items that can be selected.
     * 
     * @param newMaxItems New maximum item count
     */
    fun updateMaxItems(newMaxItems: Int) {
        maxItems = if (newMaxItems <= 1) 2 else newMaxItems
    }

    override fun createIntent(context: Context, input: PickVisualMediaRequest): Intent {
        val intent = super.createIntent(context, input)
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX, maxItems)
            }
            intent.putExtra(PickVisualMedia.EXTRA_SYSTEM_FALLBACK_PICK_IMAGES_MAX, maxItems)
        } catch (_: Exception) {
            // Ignore exceptions for backward compatibility
        }
        return intent
    }
}
