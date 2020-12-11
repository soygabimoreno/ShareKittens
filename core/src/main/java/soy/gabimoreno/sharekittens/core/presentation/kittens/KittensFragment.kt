package soy.gabimoreno.sharekittens.core.presentation.kittens

import android.content.Intent
import com.giphy.sdk.core.models.Media
import com.giphy.sdk.ui.pagination.GPHContent
import com.giphy.sdk.ui.views.GPHGridCallback
import kotlinx.android.synthetic.main.fragment_kittens.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import soy.gabimoreno.libbase.fragment.BaseFragment
import soy.gabimoreno.libframework.extension.debugToast
import soy.gabimoreno.libframework.extension.exhaustive
import soy.gabimoreno.sharekittens.core.R
import soy.gabimoreno.sharekittens.core.framework.DownloadGif

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
        initGiphyGridView()
    }

    private fun initGiphyGridView() {
        ggv.showViewOnGiphy = false

        val content = GPHContent.searchQuery("kitten")
        ggv.content = content

        ggv.callback = object : GPHGridCallback {
            override fun contentDidUpdate(resultCount: Int) {
            }

            override fun didSelectMedia(media: Media) {
                viewModel.handleGifSelected(media)
            }
        }
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
            is KittensViewModel.ViewEvents.ShareGif -> shareGif(viewEvent.media)
        }.exhaustive
    }

    private fun shareGif(media: Media) {
        val url = media.images.original!!.gifUrl!!
        val downloadGif = DownloadGif()
        downloadGif(
            requireContext(),
            url
        ) { uri ->
            val sendIntent = Intent().apply {
                type = "image/gif"
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_STREAM,
                    uri
                )
                setPackage("com.whatsapp")
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }

            try {
                startActivity(sendIntent)
            } catch (e: Exception) {
                debugToast("e: ${e.message}")
            }
        }
    }
}
