# MediaPlayer
Overall, this was a fun project to create. It was my first time working with a media player, so I was excited to tackle that challenge. The project scrolls vertically, one item at a time. I followed the Figma design as closely as possible but added some slight twists. For example, I implemented a gradual blur effect at the bottom half of the image to improve button visibility.

The side buttons are currently set up to mock functionality, such as liking, disliking, or sharing. The app does have its quirks, such as playing the next song if you scroll halfway up or down (though this might be something to clarify with a product manager regarding expected behavior). I attempted to implement swipe-to-refresh, but I couldn’t get it to work alongside the Vertical Pager.

I decided to limit scrolling to one item at a time because the alternative approach (allowing fast scrolling) caused issues with the media player. This decision was based on the assumption that users have a stable internet connection and aligns with the Vertical Pager functionality.

Currently, if the app is loaded in airplane mode, a generic error message is displayed, and there’s no way to refresh without restarting the app. If I had managed to get swipe-to-refresh working, this issue would have been resolved. Another issue is that rotating the screen restarts the song instead of continuing playback from the current position. I believe this could be resolved by saving the playback state in the ViewModel and resuming from the saved position for a better user experience. However, for an app like this, I think locking it to portrait mode would offer the best user experience.

With more time I would fully implemented the analytics module, add swipe to refresh and fix the half way scroll issue.

<h2><b><i>I was very limited with time due to personal reasons.</i></b></h2>

<h1>Built Using</h1>
<li>Retrofit: Used to easily and safely make HTTP requests to RESTFUL Apis </li>
<li>Hilt: Used for dependency injection to help modularize the code for testability and readability</li>
<li>Coroutines: Used for its ansynchronous porgamming</li>
<li>Jetpack Compose: Used per project requirements</li>
<li>Jetpack Navigation: Used to simplify navigation</li>
<li>Pager 3: Used for Pagination</li>
<li>Coil: Helps with image caching and loading</li>
<li>Firebase Analytics: Used to track basic user interactions</li>
<li>Media3: Used for Playing music</li>
<li>VerticalPager: Used for making the items scrollable 1 by 1</li>
<li>Used a data mapper to help structure data better. For example, the ui does not need to know page size and such</li>
<li>Misc: MVVM, Flows, Firebase Analytics, </li>    

<p float="left">
    <img src="https://github.com/user-attachments/assets/0b8eb959-c7d2-4192-85b8-bcf984a14858" width="300" height="600" />
</p>

