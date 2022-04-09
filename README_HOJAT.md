Hojat Ghasemi hojat72elect@gmail.com

## chosen approach:

* MVVM
* RxJava
* DataBindingUtil

## The approach to architecture:

The app has already implemented the "Model" and since there are not too many user stories, it will
be a fairly small app which can be laid out with a Model-View-ViewModel architecture.<br/><br/>
Furthermore, since the app is in the initial development stages and doesn't have many features, I
have chosen
the <a href="https://proandroiddev.com/package-by-type-by-layer-by-feature-vs-package-by-layered-feature-e59921a4dffa">
package by layer</a> approach; `model` layer is already in a separate package so I put
the `ViewModel` layer in its own package as well.

--------------------------------------------

## ViewModel:

Reads data from `Model` and delivers them to `View`. None of the definitions in this layer should
have any direct references to the UI or XML layout. Remember that in a perfect implementation of
MVVM architecture, your `ViewModel` layer is so portable that you can just copy/paste it into
another project and it will be usable with minor changes.

### ViewModel/SpotHeroViewModelFactory.kt

Inside the `ViewModel` layer, I firstly implemented the `SpotHeroViewModelFactory` class which is
nothing more than a simple builder for our `ViewModel` and extends
the `androidx.lifecycle.ViewModelProvider` class. It simply makes sure that the class that intends
to communicate with `ViewModel` is compatible with `ViewModel` class (more info
found <a href="https://developer.android.com/reference/java/lang/Class#isAssignableFrom(java.lang.Class%3C?%3E)">
here</a>). It also injects an instance of our `SpotHeroApi` to the created `ViewModel` (to be used
for communication between `ViewModel` and `Model`).

### ViewModel/SpotHeroViewModel.kt

It's the main part of the `ViewModel` layer and extends the `androidx.lifecycle.ViewModel` class.
The needed dependedncy to `SpotHeroApi` has been injected to this class via a parameter in the
constructor. By using its reference to the `SpotHeroApi`, it gets an observable object which is
a `Single<List<Spot>>` and our Activities or Fragments in the View layer can define an instance of
this ViewModel class to get this list of Spots.


--------------------------------------------

## View:

This layer contains all the GUI of the app and Activities/Fragments. User interactions and user
generated data is captured in this layer and sent back to `ViewModel` in order to be processed or
saved into Model layer. <br/><br/> In a perfect implementation of MVVM, definitions inside this
layer are as lightweight as possible, and we try to never put heavy logic of the app in this layer.

I'm interested in scalability in apps so I'm assuming that in the future development iterations, the
UI will get more complex and we will need a `Binder` between `View` and `ViewModel` layers in order
to receive events from GUI and deliever them to ViewModel; and also deliever needed data from
ViewModel to View.

Due to the lack of time, I can't implement a `Binder` part of the architecture right now, but I'm
going to define an "
Event"
class which is the object containing user info. when the app size got bigger, we will try to
implement a "Binder" in order to make our fragments/activities as light-weight as possible and also
keep our ViewModel as portable as possible.

And also, "DataBindingUtil" was used to make the connection between xml layouts and View files
easier.



--------------------------------------------

### MainActivity.kt

### MyRecyclerViewAdapter.kt

--------------------------------------------

## SH3:

The ticket described in SH-4 will add a new screen to the app. Fully implementing this user story
includes these parts: 👇👇
<ol>

<li>Designing the <b><u>UI XML</u></b></li>
<li>writing the Activity/Fragment in <b><u>View layer</u></b> that controls the UI </li>
<li>changing the <b><u>ViewModel layer</u></b> so it provides the needed data for this UI and accepts the user data gathered by this UI</li>
<li>Possibly updating the <b><u>Model layer</u></b> to save the data gathered via this UI in a local or online database</li>
<li><b><u>Test and Debug</u></b> the Activity/Fragment of this UI</li>
</ol>

⚠️ Remember that in this assessment, I'm only considering
a <a href="https://en.wikipedia.org/wiki/Minimum_viable_product">minimum viable product</a> which
simply features all the needed conditions and uses standard android builtin libraries. Adding any
additional material design elements (such as animations, icons, and drawables) or updating the
features will take more time than what I have specified in here.<br/><br/>
⚠️ The calculation of number of hours that it will take me to implement this user story is highly
dependent to my current level of <u>*experience*</u> and <u>*speed*</u> in developing such a
scenario and getting it to a bug-free stable state. If I were asked the same question a few years
later, I probably would give a different answer as my level of experience and knowledge in
developing UIs will be different from what it is now.

--------------------------------------------


<ol>


<li><b>A brief overview of how you would implement the solution at a technical level</b></li>

### 1. UI XML:

All the elements in this UI page are available in standard android XML views/widgets. I will do the UI design with ImageView, TextView, EditText, and Button as widgets and will use ConstraintLayout and LinearLayout for managing the elements on screen.

### 2. View layer:

I would create an AppCompatActivity for this page which shows it in its `onCreate()` callback and also for the scenario that "when all the required feilds are entered the button is disabled", we can use reactive programming paradigm to propagate UI changes (with ReactiveX libraries). In order to get rid of `findViewById()` for connection between Activity and XML UI, I'd use <b>Data Binding Library</b> which is part of Jetpack library and provides view binding for Activities/Fragments in our View layer.  

### 3. ViewModel layer:

