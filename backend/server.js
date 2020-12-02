const express = require('express');
const {spawn} = require('child_process');

const recommendChampionLogic = require('./recommendChampionLogic');

 // import Follower Schema
 const Follower = require('./model/FollowerSchema');
 const mongoose = require('mongoose');

// setup express app
const app = express();
// connect to mongoDB
 //mongoose.connect('mongodb://localhost:27017/RiotTrackerDB',{ useNewUrlParser: true, useUnifiedTopology: true });
 var uristring = 
 'mongodb://localhost:27017/RiotDB';
 mongoose.connect(uristring,{ useNewUrlParser: true, useUnifiedTopology: true }).then(() => console.log('Connected MongoDB: '  + uristring)).catch((err) => console.error("Coudn't connect MongoDB....", err));

//  mongoose.connect(uristring,{ useNewUrlParser: true, useUnifiedTopology: true }, function (err, res) {
//     if (err) {
//     console.log ('ERROR connecting to: ' + uristring + '. ' + err);
//     } else {
//     console.log ('Successfully connected to: ' + uristring);
//     }
//   });

mongoose.Promise = global.Promise;

app.use(express.json());

const port = 8081;    //8081
const checkActiveGamesInterval = 1500;

app.get('/summoner', (req, res) => {

    var dataToSend = "";

    let name = req.query.name

    const python = spawn('python', ["./PythonCode/SummonerSearchDemo.py", name, "summoner"]);

    python.on('error', function (data) {
        console.log('Python script failed to spawn');
        dataToSend += ("Error, python script failed to spawn")
    });
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
    
    var dataToSend = "";

    let name = req.query.name

    const python = spawn('python', ['./PythonCode/SummonerSearchDemo.py', name, "profile"]);

    python.on('error', function (data) {
        console.log('Python script failed to spawn');
        dataToSend += ("Error, python script failed to spawn")
    });

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

app.get('/recommend', (req, res) => {

    var dataToSend = "";

    let name = req.query.name
    let searchDepth = req.query.games

    const python = spawn('python', ['./PythonCode/RecommendedChamp.py', name, searchDepth]);

    python.on('error', function (data) {
        console.log('Python script failed to spawn');
        dataToSend += ("Error, python script failed to spawn")
    });

    // spawn new child process to call the python script
    // collect data from script
    python.stdout.on('data', function (data) {
    console.log('Pipe data from python script ...');
        // dataToSend += data.toString();
        console.log(recommendChampionLogic.parseChampionInfo(data.toString()))
        dataToSend += (recommendChampionLogic.parseChampionInfo(data.toString())).toString()
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


app.post('/follow', async (req, res) => {
    var dataToSend = "";

    let name = req.query.name
    let deviceId = req.body.device
    
    const q = await Follower.findById(name).then();
    if (q == null) {
        await Follower.create({_id: name,  followers: {deviceId}}).then(function(f) {
            res.send(true)
        });
    } else {
        if(q.followers.includes(deviceId)){
            await Follower.updateOne(
            { _id: name }, 
            { $pull: { followers: deviceId } }
            ).then(function(f){
                res.send(false)
            });
        } else {
            await Follower.updateOne(
            { _id: name }, 
            { $push: { followers: deviceId } }
            ).then(function(f){
                res.send(true)
            });
        }
    }
})

app.get('/checkFollowing', async (req, res) => {
    var dataToSend = "";

    let name = req.query.name
    let deviceId = req.query.device
    
    const q = await Follower.findById(name).then();
    if(q){
        res.send(q.followers.includes(deviceId));
    } else {
        res.send(false);
    }
})

function checkActiveGames(){
    //iterate through the database and check if each summoner is in game
    //You can call checkSummonerInGame with the summoner name, database should hold name and deviceId;
}

app.get('/testInGame', (req, res) => {

    var dataToSend = "";

    let name = req.query.name
    let searchDepth = req.query.games

    let followResult = checkSummonerInGame(name)

    // console.log("FollowResult", followResult)
    // console.log("result", result)


})

// Run this function on everyone in the database on the set interval
function checkSummonerInGame(name)
{
    var dataToSend = "";
    const python = spawn('python', ['./PythonCode/SummonerSearchDemo.py', name, 'follow']);

    
    python.on('error', function (data) {
        console.log('Python script failed to spawn');
        dataToSend += ("Error, python script failed to spawn")
    });

    // spawn new child process to call the python script
    // collect data from script
    python.stdout.on('data', function (data) {
    console.log('Pipe data from python script ...');
        // dataToSend += data.toString();
        dataToSend += data.toString();
    });
    python.stderr.on('data', function (data) {
        console.log('Python script errored');
            dataToSend += data.toString();
    });
    // in close event we are sure that stream from child process is closed
    python.on('close', (code) => {
        console.log(`child process close all stdio with code ${code}`);
        console.log('inside python.on close')
        console.log(dataToSend)
        console.log(typeof(dataToSend))

        //1 if in game, 0 if not in game
        if(dataToSend[0] == "0")
        {
            console.log("Inside dataToSendFalse")
            var query = Follower.find({SummonerName : name});
            console.log(query)

            if (query.followers != NULL)
            {
                console.log(query.followers[0])
                // Iterate through follower list
                // Send push notifcation to each one in follower list if they are in game
                // using sendNotification
                
            }
        }


    });

    // console.log(dataToSend);
    
}
//Database
//Device ID, Person that they want to follow

setInterval(checkActiveGames, checkActiveGamesInterval);

// function runPython(req, res) {

//     var spawn = require("child_process").spawn;

//     var process = spawn('python', [])
// }


var admin = require("firebase-admin");

var path = require('path');
const { query } = require('express');
var serviceAccount = require( path.resolve( __dirname, "riot-games-tracker-firebase-adminsdk-5r6sl-5416f03302.json" ) );

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://riot-games-tracker.firebaseio.com"
});

function sendNotification(title, body, deviceID) {
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

const server = app.listen(process.env.port||port, function () {
    var host = server.address().address
    var port = server.address().port
    console.log("App listening at http://%s:%s", host, port)

})

module.exports = { server, recommendChampionLogic };