
let fakeData = "brawler";

const recommendChampionLogic = jest.createMockFromModule('../recommendChampionLogic.js');

//Takes in  a json object
function parseChampionInfo(obj) {

    // console.log(obj);
    console.log(obj)
    console.log(fakeData);

    return fakeData;
}

recommendChampionLogic.parseChampionInfo = parseChampionInfo;

module.exports = recommendChampionLogic;
