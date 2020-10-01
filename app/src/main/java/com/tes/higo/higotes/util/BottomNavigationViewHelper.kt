package com.tes.higo.higotes.util

import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode

class BottomNavigationViewHelper {
    companion object{
        fun disableShiftMode(view: BottomNavigationView) {
            val menuView = view.getChildAt(0) as BottomNavigationMenuView
            try {
                menuView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
            } catch (e: Exception) {

            }
        }
    }
}