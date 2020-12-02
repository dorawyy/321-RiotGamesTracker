
const { json } = require("express");

//Takes in  a json object
exports.parseChampionInfo = function parseChampionInfo(obj) {

    // console.log(obj);
    console.log(obj)
    try {
        var json = JSON.parse(obj);
        console.log(json);
    } catch(e) {
        json = obj;
    }
    
    var bestChampion = json.championName['0'];
    var maxScore = 0;
    for (var championIndex in json.championName) {
        var performanceScore = 0;

        console.log(json.championName[championIndex]);
        performanceScore += json.win[championIndex] === true ? 10 : 0;
        // console.log(performanceScore);

        performanceScore += json.KDA[championIndex] * 3;
        // console.log(performanceScore);

        performanceScore += json.damageDealt[championIndex] / 1000;
        // console.log(performanceScore);

        performanceScore += json.gold[championIndex] / 1000;
        console.log(performanceScore);

        if (performanceScore > maxScore)
        {
            maxScore = performanceScore;
            bestChampion = json.championName[championIndex];
        }

    }

    return bestChampion;
}
