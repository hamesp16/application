Infrastructure repository: https://github.com/anonymelev/infrastructure

Push this repository after pushing the infrastructure repository.

## Environment key variables
* HEROKU_API_KEY
* LOGZ_URL
* LOGZ_TOKEN
* DOCKER_USERNAME
* DOCKER_PASSWORD

## Local 
1. Set up environment variables: "export key=value"
2. Run the application


## Travis
1. For HEROKU_API_KEY run: travis encrypt $(heroku auth:token) --add deploy.api_key
2. For the rest of the environment variables for travis-ci.org: "travis encrypt key=value --add"
3. Push the code to a repository that is linked with Travis, but only after having run the infrastructure project

