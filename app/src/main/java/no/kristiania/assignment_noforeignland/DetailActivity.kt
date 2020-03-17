package no.kristiania.assignment_noforeignland

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.view.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class DetailActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        recyclerView_main.layoutManager = LinearLayoutManager(this)
//        recyclerView_main.adapter = DetailAdapter()

        val  navBarTitle = intent.getStringExtra(CustomViewHolder.VIDEO_TITLE_KEY)
        supportActionBar?.title = navBarTitle


//        println(detailUrl)

        fetchJSON()


    }// end onCreate

    private fun fetchJSON() {
        val videoId = intent.getIntExtra(CustomViewHolder.VIDEO_ID_KEY,-1)
        val detailUrl = "https://api.letsbuildthatapp.com/youtube/course_detail?id=" + videoId

        val client = OkHttpClient()
        val request = Request.Builder().url(detailUrl).build()
        client.newCall(request).enqueue(object: Callback {

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)

                val gson = GsonBuilder().create()

                val courseLessons= gson.fromJson(body, Array<CourseLesson>::class.java)

                runOnUiThread {

                    recyclerView_main.adapter = DetailAdapter(courseLessons)
                }
//                println(body)

            }

            override fun onFailure(call: Call, e: IOException) {

            }
        })
    }

    private class DetailAdapter(val coursLessons: Array<CourseLesson>): RecyclerView.Adapter<DetailLessonViewHolder>(){

        override fun getItemCount(): Int {
            return coursLessons.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailLessonViewHolder {

            val layoutInflater = LayoutInflater.from(parent.context)
            val customView = layoutInflater.inflate(R.layout.activity_detail, parent, false)

            return DetailLessonViewHolder(customView)
        }

        override fun onBindViewHolder(holder: DetailLessonViewHolder, position: Int) {

            val courseLesson = coursLessons.get(position)

            holder.customView.textView_detail_information.text = courseLesson.name

            val imageView = holder.customView.imageView_detail_image
            Picasso.get().load(courseLesson.imageUrl).into(imageView)

            holder.courseLesson = courseLesson
        }


    }
     class DetailLessonViewHolder(val customView: View, var courseLesson: CourseLesson? = null): RecyclerView.ViewHolder(customView){

        companion object{
            val COURE_LESSON_LINK_KEY = "COURSE_LESSON_LINK"
        }

        init {
            customView.setOnClickListener {
                val intent = Intent(customView.context, CourseLessonActivity::class.java)

                intent.putExtra(COURE_LESSON_LINK_KEY, courseLesson?.link)

                customView.context.startActivity(intent)

            }
        }
    }
}