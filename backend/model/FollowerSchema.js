const mongoose = require('mongoose');

// create data schema and model
const Schema = mongoose.Schema;

const FollowerSchema = new Schema(
    {
        _id: {type:String},
        followers: { type : Array }
    }
);

const Follower = mongoose.model('Follower', FollowerSchema);

module.exports = Follower;