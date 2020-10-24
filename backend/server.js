const express = require('express');

const app = express();
app.use(express.json());

const port = 8081;


app.get('/time', (req, res) => {
    res.send(Date());
})

var server = app.listen(port, function () {
    var host = server.address().address
    var port = server.address().port
    console.log("Example app listening at http://%s:%s", host, port)

})


