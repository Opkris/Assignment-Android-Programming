package no.kristiania.assignment_noforeignland.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import no.kristiania.assignment_noforeignland.MainActivity
import no.kristiania.assignment_noforeignland.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // create the loading time of the "splash"
        val SPLASH_LOADING_TIME:Long = 3000 // 3.0 seconds

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            startActivity(Intent(this, MainActivity::class.java))
            // close this activity
            finish()
        }, SPLASH_LOADING_TIME)
    }// end onCreate
}
