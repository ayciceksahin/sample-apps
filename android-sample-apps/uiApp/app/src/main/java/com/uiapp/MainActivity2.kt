package com.uiapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.appsamurai.storyly.*
import com.appsamurai.storyly.analytics.StorylyEvent
import com.uiapp.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main2)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        val secondPage = binding.root
        setContentView(secondPage)

        val action: String? = intent?.action
        val data1: Uri? = intent?.data
        Log.d("<<<<<<<data>>>>>>>","uri: {$data1} and {$action}")

        val storylyView = findViewById<StorylyView>(R.id.storyly_view)

        val userPropertiesData= mapOf(
            "user_id" to "John",
            "country_count" to "9",
            "mile_count" to "2751"
        )

        storylyView.storylyInit = StorylyInit(
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NfaWQiOjcxMzcsImFwcF9pZCI6MTE3NDYsImluc19pZCI6MTI1ODJ9.k7IVUbx4b23WTobh7u-ZIAYMdjN1xIDyA8z5WWncWbU",
            //"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NfaWQiOjcxMzcsImFwcF9pZCI6MTE3NDYsImluc19pZCI6MTI1ODJ9.k7IVUbx4b23WTobh7u-ZIAYMdjN1xIDyA8z5WWncWbU",
            //"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NfaWQiOjcxMzcsImFwcF9pZCI6MTE3NTIsImluc19pZCI6MTI1ODl9.Z_F9S6OyES9rfwYDFV47zT0_DbP0WUwqE5EmYOpCZRQ",
            //"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NfaWQiOjcxMzcsImFwcF9pZCI6MTE3NTIsImluc19pZCI6MTMzMDF9.0VSpIaKA9IzeTZ1CICJOkINWyENP7ROPN_d5Qo5yEo4",

        )
        data1?.let {
            storylyView.openStory(data1)
        }




        storylyView.storylyListener = object : StorylyListener {

            override fun storylyLoaded(
                storylyView: StorylyView,
                storyGroupList: List<StoryGroup>,
                dataSource: StorylyDataSource
            ) {

                Log.d("[storyly]", "IntegrationActivity:storylyLoaded - storyGroupList size {${storyGroupList.size}} - source {$dataSource}")

            }

            override fun storylyEvent(storylyView: StorylyView, event: StorylyEvent,
                                      storyGroup: StoryGroup?, story: Story?,
                                      storyComponent: StoryComponent?) {


                Log.d("--------------storyly-------------------","story:${story?.seen} and ${story?.uniqueId}")
                Log.d("--------------Index-------------------","storyGroupIndex: ${storyGroup?.index} and storyIndex: ${story?.index}")

            }

        }


        binding.button2.setOnClickListener{
            val goFirstPage = Intent(applicationContext, MainActivity::class.java)
            startActivity(goFirstPage)
        }

    }
}