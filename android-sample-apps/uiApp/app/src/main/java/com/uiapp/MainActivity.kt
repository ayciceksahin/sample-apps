package com.uiapp

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uiapp.databinding.ActivityMainBinding
import androidx.fragment.app.Fragment
import com.appsamurai.storyly.*
import android.util.Log
import android.view.View
import com.appsamurai.storyly.StoryComponentType.*
import com.appsamurai.storyly.analytics.StorylyEvent
import com.appsamurai.storyly.config.StorylyConfig
import com.appsamurai.storyly.data.managers.product.STRProductItem
import com.appsamurai.storyly.data.managers.product.STRProductVariant
import com.appsamurai.storyly.exoplayer2.hls.playlist.HlsMediaPlaylist

import java.util.*


class MainActivity : AppCompatActivity() {
    companion object {
        const val segmentData = "french"
        const val customData = "1"
        //const val storylyFlag = true
    }
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val lang = Locale.getDefault().getLanguage()
        Log.d("<<<<<<<Lang>>>>>>>","Lang: {$lang}")
        binding = ActivityMainBinding.inflate(layoutInflater)
        val firstPage = binding.root
        setContentView(firstPage)
        val storylyFlag = true
        val storylyView = findViewById<StorylyView>(R.id.storyly_view)
        if (storylyFlag == true) {
            storylyView.storylyInit = StorylyInit(
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NfaWQiOjcxMzcsImFwcF9pZCI6MTE3NDYsImluc19pZCI6MTI1ODJ9.k7IVUbx4b23WTobh7u-ZIAYMdjN1xIDyA8z5WWncWbU",
                //"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NfaWQiOjcxMzcsImFwcF9pZCI6MTE3NTIsImluc19pZCI6MTMzMDF9.0VSpIaKA9IzeTZ1CICJOkINWyENP7ROPN_d5Qo5yEo4",
                StorylyConfig.Builder()
                    .setLabels(labels = setOf("hub"))
                    .setCustomParameter("62340800")
                    .setUserData(mapOf(
                        "name" to "Sahin",
                        "user_name" to "sahin",
                    ))
                    .build()
            )
        }
        Log.d("Init", "Init ${storylyView.storylyInit}")
        if (storylyFlag == false) {
            storylyView.visibility = View.GONE
        }


        binding.button.setOnClickListener{
            val goSecondPage = Intent(applicationContext, MainActivity2::class.java)
            startActivity(goSecondPage)
        }



        storylyView.storylyListener = object : StorylyListener {
            var initialLoad = true

            override fun storylyActionClicked(storylyView: StorylyView, story: Story) {

                story.media.actionUrl.let { url ->
                    Log.d("-----------[storyly]--------", "IntegrationActivity:storylyActionClicked - forwarding to url {$url} and ${story.media.type} ")
                    startActivity(
                        Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("$url")
                        }
                    )
                }

            }

            override fun storylyLoaded(
                storylyView: StorylyView,
                storyGroupList: List<StoryGroup>,
                dataSource: StorylyDataSource
            ) {
                //Log.d("[storyly]", "IntegrationActivity:storylyLoaded - storyGroupList size {${storyGroupList.size}} - source {$dataSource}")

                /*if (initialLoad && storyGroupList.isNotEmpty()) {
                    initialLoad = false
                    storylyView.visibility = View.VISIBLE
                    Log.d("customParameter", "-----> {${customData}}" )
                }*/

                Log.d("storylyURL", "URL:${storyGroupList}")

                if (dataSource == StorylyDataSource.Local) {
                    Log.d("[Local]", "IntegrationActivity:storylyLoaded - storyGroupList size {${storyGroupList.size}} - source {$dataSource}")
                }

                /*if (dataSource == StorylyDataSource.API) {
                    storylyView.openStory("62376", "542324")
                }*/
            }

            override fun storylyLoadFailed(
                storylyView: StorylyView,
                errorMessage: String
            ) {
                Log.d("[storyly]", "IntegrationActivity:storylyLoadFailed - error {$errorMessage}")

            }

            override fun storylyUserInteracted(
                storylyView: StorylyView,
                storyGroup: StoryGroup,
                story: Story,
                storyComponent: StoryComponent
            ) {

                val quizComponent = storyComponent as? StoryQuizComponent
                val pollComponent = storyComponent as? StoryPollComponent
                val emojiComponent = storyComponent as? StoryEmojiComponent
                val ratingComponent = storyComponent as? StoryRatingComponent
                val promoCodeComponent = storyComponent as? StoryPromoCodeComponent
                val commentComponent = storyComponent as? StoryCommentComponent

                Log.d("--------------storyly-------------------", "StoryComponent{${storyComponent}} and {${story}}")
                Log.d("--------------StoryComponent-------------------", "promoCodeComponent:${promoCodeComponent?.text}")
                Log.d("emojiComponent","customPayload:${emojiComponent?.customPayload}")
                Log.d("quizComponent","customPayload:${quizComponent?.customPayload} ")



            }

            override fun storylyEvent(storylyView: StorylyView, event: StorylyEvent,
                                      storyGroup: StoryGroup?, story: Story?,
                                      storyComponent: StoryComponent?) {
                val quizComponent = storyComponent as? StoryQuizComponent
                val pollComponent = storyComponent as? StoryPollComponent
                val emojiComponent = storyComponent as? StoryEmojiComponent
                val ratingComponent = storyComponent as? StoryRatingComponent
                val promoCodeComponent = storyComponent as? StoryPromoCodeComponent
                val commentComponent = storyComponent as? StoryCommentComponent

                /*val customPay = when (storyComponent?.type){
                    Emoji -> storyComponent as? StoryEmojiComponent
                    Quiz -> storyComponent as? StoryQuizComponent
                    Poll -> storyComponent as? StoryPollComponent
                    Rating -> storyComponent as? StoryRatingComponent
                    PromoCode -> storyComponent as? StoryPromoCodeComponent
                    //SwipeAction -> storyComponent as? Story
                    //ButtonAction -> storyComponent as?
                    //Text -> storyComponent as? StoryEmojiComponent
                    //Image -> storyComponent as? StoryEmojiComponent
                    //Countdown -> storyComponent as? StoryComponent
                    //ProductTag -> storyComponent as? StoryEmojiComponent
                    Comment -> storyComponent as? StoryCommentComponent
                    //Video -> storyComponent as? StoryEmojiComponent
                    //Vod -> storyComponent as? StoryEmojiComponent
                    //null -> storyComponent as? StoryEmojiComponent
                    SwipeAction -> TODO()
                    ButtonAction -> TODO()
                    Text -> TODO()
                    Image -> TODO()
                    Countdown -> TODO()
                    ProductTag -> TODO()
                    Video -> TODO()
                    Vod -> TODO()
                    null -> TODO()
                }*/

                //Log.d("Storyly","customPayload:${emojiComponent?.customPayload} ")
                Log.d("event----->","StoryPaused:${StorylyEvent.StoryPaused} ") // You can track StoryViewed event.
                Log.d("event----->","StoryViewed:${StorylyEvent.StoryViewed} ") // You can track StoryViewed event.
                Log.d ("Event", "PromoCode:${StorylyEvent.StoryPromoCodeCopied}")



                Log.d("--------------StoryType-------------------", "StoryComponent{${storyComponent?.type}}")


                Log.d("--------------storyly-------------------","story:${story?.seen} and ${story?.uniqueId}")
                Log.d("--------------Index-------------------","storyGroupIndex: ${storyGroup?.index} and storyIndex: ${story?.index}")

            }

        }



    }

}


