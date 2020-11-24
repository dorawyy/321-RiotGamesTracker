const mongoose = require('mongoose');

// create data schema and model
const Schema = mongoose.Schema;

const FollowerSchema = new Schema(
    {
        SummonerName: {type:String},
        followers: { type : Array , "default" : [] }
    }
);

const Follower = mongoose.model('Follower', FollowerSchema);

module.exports = Follower;