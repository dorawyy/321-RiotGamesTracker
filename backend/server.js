const express = require('express');
const {spawn} = require('child_process');

// setup express app
const app = express();

app.use(express.json());

// initialize routes
app.use( require('./followrRoute.js'));
app.use( require('./recommendRoute.js'));

const port = 8081;    //8081

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


const server = app.listen(process.env.port||port, function () {
    var host = server.address().address
    var port = server.address().port
    console.log("App listening at http://%s:%s", host, port)

})

module.exports =  server;