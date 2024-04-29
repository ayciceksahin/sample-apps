# Storyly Bottom Sheet

This demo shows how to show Bottom Sheets in front of stories.

You can browse the [official integration document](https://integration.storyly.io/android/fragment-overlay.html) before viewing the demo.

<img height="500" src="https://user-images.githubusercontent.com/45481866/178672249-9d1b88a0-368e-4ded-8bd6-2419286d6d42.png" alt="Bottom Sheet"/>

### Show Multiple Bottom Sheet via Fragment Overlay
Storyly supports overlay fragments. Once you create a Storyly instance and Fragment instance, just send fragment to showExternalFragment method as a parameter.

With adding an interlayer fragment with fragment overlay. The screen and bottom sheets you want to open can be displayed through this fragment.

Firstly show fragment and open whatever you want on this fragment
```kotlin
val fragment = Fragment()
storylyView.showExternalFragment(fragment)
```