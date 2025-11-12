package kokoro.mobile.template.ui.features.main.home

import kokoro.mobile.template.ui.common.base.BaseUIViewModel
import kokoro.mobile.template.core.network.NetRepository
import kokoro.mobile.network.dispose.pack.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by  on 2025年4月11日14:01:34
 *
 * Description:
 */
@HiltViewModel
class HomeViewModel @Inject constructor(private val currentRepository: kokoro.mobile.template.core.network.NetRepository) :
    kokoro.mobile.template.ui.common.base.BaseUIViewModel<HomeContract.HomeState, HomeContract.HomeAction>( HomeContract.HomeState()) {


    override fun initializeData() {

    }

    override fun onNetworkResponse(event: Result) {
        event.apply {

        }
    }

    override fun onUIAction(event: HomeContract.HomeAction) {

    }

}