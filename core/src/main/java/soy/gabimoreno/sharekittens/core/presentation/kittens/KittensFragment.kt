package soy.gabimoreno.sharekittens.core.presentation.kittens

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AdListener
import com.facebook.ads.AdSize
import com.facebook.ads.AdView
import com.giphy.sdk.core.models.Media
import com.giphy.sdk.ui.pagination.GPHContent
import com.giphy.sdk.ui.views.GPHGridCallback
import kotlinx.android.synthetic.main.fragment_kittens.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import soy.gabimoreno.libbase.fragment.BaseFragment
import soy.gabimoreno.libframework.extension.debugToast
import soy.gabimoreno.libframework.extension.exhaustive
import soy.gabimoreno.libframework.extension.gone
import soy.gabimoreno.libframework.extension.visible
import soy.gabimoreno.sharekittens.core.R
import soy.gabimoreno.sharekittens.core.framework.DownloadGif
import soy.gabimoreno.sharekittens.core.framework.customview.InfoAlertDialog

class KittensFragment : BaseFragment<
    KittensViewModel.ViewState,
    KittensViewModel.ViewEvents,
    KittensViewModel
    >() {

    companion object {
        private const val PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE

        fun newInstance() = KittensFragment()
    }

    override val layoutResId = R.layout.fragment_kittens
    override val viewModel: KittensViewModel by viewModel()

    private val requestWriteExternalStoragePermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> viewModel.shareGif()
                shouldShowRequestPermissionRationale(PERMISSION_WRITE_EXTERNAL_STORAGE) -> showRationaleForWriteExternalStorage()
                else -> showAppSettings()
            }
        }

    private lateinit var adView: AdView

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }

    private fun showRationaleForWriteExternalStorage() {
        InfoAlertDialog.Builder(requireContext())
            .drawable(R.drawable.ic_baseline_perm_media_24)
            .title(R.string.dialog_info_title)
            .description(R.string.dialog_info_description)
            .btnText(R.string.dialog_info_btn_text)
            .onButtonClicked {
                requestWriteExternalStoragePermission.launch(PERMISSION_WRITE_EXTERNAL_STORAGE)
            }
            .cancelable(false)
            .buildAndShow()
    }

    private fun showAppSettings() {
        InfoAlertDialog.Builder(requireContext())
            .drawable(R.drawable.ic_baseline_settings_applications_24)
            .title(R.string.dialog_app_settings_title)
            .description(R.string.dialog_app_settings_description)
            .btnText(R.string.dialog_app_settings_btn_text)
            .onButtonClicked {
                Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + requireContext().applicationContext.packageName)
                ).apply {
                    addCategory("android.intent.category.DEFAULT")
                    startActivity(this)
                }
            }
            .cancelable(false)
            .buildAndShow()
    }

    override fun initUI() {
        initSwipeRefreshLayout()
        initGiphyGridView()
        initFacebookAdd()
    }

    private fun initSwipeRefreshLayout() {
        srl.setOnRefreshListener {
            viewModel.handleLoadContent()
        }
    }

    private fun initGiphyGridView() {
        ggv.callback = object : GPHGridCallback {
            override fun contentDidUpdate(resultCount: Int) {
            }

            override fun didSelectMedia(media: Media) {
                viewModel.handleGifSelected(media)
                requestWriteExternalStoragePermission.launch(PERMISSION_WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    private fun initFacebookAdd() {
        adView = AdView(
            requireContext(),
            "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID",
            AdSize.BANNER_HEIGHT_50
        )

        clFacebookAdd.addView(adView)
        adView.loadAd(adView.buildLoadAdConfig()
                          .withAdListener(
                              object : AdListener {
                                  override fun onError(
                                      ad: Ad?,
                                      adError: AdError?
                                  ) {
                                      val error = "${adError?.errorCode} ${adError?.errorMessage}"
                                      viewModel.handleOnAdError(error)
                                  }

                                  override fun onAdLoaded(ad: Ad?) {
                                      viewModel.handleOnAdLoaded()
                                  }

                                  override fun onAdClicked(ad: Ad?) {
                                      viewModel.handleOnAdClicked()
                                  }

                                  override fun onLoggingImpression(ad: Ad?) {
                                      viewModel.handleOnLoggingImpression()
                                  }
                              }
                          )
                          .build())
    }

    override fun renderViewState(viewState: KittensViewModel.ViewState) {
        when (viewState) {
            KittensViewModel.ViewState.Loading -> showLoading()
            KittensViewModel.ViewState.Error -> showError()
            is KittensViewModel.ViewState.Content -> showContent(viewState.query)
        }.exhaustive
    }

    private fun showContent(query: String) {
        hideLoading()
        val content = GPHContent.searchQuery(query)
        ggv.content = content
    }

    private fun showLoading() {
        srl.isRefreshing = true
    }

    private fun hideLoading() {
        srl.isRefreshing = false
    }

    private fun showError() {
        hideLoading()
        debugToast("Error")
    }

    override fun handleViewEvent(viewEvent: KittensViewModel.ViewEvents) {
        when (viewEvent) {
            is KittensViewModel.ViewEvents.ShareGif -> shareGif(viewEvent.media)
            KittensViewModel.ViewEvents.ShowOverlay -> showOverlay()
            KittensViewModel.ViewEvents.HideOverlay -> hideOverlay()
        }.exhaustive
    }

    private fun shareGif(media: Media) {
        val url = media.images.original!!.gifUrl!!
        val downloadGif = DownloadGif()
        downloadGif(
            requireContext(),
            url
        ) { uri ->
            viewModel.handleGifDownloaded()
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

    private fun showOverlay() {
        clOverlay.visible()
    }

    private fun hideOverlay() {
        clOverlay.gone()
    }
}
