1. Import as a gradle project
2. Start project with the command: gradle bootRun
3. Server starts on port :8081 (to change go to application.properties set "server.port")

FeatureToggler allows to create, enable/disable features (Feature object) globally for all 
or for a single user. See FeatureController, UserFeatureController for available operations 
and unit tests accordingly.
Postman collection included (FeatureToggler.postman_collection.json)
to operate FeatureToggler API and test http requests.
