# restaurants-project

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

-------------------------------------------------------------------------
The task is:

Build a voting system for deciding where to have lunch.

    2 types of users: admin and regular users
    Admin can input a restaurant and its lunch menu of the day (2-5 items usually, just a dish name and price)
    Menu changes each day (admins do the updates)
    Users can vote on which restaurant they want to have lunch at
    Only one vote counted per user
    If user votes again the same day:
        If it is before 11:00 we asume that he changed his mind.
        If it is after 11:00 then it is too late, vote can't be changed
-------------------------------------------------------------------------

To initialize database you need to start FillingDatabase class from sql package of this project and add a scheme 'restaurants'.
After that your MYSQL will be ready to work.
Then it's important to login by "admin":"admin" with a Basic Auth and open '/api/v1/roles' POST to initialize roles.
You can also register new users using '/api/v1/users POST'. Username and password that you fill in a Basic Auth form
will be save into database. Any of them will automatically have a role 'user'. There is the only one user with a role 'admin'
using username: "admin" and password: "admin".

There is also '/api/v1/menu' POST which is only available for admins. You must send a json like this
{
	"dish": "burger",
	"price": 3
}

To change existed menu you must use '/api/v1/menu/{id}' PUT where {id} is the id of the menu you want to change and send a json like this
{
	"dish": "milk",
	"price": 5.1
}
Only for admins.

The '/api/v1/restaurants' POST will add a new restaurant to the database. This is only available for admins. You must send a json like this
{
	"name": "McDonalds"
}
Only for admins.

To change existed restaurant or to add a menu to a restaurant you must use '/api/v1/restaurants/{id}' PUT
where {id} is the id of the restaurant you want to change or add menu and send a json like this
{
	"name": "BurgerKing",
	"menu": [
		{
			"id": 1
		},
		{
			"id": 2
		},
		{
			"id": 3
		}]
}
where each menu has to be added by id. Only for admins.

Customers use '/api/v1/vote/{id}' PUT being logged in, where {id} is the id of the restaurant,
in order to vote for a restaurant that the customer wants to visit.