If we wanted to save the checkout information to a local database, I would add some functions to the ViewModel in order to manipulate DAOs (Data Access Objects) in a Room database in Model layer and save data. Alternatively if that info was uploaded to cloud servers, I would add some functions to ViewModel for calling the Retrofit library and uploading the data.

### 4. Model layer:

The implementation of this part depends on what path you have taken in the previous part (ViewModel layer); if we're saving data locally, I would extend the RoomDatabase class. But if we were to upload that data to a RESTFul web server, I would define an interface containing functions with Retrofit annotations for performing HTTP requets and connecting to web server.

### 5. Test, Debug, and publish:

In order to test the UI classes, I would try to write automated test suite with the help of <a href="https://developer.android.com/training/testing/espresso">Espresso</a>. test, debug and publishing process could be automated with the help of <a href="https://www.jenkins.io/solutions/android/">jenkins plugins for android</a>.

--------------------------------------------
--------------------------------------------

<li><b>An estimate (in hours) for the work</b></li>

### 1. UI XML (2 hours):

As mentioned in an [earlier section](#1-ui-xml), I would implement the UI in standard android XML. Since there's no need to look for 3rd party libraries for implementing this page, creating the whole UI will take me less than 2 hours. 

### 2. View layer (3 hours):

according to info in [View layer](#2-view-layer) section, writing the whole Activity (assuming we use data binding library and reactive programming paradigm for UI state change propagation), will take me no more than 3 hours.

### 3. ViewModel layer (3 hours | 5 hours):

As mentioned [earlier](#3-viewmodel-layer), this part of the project bifurcates into 2 possible options, if we're going to save info locally, it will take me 3 hours to fully implement a ViewModel layer for a Room database. And if we're sending data to a web server (assuming we're using Retrofit and GSON), it will take me 5 hours to fully implement the ViewModel layer.

### 4. Model layer (3 hours):

Again, as mentioned [before](#4-model-layer), implementing a Room database and its DAOs (depending on how many tables we wanna create in the database), will take me something around 3 hours. And also, creating a Retrofit instance and interface for connecting to the web werver will take me no more than 3 hours.

### 5. Test and Debug (10 hours | 20 hours):

I don't have previous knowledge of Espresso or jenkins. And since continuous integration is a more complex topic compared to testing libraries, I will need to spend at least 10 hours for learning Espresso and also 20 hours for learning jenkins and its usage in Android apps. But I can't tell how many hours it will take me to actually implement them in this scenario.

**So, as a conclusion, according to the assessment above, it will take me something around 21 to 33 hours to implement this scenario in an Android app**

--------------------------------------------
--------------------------------------------

<li><b>Any questions that the Product Manager should take back to the stakeholders</b></li>

* what's the age group of users?

* this UI will be used on Android phones/tablets or Android Auto?

* what payment methods are supported in this scenario? credit card? cash? pay pal? interac?

* what exactly should we do with the high-valued data (i.e. the banking information and also the info that
  shows user's car will be at a specific location at a specific time)? save in device or upload it
  to the server?



* if we're using some banking APIs for handling the checkout, should we hand over user's data to
  that API or we need to encrypt it at first and then give it to that API? What design patterns and
  software architectures we should use for performing app's transaction with those APIs?

--------------------------------------------
--------------------------------------------

<li><b>Any additional concerns or questions you have for the Product Manager or Product Designer</b></li>

* What design pattern will be chosen for performing this part of the project?

* If we decided to encrypt users data (no matter whether we save that encrypted data on device or
  upload it to the server), should we perform the encryption by our own or can we use a 3rd party
  library? if it's private key encryption, how should we save and manage our key?

* In this part of the app we need to make a network call and send user-created data to a server on
  cloud, how we're going to manage the network call? The web server we're communicating with is
  RESTFul? the data we send or receive is in JSON format or XML or something else?

* What are the statistics bout the common device specs and network speeds of the current users of
  this app? the majority of our current users are on high end devices with speedy network
  connections or they're usually using less powerful devices with low speed of network connection?

* It's obvious that the code which performs this UI will fire up a network connection and has to
  perform some heavy CPU calculations on handheld devices. What approach to mutithreading will we
  take? we'll go for *Reactive Programming* and work with data flows; or we'll choose any other
  technique for asynchronous programming?

* which parts of the info gathered in this page are to be uploaded to server and which parts can
  be saved on the device?

* if we're saving them in the device, in which form they should be saved? as raw files in the SD
  card? key-value pairs in data store? or some other forms?
</ol>

## PS:

Thanks a lot for this interesting code challenge, I really enjoyed these 3 days I spent on it; and really wish all companies did their coding interviews like this 😁.
I couldn't find any bugs or typos in the project; and it was well documented so I didn't have big problems understanding what sould I do 👏👏👍

I only have one suggestion, it'd have been a lot more convenient to work on this project (at least for me) if it was updated and better maintained. The target SDK version and lots of other libraries used in this project aren't updated and it led to some weird Gradle errors which wasted at least 4 hours of my time to get rid of them. But thanks to Git version control I didn't have to repeat the parts I had already coded. The weirdest Gradle errors I faced while working on this project were about <a href="https://stackoverflow.com/questions/67517720/error-execution-failed-for-task-appkaptgeneratestubsdebugkotlin-when-runnin">:app:kaptGenerateStubsDebugKotlin</a> and <a href="https://stackoverflow.com/questions/69044514/material-1-5-0-alpha03-res-values-v31-values-v31-xml35-94-aapt-error-resour">AAPT: error: resource android:color/system_neutral1_1000 not found.</a>

