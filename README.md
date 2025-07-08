Description and Purpose:
PlaneSpot is a plane spotting and discovery app that allows users to find the plane that is closest to them. The app includes a find flight button that a user clicks to find the plane that
appears closest to them. After clicking that button a user will see details about the flight and be allowed to save the flight on a local room database. Users can then go to another tab using
the navigation bar at the bottom and a recycler view will show all of the flights that have been saved. If a user clicks on the label for a flight they will see the details of the flight and
they can delete the flight from the database. The purpose of this app is for curious people and plane spotting enthusiests who want to see the plane that is closest to them and learn more about the world around them.

Installation and setup:
Users who want to use the app will simply install the app and once opened users will be prompted for their location. The app only requires location when actively using it so that option will work when promted. Then the app will be availbile for users to use completely and the user can start by clicking the main button to find the closest plane to them. 

For developers the app requires pulling from the github and making sure gradle dependancies are initialized properly. Then, developers should be able to effectively do their development work on their local system. Developers are encouraged to not excessively use the find plane button when the custom api is not not set to debug mode (where other internal APIs will not be called), and are ecouraged to develop without using it as much as possible. Also, clicking the find plane button repeadedly will result in automatic rate limiting by the custom api. Developers who use an emulator within android studio must set their location in the emulator configuration for the app to work by going to the emulator setting and then the location tab and setting the location from there. If there are still issues navigate to google maps on the emulator and click the target button to center on your location. You will be promted to turn on location services for the device which should fix the location issue for PlaneSpot. If there are further issues contact me with my information at the bottom of the screen.

Feature List:

Flight Finding - Find the flight that is closest to you using one button:

<img width="267" alt="image" src="https://github.com/user-attachments/assets/b1f6b7a1-9adf-40a9-b432-679e46563f29" />

Flight Information Display - Display that shows all the information about the flight nearest to you:

<img width="266" alt="image" src="https://github.com/user-attachments/assets/7385647c-f424-46e4-9101-5e8656eefcf6" />

Saved Flight Recycler View - View for all saved flights:

<img width="266" alt="image" src="https://github.com/user-attachments/assets/a51cdb7c-609e-4e57-9663-e1cde460e2a0" />

Saved Flight View (Also Demonstrating Landscape Compatibility) - View of a saved flight:

<img width="473" alt="image" src="https://github.com/user-attachments/assets/30581b6c-3e44-480f-ac6e-7a4322afc25c" />

Night Mode:

<img width="268" alt="image" src="https://github.com/user-attachments/assets/929438e6-8714-4027-becb-6b5bea5ae238" />

Basic Flight Screen (For flights with less information):

<img width="344" alt="Screenshot 2025-07-08 at 1 58 27 PM" src="https://github.com/user-attachments/assets/f0c23dd4-57fd-4b3e-a82b-1e70d668479d" />



Technology List:

App:
    
    View Models

    View Binding
    
    Room DB
    
    Jetpack Navigation Bar

    Bottom Navigation
    
    Fragments
    
    Moshi
    
    Retrofit
    
    Recycler View
    
    Api Accesss
    
    Intents
    
Api:

    Flask
    
    Redis
    
    SQLite Database
    

Demo Video:


https://github.com/user-attachments/assets/632f26fd-b0da-4dff-8272-b9bc259c4b8c

Working Features:

Finding Flight Closest to You

Saving closest flight details

Recalling previous flight details

Deleting previously captured flights

Night mode


Issues or Limitations:

Rate limiting on the backend with 3rd party apis being expensive

Some UI bugs with landscape mode (has to do with handling top bar in portrait mode)

Small delay in airplane information (backend issue with how up to date api data may or may not be)

Flights get labled as enriched so the more detailed flight info is displayed without all information availible (This is ok to show just shows up as odd sometimes)



Future Development Possibilities:

Backup online database with user logins and simple desktop website

Image Api implementation that shows pictures of planes that you spotted

Taking pictures of plane within app and attaching the closest flight data to it

Background searching for flights and notifications for users who want to know when an aircraft gets within a specific range



Contact info:
ashtonk216@vt.edu
304-620-2474


API Repo (Not much documentation but not many files either)
https://github.com/Ashtonk216/flightapi
Endpoint: ashtonashton.net/get-flight/lat/<latittude>/lon/<longitude> 
Will return JSON as either an enriched flight or basic flight.


Enriched:

{
  "aircraft_name": "Boeing 737 MAX 9",
  "aircraft_type": "B39M",
  "airline": "United Airlines",
  "altitude": 1150,
  "destination": "San Diego Intl",
  "flight": "UAL2710",
  "ground_speed": 189,
  "info_level": "enriched",
  "lat": 38.910599,
  "lon": -77.43779,
  "origin": "Washington Dulles Intl",
  "status": "En Route / On Time"
}


Basic:

{
  "altitude": 650,
  "flight": "RPA3300",
  "ground_speed": 123,
  "info_level": "basic",
  "lat": 38.915901,
  "lon": -77.460083
}


No Flight Found:

{
  "info_level": "not_found",
}

Backend Transponder API provided by ADSBexchange, http://www.ADSBexchange.com and Enriched Flight data provided by FlightAware API, flightaware.com







  




