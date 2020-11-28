const recommendChampionLogic = require('../recommendChampionLogic');

const recommend_empty = {"win":{},
                        "KDA":{},
                        "damageDealt":{},
                        "gold":{},
                        "championName":{}}
                        
const recommend_depth1 = {"win":{"0":true},
                           "KDA":{"0":3.4},
                           "damageDealt":{"0":333},
                           "gold":{"0":123},
                           "championName":{"0":"Shen"}}

const recommend_depth20 = {"win":{"0":true,"1":false,"2":true,"3":false,"4":false,"5":false,"6":true,"7":true,"8":false,"9":false,"10":true,"11":true,"12":false,"13":true,"14":false,"15":false,"16":true,"17":true,"18":true,"19":true},
                           "KDA":{"0":6.0,"1":1.75,"2":5.25,"3":1.6666666667,"4":2.0,"5":1.2857142857,"6":3.5,"7":3.2857142857,"8":1.3076923077,"9":1.4166666667,"10":2.75,"11":1.7142857143,"12":2.6,"13":20.0,"14":1.0909090909,"15":2.4,"16":6.2,"17":2.5,"18":10.0,"19":2.4},
                           "damageDealt":{"0":39236,"1":13523,"2":18674,"3":9827,"4":22460,"5":5755,"6":10086,"7":20197,"8":14418,"9":19300,"10":25986,"11":7591,"12":19749,"13":16995,"14":19585,"15":31708,"16":22283,"17":40388,"18":20215,"19":8648},
                           "gold":{"0":16907,"1":6170,"2":11620,"3":8855,"4":11198,"5":6486,"6":7303,"7":15223,"8":11886,"9":11572,"10":16782,"11":9015,"12":8589,"13":10048,"14":12087,"15":12867,"16":11487,"17":21038,"18":8438,"19":6861},
                           "championName":{"0":"Pyke","1":"Akali","2":"Pyke","3":"Shen","4":"Seraphine","5":"Bard","6":"Pantheon","7":"Pyke","8":"Pyke","9":"Pyke","10":"Pyke","11":"Pyke","12":"Nami","13":"Pyke","14":"Irelia","15":"Ornn","16":"Morgana","17":"Pyke","18":"Brand","19":"Pyke"}}
const recommend_timeout = {}

describe('Complex Logic', function(){

        test('empty data', () =>{
            jest.setTimeout(10000);
           expect(recommendChampionLogic.parseChampionInfo(recommend_empty)).toBe(" ")
           });

        test('depth1 data', () =>{
            jest.setTimeout(10000);
           expect(recommendChampionLogic.parseChampionInfo(recommend_depth1)).toBe("Shen")
           });


        test('depth20 data', () =>{
            jest.setTimeout(10000);
            expect(recommendChampionLogic.parseChampionInfo(recommend_depth20)).toBe("Pyke")
            });

        test('timeout', () =>{
                jest.setTimeout(130000); // > 2 minutes
                expect(recommendChampionLogic.parseChampionInfo(recommend_timeout)).toBe(" ")
            });

})