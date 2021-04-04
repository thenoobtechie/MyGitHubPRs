package co.jp.catech.itohen.mygitcprs

import android.widget.ImageView
import com.squareup.picasso.Picasso

class Utility {

    companion object {

        const val serverDateFormat = "yyyy-MM-dd'T'HH:mm:ss"
        const val displayDateFormat = "dd MMM yyyy, HH:mm"

        const val API_RESPONSE_LOG_TAG = "apiResponseTag"

        fun loadImage(imageURL: String, imgUserImg: ImageView) {

            if (imageURL.isEmpty()) {

                Picasso
                    .get()
                    .load(R.mipmap.ic_octocat_git)
                    .into(imgUserImg)
            } else
                Picasso
                    .get()
                    .load(imageURL)
                    .placeholder(R.mipmap.ic_octocat_git)
                    .into(imgUserImg)
        }
    }
}