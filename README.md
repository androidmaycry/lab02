# lab02

The purpose of this lab is to create a mobile application that allows the restarateur owner to advertise the daily menu offer.
The APP will allow the user to:

  1. Create and mantain the the restarateur profile
  2. Publish or edit the daily offer
  3. Show/manage the list of the current reservations
 
# Functionality

The APP allows to the user to manage his profile (as described in lab01). Moreover, it is possible to manage the daily offer inserting name, description and photo related to a dish. The APP allows also to see the active reservation done by the users and manage it: the restarateur can accept or decline it.

# Implementation

The app implenets differents **fragment**: home, daily offer, profile and reservations management.

Inside the **daily offer** and **resevation** fragment it has been implemented a recycle view that will allow a better management of the data.

For the **profile** fragment just see the [lab01](https://github.com/androidmaycry/lab01).

The **home** fragment is just a welcome back for the user, more features will follow.

All data are saved through Shared preferences and the reservation are actually simulated. In [lab03](https://github.com/androidmaycry/lab03) Firebase will be implemented and simulation will be deprecated.
