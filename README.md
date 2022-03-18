# Food-Delivery-Management-System
Design and implement a food delivery management system for a catering company. The client can order products from the company’s menu. The system should have three types of users that log in using a username and a password: administrator, regular employee, and client. 
The administrator can:

•	Import the initial set of products which will populate the menu from a .csv file. 

•	Manage the products from the menu: add/delete/modify products and create new products composed of several products (an example of composed product could be named “daily menu 1” composed of a soup, a steak, a garnish, and a dessert). 

•	Generate reports about the performed orders considering the following criteria: 

    o	time interval of the orders – a report should be generated with the orders performed between a given start hour and a given end hour regardless the date. 
    o	the products ordered more than a specified number of times so far. 
    o	the clients that have ordered more than a specified number of times and the value of the order was higher than a specified amount. 
    o	the products ordered within a specified day with the number of times they have been ordered. 

The client can: 

•	Register and use the registered username and password to log in within the system. 

•	View the list of products from the menu. 

•	Search for products based on one or multiple criteria such as keyword (e.g., “soup”), rating, number of calories/proteins/fats/sodium/prices. 

•	Create an order consisting of several products – for each order the date and time will be persisted, and a bill will be generated that will list the ordered products and the total price of the order. 

The employee is notified each time a new order is performed by a client so that it can prepare the delivery of the ordered products.
