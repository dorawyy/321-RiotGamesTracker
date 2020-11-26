const supertest = require('supertest')
const s = require('../server.js')

const request = supertest(s.server)

jest.mock('../recommendChampionLogic');

describe('GET/', function(){

     afterEach(()=> {
         s.server.close()
     });

        test('Summoner', async done =>{

            jest.setTimeout(10000);

            const Mockresp = {
            participantID: 12,
            teamId: 2,
            champion: "naruto",
            summonerSpell1: "run",
            summonerSpell2: "sage",
            win: 2,
            kills: 2,
            deaths: 1,
            assists: 1,
            totalDamageDealt: 433,
            goldEarned: 1,
            champLevel: 12,
            totalMinionsKilled: 2,
            item0: "katana",
            item1: "shuriken"
            }

            request.get = jest.fn().mockResolvedValue(Mockresp)

            const response = await request.get('/summoner?name=gunner62')
            expect(response).toBe(Mockresp);
            expect(request.get).toHaveBeenCalledTimes(1);
            done()
            });

        test('Profile', async done =>{

                jest.setTimeout(10000);
    
                const Mockresp = {
                    status:404
                }
                request.get = jest.fn().mockResolvedValue(Mockresp)
    
                const responseProfile = await request.get('/profile?name=gunner62')
                expect(responseProfile).toBe(Mockresp);
                expect(responseProfile.status).toBe(404);
                expect(request.get).toHaveBeenCalledTimes(1);
                done()
                });

       test('Recommend Champ', async done =>{

           jest.setTimeout(10000);
           expect.assertions(1);

           const Mockresp = "brawler";
           
           const response = await request.get('/recommend?name=gunner62&games=20')
           // console.log(response);
           // expect(response).toBe(Mockresp);
           expect(response.status).toBe(200);
           done()
           });

})