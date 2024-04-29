package com.example.shoppingapp

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.appsamurai.storyly.*
import com.appsamurai.storyly.analytics.StorylyEvent
import com.appsamurai.storyly.analytics.StorylyEvent.*
import com.appsamurai.storyly.config.StorylyConfig
import com.appsamurai.storyly.config.StorylyProductConfig
import com.appsamurai.storyly.config.StorylyShareConfig
import com.appsamurai.storyly.config.styling.group.StorylyStoryGroupStyling
import com.appsamurai.storyly.config.styling.story.StorylyStoryStyling
import com.appsamurai.storyly.data.managers.product.STRCart
import com.appsamurai.storyly.data.managers.product.STRCartEventResult
import com.appsamurai.storyly.data.managers.product.STRCartItem
import com.appsamurai.storyly.data.managers.product.STRProductInformation
import com.appsamurai.storyly.data.managers.product.STRProductItem
import com.appsamurai.storyly.data.managers.product.STRProductVariant
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
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NfaWQiOjcxMzcsImFwcF9pZCI6MTE3NDYsImluc19pZCI6MTI1ODJ9.k7IVUbx4b23WTobh7u-ZIAYMdjN1xIDyA8z5WWncWbU",
            StorylyConfig.Builder()
                .setStoryGroupStyling(
                    StorylyStoryGroupStyling.Builder()
                        //.setIconHeight(300)
                        //.setIconWidth(500)
                        //.setIconCornerRadius(30)
                        .setSize(StoryGroupSize.Large)
                        .build()
                )
                .setProductConfig(
                    StorylyProductConfig.Builder()
                        .setCartAvailability(true)
                        .setProductFeed(productFeed)
                        .build()
                )
                .setStoryStyling(
                    StorylyStoryStyling.Builder()
                        //.setHeaderIconVisibility(isVisible = true)
                        .build()
                )
                /*.setShareConfig(
                    StorylyShareConfig.Builder()
                        .setShareUrl("storyly.io/v2/share/{story_id}")
                        .build()
                )*/
                //.setLayoutDirection(StorylyLayoutDirection.RTL)
                .setTestMode(true)
                //.setLabels(labels = setOf("english", "turkey", "french", "de", "default","homepage"))
                .setCustomParameter("__494319cd8a8c4ea4aa0162275356c9ec")
                .setUserData(mapOf(
                    "name" to "Sahin",
                    "user_name" to "sahin"
                ))
                .build()
        )


        //storylyView.openStory("93337", "855786", PlayMode.Default)
        storylyView.hydrateProducts(getSampleHydrateProductList())

        val items = mutableListOf<STRCartItem>()

        val productListener = object : StorylyProductListener {
            override fun storylyHydration(storylyView: StorylyView, products: List<STRProductInformation>) {
                Log.d("TAG", "storylyHydration: ${products.toList()}")
            }

            override fun storylyEvent(storylyView: StorylyView, event: StorylyEvent) {
                when (event){
                    StoryCheckoutButtonClicked -> {
                        Log.d("ShoppingEvent:", "StoryCheckoutButtonClicked")
                    }
                    StoryCartButtonClicked -> {
                        Log.d("ShoppingEvent:", "StoryCartButtonClicked")
                    }
                    StoryCartViewClicked -> {
                        Log.d("ShoppingEvent:", "StoryCartViewClicked")
                    }
                    StoryProductSelected -> {
                        Log.d("ShoppingEvent:", "StoryProductSelected")
                    }
                    else -> {}
                }
            }

            override fun storylyUpdateCartEvent(storylyView: StorylyView, event: StorylyEvent, cart: STRCart?, change: STRCartItem?, onSuccess: ((STRCart?) -> Unit)?, onFail: ((STRCartEventResult) -> Unit)?) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    items.removeIf { it.item.productId == change?.item?.productId }
                }

                if (event != StoryProductRemoved) {
                    val itemPrice = (change?.item?.salesPrice ?: change?.item?.price ?: 0f) * (change?.quantity ?: 0)
                    val itemOldPrice = (change?.item?.price ?: 0f) * (change?.quantity ?: 0)

                    change?.copy(totalPrice = itemPrice, oldTotalPrice = itemOldPrice)?.let { items.add(it) }
                }

                val cartPrice = items.toList().sumOf { it.totalPrice?.toInt() ?: 0 }
                val cartOldPrice = items.toList().sumOf { it.oldTotalPrice?.toInt() ?: 0 }

                Handler().postDelayed({
                    onSuccess?.invoke(STRCart(items = items, oldTotalPrice = cartOldPrice.toFloat(), totalPrice = cartPrice.toFloat(), currency = "USD"))
                }, 500)

                when (event){
                    StorylyEvent.StoryProductAdded -> {
                        Log.d("Shopping", "StoryProductAdded")
                        //This event sent when a product is added.
                    }
                    StorylyEvent.StoryProductUpdated -> {
                        Log.d("Shopping", "StoryProductUpdated")
                        //This event sent when a product is updated.
                    }
                    StorylyEvent.StoryProductRemoved -> {
                        Log.d("Shopping", "StoryProductRemoved")
                        //This event sent when a product is removed.
                    }

                    else -> {}
                }

                Log.d("Shoppable", "ShoppableEvent: ${event}")
                Log.d("Shoppable", "ShoppableCart: ${cart}")
                Log.d("Shoppable", "ShoppableChange: ${change?.item?.productId}")

            }
        }

        storylyView.storylyProductListener = productListener

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
                storylyView.closeStory()
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

                val unseenSG = storyGroupList.filter { !it.seen }.size



                Log.d("Unseen", "Unseen SG:${unseenSG}")

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
                Log.d("event----->","StoryPaused:$StoryPaused ") // You can track StoryViewed event.
                Log.d("event----->","StoryViewed:$StoryViewed ") // You can track StoryViewed event.
                Log.d ("Event", "PromoCode:$StoryPromoCodeCopied")
                Log.d("QUIZ----->","StoryQuizComponent:${quizComponent?.title} ") // You can track StoryViewed event.


                when (event) {
                    StorylyEvent.StoryViewed -> {
                        Log.d("Seen", "seen ${story}" )
                    }

                    else -> {}
                }

                Log.d("--------------StoryType-------------------", "StoryComponent{${storyComponent?.type}}")


                Log.d("--------------storyly-------------------","story:${story?.seen} and ${story?.uniqueId}")
                Log.d("--------------Index-------------------","storyGroupIndex: ${storyGroup?.name} and storyIndex: ${story?.index}")

            }

        }
    }
    val productFeed = mapOf<String, List<STRProductItem>>(
        "SG_1" to listOf(
            STRProductItem(
                productId = "1",
                productGroupId="1",
                title="High-waist midi skirt",
                url="https://www.storyly.io/",
                desc="High-waist midi skirt made of a viscose blend. Featuring a slit at the hem and invisible zip fastening.",
                price= 25.99F,
                salesPrice= 25.99F,
                currency="USD",
                imageUrls= listOf(
                    "https://random-feed-generator.vercel.app/images/clothes/group-1/1-6D7868.jpg",
                    "https://random-feed-generator.vercel.app/images/clothes/group-1/2-6D7868.jpg",
                 ),
                variants= listOf<STRProductVariant>(),
                ctaText= "Buy now"
                )
            ),
        )
    private fun getSampleHydrateProductList(): List<STRProductItem> {
        return listOf(
            STRProductItem(
                "58",
                "6",
                "Calvin Klein Beauty woda perfumowana",
                "https://www.flaconi.pl/perfumy/calvin-klein/beauty/calvin-klein-beauty-woda-perfumowana.html",
                "Calvin Klein Beauty - zapach, który czyni cię piękną Na pewno znasz to wyjątkowe uczucie, które czujesz",
                244.40f,
                157.68f,
                "PLN",
                listOf(
                    "https://cdn.flaconi.pl/media/catalog/product/c/a/cacharel-noa-eau-de-toilette-30-ml-3360373016334.jpg",
                    "https://cdn.flaconi.pl/media/catalog/product/c/a/calvin-klein-beauty-eau-de-parfum-30-ml-3607340216046-pack.jpg",
                ),
                listOf(),
                "Buy now Calvin"
            ),
            STRProductItem(
                "89",
                "10",
                "BVLGARI Aqva pour Homme woda toaletowa",
                "https://www.flaconi.pl/perfumy/bvlgari/aqva-pour-homme/bvlgari-aqva-pour-homme-woda-toaletowa.html",
                "Bvlgari Aqva Pour Homme – zapach przywodzący na myśl świeżość oceanu Bvlgari Aqva Pour Homme to orzeźwiający",
                142.40f,
                117.68f,
                "PLN",
                listOf(
                    "https://cdn.flaconi.pl/media/catalog/product/b/v/bvlgari-aqva-pour-homme-eau-de-toilette_1.jpg",
                    "https://cdn.flaconi.pl/media/catalog/product/b/v/bvlgari-aqva-pour-homme-eau-de-toilette-100-ml-783320413698-pack.jpg",
                ),
                listOf(),
                "Buy now Bvlgari"
            ),

            )
    }
}