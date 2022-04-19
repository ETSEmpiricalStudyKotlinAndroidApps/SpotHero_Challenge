April 19th, 2022:

Today I had a coding interview with "Spot Hero" and these were the questions which were asked in
this 60 mins interview 👇👇

<ol>
<li>first question 👉 Add a progress bar to the main page where you're showing the list of spots.</li>

<br/>
<u>I did it correctly.</u> 😎💪 (The source code for that is added in the last commit).

<br/>
<li>Second question 👉 how you would make sure to deal with the error that comes from the API ?
</li>

<u>I answered it almost wrong</u> 😢<br/><br/>
The correct answer is that you already are receiving the data as
a <a href="http://reactivex.io/RxJava/3.x/javadoc/io/reactivex/rxjava3/core/Single.html">Single</a>
and
the <a href="http://reactivex.io/RxJava/3.x/javadoc/io/reactivex/rxjava3/core/SingleObserver.html">
SingleObserver</a> that looks at this object already has
the <a href="http://reactivex.io/RxJava/3.x/javadoc/io/reactivex/rxjava3/core/SingleObserver.html#onError-java.lang.Throwable-">
onError()</a> callback for doing this stuff.
<li>Third question 👉 how user will come back from second activity to the main activity.
</li><br/>


<li>Fourth question 👉 how you would save the data in the app so user could access it offline?
</li>
I explained about <a href="https://www.raywenderlich.com/books/real-world-android-by-tutorials/v1.0/chapters/5-data-layer-caching">caching</a> the data as a HashMap and also <a href="https://developer.android.com/jetpack/androidx/releases/room">Room</a> database.<br/>
<br/>
<li>Fifth question 👉 How you would control different states of the data when you're having so many ViewModels?
</li><br/>
I explained a bit about how you can use repositories in MVVM and also explained that you could
  go for MVI, so adding new features to the app will get a lot easier.
</ol>


