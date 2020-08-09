package com.moon.util

import android.app.Activity
import android.content.Context
import android.opengl.Visibility
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


fun <T> Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.get<T>(key)

fun <T> Fragment.getNavigationResultLiveData(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.setNavigationResult(result: T, key: String = "result"){
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

/// views
fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.hide(){
    this.visibility = View.GONE
}

fun View.hideKeyboard(){
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

fun ChipGroup.addChipView(chip: Chip){
    this.forEach { view ->
        if ((view as Chip).text == chip.text)
            return
    }
    this.addView(chip)
}