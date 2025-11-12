package kokoro.mobile.hachimi

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kokoro.mobile.hachimi.ui.features.login.model.LoginModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kokoro.mobile.hachimi.ui.AppEvent
import kokoro.mobile.hachimi.ui.EventBus
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
    //用户id
    val USER_ID = intPreferencesKey("user_id")
    //登录状态
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    //统一授权
    val AGREE_AUTHORIZE = booleanPreferencesKey("agree_authorize")
    val THEME = stringPreferencesKey("theme")
}

@Singleton
class UserDataManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

    fun saveToken(viewModelScope: CoroutineScope, token: String,userId:Int,finishCallback:()->Unit) {
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[PreferencesKeys.TOKEN] = token
                preferences[PreferencesKeys.USER_ID] = userId
                preferences[PreferencesKeys.IS_LOGGED_IN] = true
            }
            finishCallback.invoke()
        }
    }

    fun clearToken(viewModelScope: CoroutineScope){
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[PreferencesKeys.TOKEN] = ""
                preferences[PreferencesKeys.USER_ID] = 0
                preferences[PreferencesKeys.IS_LOGGED_IN] = false
            }
            EventBus.send(AppEvent.ResetApp)
        }
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.IS_LOGGED_IN] ?: false
        }

    fun agreeAuthorize(viewModelScope: CoroutineScope,finishCallback:()->Unit){
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[PreferencesKeys.AGREE_AUTHORIZE] = true
            }
            finishCallback.invoke()
        }
    }
    val isAgreeAuthorize: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.AGREE_AUTHORIZE] ?: false
        }

    fun setTheme(viewModelScope: CoroutineScope,theme:String,finishCallback:()->Unit){
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[PreferencesKeys.THEME] = theme
            }
            finishCallback.invoke()
        }
    }
    val getTheme: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.THEME] ?: "Auto"
        }


}