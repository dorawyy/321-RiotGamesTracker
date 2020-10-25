const express = require('express');
const {spawn} = require('child_process');



const app = express();

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

    
    // send data to browser
})



app.get('/time', (req, res) => {
    res.send(Date());
})



function runPython(req, res) {

    var spawn = require("child_process").spawn;

    var process = spawn('python', [])
}

var server = app.listen(port, function () {
    var host = server.address().address
    var port = server.address().port
    console.log("Example app listening at http://%s:%s", host, port)

})


