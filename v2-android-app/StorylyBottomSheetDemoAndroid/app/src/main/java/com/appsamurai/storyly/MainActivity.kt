package com.appsamurai.storyly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.appsamurai.storyly.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.storyly.storylyInit = StorylyInit(
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NfaWQiOjcxMzcsImFwcF9pZCI6MTE3NDYsImluc19pZCI6MTI1ODJ9.k7IVUbx4b23WTobh7u-ZIAYMdjN1xIDyA8z5WWncWbU",
            //customParameter = "lcorlandini@gmail.com"
            )

        val storylyListener = object : StorylyListener {
            override fun storylyActionClicked(storylyView: StorylyView, story: Story) {
                val fragment = DemoFragment()
                fragment.onCloseClick = {
                    removeFragments()
                }
                showExternalFragment(fragment)
            }
        }

        binding.storyly.storylyListener = storylyListener
    }

    fun showExternalFragment(fragment : Fragment) {
        binding.storyly.showExternalFragment(fragment)
    }

    fun removeFragments() {
        binding.storyly.dismissAllExternalFragment()
    }
}