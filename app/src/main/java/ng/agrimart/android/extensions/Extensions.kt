/*
 * Created by Joseph Dalughut on 13/06/2021, 17:30
 * Copyright (c) 2021 . All rights reserved.
 */

package ng.agrimart.android.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar


fun hideKeyboard(context: Activity) {
    val inputMethodManager: InputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(
        context.window.decorView.windowToken, 0
    )
}

fun showSnackbarMessage(@NonNull activity: FragmentActivity, @NonNull view: View, @StringRes message: Int) {
    hideKeyboard(activity)
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}