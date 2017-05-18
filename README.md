# 3. Pratkikumsaufgabe Software-Architektur Sommer 2017 #
 
Maximilian Lipp, Florian Tobusch<br /><br />
Das ShareIt-System soll durch eine Authentifizierung mittels OAuth erweitert werden. In dieser Aufgabe sollen Sie einen minimalen Authorisation Server (AS) anlegen. Weitere Informationen zur Authentifizierung mittels OAuth können Sie folgendem Blogbeitrag entnehmen:<br />http://nordicapis.com/how-to-control-user-identity-within-microservices/

###### REST-API
| URI-Template      | Verb          | Wirkung  |
| -------------     |-------------  | ------   |
| /users/login/               | POST          | Erstellt neuen Token<br />Mögliche Fehler: User nicht vorhanden<br />Möglicher Fehler: Passwort und Username nicht korrekt|
| /users/login/{token}         | POST          | Prüfung ob Token korrekt<br />Möglicher Fehler: Token wurde nicht ausgestellt<br />Möglicher Fehler: TTL des Token bereits abgelaufen|
