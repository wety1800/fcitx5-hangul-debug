/*
 * SPDX-License-Identifier: LGPL-2.1-or-later
 * SPDX-FileCopyrightText: Copyright 2021-2026 Fcitx5 for Android Contributors
 */
package org.fcitx.fcitx5.android.input

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONObject
import timber.log.Timber

class PerAppImeManager(context: Context) {

    private val mappingPrefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /**
     * Whether auto-switch feature is enabled.
     * Defaults to true; uses its own SharedPreferences key.
     */
    var isEnabled: Boolean
        get() = mappingPrefs.getBoolean(KEY_ENABLED, true)
        set(value) = mappingPrefs.edit().putBoolean(KEY_ENABLED, value).apply()

    /**
     * The input method we auto-switched to in the current bind session.
     * Used to distinguish auto-switches from user-initiated switches.
     */
    var autoSwitchedIme: String? = null
        private set

    /**
     * Whether we are currently in the middle of an auto-switch operation.
     */
    var isAutoSwitching: Boolean = false
        private set

    /**
     * Called when the user manually switches input method.
     * Saves the mapping: pkgName -> imeUniqueName.
     */
    fun onInputMethodChanged(pkgName: String, imeUniqueName: String) {
        val mappings = loadMappings()
        mappings.put(pkgName, imeUniqueName)
        saveMappings(mappings)
        Timber.d("PerAppIme: saved $pkgName -> $imeUniqueName")
    }

    /**
     * Get the remembered input method for a given package name.
     * Returns null if no mapping exists.
     */
    fun getRememberedIme(pkgName: String): String? {
        val mappings = loadMappings()
        val ime = mappings.optString(pkgName, null)
        Timber.d("PerAppIme: lookup $pkgName -> $ime")
        return ime
    }

    /**
     * Mark that we are about to perform an auto-switch.
     */
    fun beginAutoSwitch(ime: String) {
        isAutoSwitching = true
        autoSwitchedIme = ime
    }

    /**
     * Mark that auto-switch processing is complete.
     * Should be called after the IMChangeEvent is received.
     */
    fun endAutoSwitch() {
        isAutoSwitching = false
        autoSwitchedIme = null
    }

    /**
     * Clear all per-app input method mappings.
     */
    fun clearAll() {
        mappingPrefs.edit().remove(KEY_MAPPINGS).apply()
        Timber.d("PerAppIme: all mappings cleared")
    }

    private fun loadMappings(): JSONObject {
        val json = mappingPrefs.getString(KEY_MAPPINGS, null)
        return if (json != null) {
            try {
                JSONObject(json)
            } catch (e: Exception) {
                Timber.w(e, "PerAppIme: failed to parse mappings, resetting")
                JSONObject()
            }
        } else {
            JSONObject()
        }
    }

    private fun saveMappings(mappings: JSONObject) {
        mappingPrefs.edit().putString(KEY_MAPPINGS, mappings.toString()).apply()
    }

    companion object {
        private const val PREFS_NAME = "per_app_ime"
        private const val KEY_ENABLED = "per_app_ime_auto_switch"
        private const val KEY_MAPPINGS = "per_app_mappings"
    }
}
