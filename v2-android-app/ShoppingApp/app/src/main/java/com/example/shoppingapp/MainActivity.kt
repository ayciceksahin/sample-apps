package com.example.shoppingapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.appsamurai.storyly.*
import com.appsamurai.storyly.analytics.StorylyEvent
import com.appsamurai.storyly.config.StorylyConfig
import com.appsamurai.storyly.config.StorylyProductConfig
import com.appsamurai.storyly.config.styling.group.StorylyStoryGroupStyling
import com.appsamurai.storyly.config.styling.story.StorylyStoryStyling
import com.example.shoppingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val mainPage = binding.root
        setContentView(mainPage)

        val storylyView = findViewById<StorylyView>(R.id.storyly_view)

        storylyView.storylyInit = StorylyInit(
            //"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NfaWQiOjU5MDgsImFwcF9pZCI6MTEwNDEsImluc19pZCI6MTE3NTZ9.YM8G9WYyuJW9QLuJMxO04-Nv0pa3-w-S_cH7rSa4fSY",
            //WilliamHill"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NfaWQiOjg1NjUsImFwcF9pZCI6MTQ2MDEsImluc19pZCI6MTU5MzZ9.dzaPwAxINtVo-BtK8zBHtVxJNuX0fwlj_HMRzHvPYnU",
            //"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NfaWQiOjcxMzcsImFwcF9pZCI6MTMxMTUsImluc19pZCI6MTQyNDh9.8_WHJ9WFClC2UCi3MVBc4B4m1Hfce-LHrA0SUcnJiVo",
            //"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NfaWQiOjE4OTEsImFwcF9pZCI6MTU1MSwiaW5zX2lkIjoxNjAyfQ.goOlDWhV2ikJwJd3tvx2bKg5Ot0Qu_cDvMk0MbDpm9E",
            //"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NfaWQiOjE5MjYsImFwcF9pZCI6MTU4MywiaW5zX2lkIjoxNjM1fQ.iI0yUR2WIAyXNvIgQuVDIle-42sPNcno6jaQHAxNko0",
            //"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NfaWQiOjEwNTY5LCJhcHBfaWQiOjE1ODg2LCJpbnNfaWQiOjE3NDIwfQ.vM9PyK5NlmKwS8IibZZgTJ2fmSoK1h7yf_zfUdeH-BI",
            //"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NfaWQiOjg2MzcsImFwcF9pZCI6MTM2ODMsImluc19pZCI6MTQ5MDZ9.esNPIaerwFGweMMxi16W4CrjPb2ZDkQ42yA6U3DdIzA",
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NfaWQiOjcxMzcsImFwcF9pZCI6MTE3NDYsImluc19pZCI6MTI1ODJ9.k7IVUbx4b23WTobh7u-ZIAYMdjN1xIDyA8z5WWncWbU",
            StorylyConfig.Builder()
                .setStoryGroupStyling(
                    StorylyStoryGroupStyling.Builder()
                        //.setIconHeight(200)
                        //.setIconWidth(500)
                        //.setIconCornerRadius(30)
                        .setSize(StoryGroupSize.Large)
                        .build()
                )
                .setProductConfig(
                    StorylyProductConfig.Builder()
                        .setProductFeedLanguage("")
                        .setProductFeedCountry("")
                        .build()
                )
                .setStoryStyling(
                    StorylyStoryStyling.Builder()
                        //.setHeaderIconVisibility(isVisible = true)
                        .build()
                )
                //.setLabels(labels = setOf("english", "turkey", "french", "test", "default"))
                .setCustomParameter("3")
                .setUserData(mapOf(
                    "user_name" to "Saiful",
                    "recommend_make" to "BMW",
                    "recommend_model" to "3 series",
                    //"recommend_car_json" to "{\"json\"=\"data\"}",
                    "recommend_car_json" to "http://www.google.com"
                ))
                .build()
        )


        //storylyView.openStory("93337", "855786", PlayMode.Default)

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
                //storylyView.pauseStory()
                /*Handler().postDelayed({
                    storylyView.closeStory()
                }, 2000)*/

                /*val goSecondPage = Intent(applicationContext, MainActivity2::class.java)
                startActivity(goSecondPage) */ // go to other page

                /* val fragment = DemoFragment()
                 fragment.onCloseClick = {
                     removeFragments()
                 }
                 showExternalFragment(fragment) */
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

                Log.d("storylyURL", "URL:${StorylyDataSource.API}")

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