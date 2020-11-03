const supertest = require('supertest')
const server = require('../server.js')
const request = supertest(server)
//jest.mock('../server.js')


describe('GET /summoner', function(){

     afterEach(()=> {
         server.close()
     });

        test('should return successfully', async done =>{

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

            test('should return successfully', async done =>{

                jest.setTimeout(10000);
    
                const Mockresp = {
                }
    
                request.get = jest.fn().mockResolvedValue(Mockresp)
    
                const responseProfile = await request.get('/profile?name=gunner62')
                expect(responseProfile).toBe(Mockresp);
                expect(request.get).toHaveBeenCalledTimes(1);
                done()
                });
})