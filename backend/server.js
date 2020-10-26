const express = require('express');
const {spawn} = require('child_process');
const mongoose = require('mongoose');

// create data schema and model
const Schema = mongoose.Schema;
const PlayerSchema = new Schema(
    {
        participantID: {type:String},
        teamId: {type:Number},
        champion: {type:String},
        summonerSpell1: {type:String},
        summonerSpell2: {type:String},
        win: {type:Number},
        kills: {type:Number},
        deaths: {type:Number},
        assists: {type:Number},
        totalDamageDealt: {type:Number},
        goldEarned: {type:Number},
        champLevel: {type:Number},
        totalMinionsKilled: {type:Number},
        item0: {type:String},
        item1: {type:String}
    }
);

const Player = mongoose.model('Players', PlayerSchema);

// setup express app
const app = express();
// connect to mongoDB
mongoose.connect('mongodb://localhost:27017/RiotTrackerDB',{ useNewUrlParser: true, useUnifiedTopology: true });
mongoose.Promise = global.Promise;

app.use(express.json());

const port = 8081;

// Champion Name -> req -> 

app.get('/input', (req, res) => {
    let page = req.query.page;

    res.send(page)

})

app.get('/hello', (req, res) => {
 
    var dataToSend;
    data = [1,2,3,4,5,6,7,8,9],
    dataString = '';
    const python = spawn('python', ['PythonCode\\hello.py']);


    // spawn new child process to call the python script
    // collect data from script
    python.stdout.on('data', function (data) {
    console.log('Pipe data from python script ...');
        dataString += data.toString();
    });
    // in close event we are sure that stream from child process is closed
    python.on('close', (code) => {
        console.log(`child process close all stdio with code ${code}`);
        // send data to browser
        res.send(dataString)
    });
    
    // send data to browser
})

//Working send to python with parameters

app.get('/param', (req, res) => {
 
    // Save Player Data in DB
    Player.create(res);

    var dataToSend;
    
    const python = spawn('python', ['PythonCode\\parameters.py', 'hello', 'hello1']);

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

    // send data to browser
})

app.get('/summoner', (req, res) => {
    
    // Save Player Data in DB
    Player.create(res);

    var dataToSend = "";

    let name = req.query.name

    const python = spawn('python', ['PythonCode\\SummonerSearchDemo.py', name]);

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



app.get('/time', (req, res) => {
    res.send(Date());
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
var serviceAccount = require( path.resolve( __dirname, "riot-games-tracker-firebase-adminsdk-5r6sl-acc118704e.json" ) );

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://riot-games-tracker.firebaseio.com"
});

function sendNotification(title, body) {
    console.log('HERE')
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