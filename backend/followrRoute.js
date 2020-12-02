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

 
const checkActiveGamesInterval = 1500;

followrRouter.post('/follow', async (req, res) => {
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

function checkActiveGames(){
    //iterate through the database and check if each summoner is in game
    //You can call checkSummonerInGame with the summoner name, database should hold name and deviceId;
}

followrRouter.get('/testInGame', (req, res) => {

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
            var query = Follower.findById(name);
            console.log(query)

            if (query != NULL)
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


setInterval(checkActiveGames, checkActiveGamesInterval);


module.exports = followrRouter;