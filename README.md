Project overview:

This application has been developed based on https://developers.themoviedb.org API.
Application consists of home page with list of popular movies and movie details page.

Next Steps:
1.	Provide Cache mechanism
2.	Search bar in the home page.

Design:
Basically I have implemented MVVM architecture and as part of that created MovieListFragment and MovieDetailsFragment subscribes for the ViewModels to fetch/observe for data from the network through repository module and update UI.
In order to fetch and load each  subset of data I have used Paging Library and RecyclerView to create list with Movies information fetched from TMDB. 


Libraries used:
1. Architecture components
   > Paging
   > ViewModel
   > LiveData
2. Android Data binding
3. Retrofit2 for REST api 
4. Picasso for image loading
5. Timber for logging
