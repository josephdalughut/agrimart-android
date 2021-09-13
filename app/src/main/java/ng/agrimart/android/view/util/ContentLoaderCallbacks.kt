package ng.agrimart.android.view.util

import androidx.annotation.Nullable

/**
 * A simple interface than can be used to receive content-loadinig callbacks.
 */
interface ContentLoaderCallbacks {

    /**
     * Called when the content has started being loaded
     */
    fun onContentLoadBegan()

    /**
     * Called when the content has started loading more items
     */
    fun onContentLoadMore() {}

    /**
     * Called when the content is successfully loaded
     */
    fun onContentLoadSuccess()

    /**
     * Called when the content fails to load
     * @param exception The Exception which was thrown
     */
    fun onContentLoadError(@Nullable exception: Exception)

}