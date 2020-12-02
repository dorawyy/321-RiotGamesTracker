const express = require('express');
const {spawn} = require('child_process');

const recommendChampionLogic = require('./recommendChampionLogic');

const recommendRouter = express.Router();

recommendRouter.get('/recommend', (req, res) => {

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


module.exports = recommendRouter;