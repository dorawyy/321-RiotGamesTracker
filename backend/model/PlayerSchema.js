const mongoose = require('mongoose');

// create data schema and model
const Schema = mongoose.Schema;

const PlayerSchema = new Schema(
    {
        participantID: {type:String},
        teamId: {type:Number},
        champion: {type:String},
        summonerSpell1: {type:String},
        summonerSpell2: {type:String},
        win: {type:Number},
        kills: {type:Number},
        deaths: {type:Number},
        assists: {type:Number},
        totalDamageDealt: {type:Number},
        goldEarned: {type:Number},
        champLevel: {type:Number},
        totalMinionsKilled: {type:Number},
        item0: {type:String},
        item1: {type:String}
    }
);

const Player = mongoose.model('Player', PlayerSchema);

module.exports = Player;