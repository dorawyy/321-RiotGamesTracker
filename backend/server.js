const express = require('express');
const {spawn} = require('child_process');

// import Player Schema
const Player = require('./model/PlayerSchema');
const mongoose = require('mongoose');

// setup express app
const app = express();
// connect to mongoDB
mongoose.connect('mongodb://localhost:27017/RiotTrackerDB',{ useNewUrlParser: true, useUnifiedTopology: true });
mongoose.Promise = global.Promise;

app.use(express.json());

const port = 8081;

app.get('/summoner', (req, res) => {
    
    // Save Player Data in DB
    Player.create(res);

    var dataToSend = "";

    let name = req.query.name

    const python = spawn('python', ['PythonCode\\SummonerSearchDemo.py', name, "summoner"]);

    // spawn new child process to call the python script
    // collect data from script
    python.stdout.on('data', function (data) {
    console.log('Pipe data from python script ...');
        dataToSend += data.toString();
    });
    python.stderr.on('data', function (data) {
        console.log('Python script errored');
            dataToSend += data.toString();
    });
    // in close event we are sure that stream from child process is closed
    python.on('close', (code) => {
        console.log(`child process close all stdio with code ${code}`);
        // send data to browser
        res.send(dataToSend)
    });

})

app.get('/profile', (req, res) => {
    
    // Save Player Data in DB
    Player.create(res);

    var dataToSend = "";

    let name = req.query.name

    const python = spawn('python', ['PythonCode\\SummonerSearchDemo.py', name, "profile"]);

    // spawn new child process to call the python script
    // collect data from script
    python.stdout.on('data', function (data) {
    console.log('Pipe data from python script ...');
        dataToSend += data.toString();
    });
    python.stderr.on('data', function (data) {
        console.log('Python script errored');
            dataToSend += data.toString();
    });
    // in close event we are sure that stream from child process is closed
    python.on('close', (code) => {
        console.log(`child process close all stdio with code ${code}`);
        // send data to browser
        res.send(dataToSend)
    });

})

function runPython(req, res) {

    var spawn = require("child_process").spawn;

    var process = spawn('python', [])
}

var server = app.listen(process.env.port||port, function () {
    var host = server.address().address
    var port = server.address().port
    console.log("App listening at http://%s:%s", host, port)

})

var admin = require("firebase-admin");

var path = require('path');
var serviceAccount = require( path.resolve( __dirname, "riot-games-tracker-firebase-adminsdk-5r6sl-5416f03302.json" ) );

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://riot-games-tracker.firebaseio.com"
});

function sendNotification(title, body) {
    var message = {
      notification:{
          title:title,
          body:body
        },
      topic: "notifications"
    };

    // Send a message to the device corresponding to the provided
    // registration token.
    admin.messaging().send(message)
      .then((response) => {
        // Response is a message ID string.
        console.log('Successfully sent message:', response);
      })
      .catch((error) => {
        console.log('Error sending message:', error);
      });
}

module.exports = app;