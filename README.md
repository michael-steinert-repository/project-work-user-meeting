## Setup

### Application Server:
* The compiled Microservice *User-Meeting* already contains the Application Server and can be executed directly inside a Container that has Java version 11 installed.

* The running Microservices is available at the URL http://localhost:8082.

### Database:

* The Microservice *User-Meeting* needs its own Database. Therefore an Instance of the PostgreSQL Database *user_meeting* is set up on a Container.

### API of Microservices

http://localhost:8082/user-meeting/

| HTTP-Method | URL | Result |
| --- | --- | --- |
| POST | /register-meeting | Creates a new Meeting and returns the Meeting |
| GET | /unregister-meeting/{meetingName} | Deletes a existing Meeting and returns Result |
| POST | /register-to-meeting/{meetingName} | Registers an User to a Meeting with MeetingName and returns the Meeting |
| POST | /unregister-from-meeting/{meetingName} | Unregisters an User from a Meeting with MeetingName and returns the Meeting |
| GET | /find-all-user-meetings | Returns all existing Meetings |
| GET | /find-user-by-username/{username} | Returns an User with existing Username |
___

## Einrichtung
### Anwendungsserver:

* Der kompilierte Microservice *User-Meeting* enthält bereist den Anwendungsserver und kann direkt innerhalb eines Containers ausgeführt werden, welcher die Java-Version 11 installiert hat.

* Der laufende Microservices ist erreichbar unter der URL http://localhost:8082.

### Datenbank:

* Der Microservice *User-Meeting* benötigt seine eigene Datenbank. Dafür wird auf einem Container eine Instanz der Datenbank PostgreSQL *user_meeting* eingerichtet.

### Schnittstelle des Microservices

http://localhost:8082/user-meeting/


| HTTP-Methode | URL | Ergebnis |
| --- | --- | --- |
| POST | /register-meeting | Erstellt ein neues Meeting and liefert das Meeting |
| GET | /unregister-meeting/{meetingName} | Löscht ein existierendes Meeting und liefert das Ergebnis |
| POST | /register-to-meeting/{meetingName} | Registriert einen Nutzer zu einem Meeting und liefert das Meeting |
| POST | /unregister-from-meeting/{meetingName} | Unregistriert einen Nutzer zu einem Meeting und liefert das Meeting |
| GET | /fetch-all-user-meetings | Liefert alle existierenden Meetings |
| GET | /fetch-user-by-username/{username} | Liefert einen Nutzer mit extierenden Usernamen |
