package no.kristiania.assignment_noforeignland.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import no.kristiania.assignment_noforeignland.MainActivity
import no.kristiania.assignment_noforeignland.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // create the loading time of the "splash"
        val SPLASH_LOADING_TIME:Long = 1500 // 0.5 seconds

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
