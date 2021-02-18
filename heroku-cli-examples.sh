#stop app instance 
heroku ps:scale web=0 -a ioikka-heroku-client

#start app instance 
heroku ps:scale web=1 -a ioikka-heroku-client

#show info about application
heroku apps:info ioikka-heroku-client
