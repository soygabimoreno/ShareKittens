package soy.gabimoreno.sharekittens.core.presentation.kittens

import org.koin.androidx.viewmodel.ext.android.viewModel
import soy.gabimoreno.libbase.fragment.BaseFragment
import soy.gabimoreno.libframework.extension.debugToast
import soy.gabimoreno.libframework.extension.exhaustive
import soy.gabimoreno.sharekittens.core.R

class KittensFragment : BaseFragment<
        KittensViewModel.ViewState,
        KittensViewModel.ViewEvents,
        KittensViewModel
        >() {

    companion object {
        fun newInstance() = KittensFragment()
    }

    override val layoutResId = R.layout.fragment_kittens
    override val viewModel: KittensViewModel by viewModel()

    override fun initUI() {
    }

    override fun renderViewState(viewState: KittensViewModel.ViewState) {
        when (viewState) {
            KittensViewModel.ViewState.Loading -> showLoading()
            KittensViewModel.ViewState.Error -> showError()
            is KittensViewModel.ViewState.Content -> showContent(viewState.foo)
        }.exhaustive
    }

    private fun showContent(foo: String) {
        hideLoading()
    }

    private fun showLoading() {
//        srl.isRefreshing = true
    }

    private fun hideLoading() {
//        srl.isRefreshing = false
    }

    private fun showError() {
        hideLoading()
        debugToast("Error")
    }

    override fun handleViewEvent(viewEvent: KittensViewModel.ViewEvents) {
        when (viewEvent) {
            is KittensViewModel.ViewEvents.Foo -> foo(viewEvent.foo)
        }.exhaustive
    }

    private fun foo(foo: String) {
        // TODO
    }
}
