package no.kristiania.assignment_noforeignland

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_course_lesson.*

class CourseLessonActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_course_lesson)

        val courseLink = intent.getStringExtra(DetailActivity.DetailLessonViewHolder.COURE_LESSON_LINK_KEY)

        webView_coirse_lesson.settings.javaScriptEnabled = true
        webView_coirse_lesson.settings.loadWithOverviewMode = true
        webView_coirse_lesson.settings.useWideViewPort = true


        webView_coirse_lesson.loadUrl(courseLink)

    }
}