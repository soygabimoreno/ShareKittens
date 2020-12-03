package soy.gabimoreno.sharekittens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.giphy.sdk.ui.Giphy
import com.giphy.sdk.ui.views.GiphyDialogFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Giphy.configure(
            this,
            "Oc0z87qUstm00aqNFHAX6j6riWfdWrQI"
        )
        GiphyDialogFragment.newInstance().show(
            supportFragmentManager,
            "giphy_dialog"
        )
    }
}
