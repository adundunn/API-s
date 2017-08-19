# API-s
Small programs interacting with API's that can be used in larger applications.

## PwnedPassword 
This is a small Java console application that checks the inputted password against Troy Hunt's haveibeenpwned.com API for pwned passwords to see if the password exists in a breach or not.

## PasswordChecker
JavaScript code snippet that one can add into a node project backend that checks a users entered password upon signup / password change / etc. against Troy Hunt's haveibeenpwned.com API for pwned passwords to see if the password exists in a breach or not. Upon confirming the existence of the breached password, the code will tell the user to choose a different password.

## TimeZoneApp
Using the google maps timezone API, we can take our given UTC time, latitude and longitude and return the corresponding time zone and the corresponding time in that zone. Requires Maven for the Gson dependency to parse the JSON response from the google maps API.
