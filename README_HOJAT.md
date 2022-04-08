Hojat Ghasemi
hojat72elect@gmail.com

## chosen approach: 
* MVVM
* RxJava
* DataBindingUtil


## The approach to architecture:

The app has already implemented the "Model" and since there are not too many user stories, it will be a fairly small app
which can be laid out with a Model-View-ViewModel architecture.<br/><br/>Furthermore, since the app is in the initial development stages and doesn't have many features, I have chosen the <a href="https://proandroiddev.com/package-by-type-by-layer-by-feature-vs-package-by-layered-feature-e59921a4dffa">package by layer</a> approach; `model` layer is already in a separate package so I put the `ViewModel` layer in its own package as well.

--------------------------------------------

## ViewModel:

Reads data from `Model` and delivers them to `View`. None of the definitions in this layer should have any direct references to the UI or XML layout. Remember that in a perfect implementation of MVVM architecture, your `ViewModel` layer is so portable that you can just copy/paste it into another project and it will be usable with minor changes.

###  ViewModel/SpotHeroViewModelFactory.kt

Inside the `ViewModel` layer, I firstly implemented the `SpotHeroViewModelFactory` class which is nothing more than a simple builder for our `ViewModel` and extends the `androidx.lifecycle.ViewModelProvider` class. It simply makes sure that the class that intends to communicate with `ViewModel` is compatible with `ViewModel` class (more info found <a href="https://developer.android.com/reference/java/lang/Class#isAssignableFrom(java.lang.Class%3C?%3E)">here</a>). It also injects an instance of our `SpotHeroApi` to the created `ViewModel` (to be used for communication between `ViewModel` and `Model`).

###  ViewModel/SpotHeroViewModel.kt

It's the main part of the `ViewModel` layer and extends the `androidx.lifecycle.ViewModel` class. The needed dependedncy to `SpotHeroApi` has been injected to this class via a parameter in the constructor. By using its reference to the `SpotHeroApi`, it gets an observable object which is a `Single<List<Spot>>` and our Activities or Fragments in the View layer can define an instance of this ViewModel class to get this list of Spots.


--------------------------------------------
## View:

This layer contains all the GUI of the app and Activities/Fragments. User interactions and user generated data is captured in this layer and sent back to `ViewModel` in order to be processed or saved into Model layer. <br/><br/> In a perfect implementation of MVVM, definitions inside this layer are as lightweight as possible, and we try to never put heavy logic of the app in this layer. 

I'm interested in scalability in apps so I'm assuming that in the future development iterations, the UI will get more
complex and we will need a `Binder` between `View` and `ViewModel` layers in order to receive events from GUI and deliever them to
ViewModel; and also deliever needed data from ViewModel to View.

Due to the lack of time, I can't implement a `Binder` part of the architecture right now, but I'm going to define an "
Event"
class which is the object containing user info. when the app size got bigger, we will try to implement a "Binder" in
order to make our fragments/activities as light-weight as possible and also keep our ViewModel as portable as possible.

And also, "DataBindingUtil" was used to make the connection between xml layouts and View files easier.



--------------------------------------------

###  MainActivity.kt


###  MyRecyclerViewAdapter.kt

