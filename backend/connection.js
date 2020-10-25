var mongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/RiotTrackerDB";


mongoClient.connect(url,{ useUnifiedTopology: true } , function(err, db) {
  if (err) throw err;
  console.log("Connected!");

  var dbo = db.db("RiotTrackerDB");

  //dbo.createCollection("Players", function(err, res) {
    //if (err) throw err;
    //console.log("Collection created!");

    var myobj = [
        { name: 'John', address: 'Highway 71'},
        { name: 'Peter', address: 'Lowstreet 4'},
        { name: 'Amy', address: 'Apple st 652'},
        { name: 'Hannah', address: 'Mountain 21'},
        { name: 'Michael', address: 'Valley 345'},
        { name: 'Sandy', address: 'Ocean blvd 2'},
        { name: 'Betty', address: 'Green Grass 1'},
        { name: 'Richard', address: 'Sky st 331'},
        { name: 'Susan', address: 'One way 98'},
        { name: 'Vicky', address: 'Yellow Garden 2'},
        { name: 'Ben', address: 'Park Lane 38'},
        { name: 'William', address: 'Central st 954'},
        { name: 'Chuck', address: 'Main Road 989'},
        { name: 'Viola', address: 'Sideway 1633'}
      ];
  dbo.collection("Players").insertMany(myobj, function(err, res) {
    if (err) throw err;
    console.log("documents inserted");


  db.close();
    });
    //});
});

