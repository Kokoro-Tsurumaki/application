package kokoro.mobile.template

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kokoro.mobile.template.ui.features.login.model.LoginModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by xianjie on 2025年1月7日15:07:40
 *
 * Description: 用户信息类 主要是对于token的管理 基于token状态
 */
object PreferencesKeys {
    //登录令牌
    val TOKEN = stringPreferencesKey("user_token")
    val CAT_ID = stringPreferencesKey("cat_id")
    val MODE_RULE = stringPreferencesKey("mode_rule")
    val USER_NAME = stringPreferencesKey("user_name")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    val STATE = stringPreferencesKey("state")
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
}

@Singleton
class UserDataManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

    fun saveToken(viewModelScope: CoroutineScope, data: kokoro.mobile.template.ui.features.login.model.LoginModel, state:String) {
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[PreferencesKeys.TOKEN] = data.token
                preferences[PreferencesKeys.CAT_ID] = data.catId
                preferences[PreferencesKeys.MODE_RULE] = data.modeRule
                preferences[PreferencesKeys.USER_NAME] = data.userName
                preferences[PreferencesKeys.REFRESH_TOKEN] = data.refreshToken
                preferences[PreferencesKeys.STATE] = state
                preferences[PreferencesKeys.IS_LOGGED_IN] = true
            }
        }
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.IS_LOGGED_IN] ?: false
        }
}