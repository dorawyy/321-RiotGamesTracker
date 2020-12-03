const express = require('express');
const {spawn} = require('child_process');

const followrRouter = express.Router();

 // import Follower Schema
const Follower = require('./model/FollowerSchema');
const mongoose = require('mongoose');
var uristring =  'mongodb://localhost:27017/RiotDB';
 
// connect to mongoDB

mongoose.connect(uristring,{ useNewUrlParser: true, useUnifiedTopology: true }, function (err, res) {
    if (err) {
    console.log ('ERROR connecting to: ' + uristring + '. ' + err);
    } else {
    console.log ('Successfully connected to: ' + uristring);
    }
  });

mongoose.Promise = global.Promise;

const checkActiveGamesInterval = 150000;

followrRouter.post('/follow', async (req, res) => {
    var dataToSend = "";

    let deviceId = req.body.device
    let name = req.query.name
    
    const q = await Follower.findById(name).then();
    if (q == null) {
        await Follower.create({_id: name,  followers: {deviceId}}).then(function(f) {
            res.send(true)
        });
        await Follower.updateOne(
            { _id: name }, 
            { $push: { followers: deviceId } } ).then();
    } else {
        if(q.followers.includes(deviceId)){
            await Follower.updateOne(
            { _id: name }, 
            { $pull: { followers: deviceId } }
            ).then(function(f){
                res.send(false)
            });
            if(query.followers == undefined) {
                await Follower.deleteOne(
                    { _id: name }).then();
            }
        } else {
            await Follower.updateOne(
            { _id: name }, 
            { $push: { followers: deviceId } }
            ).then(function(f){
                res.send(true)
            });
        }
    }
    checkActiveGames()
})

followrRouter.get('/checkFollowing', async (req, res) => {
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

async function checkActiveGames(){
    //iterate through the database and check if each summoner is in game
    //You can call checkSummonerInGame with the summoner name, database should hold name and deviceId;
    
    //var query = await Follower.find().then(console.log("done"));

    // for await (const doc of Follower.find()) {     
    //   }

    console.log("Check Active Games")

    const cursor = Follower.find().cursor();

    for (let doc = cursor; doc != null; doc = await cursor.next()) {
        if (doc._id != undefined) {
            checkSummonerInGame(doc._id)
        }
    }
    
}

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
    python.on('close', async (code) => {
        console.log(`child process close all stdio with code ${code}`);
        console.log('inside python.on close')
        console.log(dataToSend)
        console.log(typeof(dataToSend))

        //1 if in game, 0 if not in game
        if(dataToSend[0] == "1")
        {
            console.log("Inside dataToSendFalse")
            var query = await Follower.findById(name).then(console.log("done"));
            console.log("Query done")
            var i = 1;
            while (query.followers[i] != null)
            {
                sendNotification("Player in Game", " ..Player is resting... ", query.followers[i]);
                i++;
                
            }
        }
    });

    // console.log(dataToSend);
}

setInterval(checkActiveGames, checkActiveGamesInterval);

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

module.exports = followrRouter;