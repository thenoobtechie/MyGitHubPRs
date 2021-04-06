package co.jp.catech.itohen.mygitcprs

import android.widget.ImageView
import com.squareup.picasso.Picasso

class Utility {

    companion object {

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